package model;

import static model.CoordStatus.BATTLESHIPSTATUS;
import static model.CoordStatus.CARRIERSTATUS;
import static model.CoordStatus.DESTROYERSTATUS;
import static model.CoordStatus.HIT;
import static model.CoordStatus.MISS;
import static model.CoordStatus.SUBMARINESTATUS;
import static model.CoordStatus.UNKNOWN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CoordStatusTest {

  @Test
  void getSymbolTest() {
    assertEquals("~", UNKNOWN.getSymbol());
    assertEquals("H", HIT.getSymbol());
    assertEquals("M", MISS.getSymbol());
    assertEquals("C", CARRIERSTATUS.getSymbol());
    assertEquals("B", BATTLESHIPSTATUS.getSymbol());
    assertEquals("D", DESTROYERSTATUS.getSymbol());
    assertEquals("S", SUBMARINESTATUS.getSymbol());
  }
}