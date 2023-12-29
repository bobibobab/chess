package RequestResponses;

/**
 * The class of createGameResult
 */
public class CreateGameResult {
  /**
   * Attribute for...
   * Success response [200] {"gameID":"1234"}
   * Failure response [400] {"message" : "Error: bad request"}
   * Failure response [403] {"message" : "Error: already taken"}
   * Failure response [500] {"message" : "Error: description"}
   */
  private String message;
  private Integer gameID;

  public CreateGameResult(){
  }

  /**
   * get an error message
   * @return message
   */
  public String getMessage() {
    return message;
  }

  /**
   * get a game id
   * @return gameID
   */
  public Integer getGameID() {
    return gameID;
  }

  /**
   * set an error message
   * @param message
   */
  public void setMessage(String message) {
    this.message=message;
  }

  /**
   * set a game ID
   * @param gameID
   */
  public void setGameID(Integer gameID) {
    this.gameID=gameID;
  }
}
