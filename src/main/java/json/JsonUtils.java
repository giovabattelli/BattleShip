package json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Any utility methods for serializing/deserializing json
 */
public class JsonUtils {

  /**
   * Converts the parameter record object to a Json node.
   *
   * @param record - Object that will be converted.
   * @return - The JsonNode representation of the given record.
   * @throws IllegalArgumentException - If the given record could not be converted correctly.
   */
  public static JsonNode serializeRecord(Record record) throws IllegalArgumentException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.convertValue(record, JsonNode.class);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Given record cannot be serialized");
    }
  }
}
