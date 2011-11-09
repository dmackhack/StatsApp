package org.dmack.statsapp.domain;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.dmack.statsapp.domain.Statistics.StatisticType;

@Entity
@Table(name = "PLAYER_MATCH_STATS")
public class PlayerMatchStatistics extends DomainObject
{
    private Player player;

    private Set<Statistics> statistics = new LinkedHashSet<Statistics>();

    private TeamMatchStatistics teamMatchStatistics;

    private Map<Long, Statistics> statsByQuarter = new HashMap<Long, Statistics>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAYER_ID")
    public Player getPlayer()
    {
        return this.player;
    }

    public void setPlayer(Player aPlayer)
    {
        this.player = aPlayer;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "PLAYER_MATCH_STATS_LINK", joinColumns =
        {
            @JoinColumn(name = "PLAYER_MATCH_STATS_ID", referencedColumnName = "ID")
        }, inverseJoinColumns =
        {
            @JoinColumn(name = "STATS_ID", referencedColumnName = "ID")
        })
    public Set<Statistics> getStatistics()
    {
        return this.statistics;
    }

    public void setStatistics(Set<Statistics> aStatistics)
    {
        this.statistics = aStatistics;
    }

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "TEAM_MATCH_STATS_ID")
    public TeamMatchStatistics getTeamMatchStatistics()
    {
        return this.teamMatchStatistics;
    }

    public void setTeamMatchStatistics(TeamMatchStatistics aTeamMatchStatistics)
    {
        this.teamMatchStatistics = aTeamMatchStatistics;
    }

    @Transient
    public Match getMatch()
    {
        return getTeamMatchStatistics().getMatch();
    }

    @Transient
    public Map<Long, Statistics> getStatsByQuarter()
    {
        if (statsByQuarter.isEmpty() && !getStatistics().isEmpty())
        {
            for (Statistics stats : getStatistics())
            {
                statsByQuarter.put(stats.getId(), stats);
            }
        }
        return this.statsByQuarter;
    }

    @Transient
    public Statistics getStatsForQuarter(Long quarter)
    {
        Statistics stats = null;
        if (getStatsByQuarter().containsKey(quarter))
        {
            stats = getStatsByQuarter().get(quarter);
        }
        return stats;
    }

    @Transient
    public void addStat(Long quarter, StatisticType type)
    {
        Statistics quarterStats = getStatsForQuarter(quarter);
        if (quarterStats != null)
        {
            switch (type)
            {
                case KICK:
                    quarterStats.addKick();
                    break;

                case MARK:
                    quarterStats.addMark();
                    break;

                case HANDBALL:
                    quarterStats.addHandball();
                    break;

                case TACKLE:
                    quarterStats.addTackle();
                    break;

                case GOAL:
                    quarterStats.addGoals();
                    break;

                case BEHIND:
                    quarterStats.addBehind();
                    break;

                default:
                    break;
            }
        }
    }

    @Transient
    public void removeStat(Long quarter, StatisticType type)
    {
        Statistics quarterStats = getStatsForQuarter(quarter);
        if (quarterStats != null)
        {
            switch (type)
            {
                case KICK:
                    quarterStats.removeKick();
                    break;

                case MARK:
                    quarterStats.removeMark();
                    break;

                case HANDBALL:
                    quarterStats.removeHandball();
                    break;

                case TACKLE:
                    quarterStats.removeTackle();
                    break;

                case GOAL:
                    quarterStats.removeGoals();
                    break;

                case BEHIND:
                    quarterStats.removeBehind();
                    break;

                default:
                    break;
            }
        }
    }

}
