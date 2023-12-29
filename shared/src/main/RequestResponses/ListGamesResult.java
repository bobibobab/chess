package RequestResponses;

import model.Game;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ListGamesResult class
 */

public class ListGamesResult {
  /**
     * Success response [200] {"games": ["gameID": 1234, "whiteUsername": "", "blackUsername": "", "gameName":""]}
   * Failure response [401] {"message" : "Error: unauthorized"}
   * Failure response [500] {"message" : "Error: description"}
   */
  private String message;
  private ArrayList<tempListGame> games = new ArrayList<>();

  public class tempListGame{
    private Integer gameID;
    private String gameName;
    private String whiteUsername;
    private String blackUsername;

    public tempListGame(Integer gameID, String gameName, String whiteUsername, String blackUsername){
      this.blackUsername = blackUsername;
      this.gameID = gameID;
      this.gameName = gameName;
      this.whiteUsername = whiteUsername;
    }

    @Override
    public String toString() {
      return  "gameID=" + gameID +
              ", gameName='" + gameName + '\'' +
              ", whiteUsername='" + whiteUsername + '\'' +
              ", blackUsername='" + blackUsername + '\'';
    }
  }

  /**
   * get a message
   * @return message
   */
  public String getMessage() {
    return message;
  }

  /**
   * set a message
   * @param message
   */
  public void setMessage(String message) {
    this.message=message;
  }

  /**
   * get a gameList
   * @return listGames
   */
  public ArrayList<tempListGame> getListGames() {
    return this.games;
  }



  public void setListGames(HashMap<Integer, Game> listGames) {
    for (Integer key : listGames.keySet()){
      Game game = listGames.get(key);
      tempListGame tempGame = new tempListGame(game.getGameID(), game.getGameName(), game.getWhiteUsername(), game.getBlackUsername());
      this.games.add(tempGame);
    }
  }
}

