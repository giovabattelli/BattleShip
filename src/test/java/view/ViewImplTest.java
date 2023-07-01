package view;

import static model.ShipType.BATTLESHIP;
import static model.ShipType.CARRIER;
import static model.ShipType.DESTROYER;
import static model.ShipType.SUBMARINE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import model.AbstractPlayer;
import model.Board;
import model.RealPlayer;
import model.ShipType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ViewImplTest {

  Readable input;
  Readable input2;
  Appendable output;
  MockAppendable mockAppendable;
  ViewImpl view;
  ViewImpl view2;

  Board realBoardTest;
  Board oppBoardTest;

  AbstractPlayer player;

  Map<ShipType, Integer> specsTest = new HashMap<>();

  @BeforeEach
  void setup() {

    input2 = new StringReader("");
    mockAppendable = new MockAppendable();
    view2 = new ViewImpl(input2, mockAppendable, new Scanner(input2));


    input = new StringReader("");
    output = new StringBuilder();
    view = new ViewImpl(input, output, new Scanner(input));

    specsTest = new HashMap<>();
    specsTest.put(CARRIER, 1);
    specsTest.put(BATTLESHIP, 1);
    specsTest.put(DESTROYER, 1);
    specsTest.put(SUBMARINE, 1);

    realBoardTest = new Board(8, 8);
    oppBoardTest = new Board(8, 8);

    player = new RealPlayer("testPlayer", view, realBoardTest, oppBoardTest, new Random());

  }

  @Test
  void displayString() {
    view.displayString("nuts");
    assertEquals("nuts", output.toString());
    assertDoesNotThrow(() -> view2.displayString("balls!!! :D"));
  }

  @Test
  void displayBoard() {

    String expectedString = """


        testPlayer

        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s""";

    view.displayBoard(player, realBoardTest.getCoords());

    assertEquals(expectedString, output.toString());
  }

  @Test
  void displayOpponentBoard() {

    String expectedString = """


        testPlayer

        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s
        ~  ~  ~  ~  ~  ~  ~  ~ \s""";

    player.setup(8, 8, specsTest);

    view.displayOpponentBoard(player, realBoardTest.getCoords());

    // even if the same board is called, there are now ships on the board, but nothing will show
    // because it's opponent board
    assertEquals(expectedString, output.toString());
  }
}