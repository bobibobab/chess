package webSocketMessages.userCommands;

import chess.ChessMove;
import chess.ChessMoveImpl;

public class MakeMoveCommand extends UserGameCommand{
  ChessMoveImpl move = null;
  Integer gameID = null;

  public MakeMoveCommand(){
    super();
  }
  public MakeMoveCommand(Integer gameID, ChessMoveImpl move, String authToken) {
    super(authToken);
    this.gameID = gameID;
    this.move = move;
  }

  public ChessMove getMove() {
    return move;
  }

  public Integer getGameID() {
    return gameID;
  }
}
