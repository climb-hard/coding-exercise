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

### Next steps

- Add some error handling examples
- Maybe improve the documentation
- Build a demo application that shows the exact example from the requirements
- Final testing to make sure everything works as expected
