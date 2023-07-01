package model;

import static model.CoordStatus.DESTROYERSTATUS;
import static model.CoordStatus.HIT;
import static model.CoordStatus.SUBMARINESTATUS;
import static model.ShipType.DESTROYER;
import static model.ShipType.SUBMARINE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {

  Coord asub1;
  Coord asub2;
  Coord asub3;
  Coord bsub1;
  Coord bsub2;
  Coord bsub3;
  List<Coord> asubListCoords;
  List<Coord> bsubListCoords;
  Ship as1;
  Ship bs2;
  Ship cs3;
  Ship sameAsAs1;
  Coord dest1;
  Coord dest2;
  Coord dest3;
  Coord dest4;
  List<Coord> destCoords;
  Ship dest;

  @BeforeEach
  void setup() {

    asub1 = new Coord(0, 0, HIT);
    asub2 = new Coord(0, 1, HIT);
    asub3 = new Coord(0, 2, SUBMARINESTATUS);
    bsub1 = new Coord(0, 0, HIT);
    bsub2 = new Coord(0, 1, HIT);
    bsub3 = new Coord(0, 2, HIT);
    dest1 = new Coord(0, 2, DESTROYERSTATUS);
    dest2 = new Coord(0, 3, DESTROYERSTATUS);
    dest3 = new Coord(0, 4, DESTROYERSTATUS);
    dest4 = new Coord(0, 5, DESTROYERSTATUS);
    asubListCoords = new ArrayList<>(Arrays.asList(asub1, asub2, asub3));
    bsubListCoords = new ArrayList<>(Arrays.asList(bsub1, bsub2, bsub3));
    destCoords = new ArrayList<>(Arrays.asList(dest1, dest2, dest3, dest4));
    dest = new Ship(DESTROYER, destCoords, false);
    as1 = new Ship(SUBMARINE, asubListCoords, false);
    bs2 = new Ship(SUBMARINE, bsubListCoords, false);
    cs3 = new Ship(SUBMARINE, asubListCoords, true);
    sameAsAs1 = new Ship(SUBMARINE, asubListCoords, false);

  }

  @Test
  void getSunkTest() {
    assertFalse(as1.getSunk());
    assertTrue(cs3.getSunk());
  }

  @Test
  void updateSunkTest() {
    bs2.updateSunk();
    assertTrue(bs2.getSunk());
    dest.updateSunk();
    assertFalse(dest.getSunk());
  }

  @Test
  void compareShipsTest() {
    assertTrue(as1.compareShips(sameAsAs1));
    assertFalse(as1.compareShips(bs2));
    assertFalse(dest.compareShips(as1));
  }
}