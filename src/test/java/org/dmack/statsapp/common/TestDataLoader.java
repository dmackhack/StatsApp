package org.dmack.statsapp.common;

public interface TestDataLoader
{
    void load(String bundle, String test) throws DataLoadException;

    void teardown(String bundle, String test) throws DataLoadException;
}
