package org.dmack.statsapp.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MATCH")
public class Match extends DomainObject
{
    private Date date;
    
    private TeamMatchStatistics homeTeamMatchStatistics;
    
    private TeamMatchStatistics awayTeamMatchStatistics;

    @OneToOne(mappedBy = "match", fetch = FetchType.LAZY)
    public TeamMatchStatistics getHomeTeamMatchStatistics()
    {
        return this.homeTeamMatchStatistics;
    }

    public void setHomeTeamMatchStatistics(TeamMatchStatistics aHomeTeamMatchStatistics)
    {
        this.homeTeamMatchStatistics = aHomeTeamMatchStatistics;
    }

    @OneToOne(mappedBy = "match", fetch = FetchType.LAZY)
    public TeamMatchStatistics getAwayTeamMatchStatistics()
    {
        return this.awayTeamMatchStatistics;
    }

    public void setAwayTeamMatchStatistics(TeamMatchStatistics aAwayTeamMatchStatistics)
    {
        this.awayTeamMatchStatistics = aAwayTeamMatchStatistics;
    }

    @Basic
    @Column(name = "DATE")
    public Date getDate()
    {
        return this.date;
    }

    public void setDate(Date aDate)
    {
        this.date = aDate;
    }
    
}
