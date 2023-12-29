package client;

import chess.*;

import java.util.Collection;
import java.util.HashMap;

import static ui.EscapeSequences.*;

public class PrintingBoard {

  ChessGameImpl game;
  private boolean whitebgboard = false;
  private boolean isWhite = false;
  private boolean isBlack = false;

  public PrintingBoard(ChessGameImpl game, boolean teamWhite) {
    this.game = game;
    if(teamWhite){
      this.isWhite = true;
    }else{
      this.isBlack = true;
    }
  }

  public void highlight(ChessGameImpl game, ChessPositionImpl position){
    Collection<ChessMove> validMoves = game.validMoves(position);

    System.out.print(SET_BG_COLOR_LIGHT_GREY);
    String[] header = {"a", "b", "c", "d", "e", "f", "g", "h"};
    String[] column = {"1", "2", "3", "4", "5", "6", "7", "8"};
    if(isBlack){
      System.out.print("   ");
      //Print the first line of header
      for(int i = header.length - 1; i >= 0  ; i --){
        System.out.print(" ");
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + header[i]);
        System.out.print(" ");
      }

      // Print the 1 line of the board.
      for(int i = 0; i < column.length; i ++){
        System.out.println();
        System.out.print(" ");
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + column[i]);
        System.out.print(" ");
        for (int col = column.length - 1; col >= 0; col --){
          ChessPositionImpl validPosition = new ChessPositionImpl(i, col);
          int ind = 0;
          // if there is no validmoves
          if(validMoves.size() == 0){
            printPiece(i, col);
          }
          else{
            for (ChessMove move : validMoves){
              if(move.getEndPosition().equals(validPosition)){
                // contain validPosition in validMoves
                printHighlight(validPosition);
                break;
              }else if(move.getStartPosition().equals(validPosition)){
                printHighlight(validPosition);
                break;
              }else if(ind == validMoves.size() - 1){
                printPiece(i, col);
              }
              ind++;
            }// end of for loop
          }
          if(whitebgboard){
            whitebgboard = false;
          }else{
            whitebgboard = true;
          }
          if(col == 0){
            if(whitebgboard){
              whitebgboard = false;
            }else{
              whitebgboard = true;
            }
          }
        }

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + " " + column[i]);
      }
      System.out.println();
      System.out.print("  ");
      for(int i = header.length - 1; i >= 0  ; i --){
        System.out.print("  ");
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + header[i]);
      }
      System.out.println();
      System.out.println(SET_BG_COLOR_BLACK);
    }
    else{
      //Opposite board.
      System.out.print(SET_BG_COLOR_LIGHT_GREY);
      System.out.print("   ");
      for(int i = 0; i < header.length  ; i ++){
        System.out.print(" ");
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + header[i]);
        System.out.print(" ");
      }

      // Print the 1 line of the board.
      for(int i =column.length - 1; i >= 0 ; i --) {
        System.out.println();
        System.out.print(" ");
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + column[i]);
        System.out.print(" ");
        for (int col = 0; col < column.length; col ++){
          ChessPositionImpl validPosition = new ChessPositionImpl(i, col);
          int ind = 0;
          if(validMoves.size() == 0){
            printPiece(i, col);
          }
          else{
            for (ChessMove move : validMoves){
              if(move.getEndPosition().equals(validPosition)){
                // contain validPosition in validMoves
                printHighlight(validPosition);
                break;
              }else if(move.getStartPosition().equals(validPosition)){
                printHighlight(validPosition);
                break;
              }else if(ind == validMoves.size() - 1){
                printPiece(i, col);
              }
              ind++;
            }// end of for loop
          }
          if(whitebgboard){
            whitebgboard = false;
          }else{
            whitebgboard = true;
          }
          if(col == 7){
            if(whitebgboard){
              whitebgboard = false;
            }else{
              whitebgboard = true;
            }
          }
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + " " + column[i]);
      }
      System.out.println();
      System.out.print("  ");
      for(int i = 0; i < header.length  ; i ++){
        System.out.print("  ");
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + header[i]);
      }
      System.out.println();
      System.out.print(SET_BG_COLOR_LIGHT_GREY);
    }
  }
  public void printHighlight(ChessPositionImpl position){
    if(game.getBoard().getPiece(position) != null && game.getBoard().getPiece(position).getPieceType() == ChessPiece.PieceType.QUEEN){
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_YELLOW  +" ");
        System.out.print("Q");
        System.out.print(" ");
      }else{
        System.out.print(SET_BG_COLOR_YELLOW  +" ");
        System.out.print("Q");
        System.out.print(" ");
      }

    }else if(game.getBoard().getPiece(position) != null && game.getBoard().getPiece(position).getPieceType() == ChessPiece.PieceType.KNIGHT){
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_YELLOW  +" ");
        System.out.print("N");
        System.out.print(" ");
      }else{
        System.out.print(SET_BG_COLOR_YELLOW  +" ");
        System.out.print("N");
        System.out.print(" ");
      }
    }else if(game.getBoard().getPiece(position) != null && game.getBoard().getPiece(position).getPieceType() == ChessPiece.PieceType.PAWN){
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_YELLOW  +" ");
        System.out.print("P");
        System.out.print(" ");
      }else{
        System.out.print(SET_BG_COLOR_YELLOW  +" ");
        System.out.print("P");
        System.out.print(" ");
      }
    }else if(game.getBoard().getPiece(position) != null && game.getBoard().getPiece(position).getPieceType() == ChessPiece.PieceType.BISHOP){
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_YELLOW  +" ");
        System.out.print("B");
        System.out.print(" ");
      }else{
        System.out.print(SET_BG_COLOR_YELLOW  +" ");
        System.out.print("B");
        System.out.print(" ");
      }
    }else if(game.getBoard().getPiece(position) != null && game.getBoard().getPiece(position).getPieceType() == ChessPiece.PieceType.ROOK){
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_YELLOW  +" ");
        System.out.print("R");
        System.out.print(" ");
      }else{
        System.out.print(SET_BG_COLOR_YELLOW  +" ");
        System.out.print("R");
        System.out.print(" ");
      }
    }else if(game.getBoard().getPiece(position) != null && game.getBoard().getPiece(position).getPieceType() == ChessPiece.PieceType.KING){
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_YELLOW +" ");
        System.out.print("K");
        System.out.print(" ");
      }else{
        System.out.print(SET_BG_COLOR_YELLOW +" ");
        System.out.print("K");
        System.out.print(" ");
      }
    }else{
      //print yellow for blank spot
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_DARK_GREEN +" ");
        System.out.print(" ");
        System.out.print(" ");
      }else{
        System.out.print(SET_BG_COLOR_GREEN +" ");
        System.out.print(" ");
        System.out.print(" ");
      }

    }

  }

  public void printBoard(){
    System.out.print(SET_BG_COLOR_LIGHT_GREY);
    String[] header = {"a", "b", "c", "d", "e", "f", "g", "h"};
    String[] column = {"1", "2", "3", "4", "5", "6", "7", "8"};

    if(isBlack){
      //Black board
      System.out.print("   ");
      //Print the first line of header
      for(int i = header.length - 1; i >= 0  ; i --){
        System.out.print(" ");
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + header[i]);
        System.out.print(" ");
      }
      // Print the 1 line of the board.
      for(int i = 0; i < column.length; i ++){
        System.out.println();
        System.out.print(" ");
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + column[i]);
        System.out.print(" ");
        for (int col= column.length- 1; col >= 0; col--){
          printPiece(i, col);
          if(whitebgboard){
            whitebgboard = false;
          }else{
            whitebgboard = true;
          }
          if(col == 0){
            if(whitebgboard){
              whitebgboard = false;
            }else{
              whitebgboard = true;
            }
          }
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + " " + column[i]);
      }
      System.out.println();
      System.out.print("  ");
      for(int i = header.length - 1; i >= 0  ; i --){
        System.out.print("  ");
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + header[i]);
      }
      System.out.println();
      System.out.println(SET_BG_COLOR_BLACK);
    }
    else{
      //White board.
      System.out.print(SET_BG_COLOR_LIGHT_GREY);
      System.out.print("   ");
      for(int i = 0; i < header.length  ; i ++){
        System.out.print(" ");
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + header[i]);
        System.out.print(" ");
      }

      // Print the 1 line of the board.
      for(int i = column.length - 1 ; i >= 0 ; i --) {
        System.out.println();
        System.out.print(" ");
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + column[i]);
        System.out.print(" ");
        for (int col= 0; col < column.length; col++) {
          printPiece(i, col);
          if(whitebgboard){
            whitebgboard = false;
          }else{
            whitebgboard = true;
          }
          if(col == 7){
            if(whitebgboard){
              whitebgboard = false;
            }else{
              whitebgboard = true;
            }
          }
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + " " + column[i]);
      }
      System.out.println();
      System.out.print("  ");
      for(int i = 0; i < header.length  ; i ++){
        System.out.print("  ");
        System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + header[i]);
      }
      System.out.println();
      System.out.print(SET_BG_COLOR_LIGHT_GREY);
    }

  }

  public void printPiece(int row, int col){
    ChessPositionImpl position = new ChessPositionImpl(row, col);
    if(game.getBoard().getPiece(position) == null){
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_BLACK + "   ");
      }else{
        System.out.print(SET_BG_COLOR_WHITE + "   ");
      }
    }else if(game.getBoard().getPiece(position).getPieceType() == ChessPiece.PieceType.PAWN){
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_BLACK + " ");
        if(game.getBoard().getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE){
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_RED + "P");
        }else{
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLUE + "P");
        }
        System.out.print(" ");
      }else{
        System.out.print(SET_BG_COLOR_WHITE + " ");
        if(game.getBoard().getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE){
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_RED + "P");
        }else{
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLUE + "P");
        }
        System.out.print(" ");
      }
    }else if(game.getBoard().getPiece(position).getPieceType() == ChessPiece.PieceType.ROOK){
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_BLACK + " ");
        if(game.getBoard().getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE){
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_RED + "R");
        }else{
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLUE + "R");
        }
        System.out.print(" ");
      }else{
        System.out.print(SET_BG_COLOR_WHITE + " ");
        if(game.getBoard().getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE){
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_RED + "R");
        }else{
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLUE + "R");
        }
        System.out.print(" ");
      }
    }else if(game.getBoard().getPiece(position).getPieceType() == ChessPiece.PieceType.KING){
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_BLACK + " ");
        if(game.getBoard().getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE){
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_RED + "K");
        }else{
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLUE + "K");
        }
        System.out.print(" ");
      }else{
        System.out.print(SET_BG_COLOR_WHITE + " ");
        if(game.getBoard().getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE){
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_RED + "K");
        }else{
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLUE + "K");
        }
        System.out.print(" ");
      }
    }else if(game.getBoard().getPiece(position).getPieceType() == ChessPiece.PieceType.KNIGHT){
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_BLACK + " ");
        if(game.getBoard().getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE){
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_RED + "N");
        }else{
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLUE + "N");
        }
        System.out.print(" ");
      }else{
        System.out.print(SET_BG_COLOR_WHITE + " ");
        if(game.getBoard().getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE){
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_RED + "N");
        }else{
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLUE + "N");
        }
        System.out.print(" ");
      }
    }else if(game.getBoard().getPiece(position).getPieceType() == ChessPiece.PieceType.QUEEN){
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_BLACK + " ");
        if(game.getBoard().getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE){
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_RED + "Q");
        }else{
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLUE + "Q");
        }
        System.out.print(" ");
      }else{
        System.out.print(SET_BG_COLOR_WHITE + " ");
        if(game.getBoard().getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE){
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_RED + "Q");
        }else{
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLUE + "Q");
        }
        System.out.print(" ");
      }
    }else if(game.getBoard().getPiece(position).getPieceType() == ChessPiece.PieceType.BISHOP){
      if(whitebgboard){
        System.out.print(SET_BG_COLOR_BLACK + " ");
        if(game.getBoard().getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE){
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_RED + "B");
        }else{
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLUE + "B");
        }
        System.out.print(" ");
      }else{
        System.out.print(SET_BG_COLOR_WHITE + " ");
        if(game.getBoard().getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE){
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_RED + "B");
        }else{
          System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLUE + "B");
        }
        System.out.print(" ");
      }
    }


  }

}
