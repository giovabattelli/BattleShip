package cs3500.pa04;

import controller.BattleshipSalvo;
import controller.Controller;
import controller.ProxyController;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import model.AbstractPlayer;
import model.ArtificialPlayer;
import model.Board;
import view.ViewImpl;

/**
 * This is the main driver of this project.
 */
public class Driver {

  /**
   * Runs the BattleSalvo client side with an AI player.
   *
   * @param host - The host of the server.
   * @param port - The port of the server.
   * @throws IOException - In the event that a socket cannot be instantiated.
   */
  private static void runClient(String host, int port) throws IOException {

    String clientName = "Floob AI";
    Board clientBoard = new Board();
    Readable readable = new StringReader("");
    Appendable appendable = new StringBuilder();
    ViewImpl view = new ViewImpl(readable, appendable, new Scanner(readable));
    Random rand = new Random();

    Socket socket = new Socket(host, port);
    AbstractPlayer player = new ArtificialPlayer(clientName, view, clientBoard, clientBoard, rand);
    ProxyController serverGameController = new ProxyController(socket, player, clientBoard);

    serverGameController.run();

  }

  /**
   * Based on command-line arguments, starts a game of BattleSalvo where client AI plays against
   * server or player in console plays against an AI.
   *
   * @param args - Array of strings; command-line arguments.
   */
  public static void main(String[] args) {

    if (args.length == 0) {

      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;
      Controller battleSalvo = new BattleshipSalvo(input, output);

      battleSalvo.run();

    } else {
      try {

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        Driver.runClient(host, port);

      } catch (NumberFormatException | IOException e) {
        throw new IllegalArgumentException("Invalid command-line arguments");
      }
    }
  }
}