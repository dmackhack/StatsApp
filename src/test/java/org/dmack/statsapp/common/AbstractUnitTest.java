package org.dmack.statsapp.common;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dmack.statsapp.domain.GeneralUser;
import org.dmack.statsapp.domain.Match;
import org.dmack.statsapp.domain.Player;
import org.dmack.statsapp.repository.Repository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Parent for all unit tests
 * 
 * @author dmackenzie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
    {
            "classpath:applicationContext.xml",
            "classpath:applicationContext-outOfContainer.xml",
            "classpath:**/AbstractUnitTest-context.xml"
    }, inheritLocations = true)
public abstract class AbstractUnitTest extends TestCase
{
    private static final Log LOG = LogFactory.getLog(AbstractUnitTest.class);
    private String name;

    @Autowired
    private TestDataLoader testDataLoader;

    @Autowired
    protected Repository<GeneralUser> generalUserRepository;
    @Autowired
    protected Repository<Player> playerRepository;
    @Autowired
    protected Repository<Match> matchRepository;

    public AbstractUnitTest(String name)
    {
        this.name = name;
    }

    /**
     * Fails the test if the actual exception is different from the expected
     * exception.
     * 
     * @param actual
     * @param expected
     */
    public static void handleException(Exception actual, Class<? extends Exception> expected)
    {
        if (actual == null)
        {
            Assert.fail("Null exception passed!");
        }
        if (actual.getClass() != expected)
        {
            StringBuilder b = new StringBuilder();
            if (LOG.isInfoEnabled())
            {
                b.append("Caught unexpected exception type '").append(actual.getClass().getName()).append("' (expected: ").append(expected.getName()).append(")");
                LOG.info(b.toString(), actual);
            }
            Assert.fail(b.toString());
        }
    }

    /**
     * Fails the test and logs the appropriate message when an exception is
     * encountered where it wasn't expected.
     * 
     * @param e
     *            The thrown exception
     */
    public static void handleUnexpected(Exception e)
    {
        String msg = "Encountered unexpected exception";
        LOG.info(msg, e);
        Assert.fail(msg + " " + e.toString());
    }

    @Before
    public void onSetUp() throws Exception
    {
        // The "bundle" is the name of the class that contains the test; the
        // testname is the name of the test method (as returned by
        // TestCase.getName())
        // with the "test" at the start removed.
        try
        {
            getTestDataLoader().load(getTestBundle(), getTestName());
        }
        catch (DataLoadException tdle)
        {
            handleUnexpected(tdle);
        }
    }

    @After
    public void onTearDown() throws Exception
    {
        // super.onTearDown();
        getTestDataLoader().teardown(getTestBundle(), getTestName());
    }

    // @Override
    // protected String[] getConfigLocations() {
    // setAutowireMode(AUTOWIRE_BY_NAME);
    // setDependencyCheck(false);
    // String[] locations = super.getConfigLocations();
    // locations = (String[]) ArrayUtils.addAll(locations, new String[] {
    // "classpath:spring/applicationContext.xml",
    // "classpath:spring/applicationContext-datasourceOutOfContainer.xml",
    // "classpath:useDbUnitTestDataLoader.xml" });
    // return locations;
    // }

    public TestDataLoader getTestDataLoader()
    {
        return testDataLoader;
    }

    public void setTestDataLoader(TestDataLoader testDataLoader)
    {
        this.testDataLoader = testDataLoader;
    }

    private String getTestBundle()
    {
        return this.getClass().getSimpleName();
    }

    private String getTestName()
    {
        return name;
    }
}
