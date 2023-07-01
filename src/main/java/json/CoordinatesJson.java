package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Record representing a list of coords in json format
 *
 * @param coordinates - A list of coords in JsonNode form
 */
public record CoordinatesJson(
    @JsonProperty("coordinates") JsonNode[] coordinates) {

}
