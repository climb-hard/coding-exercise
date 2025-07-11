package com.worldcup.scoreboard;

import java.util.List;

/**
 * Main interface for the scoreboard. Pretty straightforward - you can start games,
 * update scores, finish games and get a summary of what's happening.
 */
public interface ScoreBoard {

    /**
     * Starts a new game between two teams. Both teams start with 0-0.
     * Don't try to start the same game twice or it will throw an error.
     */
    void startGame(String homeTeam, String awayTeam);

    /**
     * Updates the score for a game that's already running.
     * Scores can't be negative (obviously).
     */
    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);

    /**
     * Finishes a game and removes it from the board.
     * Game needs to exist first or you'll get an error.
     */
    void finishGame(String homeTeam, String awayTeam);

    /**
     * Gets all current games sorted by total score (highest first).
     * If scores are the same, most recent game comes first.
     */
    List<Game> getSummary();
}
