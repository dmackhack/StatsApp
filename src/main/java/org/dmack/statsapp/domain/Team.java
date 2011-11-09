package org.dmack.statsapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TEAM")
public class Team extends DomainObject
{
    private TeamType type;

    private Club club;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    public TeamType getType()
    {
        return this.type;
    }

    public void setType(TeamType aType)
    {
        this.type = aType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLUB_ID")
    public Club getClub()
    {
        return this.club;
    }

    public void setClub(Club aClub)
    {
        this.club = aClub;
    }

    public static enum TeamType
    {
        SENIORS, RESERVES, THIRDS, FOURTHS, U19s;
    }

}
