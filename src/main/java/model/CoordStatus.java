package model;

/**
 * Represents the possible status of a coord in BattleSalvo.
 */
public enum CoordStatus {

  UNKNOWN("~"),
  HIT("H"),
  MISS("M"),
  CARRIERSTATUS("C"),
  BATTLESHIPSTATUS("B"),
  DESTROYERSTATUS("D"),
  SUBMARINESTATUS("S");

  public final String symbol;

  /**
   * Creates a CoordStatus enumeration with the given symbol.
   *
   * @param symbol - A string representing a symbol of a coordinate.
   */
  CoordStatus(String symbol) {
    this.symbol = symbol;
  }

  /**
   * Gets this CoordStatus's symbol.
   *
   * @return - String of this Status's symbol.
   */
  public String getSymbol() {
    return symbol;
  }

}
