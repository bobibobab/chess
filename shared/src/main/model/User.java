package model;

/**
 * User Class
 * One of the model classes represent user information.
 */

public class User {
  /**
   * Attribute of User class
   */
  private String username;
  private String password;
  private String email;

  public User(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  /**
   * Get User's name
   * @return username
   */
  public String getUserName(){
    return username;
  }

  /**
   * Get the user's Password
   * @return password
   */
  public String getPassword(){
    return password;
  }

  /**
   * Get the user's email
   * @return email
   */
  public String getEmail(){
    return email;
  }

  /**
   * Set user's name
   * @param username user input for name
   */
  public void setUsername(String username){
    this.username = username;
  }

  /**
   * Set user's password.
   * @param password user input for password
   */
  public void setPassword(String password){
    this.password = password;
  }

  /**
   * Set user's email.
   * @param email user input for email
   */
  public void setEmail(String email){
    this.email = email;
  }
}
