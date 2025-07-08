package com.worldcup.scoreboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    @DisplayName("Should create game with valid team names and initial score 0-0")
    void shouldCreateGameWithValidTeamNames() {
        Game game = new Game("Brazil", "Argentina");

        assertEquals("Brazil", game.getHomeTeam());
        assertEquals("Argentina", game.getAwayTeam());
        assertEquals(0, game.getHomeScore());
        assertEquals(0, game.getAwayScore());
        assertEquals(0, game.getTotalScore());
        assertNotNull(game.getCreatedAt());
        assertEquals("Brazil vs Argentina", game.getGameKey());
    }

    @Test
    @DisplayName("Should throw exception when home team is null")
    void shouldThrowExceptionWhenHomeTeamIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Game(null, "Argentina")
        );
        assertEquals("Home team cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when away team is null")
    void shouldThrowExceptionWhenAwayTeamIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Game("Brazil", null)
        );
        assertEquals("Away team cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when home team is empty")
    void shouldThrowExceptionWhenHomeTeamIsEmpty() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Game("", "Argentina")
        );
        assertEquals("Home team cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when away team is empty")
    void shouldThrowExceptionWhenAwayTeamIsEmpty() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Game("Brazil", "   ")
        );
        assertEquals("Away team cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should update score with valid values")
    void shouldUpdateScoreWithValidValues() {
        Game game = new Game("Brazil", "Argentina");

        game.updateScore(2, 1);

        assertEquals(2, game.getHomeScore());
        assertEquals(1, game.getAwayScore());
        assertEquals(3, game.getTotalScore());
    }

    @Test
    @DisplayName("Should throw exception when updating with negative home score")
    void shouldThrowExceptionWhenUpdatingWithNegativeHomeScore() {
        Game game = new Game("Brazil", "Argentina");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> game.updateScore(-1, 1)
        );
        assertEquals("Scores cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when updating with negative away score")
    void shouldThrowExceptionWhenUpdatingWithNegativeAwayScore() {
        Game game = new Game("Brazil", "Argentina");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> game.updateScore(1, -1)
        );
        assertEquals("Scores cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle zero scores correctly")
    void shouldHandleZeroScoresCorrectly() {
        Game game = new Game("Brazil", "Argentina");
        game.updateScore(2, 1);

        game.updateScore(0, 0);

        assertEquals(0, game.getHomeScore());
        assertEquals(0, game.getAwayScore());
        assertEquals(0, game.getTotalScore());
    }

    @Test
    @DisplayName("Should implement equals and hashCode correctly")
    void shouldImplementEqualsAndHashCodeCorrectly() {
        Game game1 = new Game("Brazil", "Argentina");
        Game game2 = new Game("Brazil", "Argentina");
        Game game3 = new Game("Spain", "France");

        assertEquals(game1, game2);
        assertEquals(game1.hashCode(), game2.hashCode());
        assertNotEquals(game1, game3);
        assertNotEquals(game1.hashCode(), game3.hashCode());
    }

    @Test
    @DisplayName("Should generate correct string representation")
    void shouldGenerateCorrectStringRepresentation() {
        Game game = new Game("Brazil", "Argentina");
        game.updateScore(3, 1);

        String result = game.toString();

        assertEquals("Brazil 3 - Argentina 1", result);
    }
}
