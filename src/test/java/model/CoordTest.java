package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordTest {

  Coord c1;
  Coord c2;

  @BeforeEach
  void setup() {

    c1 = new Coord(5, 7, CoordStatus.DESTROYERSTATUS);
    c2 = new Coord(5, 7, CoordStatus.HIT);

  }

  @Test
  void getStatusTest() {
    assertEquals(CoordStatus.DESTROYERSTATUS, c1.getStatus());
  }

  @Test
  void getXtest() {
    assertEquals(5, c1.getX());
  }

  @Test
  void getYtest() {
    assertEquals(7, c1.getY());
  }

  @Test
  void changeStatusTest() {
    c1.changeStatus(CoordStatus.HIT);
    assertEquals(CoordStatus.HIT, c1.getStatus());
  }
}