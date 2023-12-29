package RequestResponses;

/**
 * The class of RegisterRequest
 */
public class RegisterRequest {
  /**
   * Body {"username" : "", "password":"", "email":""}
   */
  private String username;
  private String password;
  private String email;

  /**
   * get username
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * get the user's password
   * @return password
   */
  public String getPassword() {
    return password;
  }

  /**
   * get user's email
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Set a username
   * @param username
   */
  public void setUsername(String username) {
    this.username=username;
  }

  /**
   * set the user's password
   * @param password
   */
  public void setPassword(String password) {
    this.password=password;
  }

  /**
   * set the user's email.
   * @param email
   */
  public void setEmail(String email) {
    this.email=email;
  }
}
