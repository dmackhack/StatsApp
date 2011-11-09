package org.dmack.statsapp.domain;

import java.util.LinkedHashSet;
import java.util.Set;

public class City
{
    private String name;

    private Set<USAddress> addresses = new LinkedHashSet<USAddress>();

    public Set<USAddress> getAddresses()
    {
        return this.addresses;
    }

    public void setAddresses(Set<USAddress> aAddresses)
    {
        this.addresses = aAddresses;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String aName)
    {
        this.name = aName;
    }
}
