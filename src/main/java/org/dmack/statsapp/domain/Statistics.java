package org.dmack.statsapp.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "STATS")
public class Statistics extends DomainObject
{
    private int quarter;
    private int kicks;
    private int marks;
    private int handballs;
    private int tackles;
    private int goals;
    private int behinds;

    @Basic
    @Column(name = "KICKS")
    public int getKicks()
    {
        return this.kicks;
    }

    public void setKicks(int aKicks)
    {
        this.kicks = aKicks;
    }

    @Basic
    @Column(name = "MARKS")
    public int getMarks()
    {
        return this.marks;
    }

    public void setMarks(int aMarks)
    {
        this.marks = aMarks;
    }

    @Basic
    @Column(name = "HANDBALLS")
    public int getHandballs()
    {
        return this.handballs;
    }

    public void setHandballs(int aHandballs)
    {
        this.handballs = aHandballs;
    }

    @Basic
    @Column(name = "TACKLES")
    public int getTackles()
    {
        return this.tackles;
    }

    public void setTackles(int aTackles)
    {
        this.tackles = aTackles;
    }

    @Basic
    @Column(name = "GOALS")
    public int getGoals()
    {
        return this.goals;
    }

    public void setGoals(int aGoals)
    {
        this.goals = aGoals;
    }

    @Basic
    @Column(name = "BEHINDS")
    public int getBehinds()
    {
        return this.behinds;
    }

    public void setBehinds(int aBehinds)
    {
        this.behinds = aBehinds;
    }

    @Basic
    @Column(name = "QUARTER")
    public int getQuarter()
    {
        return this.quarter;
    }

    public void setQuarter(int aQuarter)
    {
        this.quarter = aQuarter;
    }

    public static enum StatisticType
    {
        KICK, MARK, HANDBALL, TACKLE, GOAL, BEHIND;
    }

    public void addKick()
    {
        this.kicks++;
    }

    public void addMark()
    {
        this.marks++;
    }

    public void addHandball()
    {
        this.handballs++;
    }

    public void addTackle()
    {
        this.tackles++;
    }

    public void addGoals()
    {
        this.goals++;
    }

    public void addBehind()
    {
        this.behinds++;
    }

    public void removeKick()
    {
        this.kicks--;
    }

    public void removeMark()
    {
        this.marks--;
    }

    public void removeHandball()
    {
        this.handballs--;
    }

    public void removeTackle()
    {
        this.tackles--;
    }

    public void removeGoals()
    {
        this.goals--;
    }

    public void removeBehind()
    {
        this.behinds--;
    }

}
