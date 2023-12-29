package server;


import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.*;
import model.AuthToken;
import model.Game;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;
@WebSocket
public class WebSocketHandler {
  private final ConnectionManager connections = new ConnectionManager();
  private boolean isResigned = false;
  private AuthDAO authDAO = new AuthDAO();
  private GameDAO gameDAO = new GameDAO();
  @OnWebSocketError
  public void onError(Throwable error){
    error.printStackTrace();
  }
  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException{
    try{
      UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
      AuthToken authToken = authDAO.findAuth(command.getAuthString());
      String username = authToken.getUsername();
      switch (command.getCommandType()){
        case JOIN_PLAYER -> joinPlayer(username, session, message, command.getAuthString());
        case JOIN_OBSERVER -> joinObserver(username, session, message, command.getAuthString());
        case MAKE_MOVE -> makeMove(username, session, message, command.getAuthString());
        case LEAVE -> leave(username, session, message, command.getAuthString());
        case RESIGN -> resign(username, session, message, command.getAuthString());
      }
    }catch (Exception e){
      var error = String.format("Command Error. Please check your input!");
      var errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, error);
      connections.sendError(errorMessage, session);
    }

  }

  private void joinPlayer(String username, Session session, String message, String authToken) throws IOException, DataAccessException {
    JoinPlayerCommand joinPlayerCommand = new Gson().fromJson(message, JoinPlayerCommand.class);
    Game dataGame = gameDAO.find(joinPlayerCommand.getGameID());
    ChessGameImpl game =(ChessGameImpl) dataGame.getGame();

    // White team case
    if(dataGame.getWhiteUsername() != null && joinPlayerCommand.getTeamColor() == ChessGame.TeamColor.WHITE && dataGame.getWhiteUsername().equalsIgnoreCase(username)){
      connections.add(joinPlayerCommand.getAuthString(), session);
      var notification = String.format("%s joined the game as a player (%s).",username, joinPlayerCommand.getTeamColor());
      var notifi = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, notification);
      connections.broadcast(authToken, notifi);
      isResigned = false;
      var loadGame = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
      connections.loadGame(loadGame, session);
    }

    //Black team Case
    else if(dataGame.getBlackUsername() != null && joinPlayerCommand.getTeamColor() == ChessGame.TeamColor.BLACK && dataGame.getBlackUsername().equalsIgnoreCase(username)){
      connections.add(joinPlayerCommand.getAuthString(), session);
      var notification = String.format("%s joined the game as a player (%s).",username, joinPlayerCommand.getTeamColor());
      var notifi = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, notification);
      connections.broadcast(authToken, notifi);
      isResigned = false;
      var loadGame = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
      connections.loadGame(loadGame, session);
    }
    else{
      throw new IOException();
    }
  }

  private void joinObserver(String username, Session session, String message, String authToken) throws IOException, DataAccessException {
    JoinObserverCommand joinObserverCommand = new Gson().fromJson(message, JoinObserverCommand.class);
    Game dataGame = gameDAO.find(joinObserverCommand.getGameID());
    ChessGameImpl game =(ChessGameImpl) dataGame.getGame();

    //BroadCast for players
    connections.add(joinObserverCommand.getAuthString(), session);
    var notification = String.format("%s joined the game as a observer.", username);
    var notifi = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, notification);
    connections.broadcast(authToken, notifi);
    //Load game
    var loadGame = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
    connections.loadGame(loadGame, session);
  }

  private void makeMove(String username, Session session, String message, String authToken) throws IOException, DataAccessException, InvalidMoveException {
    // when the game was resigned.
    if(isResigned == false){
      MakeMoveCommand makeMoveCommand = new Gson().fromJson(message, MakeMoveCommand.class);
      ChessPositionImpl startPosition =(ChessPositionImpl) makeMoveCommand.getMove().getStartPosition();
      ChessPositionImpl endPosition =(ChessPositionImpl) makeMoveCommand.getMove().getEndPosition();
      Game dataGame = gameDAO.find(makeMoveCommand.getGameID());
      ChessGameImpl game =(ChessGameImpl) dataGame.getGame();

      //Checking if the player's color and piece's color are the same.
      if(dataGame.getWhiteUsername().equalsIgnoreCase(username) && game.getBoard().getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
        //Update the game
        game.makeMove(makeMoveCommand.getMove());
        dataGame.setGame(game);
        gameDAO.updateGame(makeMoveCommand.getGameID(), dataGame);

        //Notification
        //Load Game
        var loadGame = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
        var notification = String.format("%s moved the piece from %s to %s", username, startPosition.toString(), endPosition.toString());
        var notifi = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, notification);
        connections.loadGame(loadGame, session);
        connections.broadcast(authToken, notifi);
        connections.broadcast(authToken, loadGame);
        if(game.isInCheckmate(game.getTeamTurn())){
          String team;
          if(game.getTeamTurn() == ChessGame.TeamColor.BLACK){
            team = "White";
          }else{
            team = "Black";
          }
          var checkNoti = String.format("Check Mate!!!!!!!!!!!!!! by %s", team);
          var checkNotify = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, checkNoti);
          connections.resignMessage(checkNotify, session);
          connections.broadcast(authToken, checkNotify);

        }else if(game.isInCheck(game.getTeamTurn())){
          String team;
          if(game.getTeamTurn() == ChessGame.TeamColor.BLACK){
            team = "White";
          }else{
            team = "Black";
          }
          var checkNoti = String.format("Check !!!!!!!!!!!!!! by %s", team);
          var checkNotify = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, checkNoti);
          connections.resignMessage(checkNotify, session);
          connections.broadcast(authToken, checkNotify);
        }

      }
      //Checking if the player's color and piece's color are the same.
      else if (dataGame.getBlackUsername().equalsIgnoreCase(username) && game.getBoard().getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
        //Update the game
        game.validMoves(startPosition);
        game.makeMove(makeMoveCommand.getMove());
        dataGame.setGame(game);
        gameDAO.updateGame(makeMoveCommand.getGameID(), dataGame);

        //Notification
        //Load Game
        var loadGame = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
        var notification = String.format("%s moved the piece from %s to %s", username, startPosition.toString(), endPosition.toString());
        var notifi = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, notification);
        connections.loadGame(loadGame, session);
        connections.broadcast(authToken, notifi);
        connections.broadcast(authToken, loadGame);
        if(game.isInCheckmate(game.getTeamTurn())){
          String team;
          if(game.getTeamTurn() == ChessGame.TeamColor.BLACK){
            team = "White";
          }else{
            team = "Black";
          }
          var checkNoti = String.format("Check Mate!!!!!!!!!!!!!! by %s", team);
          var checkNotify = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, checkNoti);
          connections.resignMessage(checkNotify, session);
          connections.broadcast(authToken, checkNotify);

        }else if(game.isInCheck(game.getTeamTurn())){
          String team;
          if(game.getTeamTurn() == ChessGame.TeamColor.BLACK){
            team = "White";
          }else{
            team = "Black";
          }
          var checkNoti = String.format("Check!!!!!!!!!!!!!! by %s", team);
          var checkNotify = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, checkNoti);
          connections.resignMessage(checkNotify, session);
          connections.broadcast(authToken, checkNotify);
        }
      }
      //checking if the player is observer or not.
      else if(dataGame.getBlackUsername() != username && dataGame.getWhiteUsername() != username){
        // error
        throw new InvalidMoveException();
      }
      //Error case
      else{
        throw new InvalidMoveException();
      }
    }
    else{
      throw new IOException();
    }

  }

  private void leave(String username, Session session, String message, String authToken) throws IOException, DataAccessException {
    LeaveCommand leaveCommand = new Gson().fromJson(message, LeaveCommand.class);
    //Update a game
    Game dataGame = gameDAO.find(leaveCommand.getGameID());
    //leave white user
    if(dataGame.getWhiteUsername() != null && dataGame.getWhiteUsername().equalsIgnoreCase(username)){
      dataGame.setWhiteUsername(null);
      gameDAO.updateGame(leaveCommand.getGameID(), dataGame);
      connections.remove(authToken);
    }
    //leave black user
    else if(dataGame.getBlackUsername() != null && dataGame.getBlackUsername().equalsIgnoreCase(username)){
      dataGame.setBlackUsername(null);
      gameDAO.updateGame(leaveCommand.getGameID(), dataGame);
      connections.remove(authToken);
    }
    //leave observer
    else{
      connections.remove(authToken);
    }

    var notification = String.format("%s left the game.", username);
    var notifi = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, notification);
    connections.broadcast(authToken, notifi);
  }

  private void resign(String username, Session session, String message, String authToken) throws IOException, DataAccessException {
    ResignCommand resignCommand = new Gson().fromJson(message, ResignCommand.class);
    //Update a new game
    Game dataGame = gameDAO.find(resignCommand.getGameID());
    ChessGameImpl game =(ChessGameImpl) dataGame.getGame();
    if(isResigned){
      throw new IOException();
    }else{
      // check if the user is observer?
      if(!(dataGame.getWhiteUsername().equalsIgnoreCase(username)) && !(dataGame.getBlackUsername().equalsIgnoreCase(username))){
        throw new IOException();
      }
      //Player resign
      else{
        isResigned = true;
        var notification = String.format("%s resigned the game.", username);
        var notifi = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, notification);
        connections.resignMessage(notifi, session);
        connections.broadcast(authToken, notifi);
      }
    }
  }// end of resign
}
