# Server Battleship
- Play a game of battleship (with modified game rules) using your AI against a server's AI (run on your computer)
- Host: 0.0.0.0
- Port: 35001
- Built while carefully following object-oriented design SOLID principles, for practice

## Rules:

### Fleet Size
- First the server will decide an arbitrary board size in which the height and width of the board can be between 6 and 15 cells inclusive.
- The total fleet size may not exceed the smaller board dimension, but must have at least one of each boat type. Therefore, for a board of size 8x11, there should be no more than 8 total boats. Fleet size and boat types will be identical between players.
- There are Carriers (size 6), Battleships (size 5), Destroyers (size 4) and Submarines (size 3).

### Number of Shots
- For each turn, each AI launches one missile per non-sunk boat remaining in their fleet. For example, if a player has 3 remaining ships in their fleet, that player would launch 3 missiles. At the same point in time, if the opponent AI had 5 ships remaining, they would be able to launch 5 missiles.

### Shooting Order
- Both players select their shots (target locations), and the shots are exchanged simultaneously. Information about hits is then exchanged through the parsing and serializing of JSON information, surviving ships are updated, and the process is repeated until one (or both) players have no more surviving ships. Importantly, this means some games will end in ties!
- Since this version of battleship involves 2 AI playing against each other, a match will not last more than a second most of the time.

- The steps for a shooting stage of a game of terminal battleship are outlined below:
1. Both Players shoot their shots
2. Both Players receive the incoming shots that their opponent fired
3. Both Players update their ships accordingly, and communicate which of the incoming shots hit
4. Repeat

# Have fun!

This template includes several additional tools:
1. Gradle Build Automation
1. JaCoCo for Test Coverage
1. CheckStyle for Code Style Checks
