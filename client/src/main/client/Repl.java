package client;


import chess.ChessGameImpl;
import chess.ChessPositionImpl;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import websocket.NotificationHandler;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl implements NotificationHandler {
  private final ChessClient client;
  private ChessGameImpl game;

  public Repl(String serverUrl) throws DeploymentException, IOException, URISyntaxException {
    client = new ChessClient(serverUrl, this);
  }

  public void run(){
    System.out.println("Welcom to 240 chess. Type Help to get started.");
    System.out.print(client.help());

    Scanner scanner = new Scanner(System.in);
    var result = "";

    while(!result.equals("quit")){
      printPrompt();
      String line = scanner.nextLine();
      try{
        result = client.eval(line);
        if(result == "redraw"){
          //Redrawing
          if(client.isWhite){
            PrintingBoard printingBoard = new PrintingBoard(game, client.isWhite);
            printingBoard.printBoard();
            System.out.println(SET_TEXT_COLOR_DARK_GREY);
          }else{
            PrintingBoard printingBoard = new PrintingBoard(game, client.isWhite);
            printingBoard.printBoard();
            System.out.println(SET_TEXT_COLOR_DARK_GREY);
          }

        }else if(result == "highlight"){
          highLight(game, client.position);
        }
        System.out.print(SET_TEXT_COLOR_BLUE + result);
      }catch (Throwable e){
        System.out.print(e.getMessage());
      }

    }
    System.out.println();
  }// end of run

  public void printPrompt(){
    if(client.isLogedIn){
      System.out.print("\n" + RESET_TEXT_COLOR + "[LOGGED_IN]"  + " >>> " + SET_TEXT_COLOR_GREEN);
    } else{
      System.out.print("\n" + RESET_TEXT_COLOR + "[LOGGED_OUT]"  + " >>> " + SET_TEXT_COLOR_GREEN);
    }

  }

  private void highLight(ChessGameImpl game, ChessPositionImpl position){
    System.out.println("Hightlighting!.......");
    if(client.isWhite){
      PrintingBoard print = new PrintingBoard(game, client.isWhite);
      print.highlight(game, position);
    }else{
      PrintingBoard print = new PrintingBoard(game, client.isWhite);
      print.highlight(game, position);
    }
    System.out.println(SET_BG_COLOR_DARK_GREY);
    printPrompt();
  }

  @Override
  public void notify(NotificationMessage notification) {
    System.out.println("\n" + SET_TEXT_COLOR_GREEN + notification.message);
    printPrompt();
  }

  @Override
  public void error(ErrorMessage errorMessage) {
    System.out.println("\n" + SET_TEXT_COLOR_RED + errorMessage.errorMessage);
    printPrompt();
  }

  @Override
  public void loadGame(LoadGameMessage loadGameMessage) {
    System.out.println("\n" + SET_TEXT_COLOR_RED + "Loading Game....");
    if(client.isWhite){
      PrintingBoard print = new PrintingBoard(loadGameMessage.game, client.isWhite);
      print.printBoard();
    }else{
      PrintingBoard print = new PrintingBoard(loadGameMessage.game, client.isWhite);
      print.printBoard();
    }

    this.game = loadGameMessage.game;
    System.out.println(SET_BG_COLOR_DARK_GREY);
    printPrompt();
  }
}
