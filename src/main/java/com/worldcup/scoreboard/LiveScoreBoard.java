package com.worldcup.scoreboard;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            throw new GameAlreadyExistsException(homeTeam, awayTeam);
        }

        Game game = new Game(homeTeam, awayTeam);
        games.put(gameKey, game);
    }

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        validateTeamNames(homeTeam, awayTeam);

        String gameKey = createGameKey(homeTeam, awayTeam);
        Game game = games.get(gameKey);

        if (game == null) {
            throw new GameNotFoundException(homeTeam, awayTeam);
        }

        game.updateScore(homeScore, awayScore);
    }

    @Override
    public void finishGame(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);

        String gameKey = createGameKey(homeTeam, awayTeam);

        if (!games.containsKey(gameKey)) {
            throw new GameNotFoundException(homeTeam, awayTeam);
        }

        games.remove(gameKey);
    }

    @Override
    public List<Game> getSummary() {
        return games.values()
                .stream()
                .sorted(this::compareGamesForSummary)
                .collect(Collectors.toList());
    }

    private int compareGamesForSummary(Game game1, Game game2) {
        int totalScoreComparison = Integer.compare(game2.getTotalScore(), game1.getTotalScore());
        if (totalScoreComparison != 0) {
            return totalScoreComparison;
        }
        // When total score is equal, most recently added comes first
        return game2.getCreatedAt().compareTo(game1.getCreatedAt());
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
