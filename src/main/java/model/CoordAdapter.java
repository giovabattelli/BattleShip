package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an adapted version of a coord in the format a server would accept.
 */
public class CoordAdapter {

  private int x;
  private int y;

  /**
   * Creates a CoordAdapter object with the same x and y values of the given Coord.
   *
   * @param coord - The coord to be adapted.
   */
  public CoordAdapter(Coord coord) {
    this.x = coord.getX();
    this.y = coord.getY();
  }

  /**
   * Assigns Json values to each field in a CoordAdapter.
   *
   * @param x - A coord's x value.
   * @param y - A coord's y value.
   */
  @JsonCreator
  public CoordAdapter(
      @JsonProperty("x") int x,
      @JsonProperty("y") int y) {

  }

  /**
   * Gets this CoordAdapter's x value.
   *
   * @return - This CoordAdapter's x value.
   */
  public int getX() {
    return x;
  }

  /**
   * Gets this CoordAdapter's y value.
   *
   * @return - This CoordAdapter's y value.
   */
  public int getY() {
    return y;
  }
}
