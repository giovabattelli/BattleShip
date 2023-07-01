package model;

/**
 * The possible end game results of a game
 */
public enum GameResult {

  WIN("won"),
  LOSE("lost"),
  TIE("tied");

  private final String gameOutcome;

  /**
   * Creates a GameResult enumeration with the given gameOutcome.
   *
   * @param gameOutcome - The message associated with a GameResult.
   */
  GameResult(String gameOutcome) {
    this.gameOutcome = gameOutcome;
  }

  /**
   * Get the String game outcome of this GameResult.
   *
   * @return - String representing this game result.
   */
  public String getGameOutcome() {
    return gameOutcome;
  }

}
