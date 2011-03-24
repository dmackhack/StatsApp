package org.dmack.statsapp.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TEAM_MATCH_STATS")
public class TeamMatchStatistics extends DomainObject
{
    private Match match;

    private Team team;

    private Set<PlayerMatchStatistics> playerMatchStatistics = new LinkedHashSet<PlayerMatchStatistics>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATCH_ID")
    public Match getMatch()
    {
        return this.match;
    }

    public void setMatch(Match aMatch)
    {
        this.match = aMatch;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    public Team getTeam()
    {
        return this.team;
    }

    public void setTeam(Team aTeam)
    {
        this.team = aTeam;
    }

    @OneToMany(mappedBy = "teamMatchStatistics", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<PlayerMatchStatistics> getPlayerMatchStatistics()
    {
        return this.playerMatchStatistics;
    }

    public void setPlayerMatchStatistics(Set<PlayerMatchStatistics> aPlayerMatchStatistics)
    {
        this.playerMatchStatistics = aPlayerMatchStatistics;
    }
}
