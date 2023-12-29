package chess;

public class ChessBoardImpl implements ChessBoard{
  private ChessPiece[][] chessBoard;

  public ChessBoardImpl(){
    chessBoard = new ChessPiece[8][8];
  }

  @Override
  public void addPiece(ChessPosition position, ChessPiece piece) {
    chessBoard[position.getRow()][position.getColumn()] = piece;
  }

  @Override
  public ChessPiece getPiece(ChessPosition position) {
    return chessBoard[position.getRow()][position.getColumn()];
  }

  public void removePiece(ChessPosition position, ChessPiece piece){
    chessBoard[position.getRow()][position.getColumn()] = null;
  }

  @Override
  public void resetBoard() {
    chessBoard[0][0] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
    chessBoard[0][1] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
    chessBoard[0][2] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
    chessBoard[0][3] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
    chessBoard[0][4] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
    chessBoard[0][5] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
    chessBoard[0][6] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
    chessBoard[0][7] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
    for(int i = 0; i < 8; i ++){
      chessBoard[1][i] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
    }

    chessBoard[7][0] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
    chessBoard[7][1] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
    chessBoard[7][2] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
    chessBoard[7][3] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
    chessBoard[7][4] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
    chessBoard[7][5] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
    chessBoard[7][6] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
    chessBoard[7][7] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
    for(int i = 0; i < 8; i ++){
      chessBoard[6][i] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
    }

    for(int i = 3; i < 6; i ++){
      for(int j = 0; j < 8; j ++){
        chessBoard[i][j] = null;
      }
    }

  }
}
