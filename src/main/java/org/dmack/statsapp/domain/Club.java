package org.dmack.statsapp.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "CLUB", uniqueConstraints =
    {
        @UniqueConstraint(columnNames =
            {
                "NAME"
            })
    })
public class Club extends DomainObject
{
    private String name;

    private Set<Player> players = new LinkedHashSet<Player>();

    private Set<Team> teams = new LinkedHashSet<Team>();

    @Basic
    @Column(name = "NAME")
    public String getName()
    {
        return this.name;
    }

    public void setName(String aName)
    {
        this.name = aName;
    }

    @XmlTransient
    @ManyToMany(mappedBy = "clubs", fetch = FetchType.LAZY)
    public Set<Player> getPlayers()
    {
        return this.players;
    }

    public void setPlayers(Set<Player> aPlayers)
    {
        this.players = aPlayers;
    }

    @XmlTransient
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Team> getTeams()
    {
        return this.teams;
    }

    public void setTeams(Set<Team> aTeams)
    {
        this.teams = aTeams;
    }

}
