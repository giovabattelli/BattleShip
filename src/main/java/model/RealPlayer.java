package model;

import java.util.List;
import java.util.Random;
import view.ViewImpl;

/**
 * Represents a console player in a game of BattleSalvo
 */
public class RealPlayer extends AbstractPlayer {

  /**
   * Creates a real player object.
   *
   * @param user - The String name of this real player.
   * @param view - The object from which input can be taken and output can be sent to.
   * @param board - This real player's board.
   * @param other - The opponent of this real player in a game of BattleSalvo.
   * @param random - A random object.
   */
  public RealPlayer(String user, ViewImpl view, Board board, Board other, Random random) {
    super(user, view, board, other, random);
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return The locations of shots on the opponent's board.
   */
  @Override
  public List<Coord> takeShots() {
    int shots = shotsAvailable();
    view.displayString("\n" + userName + ": " + shots + " shots available.\n");
    return otherBoard.takeRealPlayerShots(shots, view);
  }

}
