package org.dmack.statsapp.domain.sqlite;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

import junit.framework.TestCase;

import org.junit.Test;

public class LoadSqliteDataTest extends TestCase
{

    @Test
    public void testConnection() throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:/home/dmackenzie/Projects/StatsApp/Developers Workbook/db/StatsAppMac.sqlite");

            // Statement queryStatement = connection.createStatement();
            // ResultSet clubsRS =
            // queryStatement.executeQuery("select * from zclub");
            //
            // while (clubsRS.next())
            // {
            // for (int i = 0; i < clubsRS.getMetaData().getColumnCount(); i++)
            // {
            // System.out.println("Column Name " + i + ": " +
            // clubsRS.getMetaData().getColumnName(i + 1));
            // }
            // }

            System.out.println("List of tables:");
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet tables = meta.getTables("", "", "", new String[]
                {
                    "TABLE",
                });
            while (tables.next())
            {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("delete from " + tableName);
                System.out.print("insert into " + tableName + " (");
                ResultSet res = meta.getColumns(null, null, tableName, null);
                // System.out.println("List of columns: ");
                int colCount = 0;
                while (res.next())
                {
                    // System.out.println("  " + res.getString("TABLE_SCHEM") +
                    // ", " + res.getString("TABLE_NAME") + ", " +
                    // res.getString("COLUMN_NAME") + ", " +
                    // res.getString("TYPE_NAME") + ", "
                    // + res.getInt("COLUMN_SIZE") + ", " +
                    // res.getString("NULLABLE"));
                    if (colCount == 0)
                    {
                        System.out.print(res.getString("COLUMN_NAME"));
                    }
                    else
                    {
                        System.out.print(", " + res.getString("COLUMN_NAME"));
                    }
                    colCount++;
                }
                System.out.print(") values (");
                for (int i = 0; i < colCount; i++)
                {
                    if (i == 0 || i == 1)
                    {
                        System.out.print("1,");
                    }
                    else if (i == 2)
                    {
                        System.out.print("1");
                    }
                    else
                    {
                        System.out.print(",''");
                    }
                }
                System.out.println(");");
                res.close();
            }

            tables.close();
        }
        finally
        {
            if (connection != null)
            {
                connection.close();
            }
        }
    }
}
