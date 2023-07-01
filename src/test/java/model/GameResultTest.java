package model;

import static model.GameResult.LOSE;
import static model.GameResult.TIE;
import static model.GameResult.WIN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GameResultTest {

  @Test
  void getGameOutcomeTest() {
    assertEquals("won", WIN.getGameOutcome());
    assertEquals("lost", LOSE.getGameOutcome());
    assertEquals("tied", TIE.getGameOutcome());
  }
}