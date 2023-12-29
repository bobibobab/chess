package RequestResponses;

/**
 * The class of ClearResult
 */
public class ClearResult {
  /**
   * Failure Response [500] {"message": "Error: description"}
   */
  private String message;

  /**
   * Set a Message for an error.
   * @param message
   */

  public void setMessage(String message) {
    this.message=message;
  }

  /**
   * Get a message for an error.
   * @return
   */
  public String getMessage() {
    return message;
  }
}
