package model;

import static model.ShipType.BATTLESHIP;
import static model.ShipType.CARRIER;
import static model.ShipType.DESTROYER;
import static model.ShipType.SUBMARINE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.ViewImpl;

class ArtificialPlayerTest {

  private Map<ShipType, Integer> specsTest;

  private AbstractPlayer aiPlayerTest;
  private AbstractPlayer realPlayerTest;
  Board aiBoard;
  Board playerBoard;
  List<Ship> aiPlayerShips;
  List<Ship> realPlayerShips;

  @BeforeEach
  void setup() {

    specsTest = new HashMap<>();
    specsTest.put(CARRIER, 1);
    specsTest.put(BATTLESHIP, 1);
    specsTest.put(DESTROYER, 1);
    specsTest.put(SUBMARINE, 1);

  }

  @Test
  void takeShotsTest() {

    Appendable output = new StringBuilder();
    Readable input = new StringReader(
        "0 0" + System.lineSeparator()
            + "0 1" + System.lineSeparator()
            + "0 2" + System.lineSeparator()
            + "0 3" + System.lineSeparator());
    ViewImpl viewer = new ViewImpl(input, output, new Scanner(input));

    aiBoard = new Board(6, 6);
    playerBoard = new Board(6, 6);
    aiPlayerTest =
        new ArtificialPlayer("aiTestPlayer", viewer, aiBoard, playerBoard, new Random(1));
    realPlayerTest =
        new RealPlayer("realTestPlayer", viewer, playerBoard, aiBoard, new Random(2));

    aiPlayerShips = aiPlayerTest.setup(15, 15, specsTest);
    realPlayerShips = realPlayerTest.setup(15, 15, specsTest);

    List<Coord> shots = aiPlayerTest.takeShots();

    assertEquals(1, shots.get(0).getX());
    assertEquals(4, shots.get(0).getY());

    assertEquals(4, shots.get(1).getX());
    assertEquals(5, shots.get(1).getY());

    assertEquals(0, shots.get(2).getX());
    assertEquals(5, shots.get(2).getY());

    assertEquals(0, shots.get(3).getX());
    assertEquals(4, shots.get(3).getY());


  }
}