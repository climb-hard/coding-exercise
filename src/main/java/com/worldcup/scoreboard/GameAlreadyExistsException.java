package com.worldcup.scoreboard;

public class GameAlreadyExistsException extends RuntimeException {

    public GameAlreadyExistsException(String homeTeam, String awayTeam) {
        super(String.format("Game between %s and %s already exists", homeTeam, awayTeam));
    }
} 