package model;

import static model.CoordStatus.HIT;
import static model.CoordStatus.MISS;
import static model.CoordStatus.UNKNOWN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import view.ViewImpl;

/**
 * Represents a board in a game of BattleSalvo
 */
public class Board {

  protected Coord[][] coords;
  private boolean addToArray = true;
  private ArrayList<Coord> arrayOfCoords = new ArrayList<>();

  /**
   * Creates a Board object.
   *
   * @param height - Height of this Board.
   * @param width  - Width of this Board.
   */
  public Board(int height, int width) {

    this.coords = new Coord[height][width];

    initCoords();

  }

  /**
   * Creates a board object with all null fields.
   */
  public Board() {

  }

  /**
   * Displays the opponents board with hits and misses.
   *
   * @param aiPlayer - The opponent.
   * @param view     - Object in which information is sent to appropriate appendable.
   */
  public void displayOpponentBoard(AbstractPlayer aiPlayer, ViewImpl view) {
    view.displayOpponentBoard(aiPlayer, coords);
  }

  /**
   * Displays the console player's board with ships, hits and misses.
   *
   * @param realUser - The console player.
   * @param view     - Object in which information is sent to appropriate appendable.
   */
  public void displayBoard(AbstractPlayer realUser, ViewImpl view) {
    view.displayBoard(realUser, coords);
  }

  /**
   * Initializes the coord objects in this 2D array of coords.
   */
  private void initCoords() {

    for (int i = 0; i < coords.length; i++) {
      for (int j = 0; j < coords[i].length; j++) {
        coords[i][j] = new Coord(j, i, UNKNOWN);
      }
    }

  }

  /**
   * Produces a list of coords of locations the opponent wants to shoot at on this board.
   *
   * @param shots - The number of shots available.
   * @param view  - Object in which information is sent to appropriate appendable.
   * @return - List of coords on this board.
   */
  public List<Coord> takeRealPlayerShots(int shots, ViewImpl view) {
    return view.shotsFromUser(shots, coords);
  }

  /**
   * Produces a list of coords of locations the AI want to shoot at on this board.
   *
   * @param shots  - The number of shots available.
   * @param random - A random object to randomize shot selection.
   * @return - List of coords on this board.
   */
  public List<Coord> takeArtificialPlayerShots(int shots, Random random) {

    List<Coord> finalShots = new ArrayList<>();

    if (addToArray) {
      for (Coord[] row : coords) {
        arrayOfCoords.addAll(Arrays.asList(row));
      }
      addToArray = false;
    }

    if (shots < arrayOfCoords.size()) {
      for (int i = 0; i < shots; i++) {
        int randomIndex = random.nextInt(arrayOfCoords.size());
        Coord randomCoord = arrayOfCoords.get(randomIndex);
        finalShots.add(randomCoord);
        arrayOfCoords.remove(randomCoord);
      }
    } else {
      finalShots.addAll(arrayOfCoords);
      arrayOfCoords.clear();
    }

    return finalShots;
  }

  /**
   * Adds given shot (coord) to the given shotsThatHitBoard if the given shot hits a ship or an
   * already hit location on this board.
   *
   * @param shotsThatHitBoard - List of coords that hit ships and already hit coords on this board.
   * @param shot              - Opponent shot coordinate location this board.
   */
  public void reportBoardDamage(List<Coord> shotsThatHitBoard, Coord shot, List<Ship> listOfShips) {

    List<Coord> allShipCoords = new ArrayList<>();

    for (Ship ship : listOfShips) {
      allShipCoords.addAll(ship.getShipCoords());
    }

    for (Coord coord : allShipCoords) {
      if (coord.getX() == shot.getX() && coord.getY() == shot.getY()) {
        shotsThatHitBoard.add(shot);
        coord.changeStatus(HIT);
        break;
      }
    }

  }

  /**
   * Gets this board's 2D array of coords.
   *
   * @return - 2D array of coords.
   */
  public Coord[][] getCoords() {
    return coords;
  }

  /**
   * Changes the dimensions of this Board.
   *
   * @param height - Desired height of this board.
   * @param width  - Desired width of this board.
   */
  public void changeDimensions(int height, int width) {
    this.coords = new Coord[height][width];
    initCoords();
  }
}
