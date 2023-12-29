package dataAccess;

import chess.ChessBoard;
import chess.ChessBoardImpl;
import chess.ChessPosition;
import chess.ChessPositionImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessBoardAdapter implements JsonDeserializer<ChessBoard> {

  @Override
  public ChessBoard deserialize(JsonElement el, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    return jsonDeserializationContext.deserialize(el, ChessBoardImpl.class);
  }
}
