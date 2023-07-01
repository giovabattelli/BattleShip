package model;

import static model.CoordStatus.BATTLESHIPSTATUS;
import static model.CoordStatus.CARRIERSTATUS;
import static model.CoordStatus.DESTROYERSTATUS;
import static model.CoordStatus.SUBMARINESTATUS;
import static model.ShipType.BATTLESHIP;
import static model.ShipType.CARRIER;
import static model.ShipType.DESTROYER;
import static model.ShipType.SUBMARINE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ShipTypeTest {

  @Test
  void getSizeTest() {
    assertEquals(6, CARRIER.getSize());
    assertEquals(5, BATTLESHIP.getSize());
    assertEquals(4, DESTROYER.getSize());
    assertEquals(3, SUBMARINE.getSize());
  }

  @Test
  void getStatusTest() {
    assertEquals(CARRIERSTATUS, CARRIER.getStatus());
    assertEquals(BATTLESHIPSTATUS, BATTLESHIP.getStatus());
    assertEquals(DESTROYERSTATUS, DESTROYER.getStatus());
    assertEquals(SUBMARINESTATUS, SUBMARINE.getStatus());
  }
}