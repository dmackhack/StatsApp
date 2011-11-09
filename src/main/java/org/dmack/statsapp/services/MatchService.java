package org.dmack.statsapp.services;

import java.util.Date;

import javax.jws.WebService;

import org.dmack.statsapp.domain.City;
import org.dmack.statsapp.domain.Match;
import org.dmack.statsapp.domain.USAddress;

@WebService
public interface MatchService
{
    public int add(int var1, int var2);

    public Match createMatch(Date date);

    public USAddress createAddress(String anAddress);

    public City getCity();

    public Match getMatch(Long id);
}
