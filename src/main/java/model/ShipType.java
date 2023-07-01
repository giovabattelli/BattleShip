package model;

/**
 * Represents a type of ship in a game of BattleSalvo
 */
public enum ShipType {

  CARRIER(6, CoordStatus.CARRIERSTATUS),
  BATTLESHIP(5, CoordStatus.BATTLESHIPSTATUS),
  DESTROYER(4, CoordStatus.DESTROYERSTATUS),
  SUBMARINE(3, CoordStatus.SUBMARINESTATUS);

  final int size;
  final CoordStatus coordStatus;

  /**
   * Creates a ShipType enumeration with the given parameters.
   *
   * @param size - The size of this ShipType.
   * @param coordStatus - The associated CoordStatus of this shipType.
   */
  ShipType(int size, CoordStatus coordStatus) {
    this.size = size;
    this.coordStatus = coordStatus;
  }

  /**
   * Gets the size of this ship type.
   *
   * @return - An integer representing the length of this ship type.
   */
  public int getSize() {
    return size;
  }

  /**
   * Gets the associated coord status of this ship type.
   *
   * @return - a coord status associated with this ship type.
   */
  public CoordStatus getStatus() {
    return coordStatus;
  }

}
