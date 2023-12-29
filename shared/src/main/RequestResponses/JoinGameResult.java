package RequestResponses;

import model.Game;

/**
 * The class of JoinGameResult
 */
public class JoinGameResult {
  /**
   * Failure response [400] {"message" : "Error: bad request"}
   * Failure response [401] {"message" : "Error: unauthorized"}
   * Failure response [403] {"message" : "Error: already taken"}
   * Failure response [500] {"message" : "Error: description"}
   */
  private String message;

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
}
