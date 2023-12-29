package RequestResponses;

/**
 * The class of JoinGameRequest
 */
public class JoinGameRequest {
  /**
   * Attribute for body
   * {"playerColor":"WHITE/BLACK", "gameID":1234}
   */
  private int gameID;
  private String playerColor;
  private String authTokean;

  /**
   * get a game id
   * @return gameID
   */
  public int getGameID() {
    return gameID;
  }

  public String getAuthTokean() {
    return authTokean;
  }

  public void setAuthTokean(String authTokean) {
    this.authTokean=authTokean;
  }

  public String getPlayerColor() {
    return playerColor;
  }

  public void setPlayerColor(String playerColor) {
    this.playerColor=playerColor;
  }

  /**
   * set a game id
   * @param gameID
   */
  public void setGameID(int gameID) {
    this.gameID=gameID;
  }
}
