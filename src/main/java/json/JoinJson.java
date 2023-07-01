package json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record representing the format of a json join message for a game of BattleSalvo
 *
 * @param githubUsername - A String representing a github username
 * @param gameType - SINGLE or MULTI
 */
public record JoinJson(
    @JsonProperty("name") String githubUsername,
    @JsonProperty("game-type") String gameType) {
}
