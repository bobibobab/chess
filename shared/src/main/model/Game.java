package model;

import chess.ChessGame;
import chess.ChessGameImpl;

/**
 * Game Class
 * One of the model classes represent game information
 */
public class Game {
  private Integer gameID;
  private String whiteUsername = null;
  private String blackUsername = null;
  private String gameName;
  private ChessGame game;

  public Game(){

  }

  /**
   * Constructor of user class
   * @param gameName
   */
  public Game(String gameName) {
    this.gameName = gameName;
    this.game = new ChessGameImpl();
  }

  public void setGameName(String gameName) {
    this.gameName=gameName;
  }


  public void setGameID(Integer gameID){
    this.gameID = gameID;
  }
  /**
   * Get game ID
   * @return gameID
   */
  public int getGameID(){
    return gameID;
  }


  public void setBlackUsername(String blackUsername) {
    this.blackUsername=blackUsername;
  }

  public void setWhiteUsername(String whiteUsername) {
    this.whiteUsername=whiteUsername;
  }

  /**
   * get white user's name
   * @return whiteUsername
   */
  public String getWhiteUsername(){
    return whiteUsername;
  }

  public void setGame(ChessGame game){
    this.game = game;
  }
  /**
   * get black user's name
   * @return blackUsername
   */
  public String getBlackUsername(){
    return blackUsername;
  }

  /**
   * get game name
   * @return gameName
   */
  public String getGameName(){
    return gameName;
  }

  /**
   * Get game
   * @return game
   */
  public ChessGame getGame(){
    return game;
  }

}
