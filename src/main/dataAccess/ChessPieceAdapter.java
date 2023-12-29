package dataAccess;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPieceImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPieceAdapter implements JsonDeserializer<ChessPiece> {

  @Override
  public ChessPiece deserialize(JsonElement el, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    return jsonDeserializationContext.deserialize(el, ChessPieceImpl.class);
  }
}
