package com.worldcup.scoreboard;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LiveScoreBoard implements ScoreBoard {

    private final Map<String, Game> games;

    public LiveScoreBoard() {
        this.games = new LinkedHashMap<>();
    }

    @Override
    public void startGame(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);

        String gameKey = createGameKey(homeTeam, awayTeam);

        if (games.containsKey(gameKey)) {
            throw new IllegalArgumentException("Game between " + homeTeam + " and " + awayTeam + " already exists");
        }

        Game game = new Game(homeTeam, awayTeam);
        games.put(gameKey, game);
    }

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        throw new UnsupportedOperationException("updateScore not implemented yet");
    }

    @Override
    public void finishGame(String homeTeam, String awayTeam) {
        throw new UnsupportedOperationException("finishGame not implemented yet");
    }

    @Override
    public List<Game> getSummary() {
        return new ArrayList<>(games.values());
    }

    private void validateTeamNames(String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Team names cannot be null");
        }
        if (homeTeam.trim().isEmpty() || awayTeam.trim().isEmpty()) {
            throw new IllegalArgumentException("Team names cannot be empty");
        }
    }

    private String createGameKey(String homeTeam, String awayTeam) {
        return homeTeam + " vs " + awayTeam;
    }
}
