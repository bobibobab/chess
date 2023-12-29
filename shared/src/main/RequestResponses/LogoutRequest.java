package RequestResponses;

/**
 * The class of logoutRequest
 */
public class LogoutRequest {
  private String authToken;

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }

  public String getAuthToken() {
    return authToken;
  }
}
