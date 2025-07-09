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
    @DisplayName("Should throw custom exception when trying to start duplicate game")
    void shouldThrowCustomExceptionWhenTryingToStartDuplicateGame() {
        scoreBoard.startGame("Brazil", "Argentina");

        GameAlreadyExistsException exception = assertThrows(
                GameAlreadyExistsException.class,
                () -> scoreBoard.startGame("Brazil", "Argentina")
        );
        assertEquals("Game between Brazil and Argentina already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Should update score successfully")
    void shouldUpdateScoreSuccessfully() {
        scoreBoard.startGame("Brazil", "Argentina");

        scoreBoard.updateScore("Brazil", "Argentina", 2, 1);

        List<Game> summary = scoreBoard.getSummary();
        Game game = summary.get(0);
        assertEquals(2, game.getHomeScore());
        assertEquals(1, game.getAwayScore());
        assertEquals(3, game.getTotalScore());
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent game")
    void shouldThrowExceptionWhenUpdatingNonExistentGame() {
        GameNotFoundException exception = assertThrows(
                GameNotFoundException.class,
                () -> scoreBoard.updateScore("Brazil", "Argentina", 1, 0)
        );
        assertEquals("Game between Brazil and Argentina not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when updating with negative scores")
    void shouldThrowExceptionWhenUpdatingWithNegativeScores() {
        scoreBoard.startGame("Brazil", "Argentina");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.updateScore("Brazil", "Argentina", -1, 0)
        );
        assertEquals("Scores cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when updating with null team names")
    void shouldThrowExceptionWhenUpdatingWithNullTeamNames() {
        scoreBoard.startGame("Brazil", "Argentina");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.updateScore(null, "Argentina", 1, 0)
        );
        assertEquals("Team names cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should finish game successfully")
    void shouldFinishGameSuccessfully() {
        scoreBoard.startGame("Brazil", "Argentina");
        scoreBoard.startGame("Spain", "France");
        assertEquals(2, scoreBoard.getSummary().size());

        scoreBoard.finishGame("Brazil", "Argentina");

        List<Game> summary = scoreBoard.getSummary();
        assertEquals(1, summary.size());
        assertEquals("Spain", summary.get(0).getHomeTeam());
    }

    @Test
    @DisplayName("Should throw exception when finishing non-existent game")
    void shouldThrowExceptionWhenFinishingNonExistentGame() {
        GameNotFoundException exception = assertThrows(
                GameNotFoundException.class,
                () -> scoreBoard.finishGame("Brazil", "Argentina")
        );
        assertEquals("Game between Brazil and Argentina not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when finishing with null team names")
    void shouldThrowExceptionWhenFinishingWithNullTeamNames() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.finishGame("Brazil", null)
        );
        assertEquals("Team names cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should return empty summary when no games")
    void shouldReturnEmptySummaryWhenNoGames() {
        List<Game> summary = scoreBoard.getSummary();
        assertTrue(summary.isEmpty());
    }

    @Test
    @DisplayName("Should return games ordered by total score descending")
    void shouldReturnGamesOrderedByTotalScoreDescending() throws InterruptedException {
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.updateScore("Mexico", "Canada", 0, 5); // Total: 5

        Thread.sleep(1); // Ensure different timestamps

        scoreBoard.startGame("Spain", "Brazil");
        scoreBoard.updateScore("Spain", "Brazil", 10, 2); // Total: 12

        Thread.sleep(1);

        scoreBoard.startGame("Germany", "France");
        scoreBoard.updateScore("Germany", "France", 2, 2); // Total: 4

        Thread.sleep(1);

        scoreBoard.startGame("Uruguay", "Italy");
        scoreBoard.updateScore("Uruguay", "Italy", 6, 6); // Total: 12

        Thread.sleep(1);

        scoreBoard.startGame("Argentina", "Australia");
        scoreBoard.updateScore("Argentina", "Australia", 3, 1); // Total: 4

        List<Game> summary = scoreBoard.getSummary();

        assertEquals(5, summary.size());

        // First should be Uruguay vs Italy (total 12, most recent)
        assertEquals("Uruguay", summary.get(0).getHomeTeam());
        assertEquals("Italy", summary.get(0).getAwayTeam());
        assertEquals(12, summary.get(0).getTotalScore());

        // Second should be Spain vs Brazil (total 12, less recent)
        assertEquals("Spain", summary.get(1).getHomeTeam());
        assertEquals("Brazil", summary.get(1).getAwayTeam());
        assertEquals(12, summary.get(1).getTotalScore());

        // Third should be Mexico vs Canada (total 5)
        assertEquals("Mexico", summary.get(2).getHomeTeam());
        assertEquals("Canada", summary.get(2).getAwayTeam());
        assertEquals(5, summary.get(2).getTotalScore());

        // Fourth should be Argentina vs Australia (total 4, most recent)
        assertEquals("Argentina", summary.get(3).getHomeTeam());
        assertEquals("Australia", summary.get(3).getAwayTeam());
        assertEquals(4, summary.get(3).getTotalScore());

        // Fifth should be Germany vs France (total 4, less recent)
        assertEquals("Germany", summary.get(4).getHomeTeam());
        assertEquals("France", summary.get(4).getAwayTeam());
        assertEquals(4, summary.get(4).getTotalScore());
    }

    @Test
    @DisplayName("Should handle multiple operations correctly")
    void shouldHandleMultipleOperationsCorrectly() {
        scoreBoard.startGame("Brazil", "Argentina");
        scoreBoard.startGame("Spain", "France");
        scoreBoard.updateScore("Brazil", "Argentina", 3, 2);
        scoreBoard.updateScore("Spain", "France", 1, 1);
        scoreBoard.finishGame("Spain", "France");
        scoreBoard.startGame("Germany", "Italy");
        scoreBoard.updateScore("Germany", "Italy", 2, 0);

        List<Game> summary = scoreBoard.getSummary();
        assertEquals(2, summary.size());

        assertEquals("Brazil", summary.get(0).getHomeTeam()); // Total: 5
        assertEquals("Germany", summary.get(1).getHomeTeam()); // Total: 2
    }

    @Test
    @DisplayName("Should create exact example from requirements")
    void shouldCreateExactExampleFromRequirements() throws InterruptedException {
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.updateScore("Mexico", "Canada", 0, 5);
        Thread.sleep(1);

        scoreBoard.startGame("Spain", "Brazil");
        scoreBoard.updateScore("Spain", "Brazil", 10, 2);
        Thread.sleep(1);

        scoreBoard.startGame("Germany", "France");
        scoreBoard.updateScore("Germany", "France", 2, 2);
        Thread.sleep(1);

        scoreBoard.startGame("Uruguay", "Italy");
        scoreBoard.updateScore("Uruguay", "Italy", 6, 6);
        Thread.sleep(1);

        scoreBoard.startGame("Argentina", "Australia");
        scoreBoard.updateScore("Argentina", "Australia", 3, 1);

        List<Game> summary = scoreBoard.getSummary();

        assertEquals(5, summary.size());

        assertEquals("Uruguay 6 - Italy 6", summary.get(0).toString());
        assertEquals("Spain 10 - Brazil 2", summary.get(1).toString());
        assertEquals("Mexico 0 - Canada 5", summary.get(2).toString());
        assertEquals("Argentina 3 - Australia 1", summary.get(3).toString());
        assertEquals("Germany 2 - France 2", summary.get(4).toString());
    }

    @Test
    @DisplayName("Should handle score updates multiple times")
    void shouldHandleScoreUpdatesMultipleTimes() {
        scoreBoard.startGame("Brazil", "Argentina");

        scoreBoard.updateScore("Brazil", "Argentina", 1, 0);
        assertEquals(1, scoreBoard.getSummary().get(0).getHomeScore());
        assertEquals(0, scoreBoard.getSummary().get(0).getAwayScore());

        scoreBoard.updateScore("Brazil", "Argentina", 1, 1);
        assertEquals(1, scoreBoard.getSummary().get(0).getHomeScore());
        assertEquals(1, scoreBoard.getSummary().get(0).getAwayScore());

        scoreBoard.updateScore("Brazil", "Argentina", 2, 1);
        assertEquals(2, scoreBoard.getSummary().get(0).getHomeScore());
        assertEquals(1, scoreBoard.getSummary().get(0).getAwayScore());
    }

    @Test
    @DisplayName("Should handle zero scores in sorting")
    void shouldHandleZeroScoresInSorting() throws InterruptedException {
        scoreBoard.startGame("Team1", "Team2");
        scoreBoard.updateScore("Team1", "Team2", 0, 0); // Total: 0
        Thread.sleep(1);

        scoreBoard.startGame("Team3", "Team4");
        scoreBoard.updateScore("Team3", "Team4", 1, 0); // Total: 1
        Thread.sleep(1);

        scoreBoard.startGame("Team5", "Team6");
        scoreBoard.updateScore("Team5", "Team6", 0, 0); // Total: 0, more recent

        List<Game> summary = scoreBoard.getSummary();

        assertEquals("Team3", summary.get(0).getHomeTeam()); // Total: 1
        assertEquals("Team5", summary.get(1).getHomeTeam()); // Total: 0, more recent
        assertEquals("Team1", summary.get(2).getHomeTeam()); // Total: 0, less recent
    }
}
