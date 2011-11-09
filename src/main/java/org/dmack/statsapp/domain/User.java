package org.dmack.statsapp.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class User extends DomainObject
{

    private String username = "";

    // private Set<Match> matches = new LinkedHashSet<Match>();

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

    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
    // FetchType.LAZY)
    // public Set<Match> getMatches()
    // {
    // return this.matches;
    // }
    //
    // public void setMatches(Set<Match> aMatches)
    // {
    // this.matches = aMatches;
    // }
    //
    // @Transient
    // public Match createMatch(Date date)
    // {
    // Match match = new Match();
    // match.setDate(date);
    //
    // getMatches().add(match);
    //
    // return match;
    // }

}
