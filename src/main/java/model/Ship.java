package model;

import java.util.List;

/**
 * Represents a ship in a game of BattleSalvo.
 */
public class Ship {

  private ShipType ship; // this ship's type
  private List<Coord> shipCoords; // this ship's location
  private boolean sunk; // true if this ship is sunk, false if otherwise

  /**
   * Creates a Ship object.
   *
   * @param type - The type of ship for this Ship.
   * @param coords - The list of coordinates this Ship populates.
   * @param sunk - Whether this Ship is sunk or not.
   */
  public Ship(ShipType type, List<Coord> coords, boolean sunk) {

    this.ship = type;
    this.sunk = sunk;
    this.shipCoords = coords;

  }

  public List<Coord> getShipCoords() {
    return shipCoords;
  }

  public ShipType getShip() {
    return ship;
  }

  /**
   * Gets this Ship's sunk boolean value.
   *
   * @return - This ship's sunk boolean.
   */
  public boolean getSunk() {
    return sunk;
  }

  /**
   * Updates this ship's sunk boolean based on if it is completely hit or not.
   */
  public void updateSunk() {
    for (Coord coord : shipCoords) {
      if (!coord.getStatus().equals(CoordStatus.HIT)) {
        sunk = false;
        break;
      } else {
        sunk = true;
      }
    }
  }

  /**
   * Compares this ship to the parameter ship, and returns true if they are the same, false
   * otherwise.
   *
   * @param s2 - Ship being compared to this ship.
   * @return - Boolean representing whether this ship and given ship are the same or not.
   */
  public boolean compareShips(Ship s2) {

    boolean sameCoords = true;
    int i = 0;

    for (Coord c : this.shipCoords) {
      Coord s2c;
      try {
        s2c = s2.shipCoords.get(i);
      } catch (Exception e) {
        break;
      }

      if (c.getStatus() != s2c.getStatus() || c.getX() != s2c.getX() || c.getY() != s2c.getY()) {
        sameCoords = false;
      }

      i++;
    }

    return this.sunk == s2.sunk
        && this.ship == s2.ship
        && sameCoords;
  }
}
