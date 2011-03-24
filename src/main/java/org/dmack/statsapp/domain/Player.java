package org.dmack.statsapp.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PLAYER", uniqueConstraints =
    {
        @UniqueConstraint(columnNames =
            {
                "USERNAME"
            })
    })
public class Player extends User
{
    private Set<Club> clubs = new LinkedHashSet<Club>();

    private Club currentClub;

    private Set<PlayerMatchStatistics> playerMatchStatics = new LinkedHashSet<PlayerMatchStatistics>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENT_CLUB_ID")
    public Club getCurrentClub()
    {
        return this.currentClub;
    }

    public void setCurrentClub(Club aCurrentClub)
    {
        this.currentClub = aCurrentClub;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "PLAYER_CLUB_LINK", joinColumns =
        {
            @JoinColumn(name = "PLAYER_ID", referencedColumnName = "ID")
        }, inverseJoinColumns =
        {
            @JoinColumn(name = "CLUB_ID", referencedColumnName = "ID")
        })
    public Set<Club> getClubs()
    {
        return this.clubs;
    }

    public void setClubs(Set<Club> aClubs)
    {
        this.clubs = aClubs;
    }

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
    public Set<PlayerMatchStatistics> getPlayerMatchStatics()
    {
        return this.playerMatchStatics;
    }

    public void setPlayerMatchStatics(Set<PlayerMatchStatistics> aPlayerMatchStatics)
    {
        this.playerMatchStatics = aPlayerMatchStatics;
    }
}
