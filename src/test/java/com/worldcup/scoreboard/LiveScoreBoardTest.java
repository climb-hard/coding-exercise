package com.worldcup.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LiveScoreBoardTest {

    private LiveScoreBoard scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new LiveScoreBoard();
    }

    @Test
    @DisplayName("Should start a new game successfully")
    void shouldStartNewGameSuccessfully() {
        scoreBoard.startGame("Brazil", "Argentina");

        List<Game> summary = scoreBoard.getSummary();
        assertEquals(1, summary.size());

        Game game = summary.get(0);
        assertEquals("Brazil", game.getHomeTeam());
        assertEquals("Argentina", game.getAwayTeam());
        assertEquals(0, game.getHomeScore());
        assertEquals(0, game.getAwayScore());
    }

    @Test
    @DisplayName("Should throw exception when starting game with null home team")
    void shouldThrowExceptionWhenStartingGameWithNullHomeTeam() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.startGame(null, "Argentina")
        );
        assertEquals("Team names cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when starting game with empty away team")
    void shouldThrowExceptionWhenStartingGameWithEmptyAwayTeam() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.startGame("Brazil", "")
        );
        assertEquals("Team names cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when trying to start duplicate game")
    void shouldThrowExceptionWhenTryingToStartDuplicateGame() {
        scoreBoard.startGame("Brazil", "Argentina");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.startGame("Brazil", "Argentina")
        );
        assertEquals("Game between Brazil and Argentina already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Should return empty summary when no games")
    void shouldReturnEmptySummaryWhenNoGames() {
        List<Game> summary = scoreBoard.getSummary();
        assertTrue(summary.isEmpty());
    }

    @Test
    @DisplayName("Should start multiple games successfully")
    void shouldStartMultipleGamesSuccessfully() {
        scoreBoard.startGame("Brazil", "Argentina");
        scoreBoard.startGame("Spain", "France");
        scoreBoard.startGame("Germany", "Italy");

        List<Game> summary = scoreBoard.getSummary();
        assertEquals(3, summary.size());
    }

    @Test
    @DisplayName("Should throw UnsupportedOperationException for updateScore")
    void shouldThrowUnsupportedOperationExceptionForUpdateScore() {
        scoreBoard.startGame("Brazil", "Argentina");

        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                () -> scoreBoard.updateScore("Brazil", "Argentina", 1, 0)
        );
        assertEquals("updateScore not implemented yet", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw UnsupportedOperationException for finishGame")
    void shouldThrowUnsupportedOperationExceptionForFinishGame() {
        scoreBoard.startGame("Brazil", "Argentina");

        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                () -> scoreBoard.finishGame("Brazil", "Argentina")
        );
        assertEquals("finishGame not implemented yet", exception.getMessage());
    }
}
