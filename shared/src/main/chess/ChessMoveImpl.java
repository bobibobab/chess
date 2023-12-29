package chess;

import java.util.Objects;

public class ChessMoveImpl implements ChessMove{
  private ChessPositionImpl start;
  private ChessPositionImpl end;
  private ChessPiece.PieceType type;
  public ChessMoveImpl(ChessPosition start, ChessPosition end, ChessPiece.PieceType type){
    this.start = (ChessPositionImpl)start;
    this.end = (ChessPositionImpl)end;
    this.type = type;
  }
  public void setEndPosition(ChessPosition position){
    this.end = (ChessPositionImpl)position;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChessMoveImpl chessMove=(ChessMoveImpl) o;
    return Objects.equals(start, chessMove.start) && Objects.equals(end, chessMove.end) && type == chessMove.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end, type);
  }

  @Override
  public String toString() {
    return "ChessMoveImpl{" +
            "start=" + start +
            ", end=" + end +
            ", type=" + type +
            '}';
  }


  @Override
  public ChessPosition getStartPosition() {
    return start;
  }

  @Override
  public ChessPosition getEndPosition() {
    return end;
  }

  @Override
  public ChessPiece.PieceType getPromotionPiece() {
    return type;
  }
}
