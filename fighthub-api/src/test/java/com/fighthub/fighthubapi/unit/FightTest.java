package com.fighthub.fighthubapi.unit;

import com.fighthub.fighthubapi.fight.Fight;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
public class FightTest {
    private Fight fight;
    private FighterProfile blueCornerFighter;
    private FighterProfile redCornerFighter;
    private FighterProfile winner;

    @BeforeEach
    void setUp() {
        fight = new Fight();
        blueCornerFighter = mock(FighterProfile.class);
        redCornerFighter = mock(FighterProfile.class);
        winner = blueCornerFighter;
    }

    @Test
    void testUpdateFighterStats_Draw() {
        fight.setDraw(true);
        fight.setBlueCornerFighter(blueCornerFighter);
        fight.setRedCornerFighter(redCornerFighter);

        fight.updateFighterStats();

        verify(blueCornerFighter, times(1)).incrementDraws();
        verify(redCornerFighter, times(1)).incrementDraws();
    }

    @Test
    void testUpdateFighterStats_Winner() {
        fight.setDraw(false);
        fight.setWinner(winner);
        fight.setBlueCornerFighter(blueCornerFighter);
        fight.setRedCornerFighter(redCornerFighter);

        fight.updateFighterStats();

        verify(blueCornerFighter, times(1)).incrementWins();
        verify(blueCornerFighter, times(1)).incrementWinsInARow();
        verify(redCornerFighter, times(1)).incrementLosses();
        verify(redCornerFighter, times(1)).resetWinsInARow();
    }

    @Test
    void testUpdateFighterStats_WinnerByKO() {
        fight.setDraw(false);
        fight.setWinner(winner);
        fight.setKo(true);
        fight.setBlueCornerFighter(blueCornerFighter);
        fight.setRedCornerFighter(redCornerFighter);

        fight.updateFighterStats();

        verify(blueCornerFighter, times(1)).incrementWins();
        verify(blueCornerFighter, times(1)).incrementWinsInARow();
        verify(blueCornerFighter, times(1)).incrementKos();
        verify(redCornerFighter, times(1)).incrementLosses();
        verify(redCornerFighter, times(1)).resetWinsInARow();
    }
}
