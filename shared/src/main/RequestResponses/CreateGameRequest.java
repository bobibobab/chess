package RequestResponses;

/**
 * The class of createGameRequest
 */
public class CreateGameRequest {
  /**
   * Attribute for body
   * {"gameName":""}
   */
  private String gameName;
  private String authToken;

  /**
   * get game name
   * @return gameName
   */
  public String getGameName() {
    return gameName;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }

  /**
   * set a game name
   * @param gameName
   */
  public void setGameName(String gameName) {
    this.gameName=gameName;
  }
}
