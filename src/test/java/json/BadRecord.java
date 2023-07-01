package json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Bad record that would throw an exception is serialized.
 *
 * @param x - String
 * @param y - String
 */
public record BadRecord(
    @JsonProperty("x") String x,
    @JsonProperty("x") String y
) {
}
