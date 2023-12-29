package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class ChessGameImpl implements ChessGame{
  private ChessBoard board = new ChessBoardImpl();
  private ChessGame.TeamColor team = TeamColor.WHITE;
  private boolean validEnPassant = false;
  private boolean [] checkDoubleMoveWhite = new boolean[8];
  private boolean [] checkDoubleMoveBlack = new boolean[8];

  private boolean isWhiteKingMoved = false;
  private boolean isBlackKingMoved = false;
  private boolean isWhiteRook1Moved = false;
  private boolean isWhiteRook2Moved = false;
  private boolean isBlackRook1Moved = false;
  private boolean isBlackRook2Moved = false;

  public ChessGameImpl(){
    board.resetBoard();
  }

  @Override
  public TeamColor getTeamTurn() {
    return team;
  }

  @Override
  public void setTeamTurn(TeamColor team) {
    this.team = team;
  }

  private ChessBoard copyBoard(ChessBoard copyB){
    ChessBoard newBoard = new ChessBoardImpl();
    for(int row = 0; row < 8; row ++){
      for(int col = 0; col < 8; col ++){
        ChessPosition pos = new ChessPositionImpl(row, col);
        newBoard.addPiece(pos, board.getPiece(pos));
      }
    }
    return newBoard;
  }

  private boolean checkValid(ChessMove move, TeamColor color){
    ChessBoard tempBoard = copyBoard(board);
    // Make a move for a chess piece.
    if(board.getPiece(move.getEndPosition()) != null){
      // The case of capture
      board.addPiece(move.getEndPosition(), null);
      board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
      board.addPiece(move.getStartPosition(), null);
    }else{
      // Not Capture.
      board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
      board.addPiece(move.getStartPosition(), null);
    }
    // Check if the move is in Check or not.
    if(isInCheck(color)){
      tempSetBoard(tempBoard);
      return false;
    }else{
      tempSetBoard(tempBoard);
      return true;
    }
  }

  private HashSet<ChessMove> castlingMove(ChessPosition startPosition, HashSet<ChessMove> validMoves){

    if(board.getPiece(startPosition).getTeamColor() == TeamColor.WHITE){
      // King nor Rook have moved since the game started
      if(isWhiteKingMoved == false && (isWhiteRook1Moved == false)){
        // There are no pieces between the king and the Rook
        for(int i = startPosition.getColumn() - 1; i > 0; i--){
          ChessPosition pos = new ChessPositionImpl(startPosition.getRow(), i);
          if(board.getPiece(pos) != null){
            return null;
          }
        }
        // The King is not in Check
        if(isInCheck(TeamColor.WHITE)){
          return null;
        }else{
          ChessPosition kingPos = new ChessPositionImpl(startPosition.getRow(), startPosition.getColumn() - 2);
          ChessMove kingMove = new ChessMoveImpl(startPosition, kingPos, null);
          // Both your Rook and King will be safe after making the move
          if(checkValid(kingMove, TeamColor.WHITE)){
            ChessPosition rookStartPos = new ChessPositionImpl(0,0);
            ChessPosition rookEndPos = new ChessPositionImpl(startPosition.getRow(), startPosition.getColumn() - 1);
            ChessMove rookMove = new ChessMoveImpl(rookStartPos, rookEndPos, null);
            if(checkValid(rookMove, TeamColor.WHITE)){
              validMoves.add(kingMove);
            }else{
              return  null;
            }
          }else{
            return null;
          }
        }
      }
      if(isWhiteKingMoved == false && (isWhiteRook2Moved == false)){
        for(int i = startPosition.getColumn() + 1; i < 7; i++){
          ChessPosition pos = new ChessPositionImpl(startPosition.getRow(), i);
          if(board.getPiece(pos) != null){
            return null;
          }// end of if statement
        }// end of for loop
        if(isInCheck(TeamColor.WHITE)){
          return null;
        }else{
          ChessPosition kingPos = new ChessPositionImpl(startPosition.getRow(), startPosition.getColumn() + 2);
          ChessMove kingMove = new ChessMoveImpl(startPosition, kingPos, null);
          // Both your Rook and King will be safe after making the move
          if(checkValid(kingMove, TeamColor.WHITE)){
            ChessPosition rookStartPos = new ChessPositionImpl(0,7);
            ChessPosition rookEndPos = new ChessPositionImpl(startPosition.getRow(), startPosition.getColumn() + 1);
            ChessMove rookMove = new ChessMoveImpl(rookStartPos, rookEndPos, null);
            if(checkValid(rookMove, TeamColor.WHITE)){
              validMoves.add(kingMove);
            }else{
              return  null;
            }
          }else{
            return null;
          }
        }
      }
    }
    //Black Castling
    else if(board.getPiece(startPosition).getTeamColor() == TeamColor.BLACK){
      // King nor Rook have moved since the game started
      if(isBlackKingMoved == false && (isBlackRook1Moved == false)){
        // There are no pieces between the king and the Rook
        for(int i = startPosition.getColumn() - 1; i > 0; i--){
          ChessPosition pos = new ChessPositionImpl(startPosition.getRow(), i);
          if(board.getPiece(pos) != null){
            return null;
          }
        }
        // The King is not in Check
        if(isInCheck(TeamColor.BLACK)){
          return null;
        }else{
          ChessPosition kingPos = new ChessPositionImpl(startPosition.getRow(), startPosition.getColumn() - 2);
          ChessMove kingMove = new ChessMoveImpl(startPosition, kingPos, null);
          // Both your Rook and King will be safe after making the move
          if(checkValid(kingMove, TeamColor.BLACK)){
            ChessPosition rookStartPos = new ChessPositionImpl(startPosition.getRow(),0);
            ChessPosition rookEndPos = new ChessPositionImpl(startPosition.getRow(), startPosition.getColumn() - 1);
            ChessMove rookMove = new ChessMoveImpl(rookStartPos, rookEndPos, null);
            if(checkValid(rookMove, TeamColor.BLACK)){
              validMoves.add(kingMove);
            }else{
              return  null;
            }
          }else{
            return null;
          }
        }
      }
      if(isBlackKingMoved == false && (isBlackRook2Moved == false)){
        for(int i = startPosition.getColumn() + 1; i < 7; i++){
          ChessPosition pos = new ChessPositionImpl(startPosition.getRow(), i);
          if(board.getPiece(pos) != null){
            return null;
          }// end of if statement
        }// end of for loop
        if(isInCheck(TeamColor.BLACK)){
          return null;
        }else{
          ChessPosition kingPos = new ChessPositionImpl(startPosition.getRow(), startPosition.getColumn() + 2);
          ChessMove kingMove = new ChessMoveImpl(startPosition, kingPos, null);
          // Both your Rook and King will be safe after making the move
          if(checkValid(kingMove, TeamColor.BLACK)){
            ChessPosition rookStartPos = new ChessPositionImpl(startPosition.getRow(),7);
            ChessPosition rookEndPos = new ChessPositionImpl(startPosition.getRow(), startPosition.getColumn() + 1);
            ChessMove rookMove = new ChessMoveImpl(rookStartPos, rookEndPos, null);
            if(checkValid(rookMove, TeamColor.BLACK)){
              validMoves.add(kingMove);
            }else{
              return  null;
            }
          }else{
            return null;
          }
        }
      }
    }
    return validMoves;
  }// end of castling.

  private HashSet<ChessMove> enPassantMove(ChessPosition startPosition, HashSet<ChessMove> validMoves){
    // White Pawn Case
    if(board.getPiece(startPosition).getTeamColor() == TeamColor.WHITE){
      if(startPosition.getRow() == 4){
        if(checkDoubleMoveBlack[startPosition.getColumn() - 1] && startPosition.getColumn() > 0){
          ChessPosition checkPassant = new ChessPositionImpl(startPosition.getRow(), startPosition.getColumn() - 1);
          if(board.getPiece(checkPassant).getPieceType() == ChessPiece.PieceType.PAWN && board.getPiece(checkPassant).getTeamColor() == TeamColor.BLACK){
            ChessPosition endPos = new ChessPositionImpl(startPosition.getRow() + 1, startPosition.getColumn() - 1);
            ChessMove move = new ChessMoveImpl(startPosition, endPos, null);
            validMoves.add(move);
          }
        }else if(checkDoubleMoveBlack[startPosition.getColumn() + 1] && startPosition.getColumn() < 7){
          ChessPosition checkPassant2 = new ChessPositionImpl(startPosition.getRow(), startPosition.getColumn() + 1);
          if(board.getPiece(checkPassant2).getPieceType() == ChessPiece.PieceType.PAWN && board.getPiece(checkPassant2).getTeamColor() == TeamColor.BLACK){
            ChessPosition endPos = new ChessPositionImpl(startPosition.getRow() + 1, startPosition.getColumn() + 1);
            ChessMove move = new ChessMoveImpl(startPosition, endPos, null);
            validMoves.add(move);
          }
        }
      }else{
        return null;
      }
    }
    // Black Pawn Case
    else{
      if(startPosition.getRow() == 3){
        if(checkDoubleMoveWhite[startPosition.getColumn() - 1] && startPosition.getColumn() > 0){
          ChessPosition checkPassant = new ChessPositionImpl(startPosition.getRow(), startPosition.getColumn() - 1);
          if(board.getPiece(checkPassant).getPieceType() == ChessPiece.PieceType.PAWN && board.getPiece(checkPassant).getTeamColor() == TeamColor.WHITE){
            ChessPosition endPos = new ChessPositionImpl(startPosition.getRow() - 1, startPosition.getColumn() - 1);
            ChessMove move = new ChessMoveImpl(startPosition, endPos, null);
            validMoves.add(move);
          }
        }else if(checkDoubleMoveWhite[startPosition.getColumn() + 1] && startPosition.getColumn() < 7){
          ChessPosition checkPassant2 = new ChessPositionImpl(startPosition.getRow(), startPosition.getColumn() + 1);
          if(board.getPiece(checkPassant2).getPieceType() == ChessPiece.PieceType.PAWN && board.getPiece(checkPassant2).getTeamColor() == TeamColor.WHITE){
            ChessPosition endPos = new ChessPositionImpl(startPosition.getRow() - 1, startPosition.getColumn() + 1);
            ChessMove move = new ChessMoveImpl(startPosition, endPos, null);
            validMoves.add(move);
          }
        }
      }else{
        return null;
      }
    }
    return validMoves;
  }


  @Override
  public Collection<ChessMove> validMoves(ChessPosition startPosition) {
    HashSet<ChessMove> validMoves = new HashSet<ChessMove>();
    HashSet<ChessMove> normalMoves =(HashSet<ChessMove>) board.getPiece(startPosition).pieceMoves(board, startPosition);
    //Check Castling.
    if(board.getPiece(startPosition).getPieceType() == ChessPiece.PieceType.KING && startPosition.getColumn() == 4 ){
      HashSet<ChessMove> temp = castlingMove(startPosition, validMoves);
      if(temp != null){
        validMoves = temp;
      }
    }
    //Check En Passant
    if(board.getPiece(startPosition).getPieceType() == ChessPiece.PieceType.PAWN && validEnPassant){
        HashSet<ChessMove> temp2 =  enPassantMove(startPosition, validMoves);
        if(temp2 != null){
          validMoves = temp2;
        }
    }
    for(ChessMove validMove : normalMoves){
      if(checkValid(validMove, board.getPiece(startPosition).getTeamColor())){
        validMoves.add(validMove);
      }
    }
    return validMoves;
  }

  @Override
  public void makeMove(ChessMove move) throws InvalidMoveException {
    HashSet<ChessMove>validMoves =(HashSet<ChessMove>) validMoves(move.getStartPosition());
    //Checking if KING AND ROOK have moved.
    if(board.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.KING){
      if(getTeamTurn() == TeamColor.WHITE){
        isWhiteKingMoved = true;
      }else{
        isBlackKingMoved = true;
      }
    }else if(board.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.ROOK){
      if(getTeamTurn() == TeamColor.WHITE && move.getStartPosition().getRow() == 0 && move.getStartPosition().getColumn() == 0 ){
        isWhiteRook1Moved = true;
      }else if(getTeamTurn() == TeamColor.WHITE && move.getStartPosition().getRow() == 0 && move.getStartPosition().getColumn() == 7){
        isWhiteRook2Moved = true;
      }else if(getTeamTurn() == TeamColor.BLACK && move.getStartPosition().getRow() == 7 && move.getStartPosition().getColumn() == 0){
        isBlackRook1Moved = true;
      }else if(getTeamTurn() == TeamColor.BLACK && move.getStartPosition().getRow() == 7 && move.getStartPosition().getColumn() == 7){
        isBlackRook2Moved = true;
      }
    }
    if(validMoves != null && validMoves.contains(move) && board.getPiece(move.getStartPosition()).getTeamColor() == getTeamTurn()){
      // Make the valid move.
      for(ChessMove m : validMoves){
        if(m.getEndPosition().equals(move.getEndPosition())){
          // The case of capture
          if(board.getPiece(move.getEndPosition()) != null){
            // Promotion
            if(getTeamTurn() == TeamColor.WHITE && move.getEndPosition().getRow() == 7 && board.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.PAWN){
              ChessPiece promotionPiece = new ChessPieceImpl(getTeamTurn(), move.getPromotionPiece());
              board.addPiece(move.getEndPosition(), null);
              board.addPiece(move.getEndPosition(), promotionPiece);
              board.addPiece(move.getStartPosition(), null);
              break;
            }else if(getTeamTurn() == TeamColor.BLACK && move.getEndPosition().getRow() == 0 && board.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.PAWN){
              ChessPiece promotionPiece = new ChessPieceImpl(getTeamTurn(), move.getPromotionPiece());
              board.addPiece(move.getEndPosition(), null);
              board.addPiece(move.getEndPosition(), promotionPiece);
              board.addPiece(move.getStartPosition(), null);
              break;
            }else{
              board.addPiece(move.getEndPosition(), null);
              board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
              board.addPiece(move.getStartPosition(), null);
              break;
            }

          }
          //Castling move.
          else if(board.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.KING && (m.getStartPosition().getColumn() - m.getEndPosition().getColumn() == 2 || m.getStartPosition().getColumn() - m.getEndPosition().getColumn() == -2)){
            //Move rook and King.
            if(m.getStartPosition().getColumn() - m.getEndPosition().getColumn() == 2){
              board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
              board.addPiece(move.getStartPosition(), null);
              ChessPosition rookEndPos = new ChessPositionImpl(move.getStartPosition().getRow(), move.getStartPosition().getColumn() - 1);
              ChessPosition rookStartPos = new ChessPositionImpl(move.getStartPosition().getRow(), 0);
              board.addPiece(rookEndPos, board.getPiece(rookStartPos));
              board.addPiece(rookStartPos, null);
              break;
            }else {
              board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
              board.addPiece(move.getStartPosition(), null);
              ChessPosition rookEndPos = new ChessPositionImpl(move.getStartPosition().getRow(), move.getStartPosition().getColumn() + 1);
              ChessPosition rookStartPos = new ChessPositionImpl(move.getStartPosition().getRow(), 7);
              board.addPiece(rookEndPos, board.getPiece(rookStartPos));
              board.addPiece(rookStartPos, null);
              break;
            }
          }
          // Not Capture.
          else{
            //Promotion.
            if(getTeamTurn() == TeamColor.WHITE && move.getEndPosition().getRow() == 7 && board.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.PAWN ){
              ChessPiece promotionPiece = new ChessPieceImpl(getTeamTurn(), move.getPromotionPiece());
              board.addPiece(move.getEndPosition(), promotionPiece);
              board.addPiece(move.getStartPosition(), null);
              break;
            }else if(getTeamTurn() == TeamColor.BLACK && move.getEndPosition().getRow() == 0 && board.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.PAWN){
              ChessPiece promotionPiece = new ChessPieceImpl(getTeamTurn(), move.getPromotionPiece());
              board.addPiece(move.getEndPosition(), promotionPiece);
              board.addPiece(move.getStartPosition(), null);
              break;
            }else{
              // Check if the pawn move double or not.
              if(board.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.PAWN){
                if(move.getStartPosition().getRow() - move.getEndPosition().getRow() == 2 || move.getStartPosition().getRow() - move.getEndPosition().getRow() == -2 ){
                  if(board.getPiece(move.getStartPosition()).getTeamColor() == TeamColor.WHITE){
                    validEnPassant = true;
                    checkDoubleMoveWhite[move.getStartPosition().getColumn()] = true;
                    board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                    board.addPiece(move.getStartPosition(), null);
                    break;
                  }
                  if(board.getPiece(move.getStartPosition()).getTeamColor() == TeamColor.BLACK){
                    validEnPassant = true;
                    checkDoubleMoveBlack[move.getStartPosition().getColumn()] = true;
                    board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                    board.addPiece(move.getStartPosition(), null);
                    break;
                  }
                }// end of checking the pawn moved double.
                // Case of the En Passant move
                if(board.getPiece(move.getStartPosition()).getTeamColor() == TeamColor.BLACK && move.getStartPosition().getRow() == 3){
                  if(board.getPiece(move.getEndPosition()) ==  null){
                    board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                    board.addPiece(move.getStartPosition(), null);
                    ChessPosition enemy = new ChessPositionImpl(move.getStartPosition().getRow(), move.getEndPosition().getColumn());
                    board.addPiece(enemy, null);
                    break;
                  }
                }else if (board.getPiece(move.getStartPosition()).getTeamColor() == TeamColor.WHITE && move.getStartPosition().getRow() == 4){

                  if(board.getPiece(move.getEndPosition()) ==  null){
                    board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                    board.addPiece(move.getStartPosition(), null);
                    ChessPosition enemy = new ChessPositionImpl(move.getStartPosition().getRow(), move.getEndPosition().getColumn());
                    board.addPiece(enemy, null);
                    break;
                  }
                }
              }
              board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
              board.addPiece(move.getStartPosition(), null);
              validEnPassant = false;
              break;
            }


          }
        }
      }
      if(team == TeamColor.BLACK){
        setTeamTurn(TeamColor.WHITE);
      }else{
        setTeamTurn(TeamColor.BLACK);
      }
    }else{
      // Throws InvalidMoveException.
      throw new InvalidMoveException("Your move is not valid.");
    }
  }

  private ChessPosition getKingPosition(ChessBoard chessBoard, TeamColor color){
    ChessPosition kingPos;
    for(int row = 0; row < 8; row ++){
      for(int col = 0; col < 8; col ++){
        ChessPosition pos = new ChessPositionImpl(row, col);
        if(board.getPiece(pos) != null && board.getPiece(pos).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(pos).getTeamColor() == color){
          kingPos = pos;
          return kingPos;
        }
      }
    }
    return null;
  }

  @Override
  public boolean isInCheck(TeamColor teamColor) {

    ChessPosition kingPos = getKingPosition(board, teamColor);
    //Find all enemy pieces.
    if(kingPos == null){
      return false;
    }
    for(int row = 0; row < 8; row++){
      for(int col = 0; col < 8; col++){
        ChessPosition pos = new ChessPositionImpl(row, col);
        if(board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() != board.getPiece(kingPos).getTeamColor()){
          HashSet<ChessMove> moves =(HashSet<ChessMove>) board.getPiece(pos).pieceMoves(board, pos);
          for(ChessMove move : moves){
            if(kingPos.equals(move.getEndPosition())){
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  @Override
  public boolean isInCheckmate(TeamColor teamColor) {
    ChessPosition kingPos = getKingPosition(board, teamColor);
    ArrayList<Boolean> checkMateValid = new ArrayList<Boolean>();
    HashSet<ChessMove> kingMoves;
    //Find all enemy pieces.
    if(kingPos == null){
      return false;
    }else{
      // check the same team piece can defend
      for(int row = 0; row < 8; row++) {
        for (int col=0; col < 8; col++) {
          ChessPosition pos = new ChessPositionImpl(row, col);
          if(board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() == board.getPiece(kingPos).getTeamColor()){
            HashSet<ChessMove> moves = (HashSet<ChessMove>) board.getPiece(pos).pieceMoves(board, pos);
            for(ChessMove enemyMove: moves){
              checkMateValid.add(checkValid(enemyMove, teamColor));
            }
          }
        }
      }
      // check the king have move;
      kingMoves =(HashSet<ChessMove>) board.getPiece(kingPos).pieceMoves(board, kingPos);
      for(ChessMove move : kingMoves){
        checkMateValid.add(checkValid(move, teamColor));
      }
      if(checkMateValid.contains(true)){
        return false;
      }else if(checkMateValid.size() == 0){
        return false;
      }
      else{
        return true;
      }
    }
  }

  @Override
  public boolean isInStalemate(TeamColor teamColor) {
    ChessPosition kingPos = getKingPosition(board, teamColor);
    ArrayList<Boolean> validStaleMate = new ArrayList<Boolean>();
    HashSet<ChessMove> kingMoves =(HashSet<ChessMove>) board.getPiece(kingPos).pieceMoves(board, kingPos);
    for(ChessMove move : kingMoves){
      validStaleMate.add(checkValid(move, teamColor));
    }
    if(validStaleMate.contains(true)){
      return false;
    }else{
      return true;
    }
  }

  private void tempSetBoard(ChessBoard board){
    this.board = board;
  }

  @Override
  public void setBoard(ChessBoard board) {
    if(this.board == board){
      // Nothing
    }else{
      isWhiteKingMoved = false;
      isWhiteRook1Moved = false;
      isWhiteRook2Moved = false;
      isBlackRook2Moved = false;
      isBlackRook1Moved = false;
      isBlackKingMoved = false;
    }

    this.board = board;
  }

  @Override
  public ChessBoard getBoard() {
    return board;
  }
}
