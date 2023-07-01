package model;

import java.util.List;
import java.util.Random;
import view.ViewImpl;

/**
 * Represents an AI player in a game of BattleSalvo.
 */
public class ArtificialPlayer extends AbstractPlayer {

  /**
   * Creates an AI player object.
   *
   * @param user - The String name of this AI player.
   * @param view - The object from which input can be taken and output can be sent to.
   * @param board - This AI player's board.
   * @param other - The opponent of this AI player in a game of BattleSalvo.
   * @param random - A random object.
   */
  public ArtificialPlayer(String user, ViewImpl view, Board board, Board other, Random random) {
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
    view.displayString("\n\n" + userName + ": " + shots + " shots available.\n");
    return otherBoard.takeArtificialPlayerShots(shots, random);
  }

}
