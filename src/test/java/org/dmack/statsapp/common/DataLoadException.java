package org.dmack.statsapp.common;

public class DataLoadException extends Exception
{

    private static final long serialVersionUID = 1L;

    public DataLoadException()
    {
    }

    public DataLoadException(String arg0)
    {
        super(arg0);
    }

    public DataLoadException(Throwable arg0)
    {
        super(arg0);
    }

    public DataLoadException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }

}
