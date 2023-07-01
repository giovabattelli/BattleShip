package controller;

import static model.CoordStatus.UNKNOWN;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import json.CoordJson;
import json.CoordinatesJson;
import json.FleetJson;
import json.JoinJson;
import json.JsonUtils;
import json.MessageJson;
import json.SetupJson;
import json.ShipJson;
import model.AbstractPlayer;
import model.Board;
import model.Coord;
import model.CoordAdapter;
import model.Ship;
import model.ShipAdapter;

/**
 * A controller that speaks to a server to play a game of BattleSalvo against the server's AI
 */
public class ProxyController implements Controller {

  private final Socket socket;
  private final AbstractPlayer player;
  private final InputStream in;
  private final PrintStream out;
  private final ObjectMapper mapper = new ObjectMapper();
  private Board board;
  private static final JsonNode EMPTY_ARGS = new ObjectMapper().getNodeFactory().arrayNode(0);

  /**
   * Creates a ProxyController object that handles messages from a server and helps run a game
   * of BattleSalvo in which this controller's AI plays against the server player.
   *
   * @param socket - The server's socket.
   * @param player - This controller's player.
   * @throws IOException - In the event of an IOException.
   */
  public ProxyController(Socket socket, AbstractPlayer player, Board board) throws IOException {
    this.socket = socket;
    this.player = player;
    this.board = board;
    this.in = socket.getInputStream();
    this.out = new PrintStream(socket.getOutputStream());
  }

  /**
   * Runs a game of BattleSalvo in which this player plays against a server.
   */
  @Override
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.socket.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // if things in try block cannot be completed; server will end game
    }
  }

  /**
   * Decide which helper to call based
   * on what the message from the server is
   *
   * @param message : the command the server gives us
   */
  private void delegateMessage(MessageJson message) {
    String methodName = message.methodName();
    JsonNode arguments = message.arguments();

    switch (methodName) {

      case "join" -> handleJoin();
      case "setup" -> handleSetup(arguments);
      case "take-shots" -> handleTakeShots();
      case "report-damage" -> handleReportDamage(arguments);
      case "successful-hits" -> handleSuccessfulHits(arguments);
      case "end-game" -> handleEndGame();

      default -> throw new IllegalStateException("Invalid message from server\n");

    }
  }

  /**
   * Handle the "join" command
   */
  private void handleJoin() {
    JoinJson joinResponse = new JoinJson("giovabattelli", "SINGLE"); // johnny's github username
    JsonNode join = JsonUtils.serializeRecord(joinResponse);

    MessageJson message = new MessageJson("join", join);
    JsonNode jsonResponse = JsonUtils.serializeRecord(message);
    this.out.println(jsonResponse);
  }

  /**
   * Handle the "setup" command
   *
   * @param arguments - The setup message arguments from the server.
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson setupArgs = this.mapper.convertValue(arguments, SetupJson.class);
    board.changeDimensions(setupArgs.height(), setupArgs.width());
    List<Ship> ships = player.setup(setupArgs.height(), setupArgs.width(), setupArgs.shipSpecs());
    List<ShipAdapter> adaptedShips = new ArrayList<>();

    for (Ship ship : ships) {
      adaptedShips.add(new ShipAdapter(ship));
    }

    JsonNode[] listOfShips = new JsonNode[ships.size()];
    int i = 0;

    for (ShipAdapter ship : adaptedShips) {
      ShipJson shipConverted = new ShipJson(ship.getStart(), ship.getLength(), ship.getDirection());
      JsonNode node = JsonUtils.serializeRecord(shipConverted);
      listOfShips[i] = node;
      i++;
    }

    FleetJson fleet = new FleetJson(listOfShips);
    MessageJson message = new MessageJson("setup", JsonUtils.serializeRecord(fleet));
    JsonNode fleetResponse = JsonUtils.serializeRecord(message);
    this.out.println(fleetResponse);
  }

  /**
   * Handle the "take-shots" command
   */
  private void handleTakeShots() {
    List<Coord> shots = player.takeShots();
    List<CoordAdapter> adaptedShots = new ArrayList<>();

    for (Coord coord : shots) {
      adaptedShots.add(new CoordAdapter(coord));
    }

    JsonNode[] startCoords = new JsonNode[shots.size()];
    int i = 0;

    for (CoordAdapter coord : adaptedShots) {
      CoordJson coordInJson = new CoordJson(coord.getX(), coord.getY());
      JsonNode node = JsonUtils.serializeRecord(coordInJson);
      startCoords[i] = node;
      i++;
    }

    CoordinatesJson volley = new CoordinatesJson(startCoords);
    MessageJson message = new MessageJson("take-shots", JsonUtils.serializeRecord(volley));
    JsonNode jsonResponse = JsonUtils.serializeRecord(message);
    this.out.println(jsonResponse);
  }

  /**
   * Handle the "report-damage" command
   *
   * @param arguments - The report damage message arguments from the server
   */
  private void handleReportDamage(JsonNode arguments) {
    CoordinatesJson reportDamageArgs = this.mapper.convertValue(arguments, CoordinatesJson.class);
    List<Coord> coordsFromServer = new ArrayList<>();

    for (JsonNode jn : reportDamageArgs.coordinates()) {
      CoordJson coordAdapterJson = this.mapper.convertValue(jn, CoordJson.class);
      coordsFromServer.add(new Coord(coordAdapterJson.x(), coordAdapterJson.y(), UNKNOWN));
    }

    List<Coord> shotsThatHit = player.reportDamage(coordsFromServer);
    List<CoordAdapter> adaptedCoords = new ArrayList<>();

    for (Coord coord : shotsThatHit) {
      adaptedCoords.add(new CoordAdapter(coord));
    }

    JsonNode[] startCoords = new JsonNode[shotsThatHit.size()];
    int i = 0;

    for (CoordAdapter coord : adaptedCoords) {
      CoordJson coordInJson = new CoordJson(coord.getX(), coord.getY());
      JsonNode node = JsonUtils.serializeRecord(coordInJson);
      startCoords[i] = node;
      i++;
    }

    CoordinatesJson shots = new CoordinatesJson(startCoords);
    MessageJson message = new MessageJson("report-damage", JsonUtils.serializeRecord(shots));
    JsonNode jsonResponse = JsonUtils.serializeRecord(message);
    this.out.println(jsonResponse);

  }

  /**
   * Handle the "successful-hits" command
   *
   * @param arguments - The successful hits message arguments from the server
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    // coordinates that this player hit on server player's board
    CoordinatesJson successfulHits = this.mapper.convertValue(arguments, CoordinatesJson.class);
    List<Coord> hits = new ArrayList<>();

    for (JsonNode cj : successfulHits.coordinates()) {
      CoordJson coordJson = this.mapper.convertValue(cj, CoordJson.class);
      Coord coord = new Coord(coordJson.x(), coordJson.y(), UNKNOWN);
      hits.add(coord);
    }

    player.successfulHits(hits);

    MessageJson successfulHitsResponse = new MessageJson("successful-hits", EMPTY_ARGS);
    JsonNode jsonResponse = JsonUtils.serializeRecord(successfulHitsResponse);
    this.out.println(jsonResponse);
  }

  /**
   * Handle the "end-game" command
   */
  private void handleEndGame() {
    MessageJson endGameResponse = new MessageJson("end-game", EMPTY_ARGS);
    JsonNode jsonResponse = JsonUtils.serializeRecord(endGameResponse);
    this.out.println(jsonResponse);
  }

}
