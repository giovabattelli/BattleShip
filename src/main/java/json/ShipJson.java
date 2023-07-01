package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.CoordAdapter;
import model.Direction;

/**
 * Record representing the format of a Ship in json
 *
 * @param start - The start of a ship
 * @param length - The length of a ship
 * @param direction - The VERTICAL or HORIZONTAL direction of a ship from its start
 */
public record ShipJson(
    @JsonProperty("coord") CoordAdapter start,
    @JsonProperty("length") int length,
    @JsonProperty("direction") Direction direction) {
}
