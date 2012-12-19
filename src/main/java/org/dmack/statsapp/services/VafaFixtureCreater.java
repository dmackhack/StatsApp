package org.dmack.statsapp.services;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class VafaFixtureCreater
{
    private Map<String, String> teams = new HashMap<String, String>();
    private int divisionSequenceStart = 100;

    public void createFixture(String path) throws ParserException
    {
        Parser parser = new Parser(path);
        String season2013Id = "2";

        createTeamsAndClubs(path);

        NodeFilter roundsFilter = new HasAttributeFilter("class", "all-fixture-wrap side-by-side");
        NodeList roundNodes = parser.extractAllNodesThatMatch(roundsFilter);

        int matchCount = 0;
        int participantId = 0;
        SimpleNodeIterator roundElements = roundNodes.elements();
        Node roundNode = null;
        while (roundElements.hasMoreNodes())
        {
            NodeFilter roundFilter = new TagNameFilter("H4");
            roundNode = roundElements.nextNode();
            NodeList roundNodeList = roundNode.getChildren().extractAllNodesThatMatch(roundFilter, true);
            Node roundNodeHeading = roundNodeList.elementAt(0);
            String round = roundNodeHeading.getFirstChild().getText();

            System.out.println("-- Round: " + round);

            int roundId = divisionSequenceStart + Integer.valueOf(round);

            Calendar macCal = Calendar.getInstance();
            macCal.set(2001, Calendar.JANUARY, 1, 0, 0);
            Calendar nowCal = Calendar.getInstance();
            nowCal.set(2013, Calendar.MAY, 20);
            long seconds = (nowCal.getTimeInMillis() - macCal.getTimeInMillis()) / 1000;
            StringBuffer roundSQL = new StringBuffer().append("insert into ZROUND (Z_PK, Z_ENT, Z_OPT, ZNUMBER, ZSEASON, ZDATE) values (").append(roundId).append(",11,1,").append(round).append(",")
                    .append(season2013Id).append(",").append(seconds).append(");");

            System.out.println(roundSQL.toString());

            NodeFilter attributeFilter = new HasAttributeFilter("class", "match-wrap sport-4");
            NodeList nodes = roundNode.getChildren().extractAllNodesThatMatch(attributeFilter, true);

            SimpleNodeIterator elements = nodes.elements();
            Node node = null;
            while (elements.hasMoreNodes())
            {
                matchCount++;
                node = elements.nextNode();

                String homeTeam = getTeam(node, "home-team-name");
                String awayTeam = getTeam(node, "away-team-name");
                System.out.println("-- " + homeTeam + " VS " + awayTeam);
                int matchId = divisionSequenceStart + matchCount;
                StringBuffer matchSQL = new StringBuffer().append("insert into ZMATCH (Z_PK, Z_ENT, Z_OPT, ZROUND, ZDATE) values (").append(matchId).append(",4,1,").append(roundId).append(",")
                        .append(seconds).append(");");
                System.out.println(matchSQL.toString());
                participantId++;
                StringBuffer homeParticipantSQL = new StringBuffer()
                        .append("insert into ZPARTICIPANT (Z_PK, Z_ENT, Z_OPT, ZMATCH, ZPARTICIPANTSTATISTICS, Z8_PARTICIPANTSTATISTICS, ZPLAYER, ZTEAMPARTICIPANT, ZTEAM, ZHOME) values (")
                        .append(divisionSequenceStart + participantId).append(",7,1,").append(matchId).append(",NULL,NULL,NULL,NULL,").append(teams.get(homeTeam)).append(",1);");

                participantId++;
                StringBuffer awayParticipantSQL = new StringBuffer()
                        .append("insert into ZPARTICIPANT (Z_PK, Z_ENT, Z_OPT, ZMATCH, ZPARTICIPANTSTATISTICS, Z8_PARTICIPANTSTATISTICS, ZPLAYER, ZTEAMPARTICIPANT, ZTEAM, ZHOME) values (")
                        .append(divisionSequenceStart + participantId).append(",7,1,").append(matchId).append(",NULL,NULL,NULL,NULL,").append(teams.get(awayTeam)).append(",0);");

                System.out.println(homeParticipantSQL.toString());
                System.out.println(awayParticipantSQL.toString());
            }

        }
        // System.out.println("Numer of Games: " + matchCount);
    }

    public void createTeamsAndClubs(String path) throws ParserException
    {
        Parser parser = new Parser(path);
        populateClubs(parser);
        // insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values
        // (1,14,1,1,'Seniors');

        // insert into ZCLUB (Z_PK, Z_ENT, Z_OPT, ZNAME) values (1,1,1,'Old
        // Haileybury Amateur Football Club');
        int clubCount = 0;
        for (String club : teams.keySet())
        {
            clubCount++;
            int clubId = divisionSequenceStart + clubCount;
            StringBuffer clubBuffer = new StringBuffer().append("insert into ZCLUB (Z_PK, Z_ENT, Z_OPT, ZNAME) values (").append(clubId).append(",1,1,'").append(club).append("');");
            int teamId = clubId;
            StringBuffer teamBuffer = new StringBuffer().append("insert into ZTEAM (Z_PK, Z_ENT, Z_OPT, ZCLUB, ZNAME) values (").append(teamId).append(",14,1,")
                    .append(divisionSequenceStart + clubCount).append(",'Seniors'").append(");");
            System.out.println(clubBuffer.toString());
            System.out.println(teamBuffer.toString());
            teams.put(club, String.valueOf(teamId));
        }
    }

    private void populateClubs(Parser aParser) throws ParserException
    {
        NodeFilter attributeFilter = new HasAttributeFilter("class", "match-wrap sport-4");
        NodeList nodes = aParser.extractAllNodesThatMatch(attributeFilter);

        SimpleNodeIterator elements = nodes.elements();
        Node node = null;
        while (elements.hasMoreNodes())
        {
            node = elements.nextNode();

            String homeTeam = getTeam(node, "home-team-name");
            String awayTeam = getTeam(node, "away-team-name");
            teams.put(homeTeam, "");
            teams.put(awayTeam, "");
        }
    }

    private String getTeam(Node node, String homeOrAway)
    {
        String homeTeam = "";
        NodeFilter homeTeamFilter = new HasAttributeFilter("class", homeOrAway);
        NodeList homeTeamNodes = node.getChildren().extractAllNodesThatMatch(homeTeamFilter, true);
        Node homeTeamNode = null;
        SimpleNodeIterator homeTeamElements = homeTeamNodes.elements();
        while (homeTeamElements.hasMoreNodes())
        {
            homeTeamNode = homeTeamElements.nextNode();
            homeTeam = homeTeamNode.getFirstChild().getFirstChild().getText();
            break;
        }
        return homeTeam;
    }

    public Map<String, String> getTeams()
    {
        return this.teams;
    }

    public void setTeams(Map<String, String> aTeams)
    {
        this.teams = aTeams;
    }

}
