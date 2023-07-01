package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.AbstractPlayer;
import model.Coord;

/**
 * Represents the console viewing of a game of BattleSalvo.
 */
public class ViewImpl implements ViewInterface {

  private Readable input;
  private Appendable output;
  private Scanner sc;

  /**
   * Creates an object that allows input and output from/to the given locations.
   *
   * @param output - Location of where output goes.
   */
  public ViewImpl(Readable input, Appendable output, Scanner scanner) {
    this.input = input;
    this.output = output;
    this.sc = scanner;
  }

  /**
   * Creates a ViewImpl object with null fields.
   */
  public ViewImpl() {

  }

  /**
   * Appends the given string to this output.
   *
   * @param string - String to be appended.
   */
  @Override
  public void displayString(String string) {
    try {
      output.append(string);
    } catch (IOException e) {
      System.err.println("Unable to display text in displayString.");
    }
  }

  /**
   * Displays a player's board.
   *
   * @param player - A player that represents the current user.
   */
  public void displayBoard(AbstractPlayer player, Coord[][] coords) {

    String userName = player.name();

    displayString("\n\n" + userName + "\n");
    for (int i = 0; i < coords.length; i++) {
      displayString("\n");
      for (int j = 0; j < coords[i].length; j++) {
        displayString(coords[i][j].getStatus().getSymbol() + "  ");
      }
    }

  }

  /**
   * Displays only the hit, miss or unknown coords of the opponent's board.
   *
   * @param opponent - A player that represents the opponent.
   */
  public void displayOpponentBoard(AbstractPlayer opponent, Coord[][] coords) {

    String userName = opponent.name();

    displayString("\n\n" + userName + "\n");
    for (int i = 0; i < coords.length; i++) {
      displayString("\n");
      for (int j = 0; j < coords[i].length; j++) {
        String s = coords[i][j].getStatus().getSymbol();
        if (s.equals("H") || s.equals("M")) {
          displayString(s + "  ");
        } else {
          displayString("~" + "  ");
        }
      }
    }

  }

  /**
   * Receives input from user for shots on a board.
   *
   * @param shots  - Number of shots available to the user.
   * @param coords - The coords on the player's board.
   * @return - A list of coord.
   */
  public List<Coord> shotsFromUser(int shots, Coord[][] coords) {

    //Scanner sc = new Scanner(input);
    List<Coord> listOfShots = new ArrayList<>();

    displayString("\n" + "-".repeat(66) + "\n" + "Please enter " + shots + " unique shots:\n");

    while (sc.hasNextLine()) {
      try {
        for (int i = 0; i < shots; i++) {
          String[] inputs = sc.nextLine().split(" ");
          int x = Integer.parseInt(inputs[0]);
          int y = Integer.parseInt(inputs[1]);
          listOfShots.add(findMatching(x, y, coords));
        }
        break;
      } catch (Exception e) {
        listOfShots.clear();
        displayString(
            "\nInvalid shots. Please enter "
                + shots
                + " unique shots, [x, y], new line per shot:\n");
      }
    }

    return listOfShots;
  }

  /**
   * Given an x and a y value, it returns the corresponding coord in the parameter 2D array
   * of coords.
   *
   * @param x      - An x value.
   * @param y      - A y value.
   * @param coords - A list of coordinates on a battleship board.
   * @return - The coord matching the parameter x and y integers.
   * @throws IllegalArgumentException - If no coordinate was found given an x and a y value.
   */
  private Coord findMatching(int x, int y, Coord[][] coords) throws IllegalArgumentException {
    for (int i = 0; i < coords.length; i++) {
      for (int j = 0; j < coords[i].length; j++) {
        if (coords[i][j].getX() == x && coords[i][j].getY() == y) {
          return coords[i][j];
        }
      }
    }

    throw new IllegalArgumentException("No such coordinate(s) present in opponent's board.");
  }

}
