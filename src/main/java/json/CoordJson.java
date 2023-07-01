package json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record representing a coord in json form
 *
 * @param x - The x value of a coord
 * @param y - The y value of a coord
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {

}
