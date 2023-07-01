package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import model.ShipType;

/**
 * Record representing a setup response in json form to a server in a game of BattleSalvo
 *
 * @param width - The width of a board
 * @param height - The height of a board
 * @param shipSpecs - The specifications of a fleet of ships in a game of BattleSalvo
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> shipSpecs) {

}
