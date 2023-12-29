package webSocketMessages.serverMessages;

import chess.ChessGame;
import chess.ChessGameImpl;

public class LoadGameMessage extends ServerMessage{
  public ChessGameImpl game;

  public LoadGameMessage(ServerMessageType type, ChessGameImpl game) {
    super(type);
    this.game = game;
  }
}
