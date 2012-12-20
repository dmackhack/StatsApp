package org.dmack.statsapp.services;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class VafaPlayersCreater
{
    private int divisionSequenceStart = 100;
    private int playerCount = 0;

    public void createPlayers(String path, Map<String, String> aTeams) throws ParserException
    {
        Parser parser = new Parser(path);

        NodeFilter roundsFilter = new HasAttributeFilter("class", "tableClass stats");
        NodeList clubNodes = parser.extractAllNodesThatMatch(roundsFilter);

        int clubCount = 0;
        int participantId = 0;
        SimpleNodeIterator clubElements = clubNodes.elements();
        Node clubNode = null;
        while (clubElements.hasMoreNodes())
        {
            clubCount++;
            clubNode = clubElements.nextNode();

            NodeFilter clubNameFilter = new TagNameFilter("h4");
            NodeList clubNameNodeList = clubNode.getChildren().extractAllNodesThatMatch(clubNameFilter, true);
            Node clubNameNode = clubNameNodeList.elementAt(0);
            String clubName = clubNameNode.getFirstChild().getText();

            String clubId = "";
            if (aTeams != null)
            {
                clubId = aTeams.get(clubName);
            }
            // System.out.println("Club Name: " + clubName + ": Club Id: " +
            // clubId);
            createPlayers(clubNode, clubId);

            // System.out.println(clubNode.toHtml());
        }

        // System.out.println("Club Count: " + clubCount);
    }

    private void createPlayers(Node aClubNode, String aClubId)
    {
        NodeFilter playerFilter = new HasAttributeFilter("class", "resultlink");
        NodeList clubNameNodeList = aClubNode.getChildren().extractAllNodesThatMatch(playerFilter, true);

        SimpleNodeIterator playerNodesIter = clubNameNodeList.elements();
        while (playerNodesIter.hasMoreNodes())
        {
            Node playerNode = playerNodesIter.nextNode();
            String playerName = playerNode.getFirstChild().getText();
            // System.out.println("Player Name: " + playerName);

            String[] playerNames = StringUtils.split(playerName);

            String number = "";
            if (playerNode.getParent().getParent().getChildren().elementAt(3).getFirstChild() != null)
            {
                number = playerNode.getParent().getParent().getChildren().elementAt(3).getFirstChild().getText();
            }
            // insert into ZUSER (Z_PK, Z_ENT, Z_OPT, ZUSERID, ZGOALS, ZBEHINDS,
            // ZKICKS, ZTACKLES, ZHANDBALLS, ZMARKS, ZUSERNAME, ZLASTNAME,
            // ZFIRSTNAME) values
            // (1,16,1,1,0,0,0,0,0,0,'dmackenz1981@gmail.com','Mackenzie',
            // 'David');

            int playerId = divisionSequenceStart + ++playerCount;
            StringBuffer playerSQL = new StringBuffer().append("insert into ZUSER (Z_PK, Z_ENT, Z_OPT, ZUSERID, ZUSERNAME, ZPASSWORD, ZLASTNAME, ZFIRSTNAME, ZNUMBER) values (").append(playerId)
                    .append(",16,1,").append(playerId).append(",'','','").append(playerNames[1]).append("', '").append(playerNames[0]).append("'");

            if (StringUtils.isEmpty(number))
            {
                number = "NULL";
            }
            playerSQL.append(",").append(number);
            playerSQL.append(");");

            System.out.println(playerSQL.toString());

            // insert into ZPLAYERCLUB (Z_PK, Z_ENT, Z_OPT, ZACTIVE, ZCLUB,
            // ZPLAYER) values (10,10,1,1,3,10);
            int playerClubId = playerId;
            StringBuffer playerClubSQL = new StringBuffer().append("insert into ZPLAYERCLUB (Z_PK, Z_ENT, Z_OPT, ZACTIVE, ZCLUB, ZPLAYER) values (").append(playerClubId).append(",10,1,1,")
                    .append(aClubId).append(",").append(playerId).append(");");
            System.out.println(playerClubSQL.toString());
        }
    }
}
