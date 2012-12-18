package org.dmack.statsapp.services;

import org.dmack.statsapp.common.AbstractUnitTest;
import org.dmack.statsapp.domain.Match;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations =
    {
            "classpath:applicationContext.xml",
            "classpath:applicationContext-outOfContainer.xml",
            "classpath:**/AbstractUnitTest-context.xml",
            "classpath:**/*-context.xml"
    }, inheritLocations = true)
public class MatchServiceTest extends AbstractUnitTest
{

    @Autowired
    protected MatchServiceEndpoint matchServiceEndpoint;
    @Autowired
    protected MatchService matchServiceClient;

    public MatchServiceTest()
    {
        super(MatchServiceTest.class.getName());
    }

    @Test
    public void testGetMatch()
    {
        Match match1 = matchServiceEndpoint.getMatch(1L);
        assertNotNull(match1);
        assertEquals(1, match1.getId().longValue());
    }

    @Test
    // @Ignore
    public void testGetMatchClient()
    {
        Match match1 = matchServiceClient.getMatch(1L);
        assertNotNull(match1);
        assertEquals(1, match1.getId().longValue());
    }

}
