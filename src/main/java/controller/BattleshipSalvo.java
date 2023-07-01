package controller;

import static model.ShipType.BATTLESHIP;
import static model.ShipType.CARRIER;
import static model.ShipType.DESTROYER;
import static model.ShipType.SUBMARINE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import model.AbstractPlayer;
import model.ArtificialPlayer;
import model.Board;
import model.Coord;
import model.RealPlayer;
import model.Ship;
import model.ShipType;
import view.ViewImpl;

/**
 * Controller for battleship salvo game that handles player interaction
 */
public class BattleshipSalvo implements Controller {

  private ViewImpl view;
  private int height;
  private int width;
  private Map<ShipType, Integer> specifications = new HashMap<>();
  private ShipType[] shipTypes;
  private Random random1;
  private Random random2;
  private Scanner sc;

  /**
   * Creates a controller object for a game of BattleSalvo in which a console player plays against
   * an AI player.
   *
   * @param input  - Readable input, from where input is coming from.
   * @param output - Appendable output, where the output goes.
   */
  public BattleshipSalvo(Readable input, Appendable output) {

    this.sc = new Scanner(input);
    this.view = new ViewImpl(input, output, sc);
    this.random1 = new Random();
    this.random2 = new Random();
    specifications.put(CARRIER, 0);
    specifications.put(BATTLESHIP, 0);
    specifications.put(DESTROYER, 0);
    specifications.put(SUBMARINE, 0);
    this.shipTypes = new ShipType[] {CARRIER, BATTLESHIP, DESTROYER, SUBMARINE};

  }

  /**
   * Creates a controller object for a game of BattleSalvo in which a console player plays against
   * an AI player, with seeded randoms.
   *
   * @param input  - Readable input, from where input is coming from.
   * @param output - Appendable output, where the output goes.
   * @param seed1  - Seed for seeding random in AI player (for testing).
   * @param seed2  - Seed for seeding random in real user console player (for testing).
   */
  public BattleshipSalvo(Readable input, Appendable output, int seed1, int seed2) {
    this(input, output);
    this.random1 = new Random(seed1);
    this.random2 = new Random(seed2);
  }

  /**
   * Runs a game of BattleSalvo in which a user interacts with the console to play against
   * an AI player.
   */
  public void run() {

    initBoard();
    initFleet();
    Board aiBoard = new Board(height, width);
    Board realBoard = new Board(height, width);
    AbstractPlayer aiPlayer = new ArtificialPlayer("AI", view, aiBoard, realBoard, random1);
    AbstractPlayer realUser = new RealPlayer("You", view, realBoard, aiBoard, random2);
    List<Ship> aiShips = aiPlayer.setup(height, width, specifications);
    List<Ship> realShips = realUser.setup(height, width, specifications);

    while (!(isGameOver(aiShips) || (isGameOver(realShips)))) {

      //aiBoard.displayBoard(aiPlayer, view);
      aiBoard.displayOpponentBoard(aiPlayer, view);
      realBoard.displayBoard(realUser, view);

      List<Coord> shotCoordsOnUserBoard = aiPlayer.takeShots();
      List<Coord> shotCoordsOnAiBoard = realUser.takeShots();

      // also updates the sunk status of each ship in each list of ships
      List<Coord> hitShipsOnAiBoard = aiPlayer.reportDamage(shotCoordsOnAiBoard);
      List<Coord> hitShipsOnUserBoard = realUser.reportDamage(shotCoordsOnUserBoard);

      realUser.successfulHits(hitShipsOnAiBoard);
      aiPlayer.successfulHits(hitShipsOnUserBoard);

    }

    endBattleSalvo(aiPlayer, realUser, isGameOver(aiShips), isGameOver(realShips));

  }

  /**
   * Displays the appropriate end game text.
   *
   * @param ai       - The AI player.
   * @param user     - The user playing from the console.
   * @param aiLost   - A boolean representing whether the AI has lost or not.
   * @param userLost - A boolean representing whether the real user has lost or not.
   */
  private void endBattleSalvo(
      AbstractPlayer ai, AbstractPlayer user, boolean aiLost, boolean userLost) {
    if (aiLost && userLost) {
      view.displayString("\n\ntie!\n");
    } else if (aiLost) {
      view.displayString("\n\n" + user.name() + " won!\n");
    } else if (userLost) {
      view.displayString("\n\n" + ai.name() + " won!\n");
    }
  }

  /**
   * Whether the BattleSalvo game is over or not for the given list of ships.
   *
   * @param ships - A list of ships.
   * @return - boolean that is returned based on if all the ships in a list of ships are sunk
   *         or if there is at least 1 ship that is not sunk.
   */
  private boolean isGameOver(List<Ship> ships) {
    boolean gameOver = true;

    for (Ship s : ships) {
      if (!s.getSunk()) {
        gameOver = false;
        break;
      }
    }

    return gameOver;
  }

  /**
   * Receives input from the user and initializes the board size for both players in a game of
   * BattleSalvo.
   */
  private void initBoard() {

    view.displayString(
        "Welcome to BattleSalvo.\nPlease enter a valid board height and width below:\n"
            + "-".repeat(66)
            + "\n");

    while (sc.hasNextLine()) {

      String[] inputs = sc.nextLine().split(" ");

      try {

        height = Integer.parseInt(inputs[0]);
        width = Integer.parseInt(inputs[1]);

        if (height < 6 || height > 15 || width < 6 || width > 15 || inputs.length > 2) {
          throw new Exception();
        } else {
          break;
        }

      } catch (Exception e) {
        view.displayString(
            "\nInvalid dimensions. The height and width of the\n"
                + "game must be in the range (6, 15) inclusive. Try again:\n"
                + "-".repeat(66)
                + "\n");
      }
    }
  }

  /**
   * Receives input from the user for fleet specifications  for both players in a game of
   * BattleSalvo.
   */
  private void initFleet() {
    int minBoardSize = Math.min(height, width);

    view.displayString(
        "\nPlease enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
            + "Remember, your fleet may not exceed size " + minBoardSize + ".\n"
            + "-".repeat(66)
            + "\n");

    while (sc.hasNextLine()) {
      String[] inputs = sc.nextLine().split(" ");
      try {
        int i = 0;
        int sum = 0;
        for (ShipType s : shipTypes) {
          int k = Integer.parseInt(inputs[i]);
          if (k < 1 || inputs.length > 4) {
            throw new Exception();
          }
          specifications.put(s, k);
          sum += specifications.get(s);
          i++;
        }
        if (sum > minBoardSize) {
          throw new Exception();
        }
        break;
      } catch (Exception e) {
        view.displayString(
            "\nInvalid fleet specifications.\n"
                + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]"
                + ".\nRemember, your fleet may not exceed size " + minBoardSize + ".\n"
                + "-".repeat(66) + "\n");
      }
    }
  }

}
