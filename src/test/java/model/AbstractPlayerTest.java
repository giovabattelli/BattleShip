package model;

import static model.CoordStatus.BATTLESHIPSTATUS;
import static model.CoordStatus.CARRIERSTATUS;
import static model.CoordStatus.DESTROYERSTATUS;
import static model.CoordStatus.HIT;
import static model.CoordStatus.SUBMARINESTATUS;
import static model.CoordStatus.UNKNOWN;
import static model.ShipType.BATTLESHIP;
import static model.ShipType.CARRIER;
import static model.ShipType.DESTROYER;
import static model.ShipType.SUBMARINE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.ViewImpl;

class AbstractPlayerTest {

  private Readable input;
  private Appendable output;
  private ViewImpl viewer;

  private Map<ShipType, Integer> specsTest;

  private Coord aiCarr1;
  private Coord aiCarr2;
  private Coord aiCarr3;
  private Coord aiCarr4;
  private Coord aiCarr5;
  private Coord aiCarr6;
  List<Coord> aiCarrCoords;
  private Coord aiBattle1;
  private Coord aiBattle2;
  private Coord aiBattle3;
  private Coord aiBattle4;
  private Coord aiBattle5;
  List<Coord> aiBattleCoords;
  private Coord aiDest1;
  private Coord aiDest2;
  private Coord aiDest3;
  private Coord aiDest4;
  List<Coord> aiDestCoords;
  private Coord aiSub1;
  private Coord aiSub2;
  private Coord aiSub3;
  List<Coord> aiSubCoords;
  private Ship aiCarr;
  private Ship aiBattle;
  private Ship aiDest;
  private Ship aiSub;
  private Board aiBoard;
  private AbstractPlayer aiPlayerTest;
  private List<Ship> aiListOfshipsTest;

  private Coord realCarr1;
  private Coord realCarr2;
  private Coord realCarr3;
  private Coord realCarr4;
  private Coord realCarr5;
  private Coord realCarr6;
  List<Coord> realCarrCoords;
  private Coord realBattle1;
  private Coord realBattle2;
  private Coord realBattle3;
  private Coord realBattle4;
  private Coord realBattle5;
  List<Coord> realBattleCoords;
  private Coord realDest1;
  private Coord realDest2;
  private Coord realDest3;
  private Coord realDest4;
  List<Coord> realDestCoords;
  private Coord realSub1;
  private Coord realSub2;
  private Coord realSub3;
  List<Coord> realSubCoords;
  private Ship realCarr;
  private Ship realBattle;
  private Ship realDest;
  private Ship realSub;
  private Board playerBoard;
  private AbstractPlayer realPlayerTest;
  private List<Ship> realListOfShipsTest;

  @BeforeEach
  void setup() {

    input = new StringReader("input that changes in each method that uses input...");
    output = new StringBuilder();
    viewer = new ViewImpl(input, output, new Scanner(input));

    specsTest = new HashMap<>();
    specsTest.put(CARRIER, 1);
    specsTest.put(BATTLESHIP, 1);
    specsTest.put(DESTROYER, 1);
    specsTest.put(SUBMARINE, 1);

    // coords for ships in aiPlayerTest
    aiCarr1 = new Coord(0, 4, CARRIERSTATUS);
    aiCarr2 = new Coord(1, 4, CARRIERSTATUS);
    aiCarr3 = new Coord(2, 4, CARRIERSTATUS);
    aiCarr4 = new Coord(3, 4, CARRIERSTATUS);
    aiCarr5 = new Coord(4, 4, CARRIERSTATUS);
    aiCarr6 = new Coord(5, 4, CARRIERSTATUS);
    aiCarrCoords =
        new ArrayList<>(Arrays.asList(aiCarr1, aiCarr2, aiCarr3, aiCarr4, aiCarr5, aiCarr6));
    aiBattle1 = new Coord(0, 2, BATTLESHIPSTATUS);
    aiBattle2 = new Coord(1, 2, BATTLESHIPSTATUS);
    aiBattle3 = new Coord(2, 2, BATTLESHIPSTATUS);
    aiBattle4 = new Coord(3, 2, BATTLESHIPSTATUS);
    aiBattle5 = new Coord(4, 2, BATTLESHIPSTATUS);
    aiBattleCoords =
        new ArrayList<>(Arrays.asList(aiBattle1, aiBattle2, aiBattle3, aiBattle4, aiBattle5));
    aiDest1 = new Coord(5, 0, DESTROYERSTATUS);
    aiDest2 = new Coord(5, 1, DESTROYERSTATUS);
    aiDest3 = new Coord(5, 2, DESTROYERSTATUS);
    aiDest4 = new Coord(5, 3, DESTROYERSTATUS);
    aiDestCoords =
        new ArrayList<>(Arrays.asList(aiDest1, aiDest2, aiDest3, aiDest4));
    aiSub1 = new Coord(1, 5, SUBMARINESTATUS);
    aiSub2 = new Coord(2, 5, SUBMARINESTATUS);
    aiSub3 = new Coord(3, 5, SUBMARINESTATUS);
    aiSubCoords =
        new ArrayList<>(Arrays.asList(aiSub1, aiSub2, aiSub3));
    // ships for list of ships in aiPlayerTest
    aiCarr = new Ship(CARRIER, aiCarrCoords, false);
    aiBattle = new Ship(BATTLESHIP, aiBattleCoords, false);
    aiDest = new Ship(DESTROYER, aiDestCoords, false);
    aiSub = new Ship(SUBMARINE, aiSubCoords, false);
    // more stuff for aiPlayerTest
    aiBoard = new Board(6, 6);
    aiPlayerTest =
        new ArtificialPlayer("aiTestPlayer", viewer, aiBoard, playerBoard, new Random(1));
    aiListOfshipsTest = new ArrayList<>(Arrays.asList(aiCarr, aiBattle, aiDest, aiSub));

    // coords for ships in realPlayerTest
    realCarr1 = new Coord(0, 0, CARRIERSTATUS);
    realCarr2 = new Coord(1, 0, CARRIERSTATUS);
    realCarr3 = new Coord(2, 0, CARRIERSTATUS);
    realCarr4 = new Coord(3, 0, CARRIERSTATUS);
    realCarr5 = new Coord(4, 0, CARRIERSTATUS);
    realCarr6 = new Coord(5, 0, CARRIERSTATUS);
    realCarrCoords =
        new ArrayList<>(Arrays.asList(
            realCarr1, realCarr2, realCarr3, realCarr4, realCarr5, realCarr6));
    realBattle1 = new Coord(0, 1, BATTLESHIPSTATUS);
    realBattle2 = new Coord(0, 2, BATTLESHIPSTATUS);
    realBattle3 = new Coord(0, 3, BATTLESHIPSTATUS);
    realBattle4 = new Coord(0, 4, BATTLESHIPSTATUS);
    realBattle5 = new Coord(0, 5, BATTLESHIPSTATUS);
    realBattleCoords =
        new ArrayList<>(Arrays.asList(
            realBattle1, realBattle2, realBattle3, realBattle4, realBattle5));
    realDest1 = new Coord(2, 5, DESTROYERSTATUS);
    realDest2 = new Coord(3, 5, DESTROYERSTATUS);
    realDest3 = new Coord(4, 5, DESTROYERSTATUS);
    realDest4 = new Coord(5, 5, DESTROYERSTATUS);
    realDestCoords =
        new ArrayList<>(Arrays.asList(
            realDest1, realDest2, realDest3, realDest4));
    realSub1 = new Coord(3, 1, SUBMARINESTATUS);
    realSub2 = new Coord(4, 1, SUBMARINESTATUS);
    realSub3 = new Coord(5, 1, SUBMARINESTATUS);
    realSubCoords =
        new ArrayList<>(Arrays.asList(realSub1, realSub2, realSub3));
    // ships for list of ships in realPlayerTest
    realCarr = new Ship(CARRIER, realCarrCoords, false);
    realBattle = new Ship(BATTLESHIP, realBattleCoords, false);
    realDest = new Ship(DESTROYER, realDestCoords, false);
    realSub = new Ship(SUBMARINE, realSubCoords, false);
    // more stuff for realPlayerTest
    playerBoard = new Board(6, 6);
    realPlayerTest =
        new RealPlayer("realTestPlayer", viewer, playerBoard, aiBoard, new Random(2));
    realListOfShipsTest = new ArrayList<>(Arrays.asList(realCarr, realBattle, realDest, realSub));

  }

  @Test
  void nameTest() {
    assertEquals("aiTestPlayer", aiPlayerTest.name());
    assertEquals("realTestPlayer", realPlayerTest.name());
  }

  @Test
  void setupTest() {

    List<Ship> aiShipsForSetup = aiPlayerTest.setup(6, 6, specsTest);

    assertTrue(aiShipsForSetup.get(0).compareShips(aiCarr));
    assertTrue(aiShipsForSetup.get(1).compareShips(aiBattle));
    assertTrue(aiShipsForSetup.get(2).compareShips(aiDest));
    assertTrue(aiShipsForSetup.get(3).compareShips(aiSub));

    List<Ship> realShipsForSetup = realPlayerTest.setup(6, 6, specsTest);

    assertTrue(realShipsForSetup.get(0).compareShips(realCarr));
    assertTrue(realShipsForSetup.get(1).compareShips(realBattle));
    assertTrue(realShipsForSetup.get(2).compareShips(realDest));
    assertTrue(realShipsForSetup.get(3).compareShips(realSub));

  }

  @Test
  void reportDamageTest() {

    List<Ship> aiShipsForSetup = aiPlayerTest.setup(6, 6, specsTest);

    Coord ac1 = new Coord(0, 0, UNKNOWN);
    Coord ac2 = new Coord(0, 4, UNKNOWN);

    List<Coord> shotsOnAiBoardFromReal =
        new ArrayList<>(Arrays.asList(ac1, ac2, aiShipsForSetup.get(0).getShipCoords().get(0)));
    List<Coord> shotsOnAiBoardFromRealThatHit =
        new ArrayList<>(Arrays.asList(ac2, aiShipsForSetup.get(0).getShipCoords().get(0)));

    List<Coord> shotsOnAiBoardFromReal2 =
        new ArrayList<>(Arrays.asList(ac1, ac2));
    List<Coord> shotsOnAiBoardFromRealThatHit2 =
        new ArrayList<>(List.of(ac2));

    assertEquals(shotsOnAiBoardFromRealThatHit,
        aiPlayerTest.reportDamage(shotsOnAiBoardFromReal));
    assertEquals(shotsOnAiBoardFromRealThatHit2,
        aiPlayerTest.reportDamage(shotsOnAiBoardFromReal2));

  }

  @Test
  void successfulHitsTest() {

    List<Ship> aiShipsForSetup = aiPlayerTest.setup(6, 6, specsTest);
    realPlayerTest.setup(6, 6, specsTest);

    String expectedString = "\nrealTestPlayer hit 1 shots! :D\n";

    List<Coord> shotsOnAiBoardFromRealThatHit =
        new ArrayList<>(Arrays.asList(aiShipsForSetup.get(0).getShipCoords().get(0)));

    realPlayerTest.successfulHits(shotsOnAiBoardFromRealThatHit);

    assertEquals(expectedString, output.toString());

  }

  @Test
  void shotsAvailableTest() {

    aiPlayerTest.setup(6, 6, specsTest);

    // 4 because aiPlayerTest should have been initialized with 4 ships in total, all of which are
    // not sunk.
    assertEquals(4, aiPlayerTest.shotsAvailable());

  }
}