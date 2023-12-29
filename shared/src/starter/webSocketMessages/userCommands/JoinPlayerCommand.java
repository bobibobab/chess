package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayerCommand extends UserGameCommand{
  private Integer gameID = null;
  private ChessGame.TeamColor playerColor;
  public JoinPlayerCommand(Integer gameID, ChessGame.TeamColor playerColor, String authToken) {
    super(authToken);
    this.gameID = gameID;
    this.playerColor = playerColor;
  }

  public Integer getGameID(){
    return gameID;
  }

  public ChessGame.TeamColor getTeamColor(){
    return playerColor;
  }
}

