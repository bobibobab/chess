package RequestResponses;

/**
 * The class of RegisterResult
 */
public class RegisterResult {
  /**
   * Attribute for ...
   * Success Response [200] {"username":"", "authToken":""}
   * Failure response [400] {"message" : "Error: bad request"}
   * Failure response [403] {"message" : "Error: already taken"}
   * Failure response [500] {"message" : "Error: description"}
   */
  private String message;
  private String username;
  private String authToken;
  public boolean success = false;

  /**
   * get an error message
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
   * get the user's authtoken
   * @return authToken
   */
  public String getAuthToken() {
    return authToken;
  }

  /**
   * set the username
   * @param username
   */
  public void setUsername(String username) {
    this.username=username;
  }
  /**
   * set authToekn
   * @param authToken
   */
  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }

  /**
   * set a message
   * @param message
   */
  public void setMessage(String message) {
    this.message=message;
  }
}
