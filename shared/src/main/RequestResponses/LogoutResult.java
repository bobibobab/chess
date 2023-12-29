package RequestResponses;

/**
 * LogoutResult Class
 */

public class LogoutResult {
  /**
   * Success response [200]
   * Failure response [401] {"message" : "Error: unauthorized"}
   * Failure response [500] {"message" : "Error: description"}
   */
  private String message = null;

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
