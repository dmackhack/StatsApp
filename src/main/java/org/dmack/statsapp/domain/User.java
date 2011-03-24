package org.dmack.statsapp.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class User extends DomainObject
{

    private String username = "";

    @Basic
    @Column(name = "USERNAME")
    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String aUsername)
    {
        this.username = aUsername;
    }
    
}
