package model;

import static model.Direction.HORIZONTAL;
import static model.Direction.VERTICAL;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Adapts the given ship into a ship with a start, length and direction.
 */
public class ShipAdapter {

  private CoordAdapter start;
  private int length;
  private Direction direction;

  /**
   * Creates an adapted version of the given ship.
   *
   * @param ship - The given ship that will be adapted.
   */
  public ShipAdapter(Ship ship) {
    this.length = ship.getShip().getSize();
    adaptShip(ship);
  }

  /**
   * The Json tags associated with this Ship.
   *
   * @param start - The starting coordinate of this Ship.
   * @param length - The length of this Ship.
   * @param direction - The direction of this Ship.
   */
  @JsonCreator
  public ShipAdapter(@JsonProperty("coord") CoordAdapter start,
                     @JsonProperty("length") int length,
                     @JsonProperty("direction") Direction direction) {

  }

  /**
   * Initializes the start field of this ShipAdapter based on the given Ship.
   *
   * @param ship - The ship from which the first coord will be taken.
   */
  private void adaptShip(Ship ship) {
    CoordAdapter start = new CoordAdapter(ship.getShipCoords().get(0));
    Coord secondCoord = ship.getShipCoords().get(1);
    this.start = start;
    this.direction = (start.getX() < secondCoord.getX()) ? HORIZONTAL : VERTICAL;
  }

  public CoordAdapter getStart() {
    return start;
  }

  public int getLength() {
    return length;
  }

  public Direction getDirection() {
    return direction;
  }

}


