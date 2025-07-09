package com.worldcup.scoreboard;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String homeTeam, String awayTeam) {
        super(String.format("Game between %s and %s not found", homeTeam, awayTeam));
    }
}
