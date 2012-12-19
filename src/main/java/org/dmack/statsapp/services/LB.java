package org.dmack.statsapp.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LB extends GenericServlet
{
    private static final int DEFAULT_SLEEP_TIME = 7000;
    private static final String SLEEP_FILENAME = "/home/dmackenzie/tmp/sleep.txt";
    private static final long serialVersionUID = 8582266581200544562L;
    private static Log LOG = LogFactory.getLog(LB.class);

    @Override
    public void service(ServletRequest aReq, ServletResponse aRes) throws ServletException, IOException
    {
        HttpServletRequest request = (HttpServletRequest) aReq;
        LOG.info("D> [" + request.getSession().getId() + "] Entered LB Servlet.");
        try
        {
            int sleepTime = getSleepTime();
            LOG.info("D> [" + request.getSession().getId() + "] SLEEPING FOR " + sleepTime + " ms...");
            Thread.sleep(sleepTime);
        }
        catch (InterruptedException e)
        {
            LOG.error("Sleep interrupted", e);
        }

        HttpServletResponse response = (HttpServletResponse) aRes;

        StringBuffer buffy = new StringBuffer();
        buffy.append("<html>").append("<body><p>WEBSERVER IS UP</p></body>").append("</html>");

        LOG.info("D> [" + request.getSession().getId() + "] Response : " + buffy.toString());
        response.setContentType("text/html");
        response.getOutputStream().print(buffy.toString());

    }

    private int getSleepTime()
    {
        int sleepTime = DEFAULT_SLEEP_TIME;
        BufferedReader reader = null;
        try
        {
            // InputStream stream =
            // Thread.currentThread().getContextClassLoader().getResourceAsStream("sleep.txt");
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(SLEEP_FILENAME)));
            String sleepTimeProperty = reader.readLine();

            sleepTime = Integer.parseInt(sleepTimeProperty);
        }
        catch (NumberFormatException e)
        {
            LOG.error("Bad sleepTime", e);
        }
        catch (FileNotFoundException e)
        {
            LOG.error("File Not Found", e);
        }
        catch (IOException e)
        {
            LOG.error("IO Error", e);
        }

        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    LOG.error("IO Error", e);
                }
            }
        }
        return sleepTime;
    }

}
