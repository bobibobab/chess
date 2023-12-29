package RequestResponses;

public class ListGamesRequest {

  private String authToken;

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }

  public String getAuthToken() {
    return authToken;
  }
}
