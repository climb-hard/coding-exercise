package com.worldcup.scoreboard;

import java.time.Instant;
import java.util.Objects;

public class Game {
    private final String homeTeam;
    private final String awayTeam;
    private final Instant createdAt;
    private int homeScore;
    private int awayScore;

    public Game(String homeTeam, String awayTeam) {
        validateTeamName(homeTeam, "Home team");
        validateTeamName(awayTeam, "Away team");

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.createdAt = Instant.now();
    }

    private void validateTeamName(String teamName, String fieldName) {
        if (teamName == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
        if (teamName.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }

    public void updateScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getGameKey() {
        return homeTeam + " vs " + awayTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(homeTeam, game.homeTeam) && Objects.equals(awayTeam, game.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam);
    }

    @Override
    public String toString() {
        return String.format("%s %d - %s %d", homeTeam, homeScore, awayTeam, awayScore);
    }
}
