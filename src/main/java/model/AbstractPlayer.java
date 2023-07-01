package model;

import static model.CoordStatus.HIT;
import static model.CoordStatus.MISS;
import static model.ShipType.BATTLESHIP;
import static model.ShipType.CARRIER;
import static model.ShipType.DESTROYER;
import static model.ShipType.SUBMARINE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import view.ViewImpl;

/**
 * Represents a player in a game of BattleSalvo.
 */
public abstract class AbstractPlayer implements Player {

  protected String userName;
  private Board board;
  protected Board otherBoard;
  protected Random random;
  protected ViewImpl view;
  private List<Ship> listOfShips;

  /**
   * Creates an AbstractPlayer with the given information.
   *
   * @param name - Name of this player.
   * @param view - Object that has input and output and handles them accordingly.
   * @param board - This player's board in a game of BattleSalvo.
   * @param other - The opponent's board in a game of BattleSalvo.
   * @param random - A random object.
   */
  public AbstractPlayer(String name, ViewImpl view, Board board, Board other, Random random) {
    this.userName = name;
    this.view = view;
    this.random = random;
    this.board = board;
    this.otherBoard = other;
  }

  /**
   * Get this player's name.
   *
   * @return - String representing this player's name.
   */
  @Override
  public String name() {
    return userName;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> ships = new ArrayList<>();
    ShipType[] shipTypes = new ShipType[] {CARRIER, BATTLESHIP, DESTROYER, SUBMARINE};

    for (ShipType s : shipTypes) {
      int numberOfShips = specifications.get(s);
      for (int i = 0; i < numberOfShips; i++) {
        Ship newShip = new Ship(s, initShipCoords(s), false);
        ships.add(newShip);
      }
    }

    listOfShips = ships;
    return listOfShips;
  }

  /**
   * Returns a list of coordinates that are vertically or horizontally connected on this board.
   *
   * @param shipType - The type of ship from which coordinates will be set.
   * @return - A list of coordinates.
   */
  private List<Coord> initShipCoords(ShipType shipType) {

    List<Coord> horiCoords = new ArrayList<>();
    List<Coord> vertCoords = new ArrayList<>();
    List<Coord> finalShipCoords = new ArrayList<>();
    boolean randomBool = random.nextBoolean();

    filterCoords(horiCoords, vertCoords, shipType.getSize());

    if (horiCoords.size() == 0) {
      Coord vertCoord = vertCoords.get(random.nextInt(vertCoords.size()));
      for (int i = 0; i < shipType.getSize(); i++) {
        finalShipCoords.add(board.coords[vertCoord.getY() + i][vertCoord.getX()]);
      }
    } else if (vertCoords.size() == 0) {
      Coord horiCoord = horiCoords.get(random.nextInt(horiCoords.size()));
      for (int i = 0; i < shipType.getSize(); i++) {
        finalShipCoords.add(board.coords[horiCoord.getY()][horiCoord.getX() + i]);
      }
    } else {
      Coord horiCoord = horiCoords.get(random.nextInt(horiCoords.size()));
      Coord vertCoord = vertCoords.get(random.nextInt(vertCoords.size()));
      for (int i = 0; i < shipType.getSize(); i++) {
        if (randomBool) {
          finalShipCoords.add(board.coords[horiCoord.getY()][horiCoord.getX() + i]);
        } else {
          finalShipCoords.add(board.coords[vertCoord.getY() + i][vertCoord.getX()]);
        }
      }
    }

    changeStatus(finalShipCoords, shipType);

    return finalShipCoords;
  }

  /**
   * Updates the list of vertical coordinates that have space vertically, and the list of horizontal
   * coordinates that have space horizontally, (number of spaces equivalent to the shipSize).
   *
   * @param hori     - The list of coords that have space horizontally given a ship size.
   * @param vert     - The list of coords that have space vertically given a ship size.
   * @param shipSize - The size of the ship; how many spaces need to be open vertically or
   *                 horizontally from a coordinate on a board.
   */
  private void filterCoords(List<Coord> hori, List<Coord> vert, int shipSize) {
    for (int i = 0; i < board.coords.length; i++) {
      for (int j = 0; j < board.coords[i].length - shipSize + 1; j++) {
        if (enoughHorizontalSpace(board.coords[i][j], shipSize)) {
          hori.add(board.coords[i][j]);
        }
      }
    }
    for (int i = 0; i < board.coords.length - shipSize + 1; i++) {
      for (int j = 0; j < board.coords[i].length; j++) {
        if (enoughVerticalSpace(board.coords[i][j], shipSize)) {
          vert.add(board.coords[i][j]);
        }
      }
    }
  }

  /**
   * Checks if the current coord has enough spoace equivalent to the given shipSize horizontally.
   *
   * @param current  - A coord.
   * @param shipSize - The size of a ship.
   * @return boolean - Whether or not there is space horizontally next to the current given coord.
   */
  private boolean enoughHorizontalSpace(Coord current, int shipSize) {
    boolean spaceAvailable = true;

    for (int i = 0; i < shipSize; i++) {
      if (!(board.coords[current.getY()][current.getX() + i].getStatus() == CoordStatus.UNKNOWN)) {
        spaceAvailable = false;
        break;
      }
    }

    return spaceAvailable;
  }

  /**
   * Checks if the current coord has enough space equivalent to the given shipSize vertically.
   *
   * @param current  - A coord.
   * @param shipSize - The size of a ship.
   * @return boolean - Whether or not there is space vertically next to the current given coord.
   */
  private boolean enoughVerticalSpace(Coord current, int shipSize) {
    boolean spaceAvailable = true;

    for (int i = 0; i < shipSize; i++) {
      if (!(board.coords[current.getY() + i][current.getX()].getStatus() == CoordStatus.UNKNOWN)) {
        spaceAvailable = false;
        break;
      }
    }

    return spaceAvailable;
  }

  /**
   * Updates the status of each coord in the given list of coords based on the given shipType.
   *
   * @param coords   - A list of coords adjacent to each other.
   * @param shipType - shipType populating each coord in the given list of coords.
   */
  private void changeStatus(List<Coord> coords, ShipType shipType) {
    for (Coord c : coords) {
      c.changeStatus(shipType.getStatus());
    }
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *         ship on this board.
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {

    List<Coord> shotsThatHitBoard = new ArrayList<>();

    for (Coord coord : opponentShotsOnBoard) {
      board.reportBoardDamage(shotsThatHitBoard, coord, listOfShips);
      if (!shotsThatHitBoard.contains(coord)) {
        coord.changeStatus(MISS);
      }
    }

    updateSunkShips(listOfShips);

    return shotsThatHitBoard;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

    int shotsHit = shotsThatHitOpponentShips.size();

    if (shotsHit == 0) {
      view.displayString("\n" + userName + " didn't hit anything! :(\n");
    } else {
      view.displayString("\n" + userName + " hit " + shotsHit + " shots! :D\n");
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {

  }

  /**
   * Updates the number of shots a player can fire based on the player's number of remaining ships
   * that are not sunk.
   */
  protected int shotsAvailable() {
    int possibleShots = 0;

    for (Ship s : listOfShips) {
      if (!s.getSunk()) {
        possibleShots++;
      }
    }

    return possibleShots;
  }

  /**
   * Updates the status of one or more ships in the given list of ships if any are now sunk.
   *
   * @param ships - A list of ships.
   */
  private void updateSunkShips(List<Ship> ships) {
    for (Ship ship : ships) {
      ship.updateSunk();
    }
  }

}
