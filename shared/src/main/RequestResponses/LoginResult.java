package RequestResponses;

/**
 * LoginResult Class
 */

public class LoginResult {
  public boolean success = false;
  /**
   * Success response [200] {"username" : "", "authToken: ""}
   * Failure response [401] {"message" : "Error: unauthorized"}
   * Failure response [500] {"message" : "Error: description"}
   */
  private String message;
  private String authToken;
  private String username;

  public void LoginResult(){

  }

  /**
   * get a message
   * @return message
   */
  public String getMessage() {
    return message;
  }

  /**
   * get a username
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * get a authToken
   * @return authToken
   */
  public String getAuthToken() {
    return authToken;
  }

  /**
   * set a authToken
   * @param authToken
   */
  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }

  /**
   * set a username
   * @param username
   */
  public void setUsername(String username) {
    this.username=username;
  }

  /**
   * set a message
   * @param message
   */
  public void setMessage(String message) {
    this.message=message;
  }
}
