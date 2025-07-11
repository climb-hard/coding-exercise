# coding-exercise

A simple library implementation for the Football World Cup Score Board

## Football World Cup Score Board:

You are working on a sports data company. And we would like you to develop a new
Live Football World Cup Score Board that shows matches and scores.

The boards support the following operations:

1. Start a game. When a game starts, it should capture (being initial score 0-0)

   a. Home team

   b. Away Team

1. Finish a game. It will remove a match from the scoreboard.

1. Update score. Receiving the pair score; home team score and away team score
   updates a game score

1. Get a summary of games by total score. Those games with the same total score
   will be returned ordered by the most recently added to our system.

   As an example, being the current data in the system:

   ` a. Mexico - Canada: 0 – 5 `

   ` b. Spain - Brazil: 10 – 2 `

   ` c. Germany - France: 2 – 2 `

   ` d. Uruguay - Italy: 6 – 6 `

   ` e. Argentina - Australia: 3 - 1 `

The summary would provide with the following information:

` a. Uruguay 6 - Italy 6 `

` b. Spain 10 - Brazil 2 `

` c. Mexico 0 - Canada 5 `

` d. Argentina 3 - Australia 1 `

`  e. Germany 2 - France 2 `

## Usage Examples

### Basic Usage

```java
ScoreBoard scoreBoard = new LiveScoreBoard();

// Start a new game
scoreBoard.startGame("Brazil", "Argentina");

// Update the score
scoreBoard.updateScore("Brazil", "Argentina", 2, 1);

// Get current games
List<Game> games = scoreBoard.getSummary();
System.out.println(games.get(0)); // prints: Brazil 2 - Argentina 1

// Finish the game
scoreBoard.finishGame("Brazil", "Argentina");
```

### The full example from the requirements

```java
ScoreBoard scoreBoard = new LiveScoreBoard();

// Start some games
scoreBoard.startGame("Mexico", "Canada");
scoreBoard.startGame("Spain", "Brazil");
scoreBoard.startGame("Germany", "France");
scoreBoard.startGame("Uruguay", "Italy");
scoreBoard.startGame("Argentina", "Australia");

// Update the scores
scoreBoard.updateScore("Mexico", "Canada", 0, 5);
scoreBoard.updateScore("Spain", "Brazil", 10, 2);
scoreBoard.updateScore("Germany", "France", 2, 2);
scoreBoard.updateScore("Uruguay", "Italy", 6, 6);
scoreBoard.updateScore("Argentina", "Australia", 3, 1);

// Get summary - returns result sorted by total score, ties are broken by most recent
List<Game> summary = scoreBoard.getSummary();
// You get:
// 1. Uruguay 6 - Italy 6 (total: 12)
// 2. Spain 10 - Brazil 2 (total: 12) 
// 3. Mexico 0 - Canada 5 (total: 5)
// 4. Argentina 3 - Australia 1 (total: 4)
// 5. Germany 2 - France 2 (total: 4)
```

### What happens when things go wrong

```java
// Trying to start the same game twice
scoreBoard.startGame("Team A", "Team B");
scoreBoard.startGame("Team A", "Team B"); // throws GameAlreadyExistsException

// Trying to update a game that doesn't exist
scoreBoard.updateScore("Random", "Team", 1, 0); // throws GameNotFoundException

// Negative scores
scoreBoard.updateScore("Team A", "Team B", -1, 0); // throws IllegalArgumentException
```

## Current Implementation Status

### First step - basic functionality (COMPLETE)

- Game class is complete with proper validation and timestamps
- ScoreBoard interface defines all the required methods
- LiveScoreBoard has basic implementation for starting games and getting summaries
- Can start new games with initial 0-0 score
- Basic validation for team names (null/empty checks)
- Prevents duplicate games between same teams
- Simple getSummary() returns list of active games
- All tests are passing

### Second step - score management (COMPLETE)

- updateScore() method now works with proper validation
- finishGame() method removes games from the board
- Added proper sorting logic for getSummary() (by total score desc, then by most recently added)
- Created custom exception classes (GameAlreadyExistsException, GameNotFoundException)
- Added tests for all edge cases
- The exact example from requirements now works correctly
- All tests are passing

### Final step - documentation and polish (COMPLETE)

- Added some basic documentation to the main API classes
- Cleaned up the README + added some examples of usage

## Running the code

Just use `mvn test` to run all tests. Need Java 17 and Maven to build this.
