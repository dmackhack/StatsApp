package org.dmack.statsapp.domain;

import javax.xml.bind.annotation.XmlTransient;

public class USAddress
{
    private City city;

    private String address;

    @XmlTransient
    public City getCity()
    {
        return this.city;
    }

    public void setCity(City aCity)
    {
        this.city = aCity;
    }

    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(String aAddress)
    {
        this.address = aAddress;
    }

}
