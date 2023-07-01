package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Record representing a fleet in json format
 *
 * @param ships - A list of ships in JsonNode form
 */
public record FleetJson(
    @JsonProperty("fleet") JsonNode[] ships) {

}
