package org.dmack.statsapp.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.BeanInitializationException;

public class PropertiesHolder
{
    private static final String INPUT_PROPERTIES_FILENAME = System.getProperty("user.home")
            + "/.statsapp/statsapp.local.application.properties";

    // default value, we need to load the properties
    private static boolean isRequiredReload = true;

    private static Properties properties = new Properties();

    private static Object lock = new Object();

    public static Properties getProperties()
    {
        if (isRequiredReload)
        {
            synchronized (lock)
            {
                if (isRequiredReload)
                {
                    reloadProperties();
                    isRequiredReload = false;
                }
            }
        }

        return properties;
    }

    private static void reloadProperties()
    {
        FileInputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(INPUT_PROPERTIES_FILENAME);
            properties.load(inputStream);
        }
        catch (FileNotFoundException e)
        {
            throw new BeanInitializationException("Error reading properites from file: "
                    + INPUT_PROPERTIES_FILENAME, e);
        }
        catch (IOException e)
        {
            throw new BeanInitializationException("Error reading properites: "
                    + INPUT_PROPERTIES_FILENAME, e);
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    throw new BeanInitializationException("Error closing  FileInputStream", e);
                }
            }
        }
    }

    public static void forceReload()
    {
        synchronized (lock)
        {
            isRequiredReload = true;
        }
    }

    public static String getProperty(String key)
    {
        return getProperties().getProperty(key);
    }

    public static String getProperty(String key, String defaultValue)
    {
        return getProperties().getProperty(key, defaultValue);
    }

    public static String getDatePattern()
    {
        return getProperty("date.pattern", "dd/MM/yyyy");
    }

    // @Override
    // public void propertiesReloaded(PropertiesReloadedEvent event)
    // {
    // LOG.info("In propertiesReloaded");
    // properties = event.getTarget().getProperties();
    //
    // // unit tests can force this exception to test exception handling higher
    // up the stack
    // if ("true".equals(properties.getProperty("force-reload-exception")))
    // {
    // throw new RuntimeException("force-reload-exception=true");
    // }
    // }

}
