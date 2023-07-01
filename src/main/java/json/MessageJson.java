package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Record representing a message being sent to a server in a game of BattleSalvo
 *
 * @param methodName - The name of the method information being sent to the server
 * @param arguments - The arguments the server needs based on the message being sent
 */
public record MessageJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") JsonNode arguments) {

}
