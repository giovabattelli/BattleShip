package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import json.CoordJson;
import json.CoordinatesJson;
import json.JsonUtils;
import json.MessageJson;
import json.SetupJson;
import model.AbstractPlayer;
import model.ArtificialPlayer;
import model.Board;
import model.ShipType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.ViewImpl;

class ProxyControllerTest {

  Readable input;
  Appendable output;
  ViewImpl view;

  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  private static final JsonNode EMPTY_ARGS = new ObjectMapper().getNodeFactory().arrayNode(0);

  private AbstractPlayer player;
  private Board board;

  private Map<ShipType, Integer> specifications;

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  void setup() {

    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());

    this.input = new StringReader("");
    this.output = new StringBuilder();
    this.view = new ViewImpl(input, output, new Scanner(input));

    this.board = new Board();
    this.player = new ArtificialPlayer("testAI", view, board, board, new Random(1));

    this.specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);

  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * When server sends "join" message to client
   */
  @Test
  void joinRequestTest() {

    // join message from server
    JsonNode jsonNode = JsonUtils.serializeRecord(new MessageJson("join", EMPTY_ARGS));

    // Create a mock socket with sample join message
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a proxy controller
    try {
      this.controller = new ProxyController(socket, player, board);
    } catch (IOException e) {
      fail(); // fail the test if the proxy controller can't be created
    }

    // run the controller and verify that the response is a message
    this.controller.run();
    responseToClass(MessageJson.class);
    String expectedClientResponse =
        "{\"method-name\":\"join\",\"arguments\":{\"name"
            + "\":\"giovabattelli\",\"game-type\":\"SINGLE\"}}\n";
    assertEquals(expectedClientResponse, logToString());

  }

  /**
   * When server sends "setup" message to client
   */
  @Test
  void setupRequestTest() {

    // setup message from server
    SetupJson setupJson = new SetupJson(6, 6, specifications);
    JsonNode setupNode = JsonUtils.serializeRecord(setupJson);
    JsonNode jsonNode = JsonUtils.serializeRecord(new MessageJson("setup", setupNode));

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, player, board);
    } catch (IOException e) {
      fail(); // fail the test if the proxy controller can't be created
    }

    this.controller.run();
    responseToClass(MessageJson.class);
    String expectedClientResponse = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coo"
        + "rd\":{\"x\":0,\"y\":4},\"length\":6,\"direction\":\"HORIZONTAL\"},{\"coord"
        + "\":{\"x\":0,\"y\":2},\"length\":5,\"direction\":\"HORIZONTAL\"},{\"coord"
        + "\":{\"x\":5,\"y\":0},\"length\":4,\"direction\":\"VERTICAL\"},{\"coord\":{"
        + "\"x\":1,\"y\":5},\"length\":3,\"direction\":\"HORIZONTAL\"}]}}\n";
    assertEquals(expectedClientResponse, logToString());

  }

  /**
   * When server sends "take-shots" message to client
   */
  @Test
  void takeShotsRequestTest() {

    // setup message from server
    SetupJson setupJson = new SetupJson(6, 6, specifications);
    JsonNode setupNode = JsonUtils.serializeRecord(setupJson);
    JsonNode jsonNode1 = JsonUtils.serializeRecord(new MessageJson("setup", setupNode));

    // take-shots message from server
    JsonNode jsonNode2 = JsonUtils.serializeRecord(new MessageJson("take-shots", EMPTY_ARGS));

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode1.toString(), jsonNode2.toString()));

    try {
      this.controller = new ProxyController(socket, player, board);
    } catch (IOException e) {
      fail(); // fail the test if the proxy controller can't be created
    }

    this.controller.run();
    responseToClass(MessageJson.class);
    String expectedClientResponse = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord"
        + "\":{\"x\":0,\"y\":4},\"length\":6,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":0,"
        + "\"y\":2},\"length\":5,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":5,\"y\":0},"
        + "\"length\":4,\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":1,\"y\":5},\"length\":3,\""
        + "direction\":\"HORIZONTAL\"}]}}\n{\"method-name\":\"take-shots\",\"arguments\":{\""
        + "coordinates\":[{\"x\":1,\"y\":4},{\"x\":4,\"y\":5},{\"x\":0,\"y\":5},"
        + "{\"x\":0,\"y\":4}]}}\n";
    assertEquals(expectedClientResponse, logToString());

  }

  /**
   * When server sends "report-damage" message to client
   */
  @Test
  void reportDamageRequestTest() {

    // setup message from server
    SetupJson setupJson = new SetupJson(6, 6, specifications);
    JsonNode setupNode = JsonUtils.serializeRecord(setupJson);
    JsonNode jsonNode1 = JsonUtils.serializeRecord(new MessageJson("setup", setupNode));

    // take-shots message from server
    JsonNode jsonNode2 = JsonUtils.serializeRecord(new MessageJson("take-shots", EMPTY_ARGS));

    // report-damage message from server
    JsonNode c1 = JsonUtils.serializeRecord(new CoordJson(0, 4)); // HIT!
    JsonNode c2 = JsonUtils.serializeRecord(new CoordJson(0, 0)); // MISS!
    JsonNode c3 = JsonUtils.serializeRecord(new CoordJson(1, 5)); // HIT!
    JsonNode c4 = JsonUtils.serializeRecord(new CoordJson(0, 1)); // MISS!
    JsonNode[] listOfCoordJson = new JsonNode[] {c1, c2, c3, c4};
    CoordinatesJson coordinatesJson = new CoordinatesJson(listOfCoordJson);
    JsonNode coordinatesSerialized = JsonUtils.serializeRecord(coordinatesJson);
    JsonNode jsonNode3 = JsonUtils.serializeRecord(
        new MessageJson("report-damage", coordinatesSerialized));

    Mocket socket = new Mocket(
        this.testLog, List.of(jsonNode1.toString(), jsonNode2.toString(), jsonNode3.toString()));

    try {
      this.controller = new ProxyController(socket, player, board);
    } catch (IOException e) {
      fail(); // fail the test if the proxy controller can't be created
    }

    this.controller.run();
    responseToClass(MessageJson.class);
    String expectedClientResponse = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord"
        + "\":{\"x\":0,\"y\":4},\"length\":6,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":0,"
        + "\"y\":2},\"length\":5,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":5,\"y\":0},"
        + "\"length\":4,\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":1,\"y\":5},\"length\":3,"
        + "\"direction\":\"HORIZONTAL\"}]}}\n{\"method-name\":\"take-shots\",\"arguments\""
        + ":{\"coordinates\":[{\"x\":1,\"y\":4},{\"x\":4,\"y\":5},{\"x\":0,\"y\":5},{\"x"
        + "\":0,\"y\":4}]}}\n{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates"
        + "\":[{\"x\":0,\"y\":4},{\"x\":1,\"y\":5}]}}\n";
    assertEquals(expectedClientResponse, logToString());

  }

  /**
   * When server sends "successful-hits" message to client
   */
  @Test
  void successfulHitsRequestTest() {

    // DUMMY COORDINATE because whatever is sent from server to client, client will ALWAYS respond
    // with the same message containing empty arguments
    JsonNode c1 = JsonUtils.serializeRecord(new CoordJson(0, 0));
    JsonNode shots = JsonUtils.serializeRecord(new CoordinatesJson(new JsonNode[] {c1}));
    JsonNode jsonNode = JsonUtils.serializeRecord(new MessageJson("successful-hits", shots));

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, player, board);
    } catch (IOException e) {
      fail(); // fail the test if the proxy controller can't be created
    }

    this.controller.run();
    responseToClass(MessageJson.class);
    String expectedClientResponse = "{\"method-name\":\"successful-hits\",\"arguments\":[]}\n";
    assertEquals(expectedClientResponse, logToString());

  }

  /**
   * When server sends "end-game" message to client
   */
  @Test
  void endGameRequestTest() {

    // EMPTY_ARGS because no matter the reason for ending the game, the client will always
    // send back a message with empty arguments.
    JsonNode jsonNode = JsonUtils.serializeRecord(new MessageJson("end-game", EMPTY_ARGS));

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, player, board);
    } catch (IOException e) {
      fail(); // fail the test if the proxy controller can't be created
    }

    this.controller.run();
    responseToClass(MessageJson.class);
    String expectedClientResponse = "{\"method-name\":\"end-game\",\"arguments\":[]}\n";
    assertEquals(expectedClientResponse, logToString());

  }

  /**
   * When server sends an invalid message to client
   */
  @Test
  void invalidServerRequestTest() {

    // message with an invalid name
    JsonNode jsonNode = JsonUtils.serializeRecord(new MessageJson("floob", EMPTY_ARGS));

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, player, board);
    } catch (IOException e) {
      fail(); // fail the test if the proxy controller can't be created
    }

    // throws exception because invalid MessageJson name
    assertThrows(
        IllegalStateException.class,
        () -> controller.run());

  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to given class reference, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> wrong type of response, thus the test fails
      fail();
    }
  }

}