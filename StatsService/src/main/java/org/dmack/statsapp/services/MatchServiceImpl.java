package org.dmack.statsapp.services;

import java.util.Date;

import org.dmack.statsapp.domain.City;
import org.dmack.statsapp.domain.Match;
import org.dmack.statsapp.domain.USAddress;
import org.dmack.statsapp.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MatchServiceImpl implements MatchService
{
    @Autowired
    private Repository<Match> matchRepository;

    public int add(int var1, int var2)
    {
        return var1 + var2;
    }

    public Match createMatch(Date date) // , Team homeTeam, Team awayTeam
    {
        Match match = new Match();
        match.setId(50L);
        match.setDate(date);
        match.setVersion(0);
        return match;
    }

    public USAddress createAddress(String anAddress)
    {
        USAddress address = new USAddress();
        address.setAddress(anAddress);
        return address;
    }

    public City getCity()
    {
        City city = new City();
        city.setName("Melbourne");

        USAddress address = new USAddress();
        address.setAddress("35 Some St");
        address.setCity(city);

        city.getAddresses().add(address);

        return city;
    }

    public Match getMatch(Long id) // , Team homeTeam, Team awayTeam
    {
        return matchRepository.findByPK(id);
    }
}
