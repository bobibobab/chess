package model;

/**
 * AuthToen Class
 * One of the model clesses represetn authToken information.
 */
public class AuthToken {

  private String authToken;
  private String username;

  public AuthToken(String username, String authToke) {
    this.username = username;
    this.authToken = authToke;
  }

  /**
   * Constructor of this class
   * @param authToken
   * @param username
   */
  public void authToken(String authToken, String username){
    this.authToken = authToken;
    this.username = username;
  }

  /**
   * Get authToekn
   * @return authToken
   */
  public String getAuthToken() {
    return authToken;
  }

  /**
   * Get user's name
   * @return username
   */
  public String getUsername(){
    return username;
  }
}
