package RequestResponses;

/**
 * The class of loginRequest
 */
public class LoginRequest {
  /**
   * Attribute for body
   * {"username":"", "password":""}
   */
  private String username;
  private String password;
  public LoginRequest(){

  }

  /**
   * Set a password
   * @param password
   */
  public void setPassword(String password) {
    this.password=password;
  }

  /**
   * Set a username
   * @param username
   */
  public void setUsername(String username) {
    this.username=username;
  }

  /**
   * get a password
   * @return password
   */
  public String getPassword() {
    return password;
  }

  /**
   * get a username
   * @return username
   */
  public String getUsername() {
    return username;
  }
}
