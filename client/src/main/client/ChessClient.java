package client;

import RequestResponses.*;
import chess.*;
import dataAccess.GameDAO;
import model.Game;
import server.ServerFacade;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import static ui.EscapeSequences.*;

public class ChessClient {
  public ChessPositionImpl position;
  public boolean isWhite = false;
  public boolean isBlack = false;
  private String serverUrl = null;
  private WebSocketFacade ws;
  private NotificationHandler notificationHandler;
  private String username= null;
  private String password = null;
  private String email = null;
  private String authToken = null;
  private Integer gameID = null;
  ServerFacade server;

  public boolean isLogedIn = false;
  public boolean isPlayer = false;
  public boolean isObserver = false;
  public ChessClient (String url, NotificationHandler notificationHandler) throws DeploymentException, IOException, URISyntaxException {
    server = new ServerFacade(url);
    ws = new WebSocketFacade(url, notificationHandler);
    this.notificationHandler = notificationHandler;
    this.serverUrl = url;
  }

  public String eval(String input) {
      var tokens=input.toLowerCase().split(" ");
      String cmd = null;
      if(tokens.length > 0){
        cmd = tokens[0];
      }else{
        cmd = "help";
      }
      //String cmd=(tokens.length > 0) ? tokens[0] : "help";
      var params=Arrays.copyOfRange(tokens, 1, tokens.length);
      if (isLogedIn && isObserver == false && isPlayer == false) {
        //Should I write the code like this for game command??
        return switch (cmd) {
        case "create" -> create(params);
        case "list" -> list();
        case "join" -> join(params);
        case "observe" -> observe(params);
        case "logout" -> logout();
        case "quit" -> "quit";
        case "help" -> help();
        default -> help();
        };
      }else if(isLogedIn && isObserver){
        return switch (cmd){
          case "help" -> help();
          case "redraw" -> redraw();
          case "leave" -> leave();
          default -> help();
        };
      }else if(isLogedIn && isPlayer){
        return switch (cmd){
          case "help" -> help();
          case "redraw" -> redraw();
          case "move" -> move(params);
          case "leave" -> leave();
          case "resign" -> resign();
          case "highlight" -> highlight(params);
          default -> help();
        };
      } else{
        return switch (cmd) {
          case "register" -> register(params);
          case "login" -> login(params);
          case "quit" -> "quit";
          case "help" -> help();
          default -> help();
        };
      }
    }// end of eval

  public String highlight(String... params){
    ChessPositionImpl position;
    char a = params[0].charAt(0);
    String i = params[0].substring(1,2);
    Integer startCol = params[0].charAt(0) - 'a';
    Integer startRow = Integer.parseInt(params[0].substring(1,2));
    position = new ChessPositionImpl(startRow - 1, startCol);
    this.position = position;
    return "highlight";
  }

  public String redraw(){
    return "redraw";
  }
  public String resign(){
    try{
      ws.resignGame(gameID, authToken);
    }catch (Exception e){
      return "Error";
    }

   return "You resigned";
  }
  public String move(String... params){
    ChessPositionImpl startPosition;
    ChessPositionImpl endPosition;
    ChessMoveImpl move;
    try{
      if(params.length == 2){
        // move before and after
        char a = params[0].charAt(0);
        String i = params[0].substring(1,2);
        Integer startCol = params[0].charAt(0) - 'a';
        Integer startRow = Integer.parseInt(params[0].substring(1,2));
        startPosition = new ChessPositionImpl(startRow - 1, startCol);

        Integer endCol = params[1].charAt(0) - 'a';
        Integer endRow = Integer.parseInt(params[1].substring(1,2));
        endPosition = new ChessPositionImpl(endRow - 1, endCol);

        move = new ChessMoveImpl(startPosition, endPosition, null);
        ws.makeMove(gameID, move, authToken);

        return String.format("%s is trying to move ", username);
      }else{
        //error of command
        return String.format("Wrong");
      }
    }catch (Exception e){
      return String.format("Error");
    }

  }

  public String leave() {
    try{
      ws.leaveGame(gameID, authToken);
      if(isPlayer){
        if(isWhite){
          isWhite = false;
        }else{
          isBlack = false;
        }
        isPlayer = false;
      }
      if(isObserver){
        isObserver = false;
      }
      return String.format("%s left the game.", username);
    }catch (Exception e){
      // what should I return for error? errorMessage?? or just string??
      return null;
    }
  }

  public String logout(){

    LogoutResult result = null;
    try{
      result = server.doLogout(authToken);
      isLogedIn = false;
      return String.format("You logged out as %s", username);
    }catch (Exception e){
      return result.getMessage();
    }
  }

  public String create(String... params){
    CreateGameResult result=null;

    try{
      if(params.length == 1){
        String gameName = params[0];
        result = server.doCreateGame(gameName, authToken);
        return String.format("You created a game out as %s", username);
      }else{
        return String.format("Wrong input");
      }
    }catch (Exception e){
      return result.getMessage();
    }

  }

  public String list(){
    ListGamesResult result=null;
      try{
        result = server.doListGames(authToken);
        // How to print each games. ID? Name?
        ArrayList<ListGamesResult.tempListGame> list = result.getListGames();
        for(ListGamesResult.tempListGame game : list){
          System.out.println(game.toString());
        }
        return String.format("Listed Games.");
      }catch (Exception e){
        return result.getMessage();
      }
  }

  public String join(String... params){
    JoinGameResult result = null;
    try{
      if(params.length == 1){
        //Join as observe
        return observe(params);
      }else if (params.length == 2){
        //Join as a player
        String tempId = params[0];
        Integer id = Integer.parseInt(tempId);
        String team = params[1];
        if(team.equalsIgnoreCase("white")){
          isWhite = true;
        }else{
          isBlack = true;
        }
        result = server.doJoinGame(id, team, authToken);
        if(result.getMessage() != null){
          return result.getMessage();
        }else{
          ws.joinGameAsPlayer(id, team, authToken);
          isPlayer = true;
          gameID = id;
          System.out.print(SET_BG_COLOR_DARK_GREY);
          return String.format("You joined a game as ID: %d", id);
        }
      }
    }catch (Exception e){
      // error server....
      if(result == null){
        return String.format("Wrong input");
      }else{
        return result.getMessage();
      }

    }
    return String.format("Wrong input");
  }

  public String observe(String... params){
    JoinGameResult result=null;
    try{
      if(params.length == 1){
        String tempId = params[0];
        Integer id = Integer.parseInt(tempId);
        result = server.doJoinGame(id, null, authToken);
        isObserver = true;
        gameID = id;
        System.out.print(SET_BG_COLOR_DARK_GREY);
        ws.joinGameAsObserver(id, authToken);
        return String.format("You joined a game ID: %d as an observe.", id);
      }
    }catch (Exception e){
      return result.getMessage();
    }
    return String.format("Wrong input");
  }
  public String register(String... params){
    RegisterResult regRes = null;
    //Correct Register
    if(params.length == 3){
      username = params[0];
      password = params[1];
      email = params[2];
      //Register and Login
      try{
        regRes = server.doRegister(username, password, email);
        LoginResult logRes = server.doLogin(username, password);
        if(regRes.getMessage() == null){
          this.authToken = logRes.getAuthToken();
          isLogedIn = true;
          return String.format("You logged in as %s", username);
        }else{
          return regRes.getMessage();
        }
      }catch (Exception e){
        return "Error";
      }
    }
    return String.format("Wrong input");
  }// end of register
  public String login(String... params){
    LoginResult logRes = null;
    if(params.length == 2){

      username = params[0];
      password = params[1];
      try{
        logRes = server.doLogin(username, password);
        if(logRes.getMessage() == null){
          isLogedIn = true;
          this.authToken = logRes.getAuthToken();
          return String.format("You logged in as %s", username);
        }else{
          return logRes.getMessage();
        }
      }catch (Exception e){
        return "Error";
      }
    }
    return String.format("Wrong Input");
  }

  public String help(){
    String result = "";
    if(isLogedIn && isPlayer == false && isObserver == false){
      result += SET_TEXT_COLOR_BLUE + "create <NAME> " + SET_TEXT_COLOR_MAGENTA + "- a game\n";
      result += SET_TEXT_COLOR_BLUE + "list " + SET_TEXT_COLOR_MAGENTA + "- games\n";
      result += SET_TEXT_COLOR_BLUE + "join <ID> [WHITE|BLACK|<empty>] " + SET_TEXT_COLOR_MAGENTA + "- a game\n";
      result += SET_TEXT_COLOR_BLUE + "observe <ID> " + SET_TEXT_COLOR_MAGENTA + "- a game\n";
      result += SET_TEXT_COLOR_BLUE + "logout " + SET_TEXT_COLOR_MAGENTA + "- when you are done\n";
      result += SET_TEXT_COLOR_BLUE + "quit " + SET_TEXT_COLOR_MAGENTA + "- playing chess\n";
      result += SET_TEXT_COLOR_BLUE + "help " + SET_TEXT_COLOR_MAGENTA + "- with possible commands\n";
    }else if(isLogedIn && isPlayer){
      result += SET_TEXT_COLOR_BLUE + "redraw" +SET_TEXT_COLOR_MAGENTA+ "- redraw chess board\n";
      result += SET_TEXT_COLOR_BLUE + "leave" +SET_TEXT_COLOR_MAGENTA+ "- leave the game\n";
      result += SET_TEXT_COLOR_BLUE + "move <beforeMove> <afterMove>" +SET_TEXT_COLOR_MAGENTA+ "- you can move a piece that you want\n";
      result += SET_TEXT_COLOR_BLUE + "resign" +SET_TEXT_COLOR_MAGENTA+ "- resign the game\n";
      result += SET_TEXT_COLOR_BLUE + "highlight <position>" +SET_TEXT_COLOR_MAGENTA+ "- highlight legal moves\n";
    }else if(isLogedIn && isObserver){
      result += SET_TEXT_COLOR_BLUE + "redraw" +SET_TEXT_COLOR_MAGENTA+ "- redraw chess board\n";
      result += SET_TEXT_COLOR_BLUE + "leave" +SET_TEXT_COLOR_MAGENTA+ "- leave the game\n";
    }else{
      result += SET_TEXT_COLOR_BLUE + "register <USERNAME> <PASSWORD> <EMAIL> " + SET_TEXT_COLOR_MAGENTA + "- to create an account\n";
      result += SET_TEXT_COLOR_BLUE + "login <USERNAME> <PASSWORD> " + SET_TEXT_COLOR_MAGENTA + "- to play chess\n";
      result += SET_TEXT_COLOR_BLUE + "quit " + SET_TEXT_COLOR_MAGENTA + "- playing chess\n";
      result += SET_TEXT_COLOR_BLUE + "help " + SET_TEXT_COLOR_MAGENTA + "- with possible commands\n";
    }
    return result;
  }// end of help

}
