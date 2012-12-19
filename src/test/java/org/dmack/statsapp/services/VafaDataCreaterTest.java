package org.dmack.statsapp.services;

import java.util.Calendar;
import java.util.Map;

import junit.framework.TestCase;

import org.htmlparser.util.ParserException;
import org.junit.Test;

public class VafaDataCreaterTest extends TestCase
{
    private static final String FIXTURE_PATH = "src/main/resources/fixtures/vafa/VAFA Premier B.html";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    @Test
    public void testCreateFixture() throws ParserException
    {
        VafaFixtureCreater creater = new VafaFixtureCreater();
        creater.createFixture(FIXTURE_PATH);
    }

    @Test
    public void testCreatePlayers() throws ParserException
    {
        VafaFixtureCreater fixtureCreater = new VafaFixtureCreater();
        fixtureCreater.createTeamsAndClubs(FIXTURE_PATH);
        Map<String, String> teams = fixtureCreater.getTeams();

        VafaPlayersCreater playersCreater = new VafaPlayersCreater();
        playersCreater.createPlayers("src/main/resources/fixtures/vafa/2012 Rd 18 - Old Haileybury vs St Kevins - Match Results.html", teams);
    }

    @Test
    public void testDate()
    {
        double macTime = 377612284.794189;
        // 60*60*24*7

        // January 1, 2001
        Calendar macCal = Calendar.getInstance();
        macCal.set(2001, Calendar.JANUARY, 1, 0, 0);

        Calendar macCal2 = Calendar.getInstance();
        macCal2.set(2013, Calendar.MAY, 20);

        long timeInMs = macCal2.getTimeInMillis() - macCal.getTimeInMillis();

        System.out.println(timeInMs / 1000);

        System.out.println(macCal.get(Calendar.YEAR) + "-" + macCal.get(Calendar.MONTH) + "-" + macCal.get(Calendar.DAY_OF_MONTH));
    }
}
