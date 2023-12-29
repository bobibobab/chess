package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class ChessPieceImpl implements ChessPiece{

  private ChessGame.TeamColor color;
  private ChessPiece.PieceType piece;


  public ChessPieceImpl (ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
    this.color = pieceColor;
    this.piece = type;
  }
  private ChessMove checkColor(ChessBoard board, ChessPosition position, ChessPosition myPos){
      ChessMove move = new ChessMoveImpl(myPos, position, null);
      return move;
  }
  @Override
  public ChessGame.TeamColor getTeamColor() {
    return color;
  }

  @Override
  public PieceType getPieceType() {
    return piece;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChessPieceImpl that=(ChessPieceImpl) o;
    return color == that.color && piece == that.piece;
  }

  @Override
  public int hashCode() {
    return Objects.hash(color, piece);
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    HashSet<ChessMove> moves = new HashSet();
    // move Bishop piece.
    if (this.piece == PieceType.BISHOP){
      moveOfQueen(board, myPosition, moves);
    }
    // move Knight piece
    else if(this.piece == PieceType.KNIGHT){
      int row = myPosition.getRow();
      int col = myPosition.getColumn();
      ChessPositionImpl pos1 = new ChessPositionImpl(row + 2 , col + 1);
      if(row + 2> 7 || col + 1 > 7){
        //Nothing
      }
      else if(board.getPiece(pos1) == null){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos1, null);
        moves.add(move);
      }
      else if(board.getPiece(pos1).getTeamColor() != color){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos1, null);
        moves.add(move);
      }
      ChessPositionImpl pos2 = new ChessPositionImpl(row + 2 , col - 1);
      if(row + 2 > 7 || col - 1 < 0){
        //Nothing
      }
      else if(board.getPiece(pos2) == null){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos2, null);
        moves.add(move);
      }
      else if(board.getPiece(pos2).getTeamColor() != color){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos2, null);
        moves.add(move);
      }

      ChessPositionImpl pos3 = new ChessPositionImpl(row - 2 , col + 1);
      if(row - 2 < 0 || col + 1 > 7){
        //do nothing.
      }
      else if(board.getPiece(pos3) == null){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos3, null);
        moves.add(move);
      }
      else if(board.getPiece(pos3).getTeamColor() != color){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos3, null);
        moves.add(move);
      }

      ChessPositionImpl pos4 = new ChessPositionImpl(row - 2 , col - 1);
      if(row - 2< 0 || col - 1 < 0){
        //do nothing.
      }
      else if(board.getPiece(pos4) == null){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos4, null);
        moves.add(move);
      }else if(board.getPiece(pos4).getTeamColor() != color){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos4, null);
        moves.add(move);
      }

      ChessPositionImpl pos5 = new ChessPositionImpl(row - 1 , col + 2);
      if(row - 1 < 0 || col + 2 > 7){
        //do nothing.
      }
      else if(board.getPiece(pos5) == null){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos5, null);
        moves.add(move);
      }else if(board.getPiece(pos5).getTeamColor() != color){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos5, null);
        moves.add(move);
      }

      ChessPositionImpl pos6 = new ChessPositionImpl(row - 1 , col - 2);
      if(row - 1< 0 || col - 2 < 0){
        //do nothing.
      }
      else if(board.getPiece(pos6) == null){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos6, null);
        moves.add(move);
      }else if(board.getPiece(pos6).getTeamColor() != color){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos6, null);
        moves.add(move);
      }

      ChessPositionImpl pos7 = new ChessPositionImpl(row + 1 , col + 2);
      if(row + 1 > 7 || col + 2 > 7){
        //do nothing.
      }
      else if(board.getPiece(pos7) == null){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos7, null);
        moves.add(move);
      }else if(board.getPiece(pos7).getTeamColor() != color){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos7, null);
        moves.add(move);
      }

      ChessPositionImpl pos8 = new ChessPositionImpl(row + 1 , col - 2);
      if(row + 1 > 7 || col - 2 < 0){
        //do nothing.
      }
      else if(board.getPiece(pos8) == null){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos8, null);
        moves.add(move);
      }else if(board.getPiece(pos8).getTeamColor() != color){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos8, null);
        moves.add(move);
      }
    }
    //Move the King Piece.
    else if(this.piece == PieceType.KING){
      int row = myPosition.getRow();
      int col = myPosition.getColumn();


      if(row == 7 && col == 7){
        ChessPositionImpl pos = new ChessPositionImpl(row -1, col);
        if(board.getPiece(pos) == null ||board.getPiece(pos).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos, null);
          moves.add(move);
        }
        ChessPositionImpl pos1 = new ChessPositionImpl(row, col -1);
        if(board.getPiece(pos1) == null ||board.getPiece(pos1).getTeamColor() != color) {
          ChessMove move1=new ChessMoveImpl(myPosition, pos1, null);
          moves.add(move1);
        }
        ChessPositionImpl pos2 = new ChessPositionImpl(row - 1, col -1);
        if(board.getPiece(pos2) == null || board.getPiece(pos2).getTeamColor() != color){
          ChessMove move2 = new ChessMoveImpl(myPosition, pos2, null);
          moves.add(move2);
        }
      }else if(row == 0 && col == 7){
        ChessPositionImpl pos = new ChessPositionImpl(row + 1, col);
        if(board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos, null);
          moves.add(move);
        }
        ChessPositionImpl pos1 = new ChessPositionImpl(row, col -1);
        if(board.getPiece(pos1) == null || board.getPiece(pos1).getTeamColor() != color) {
          ChessMove move1=new ChessMoveImpl(myPosition, pos1, null);
          moves.add(move1);
        }
        ChessPositionImpl pos2 = new ChessPositionImpl(row + 1, col -1);
        if(board.getPiece(pos2) == null || board.getPiece(pos2).getTeamColor() != color){
          ChessMove move2 = new ChessMoveImpl(myPosition, pos2, null);
          moves.add(move2);
        }
      }else if(row == 7 && col == 0){
        ChessPositionImpl pos = new ChessPositionImpl(row -1, col);
        if(board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos, null);
          moves.add(move);
        }
        ChessPositionImpl pos1 = new ChessPositionImpl(row, col + 1);
        if(board.getPiece(pos1) == null || board.getPiece(pos1).getTeamColor() != color) {
          ChessMove move1=new ChessMoveImpl(myPosition, pos1, null);
          moves.add(move1);
        }
        ChessPositionImpl pos2 = new ChessPositionImpl(row - 1, col + 1);
        if(board.getPiece(pos2) == null || board.getPiece(pos2).getTeamColor() != color){
          ChessMove move2 = new ChessMoveImpl(myPosition, pos2, null);
          moves.add(move2);
        }
      }else if(row == 0 && col == 0){
        ChessPositionImpl pos = new ChessPositionImpl(row + 1, col);
        if(board.getPiece(pos) == null ||board.getPiece(pos).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos, null);
          moves.add(move);
        }
        ChessPositionImpl pos1 = new ChessPositionImpl(row, col + 1);
        if(board.getPiece(pos1) == null || board.getPiece(pos1).getTeamColor() != color) {
          ChessMove move1=new ChessMoveImpl(myPosition, pos1, null);
          moves.add(move1);
        }
        ChessPositionImpl pos2 = new ChessPositionImpl(row + 1, col + 1);
        if(board.getPiece(pos2) == null || board.getPiece(pos2).getTeamColor() != color){
          ChessMove move2 = new ChessMoveImpl(myPosition, pos2, null);
          moves.add(move2);
        }
      }else if(row == 0){
        ChessPosition pos = new ChessPositionImpl(row, col + 1);
        if(board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos, null);
          moves.add(move);
        }
        ChessPosition pos1 = new ChessPositionImpl(row, col - 1);
        if(board.getPiece(pos1) == null || board.getPiece(pos1).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos1, null);
          moves.add(move);
        }
        ChessPosition pos2 = new ChessPositionImpl(row + 1, col + 1);
        if(board.getPiece(pos2) == null || board.getPiece(pos2).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos2, null);
          moves.add(move);
        }
        ChessPosition pos3 = new ChessPositionImpl(row + 1, col);
        if(board.getPiece(pos3) == null ||board.getPiece(pos3).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos3, null);
          moves.add(move);
        }
        ChessPosition pos4 = new ChessPositionImpl(row + 1, col - 1);
        if(board.getPiece(pos4) == null || board.getPiece(pos4).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos4, null);
          moves.add(move);
        }
      }else if(col == 0){
        ChessPosition pos = new ChessPositionImpl(row + 1, col);
        if(board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos, null);
          moves.add(move);
        }
        ChessPosition pos1 = new ChessPositionImpl(row - 1, col);
        if(board.getPiece(pos1) == null || board.getPiece(pos1).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos1, null);
          moves.add(move);
        }
        ChessPosition pos2 = new ChessPositionImpl(row + 1, col + 1);
        if(board.getPiece(pos2) == null || board.getPiece(pos2).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos2, null);
          moves.add(move);
        }
        ChessPosition pos3 = new ChessPositionImpl(row - 1, col + 1);
        if(board.getPiece(pos3) == null ||board.getPiece(pos3).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos3, null);
          moves.add(move);
        }
        ChessPosition pos4 = new ChessPositionImpl(row , col + 1);
        if(board.getPiece(pos4) == null || board.getPiece(pos4).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos4, null);
          moves.add(move);
        }
      }else if(row == 7){
        ChessPosition pos = new ChessPositionImpl(row, col + 1);
        if(board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos, null);
          moves.add(move);
        }
        ChessPosition pos1 = new ChessPositionImpl(row, col - 1);
        if(board.getPiece(pos1) == null || board.getPiece(pos1).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos1, null);
          moves.add(move);
        }
        ChessPosition pos2 = new ChessPositionImpl(row - 1, col + 1);
        if(board.getPiece(pos2) == null || board.getPiece(pos2).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos2, null);
          moves.add(move);
        }
        ChessPosition pos3 = new ChessPositionImpl(row - 1, col);
        if(board.getPiece(pos3) == null || board.getPiece(pos3).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos3, null);
          moves.add(move);
        }
        ChessPosition pos4 = new ChessPositionImpl(row - 1, col - 1);
        if(board.getPiece(pos4) == null || board.getPiece(pos4).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos4, null);
          moves.add(move);
        }
      }else if(col == 7){
        ChessPosition pos = new ChessPositionImpl(row + 1, col);
        if(board.getPiece(pos) == null ||board.getPiece(pos).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos, null);
          moves.add(move);
        }
        ChessPosition pos1 = new ChessPositionImpl(row - 1, col);
        if(board.getPiece(pos1) == null ||board.getPiece(pos1).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos1, null);
          moves.add(move);
        }
        ChessPosition pos2 = new ChessPositionImpl(row + 1, col - 1);
        if(board.getPiece(pos2) == null ||board.getPiece(pos2).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos2, null);
          moves.add(move);
        }
        ChessPosition pos3 = new ChessPositionImpl(row, col - 1);
        if(board.getPiece(pos3) == null ||board.getPiece(pos3).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos3, null);
          moves.add(move);
        }
        ChessPosition pos4 = new ChessPositionImpl(row - 1, col - 1);
        if(board.getPiece(pos4) == null ||board.getPiece(pos4).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos4, null);
          moves.add(move);
        }
      }else{
        ChessPosition pos = new ChessPositionImpl(row, col + 1);
        if(board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos, null);
          moves.add(move);
        }
        ChessPosition pos1 = new ChessPositionImpl(row + 1, col);
        if(board.getPiece(pos1) == null ||board.getPiece(pos1).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos1, null);
          moves.add(move);
        }
        ChessPosition pos2 = new ChessPositionImpl(row + 1, col + 1);
        if(board.getPiece(pos2) == null ||board.getPiece(pos2).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos2, null);
          moves.add(move);
        }
        ChessPosition pos3 = new ChessPositionImpl(row - 1, col);
        if(board.getPiece(pos3) == null ||board.getPiece(pos3).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos3, null);
          moves.add(move);
        }
        ChessPosition pos4 = new ChessPositionImpl(row, col - 1);
        if(board.getPiece(pos4) == null || board.getPiece(pos4).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos4, null);
          moves.add(move);
        }
        ChessPosition pos5 = new ChessPositionImpl(row - 1, col - 1);
        if(board.getPiece(pos5) == null ||board.getPiece(pos5).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos5, null);
          moves.add(move);
        }
        ChessPosition pos6 = new ChessPositionImpl(row - 1, col + 1);
        if(board.getPiece(pos6) == null ||board.getPiece(pos6).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos6, null);
          moves.add(move);
        }
        ChessPosition pos7 = new ChessPositionImpl(row + 1, col - 1);
        if(board.getPiece(pos7) == null ||board.getPiece(pos7).getTeamColor() != color){
          ChessMove move = new ChessMoveImpl(myPosition, pos7, null);
          moves.add(move);
        }
      }

    }// Move the Queen Piece
    else if(this.piece == PieceType.QUEEN){
      moveOfQueen(board, myPosition, moves);
      movesOfRook(board, myPosition, moves);
    }else if(this.piece == PieceType.ROOK){
      movesOfRook(board, myPosition, moves);
    }else if(this.piece == PieceType.PAWN){
      movesOfPawn(board, myPosition, moves);
    }
    return moves;
  }// end of piece movement.

  private void movesOfPawn(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves){
    int row = myPosition.getRow();
    int col = myPosition.getColumn();
    //White Case
    if(color == ChessGame.TeamColor.WHITE ){
      if(row == 1){
        ChessPosition checkBlack = new ChessPositionImpl(row + 1, col + 1);
        ChessPosition checkBlack2 = new ChessPositionImpl(row + 1, col -1);
        if(col < 7 && board.getPiece(checkBlack) != null && board.getPiece(checkBlack).getTeamColor() != color){
          ChessMove capture = new ChessMoveImpl(myPosition, checkBlack, null);
          moves.add(capture);
        }
        if(col > 0 && board.getPiece(checkBlack2) != null && board.getPiece(checkBlack2).getTeamColor() != color ){
          ChessMove capture = new ChessMoveImpl(myPosition, checkBlack2, null);
          moves.add(capture);
        }
        ChessPosition pos = new ChessPositionImpl(row + 1, col);
        ChessPosition pos1 = new ChessPositionImpl(row + 2, col);
        if(board.getPiece(pos) == null){
          ChessMove move = new ChessMoveImpl(myPosition, pos, null);
          moves.add(move);
          if(board.getPiece(pos1) == null){
            ChessMove move2 = new ChessMoveImpl(myPosition, pos1, null);
            moves.add(move2);
          }
        }

      }// the case of starting the pawn
      else if(row == 6){
        ChessPosition checkBlack = new ChessPositionImpl(row + 1, col + 1);
        ChessPosition checkBlack2 = new ChessPositionImpl(row + 1, col -1);
        if(col < 7 && board.getPiece(checkBlack) != null && board.getPiece(checkBlack).getTeamColor() != color){
          ChessMove capture = new ChessMoveImpl(myPosition, checkBlack, PieceType.BISHOP);
          ChessMove capture2 = new ChessMoveImpl(myPosition, checkBlack, PieceType.KNIGHT);
          ChessMove capture3 = new ChessMoveImpl(myPosition, checkBlack, PieceType.QUEEN);
          ChessMove capture4 = new ChessMoveImpl(myPosition, checkBlack, PieceType.ROOK);
          moves.add(capture);
          moves.add(capture2);
          moves.add(capture3);
          moves.add(capture4);
        }
        if(col > 0 && board.getPiece(checkBlack2) != null && board.getPiece(checkBlack2).getTeamColor() != color ){
          ChessMove capture = new ChessMoveImpl(myPosition, checkBlack2, PieceType.BISHOP);
          ChessMove capture2 = new ChessMoveImpl(myPosition, checkBlack2, PieceType.KNIGHT);
          ChessMove capture3 = new ChessMoveImpl(myPosition, checkBlack2, PieceType.QUEEN);
          ChessMove capture4 = new ChessMoveImpl(myPosition, checkBlack2, PieceType.ROOK);
          moves.add(capture);
          moves.add(capture2);
          moves.add(capture3);
          moves.add(capture4);
        }
        ChessPosition pos = new ChessPositionImpl(row + 1, col);
        if(board.getPiece(pos) == null){
          ChessMove move = new ChessMoveImpl(myPosition, pos, PieceType.BISHOP);
          ChessMove move1 = new ChessMoveImpl(myPosition, pos, PieceType.KNIGHT);
          ChessMove move2 = new ChessMoveImpl(myPosition, pos, PieceType.QUEEN);
          ChessMove move3 = new ChessMoveImpl(myPosition, pos, PieceType.ROOK);
          moves.add(move);
          moves.add(move1);
          moves.add(move2);
          moves.add(move3);
        }
      }// end of case of promotion.
      else{
        ChessPosition checkBlack = new ChessPositionImpl(row + 1, col + 1);
        ChessPosition checkBlack2 = new ChessPositionImpl(row + 1, col -1);
        if(col < 7 && board.getPiece(checkBlack) != null && board.getPiece(checkBlack).getTeamColor() != color ){
          ChessMove capture = new ChessMoveImpl(myPosition, checkBlack, null);
          moves.add(capture);
        }
        if(col > 0 && board.getPiece(checkBlack2) != null && board.getPiece(checkBlack2).getTeamColor() != color ){
          ChessMove capture = new ChessMoveImpl(myPosition, checkBlack2, null);
          moves.add(capture);
        }
        ChessPosition pos = new ChessPositionImpl(row + 1, col);
        if(board.getPiece(pos) == null){
          ChessMove move = new ChessMoveImpl(myPosition, pos, null);
          moves.add(move);
        }
      }// end of the case when the Pawn is in the middle.
    }
      //Black Case
    else{
      if(row == 6){
        ChessPosition checkWhite = new ChessPositionImpl(row - 1, col + 1);
        ChessPosition checkWhite2 = new ChessPositionImpl(row - 1, col -1);
        if(col < 7 && board.getPiece(checkWhite) != null && board.getPiece(checkWhite).getTeamColor() != color ){
          ChessMove capture = new ChessMoveImpl(myPosition, checkWhite, null);
          moves.add(capture);
        }
        if(col > 0 && board.getPiece(checkWhite2) != null && board.getPiece(checkWhite2).getTeamColor() != color ){
          ChessMove capture = new ChessMoveImpl(myPosition, checkWhite2, null);
          moves.add(capture);
        }

        ChessPosition pos = new ChessPositionImpl(row - 1, col);
        ChessPosition pos1 = new ChessPositionImpl(row - 2, col);
        if(board.getPiece(pos) == null){
          ChessMove move = new ChessMoveImpl(myPosition, pos, null);
          moves.add(move);
          if(board.getPiece(pos1) == null){
            ChessMove move2 = new ChessMoveImpl(myPosition, pos1, null);
            moves.add(move2);
          }
        }
      }// end of the case of starting Pawn
      else if(row == 1){
        ChessPosition checkWhite= new ChessPositionImpl(row - 1, col + 1);
        ChessPosition checkWhite2 = new ChessPositionImpl(row - 1, col -1);
        if(col < 7 && board.getPiece(checkWhite) != null && board.getPiece(checkWhite).getTeamColor() != color ){
          ChessMove capture = new ChessMoveImpl(myPosition, checkWhite, PieceType.BISHOP);
          ChessMove capture2 = new ChessMoveImpl(myPosition, checkWhite, PieceType.KNIGHT);
          ChessMove capture3 = new ChessMoveImpl(myPosition, checkWhite, PieceType.QUEEN);
          ChessMove capture4 = new ChessMoveImpl(myPosition, checkWhite, PieceType.ROOK);
          moves.add(capture);
          moves.add(capture2);
          moves.add(capture3);
          moves.add(capture4);
        }
        if(col > 0 && board.getPiece(checkWhite2) != null && board.getPiece(checkWhite2).getTeamColor() != color ){
          ChessMove capture = new ChessMoveImpl(myPosition, checkWhite2, PieceType.BISHOP);
          ChessMove capture2 = new ChessMoveImpl(myPosition, checkWhite2, PieceType.KNIGHT);
          ChessMove capture3 = new ChessMoveImpl(myPosition, checkWhite2, PieceType.QUEEN);
          ChessMove capture4 = new ChessMoveImpl(myPosition, checkWhite2, PieceType.ROOK);
          moves.add(capture);
          moves.add(capture2);
          moves.add(capture3);
          moves.add(capture4);
        }
        ChessPosition pos = new ChessPositionImpl(row - 1, col);
        if(board.getPiece(pos) == null){
          ChessMove move = new ChessMoveImpl(myPosition, pos, PieceType.BISHOP);
          ChessMove move1 = new ChessMoveImpl(myPosition, pos, PieceType.KNIGHT);
          ChessMove move2 = new ChessMoveImpl(myPosition, pos, PieceType.QUEEN);
          ChessMove move3 = new ChessMoveImpl(myPosition, pos, PieceType.ROOK);
          moves.add(move);
          moves.add(move1);
          moves.add(move2);
          moves.add(move3);
        }
      }// end of case of promotion.
      else{
        ChessPosition checkWhite= new ChessPositionImpl(row - 1, col + 1);
        ChessPosition checkWhite2 = new ChessPositionImpl(row - 1, col -1);
        if(col < 7 && board.getPiece(checkWhite) != null && board.getPiece(checkWhite).getTeamColor() != color){
          ChessMove capture = new ChessMoveImpl(myPosition, checkWhite, null);
          moves.add(capture);
        }
        if(col > 0 && board.getPiece(checkWhite2) != null && board.getPiece(checkWhite2).getTeamColor() != color ){
          ChessMove capture = new ChessMoveImpl(myPosition, checkWhite2, null);
          moves.add(capture);
        }
        ChessPosition pos = new ChessPositionImpl(row - 1, col);
        if(board.getPiece(pos) == null){
          ChessMove move = new ChessMoveImpl(myPosition, pos, null);
          moves.add(move);
        }
      }// end of the case when the Pawn is in the middle.
    }// end of Black case
  }
  private void movesOfRook(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves){

    int row = myPosition.getRow();
    int col = myPosition.getColumn();
    // Increase row
    for(int i = row + 1; i < 8; i ++){
      ChessPosition pos = new ChessPositionImpl(i, col);
      if(board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() == color){
        break;
      }else if(board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() != color){
        ChessMove move = new ChessMoveImpl(myPosition, pos,null);
        moves.add(move);
        break;
      }else{
        ChessMove move = new ChessMoveImpl(myPosition, pos,null);
        moves.add(move);
      }

    }
    // Decrease row
    for(int i = row - 1; i > -1; i --){
      ChessPosition pos = new ChessPositionImpl(i, col);
      if(board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() == color){
        break;
      }else if(board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() != color){
        ChessMove move = new ChessMoveImpl(myPosition, pos,null);
        moves.add(move);
        break;
      }else{
        ChessMove move = new ChessMoveImpl(myPosition, pos,null);
        moves.add(move);
      }
    }
    // Increase Column
    for(int i = col + 1; i < 8; i ++){
      ChessPosition pos = new ChessPositionImpl(row, i);
      if(board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() == color){
        break;
      }else if(board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() != color){
        ChessMove move = new ChessMoveImpl(myPosition, pos,null);
        moves.add(move);
        break;
      }else{
        ChessMove move = new ChessMoveImpl(myPosition, pos,null);
        moves.add(move);
      }
    }
    // Decrease Column
    for(int i = col - 1; i > -1; i --) {
      ChessPosition pos=new ChessPositionImpl(row, i);
      if (board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() == color) {
        break;
      } else if (board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() != color) {
        ChessMove move=new ChessMoveImpl(myPosition, pos, null);
        moves.add(move);
        break;
      } else {
        ChessMove move=new ChessMoveImpl(myPosition, pos, null);
        moves.add(move);
      }
    }
  }// end of moves OF Rook

  private void moveOfQueen(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves){
    int row = myPosition.getRow();
    int col =myPosition.getColumn();
    while(row != 7 && col != 7){
      row++;
      col++;
      ChessPositionImpl pos = new ChessPositionImpl(row, col);
      if(board.getPiece(pos) == null){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos, null);
        moves.add(move);
      }
      else if(board.getPiece(pos).getTeamColor() != color){
        //capture.
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos, null);
        moves.add(move);
        break;
      }
      else if(board.getPiece(pos).getTeamColor() == color){
        break;
      }
    }
    row =  myPosition.getRow();
    col =myPosition.getColumn();
    while(row != 7 && col != 0){
      row++;
      col--;
      ChessPositionImpl pos = new ChessPositionImpl(row, col);
      if(board.getPiece(pos) == null){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos, null);
        moves.add(move);
      }else if(board.getPiece(pos).getTeamColor() != color){
        //capture.
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos, null);
        moves.add(move);
        break;
      }else if(board.getPiece(pos).getTeamColor() == color){
        break;
      }
    }
    // Decrease row
    row = myPosition.getRow();
    col = myPosition.getColumn();
    while(row != 0 && col != 0){
      row--;
      col--;
      ChessPositionImpl pos = new ChessPositionImpl(row , col );
      if(board.getPiece(pos) == null){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos, null);
        moves.add(move);
      }else if(board.getPiece(pos).getTeamColor() != color){
        //capture.
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos, null);
        moves.add(move);
        break;
      }else if(board.getPiece(pos).getTeamColor() == color){
        break;
      }
    }
    row =myPosition.getRow();
    col =myPosition.getColumn();
    while(row != 0 && col != 7){
      row--;
      col++;
      ChessPositionImpl pos = new ChessPositionImpl(row , col);
      if(board.getPiece(pos) == null){
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos, null);
        moves.add(move);
      }else if(board.getPiece(pos).getTeamColor() != color){
        //capture.
        ChessMoveImpl move = new ChessMoveImpl(myPosition, pos, null);
        moves.add(move);
        break;
      }else if(board.getPiece(pos).getTeamColor() == color){
        break;
      }
    }
  }
}

