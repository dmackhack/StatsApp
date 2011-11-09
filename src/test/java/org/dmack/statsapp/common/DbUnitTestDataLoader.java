package org.dmack.statsapp.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeTable;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class DbUnitTestDataLoader implements TestDataLoader, InitializingBean, ApplicationContextAware
{
    public static final String TESTDATA = "testdata";

    private static final String BASE_SETUP = "BaseSetup.xml";

    private static final String EXPECTED_SUFFIX = "_EXPECTED.xml";

    private static final Log LOG = LogFactory.getLog(TestDataLoader.class);

    private static final String ORACLE_DIALECT = "org.hibernate.dialect.Oracle10gDialect";

    private static final String HSQL_DIALECT = "org.hibernate.dialect.HSQLDialect";

    private ApplicationContext applicationContext;

    /**
     * Populates the database with the data for the given test. TODO Change this
     * so the loader is instantiated with the bundle/test.
     */
    public void load(String bundle, String test) throws DataLoadException
    {
        IDatabaseConnection connection = null;

        try
        {
            connection = getDbConnection(true);

            LOG.info("Cleaning database [preload]..");
            truncateTables(new ClassPathResource("testdata/truncate-all.xml").getFile(), connection);

            // initialise sequences
            connection = getDbConnection(true);
            initSequences(connection, true);

            if (LOG.isInfoEnabled())
            {
                LOG.info("Loading test data for test: " + bundle + "." + test);
            }

            // load test data
            refreshFromResource(TESTDATA + "/" + BASE_SETUP, connection);
            // refreshFromResource(TESTDATA + "/" + bundle + "/" + BASE_SETUP,
            // connection);
            // refreshFromResource(TESTDATA + "/" + bundle + "/" + test
            // + SETUP_SUFFIX, connection);

        }
        catch (Exception e)
        {
            LOG.info("Unable to load test data", e);
            throw new DataLoadException("Unable to load test data", e);
        }
        finally
        {
            try
            {
                if (connection != null)
                {
                    connection.close();
                }
            }
            catch (SQLException e)
            {
                throw new DataLoadException(e);
            }
        }
    }

    private static void refreshFromResource(String path, IDatabaseConnection connection) throws DataLoadException
    {
        try
        {
            Resource springResource = new ClassPathResource(path);
            if (springResource.exists())
            {
                if (LOG.isInfoEnabled())
                {
                    LOG.info("Loading test data from '" + springResource.getFile().getAbsolutePath());
                }

                FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
                builder.setColumnSensing(true);
                IDataSet setupDataset = builder.build(springResource.getFile());

                DatabaseOperation.REFRESH.execute(connection, setupDataset);
            }
            else
            {
                if (LOG.isInfoEnabled())
                {
                    LOG.info("Not loading test data: '" + springResource.getFilename() + "' not found.");
                }
            }
        }
        catch (Exception e)
        {
            String msg = "Unable to load test data";
            if (LOG.isErrorEnabled())
            {
                LOG.error(msg, e);
            }
            throw new DataLoadException(msg, e);
        }
    }

    public void initSequences() throws SQLException, DatabaseUnitException
    {
        IDatabaseConnection connection = getDbConnection();
        try
        {
            initSequences(connection);
        }
        finally
        {
            connection.close();
        }
    }

    protected void initSequences(IDatabaseConnection connection, boolean override) throws DataSetException, SQLException
    {
        initSequences(connection);
    }

    private void initSequences(IDatabaseConnection connection) throws DataSetException, SQLException
    {
        // only initialise sequences if create-drop is specified
        // TODO: FIX THIS to read in properties properly
        // if (!StringUtils.equals("create-drop",
        // ApplicationContextHolder.getProperty(
        // "hibernate.hbm2ddl.auto")))
        // {
        // return;
        // }

        String selectSQL = "select sequence_name from ";
        String alterSQL = "alter sequence {0} ";
        String dialect = PropertiesHolder.getProperty("hibernate.dialect");
        String schema = PropertiesHolder.getProperty("hibernate.default_schema");
        if (StringUtils.equals(ORACLE_DIALECT, dialect))
        {
            selectSQL += "all_sequences where sequence_owner='" + schema + "'";
            alterSQL += "increment by 1000";
        }
        else if (StringUtils.equals(HSQL_DIALECT, dialect))
        {
            selectSQL += "information_schema.system_sequences where sequence_schema='PUBLIC'";
            alterSQL += "restart with 1000";
        }
        else
        {
            throw new UnsupportedOperationException("Dialect " + String.valueOf(dialect) + " not supported");
        }

        ITable sequenceNames = connection.createQueryTable("SEQUENCES", selectSQL);
        for (int i = 0; i < sequenceNames.getRowCount(); i++)
        {
            String name = (String) sequenceNames.getValue(i, "sequence_name");
            Statement stmt = connection.getConnection().createStatement();
            try
            {
                stmt.execute(MessageFormat.format(alterSQL, name));
            }
            finally
            {
                stmt.close();
            }
        }
    }

    public IDatabaseConnection getDbConnection(boolean override) throws SQLException, DatabaseUnitException
    {
        return getDbConnection();
    }

    public IDatabaseConnection getDbConnection() throws DatabaseUnitException, SQLException
    {
        IDatabaseConnection connection;

        String schema = getDefaultSchema();

        DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
        Connection jdbcConnection = dataSource.getConnection();

        if (StringUtils.isNotEmpty(schema))
        {
            LOG.info("Connecting to schema: " + schema);
            connection = new DatabaseConnection(jdbcConnection, schema);
        }
        else
        {
            LOG.info("Not using a schema to create Connection.");
            connection = new DatabaseConnection(jdbcConnection);
        }

        DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.FEATURE_SKIP_ORACLE_RECYCLEBIN_TABLES, true);
        String dialect = PropertiesHolder.getProperty("hibernate.dialect");
        if (StringUtils.equals(HSQL_DIALECT, dialect))
        {
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        }
        else if (StringUtils.equals(ORACLE_DIALECT, dialect))
        {
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Oracle10DataTypeFactory());
        }
        return connection;
    }

    private String getDefaultSchema()
    {
        String schema = "";
        schema = PropertiesHolder.getProperty("hibernate.default_schema");

        if (StringUtils.isEmpty(schema))
        {
            LOG.warn("Using empty schema");
        }

        return schema;
    }

    /**
     * Check the expected results against what's in the DB, then tear down the
     * test. The transaction will roll back at the end of the unit test, so
     * there is no need to explicitly clear out the test data.
     * 
     * @param bundle
     * @param test
     * @throws DataLoadException
     */
    public void teardown(String bundle, String test) throws DataLoadException
    {
        Resource expectedResource = getResource(bundle, test, EXPECTED_SUFFIX);
        if (LOG.isInfoEnabled())
        {
            LOG.info("Loading expected results data from resource: " + expectedResource);
        }

        IDataSet actualSet = null;
        IDataSet expectedSet = null;
        IDataSet baselineSet = null;
        IDatabaseConnection dbConnection = null;
        try
        {
            try
            {
                dbConnection = getDbConnection(true);
                if (expectedResource != null && expectedResource.exists())
                {
                    actualSet = dbConnection.createDataSet();
                    expectedSet = new FlatXmlDataSet(expectedResource.getInputStream());
                    Resource baselineResource = getResource(bundle, BASE_SETUP);
                    if (baselineResource != null && baselineResource.exists())
                    {
                        baselineSet = new FlatXmlDataSet(baselineResource.getInputStream());
                    }
                }
            }
            catch (Exception e)
            {
                String msg = "Unable to load data for test from " + expectedResource.getFilename();
                throw new DataLoadException(msg, e);
            }

            if (expectedSet == null && actualSet == null)
            {
                if (LOG.isInfoEnabled())
                {
                    LOG.info("Both actual and expected datasets are null");
                }
                return;
            }
            else if (expectedSet == null && actualSet != null)
            {
                throw new DataLoadException("Actual dataset is unexpectedly non-null");
            }
            else if (actualSet == null && expectedSet != null)
            {
                throw new DataLoadException("Actual dataset is unexpectedly null");
            }

            try
            {
                String[] expectedTables = expectedSet.getTableNames();
                if (expectedTables != null && expectedTables.length > 0)
                {
                    if (LOG.isDebugEnabled())
                    {
                        LOG.debug("Comparing database contents to expected state.");
                    }
                    for (String table : expectedTables)
                    {
                        ITable expectedTable = expectedSet.getTable(table);
                        CompositeTable composedExpected = null;
                        if (baselineSet != null)
                        {
                            ITable baselineTable = baselineSet.getTable(table);
                            composedExpected = new CompositeTable(expectedTable.getTableMetaData(), new ITable[] { baselineTable, expectedTable });
                        }
                        ITable actualTable = new CompositeTable(expectedTable.getTableMetaData(), actualSet.getTable(table));
                        ITable sortedActual = null;
                        ITable sortedExpected = null;

                        if (composedExpected == null)
                        {
                            if (LOG.isDebugEnabled())
                            {
                                LOG.debug("Asserting expected results against actual results for table '" + table + "'.");
                            }
                            // Sort both composedExpected and actualTable by
                            // column
                            // order from expectedTable
                            sortedExpected = new SortedTable(expectedTable, expectedTable.getTableMetaData().getColumns());
                            sortedActual = new SortedTable(actualTable, expectedTable.getTableMetaData().getColumns());
                        }
                        else
                        {
                            if (LOG.isDebugEnabled())
                            {
                                StringBuilder b = new StringBuilder();
                                b.append("Base data was inserted for table '").append(table);
                                b.append("'; merging base data with expected additional data.");
                                LOG.debug(b.toString());
                                b.setLength(0);
                                b.append("Comparing a total of ").append(composedExpected.getRowCount()).append(" rows");
                                LOG.debug(b.toString());
                            }
                            // Sort both composedExpected and actualTable by
                            // column
                            // order from composedExpected
                            sortedExpected = new SortedTable(composedExpected, composedExpected.getTableMetaData().getColumns());
                            sortedActual = new SortedTable(actualTable, composedExpected.getTableMetaData().getColumns());
                        }
                        Assertion.assertEquals(sortedExpected, sortedActual);
                    }
                }
            }
            catch (Exception e)
            {
                throw new DataLoadException("Unable to compare actual and expected rows", e);
            }
        }
        finally
        {
            if (dbConnection != null)
            {
                try
                {
                    dbConnection.close();
                }
                catch (SQLException e)
                {
                    throw new DataLoadException("Could not close connection", e);
                }

            }
        }

    }

    private Resource getResource(String bundle, String resource)
    {
        return new ClassPathResource(TESTDATA + "/" + bundle + "/" + resource);
    }

    /**
     * @param bundle
     * @param test
     */
    // TODO Clean this up!
    private Resource getResource(String bundle, String test, String suffix)
    {
        return new ClassPathResource(TESTDATA + "/" + bundle + "/" + test + suffix);
    }

    public void afterPropertiesSet() throws Exception
    {

    }

    /**
     * Inserts the data specified in the input xml file after cleaning all
     * tables represented in the input file
     * 
     * @param sourceXmlFile
     * @throws SQLException
     * @throws DatabaseUnitException
     * @throws IOException
     */
    public void truncateTables(File sourceXmlFile) throws SQLException, DatabaseUnitException, IOException
    {
        IDatabaseConnection connection = getDbConnection();
        truncateTables(sourceXmlFile, connection);
    }

    /**
     * Inserts the data specified in the input xml file after cleaning all
     * tables represented in the input file
     * 
     * @param sourceXmlFile
     * @throws SQLException
     * @throws DatabaseUnitException
     * @throws IOException
     */
    public void truncateTables(File sourceXmlFile, IDatabaseConnection connection) throws SQLException, DatabaseUnitException, IOException
    {
        FileInputStream fileInputStream = new FileInputStream(sourceXmlFile);
        ;
        try
        {
            LOG.debug("Deleting all records using source file: " + sourceXmlFile);

            FlatXmlDataSet flatXmlDataSet = new FlatXmlDataSet(fileInputStream);
            DatabaseOperation.DELETE_ALL.execute(connection, flatXmlDataSet);
        }
        finally
        {
            try
            {
                connection.close();
            }
            finally
            {
                fileInputStream.close();
            }
        }
    }

    public void setApplicationContext(ApplicationContext arg0) throws BeansException
    {
        applicationContext = arg0;
    }

    public static void main(String[] args) throws Exception
    {
        ClassPathXmlApplicationContext xmlAppContext = new ClassPathXmlApplicationContext(new String[] { "classpath:applicationContext.xml", "classpath:**/AbstractUnitTest-context.xml" });

        DbUnitTestDataLoader dataLoader = (DbUnitTestDataLoader) xmlAppContext.getBean("testDataLoader");
        dataLoader.load("", "");
    }
}
