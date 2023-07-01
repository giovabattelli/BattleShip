package json;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class JsonUtilsTest {

  @Test
  void serializeRecordTest() {
    assertThrows(
        IllegalArgumentException.class,
        () -> JsonUtils.serializeRecord(new BadRecord("foo", "bar"))
    );

  }
}