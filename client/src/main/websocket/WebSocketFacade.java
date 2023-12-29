package websocket;

import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.ChessBoardAdapter;
import dataAccess.ChessGameAdapter;
import dataAccess.ChessPieceAdapter;
import model.Game;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.userCommands.*;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

  Session session;
  NotificationHandler notificationHandler;

  public WebSocketFacade(String url, NotificationHandler notificationHandler) throws DeploymentException, IOException, URISyntaxException {
    try{
      url = url.replace("http", "ws");
      URI socketURI = new URI(url + "/connect");
      this.notificationHandler = notificationHandler;
      WebSocketContainer container = ContainerProvider.getWebSocketContainer();
      this.session = container.connectToServer(this, socketURI);

      this.session.addMessageHandler(new MessageHandler.Whole<String>(){
        @Override
        public void onMessage(String message){
          ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
          if(notification.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME){
            var builder = new GsonBuilder();
            builder.registerTypeAdapter(ChessGame.class, new ChessGameAdapter());
            builder.registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter());
            builder.registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter());
            LoadGameMessage loadGameMessage = builder.create().fromJson(message, LoadGameMessage.class);
            notificationHandler.loadGame(loadGameMessage);
          }else if(notification.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION){
            NotificationMessage notificationMessage = new Gson().fromJson(message, NotificationMessage.class);
            notificationHandler.notify(notificationMessage);
          }else{
            ErrorMessage errorMessage = new Gson().fromJson(message, ErrorMessage.class);
            notificationHandler.error(errorMessage);
          }

        }
      });
    }catch (DeploymentException | IOException | URISyntaxException ex){
      throw ex;//ResponseException()
    }
  }

  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {

  }


  public void joinGameAsPlayer(Integer gameID, String color, String authToken) throws IOException {
    ChessGame.TeamColor playerColor;
    if(color.equalsIgnoreCase("white")){
      playerColor = ChessGame.TeamColor.WHITE;
    }else{
      playerColor = ChessGame.TeamColor.BLACK;
    }
    try{
      var command = new JoinPlayerCommand(gameID, playerColor, authToken);
      command.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));

    }catch (IOException ex){
      // exception problem
      throw ex;
    }
  }

  public void joinGameAsObserver(Integer gameID, String authToken) throws IOException {
    try{
      var command = new JoinObserverCommand(gameID, authToken);
      command.setCommandType(UserGameCommand.CommandType.JOIN_OBSERVER);
      // What does this code line do??
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }catch (IOException ex){
      // exception problem
      throw ex;
    }
  }

  public void makeMove(Integer gameID, ChessMoveImpl move, String authToken) throws IOException {
    try{
      var command = new MakeMoveCommand(gameID, move, authToken);
      command.setCommandType(UserGameCommand.CommandType.MAKE_MOVE);
      // What does this code line do??
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }catch (IOException ex){
      // exception problem
      throw ex;
    }
  }

  public void leaveGame(Integer gameID, String authToken) throws IOException {
    try{
      var command = new LeaveCommand(gameID, authToken);
      command.setCommandType(UserGameCommand.CommandType.LEAVE);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }catch (IOException ex){
      // exception problem
      throw ex;
    }
  }

  public void resignGame(Integer gameID, String authToken) throws IOException {
    try{
      var command = new ResignCommand(gameID, authToken);
      command.setCommandType(UserGameCommand.CommandType.RESIGN);
      this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }catch (IOException ex){
      // exception problem
      throw ex;
    }
  }
}
