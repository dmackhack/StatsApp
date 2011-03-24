package org.dmack.statsapp.domain;

import org.dmack.statsapp.common.AbstractUnitTest;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

public class DomainTest extends AbstractUnitTest
{
    public DomainTest()
    {
        super(DomainTest.class.getName());
    }

    @Test
    public void testRetrieveById()
    {
        User dave = generalUserRepository.findByPK(1L);
        assertEquals("dmackenz1981@gmail.com", dave.getUsername());
    }

    @Test
    @Transactional
    public void testRetrieveDmack()
    {
        Player dave = playerRepository.findByPK(1L);
        assertEquals(1, dave.getClubs().size());
        assertNotNull(dave.getCurrentClub());
        assertEquals("OHAFC", dave.getCurrentClub().getName());

        Statistics rd1q1 = null;
        for (PlayerMatchStatistics matchStats : dave.getPlayerMatchStatics())
        {
            if (matchStats.getId() == 1)
            {
                assertEquals(4, matchStats.getStatistics().size());
                for (Statistics stats : matchStats.getStatistics())
                {
                    if (stats.getId() == 1)
                    {
                        rd1q1 = stats;
                        break;
                    }
                }
                break;
            }
        }
        assertNotNull(rd1q1);
        assertEquals(5, rd1q1.getKicks());

    }
}
