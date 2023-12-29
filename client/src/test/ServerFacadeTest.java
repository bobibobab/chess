
import RequestResponses.*;
import model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import server.ServerFacade;
import dataAccess.GameDAO;
import java.io.IOException;
import dataAccess.*;

public class ServerFacadeTest {
  GameDAO gameDAO = new GameDAO();
  String serverUrl = "http://localhost:8080";
  ServerFacade server = new ServerFacade(serverUrl);


  @Test
  @Order(1)
  @DisplayName("Register positive test")
  public void positiveRegister() throws IOException, DataAccessException {
    ClearResult result = server.doClear();
    String username = "jisu";
    String passwrod = "flflflfl";
    String email = "fleh1221@gmail.com";

    RegisterResult registerResult =server.doRegister(username, passwrod, email);

    Assertions.assertEquals(registerResult.getUsername(), username, "Your username and password is not registed.");
  }

  @Test
  @Order(2)
  @DisplayName("Register Negative test")
  public void negativeRegister() throws IOException, DataAccessException {
    ClearResult result = server.doClear();

    String username = "jisu";
    String passwrod = "flflflfl";
    String email = "fleh1221@gmail.com";

    String username1 = "jisu";
    String passwrod2 = "flflflfl";
    String email3 = "fleh1221@gmail.com";

    RegisterResult registerResult =server.doRegister(username, passwrod, email);
    RegisterResult duplicateResult =server.doRegister(username1, passwrod2, email3);

    Assertions.assertEquals(registerResult.getUsername(), username, "Your username and password is not registed.");
    Assertions.assertNull(duplicateResult.getUsername(), "Duplicated username is registered");
  }
  @Test
  @Order(3)
  @DisplayName("Login Positive test")
  public void positiveLogin() throws DataAccessException, IOException {
    ClearResult result = server.doClear();

    String username = "jisu";
    String passwrod = "flflflfl";
    String email = "fleh1221@gmail.com";

    RegisterResult registerResult =server.doRegister(username, passwrod, email);
    LoginResult loginResult = server.doLogin(username, passwrod);

    Assertions.assertEquals(loginResult.getUsername(), username);
    Assertions.assertNotNull(loginResult.getAuthToken());
  }
  @Test
  @Order(4)
  @DisplayName("Login Negative test")
  public void negativeLogin() throws DataAccessException, IOException {
    ClearResult result = server.doClear();

    String username = "jisu123";
    String passwrod = "flflflfl";
    String email = "fleh1221@gmail.com";

    RegisterResult registerResult =server.doRegister(username, passwrod, email);
    LoginResult loginResult = server.doLogin("username", passwrod);

    Assertions.assertNotEquals(loginResult.getUsername(), username);
    Assertions.assertNull(loginResult.getAuthToken());
  }

  @Test
  @Order(5)
  @DisplayName("Logout Positive test")
  public void positiveLogout() throws DataAccessException, IOException{
    ClearResult result = server.doClear();
    String username = "jisu";
    String passwrod = "flflflfl";
    String email = "fleh1221@gmail.com";

    RegisterResult registerResult =server.doRegister(username, passwrod, email);
    LoginResult loginResult = server.doLogin(username, passwrod);
    LogoutResult logoutResult = server.doLogout(loginResult.getAuthToken());

    Assertions.assertNull(logoutResult.getMessage());
  }

  @Test
  @Order(6)
  @DisplayName("Logout Negative test")
  public void negativeLogout() throws DataAccessException, IOException{
    ClearResult result = server.doClear();

    String username = "jisu";
    String passwrod = "flflflfl";
    String email = "fleh1221@gmail.com";

    RegisterResult registerResult =server.doRegister(username, passwrod, email);
    LoginResult loginResult = server.doLogin(username, passwrod);
    LogoutResult logoutResult = server.doLogout("loginResult.getAuthToken()");

    Assertions.assertNotNull(logoutResult.getMessage());
  }

  @Test
  @Order(7)
  @DisplayName("Create Game Positive test")
  public void positveCreateGame() throws DataAccessException, IOException{
    ClearResult result = server.doClear();

    String username = "jisu";
    String passwrod = "flflflfl";
    String email = "fleh1221@gmail.com";

    RegisterResult registerResult =server.doRegister(username, passwrod, email);
    LoginResult loginResult = server.doLogin(username, passwrod);
    CreateGameResult gameResult = server.doCreateGame("GameName", loginResult.getAuthToken());

    Assertions.assertEquals( 1, gameResult.getGameID());
  }

  @Test
  @Order(8)
  @DisplayName("Create Game Negative test")
  public void negativeCreateGame() throws DataAccessException, IOException{
    ClearResult result = server.doClear();

    String username = "jisu";
    String passwrod = "flflflfl";
    String email = "fleh1221@gmail.com";

    RegisterResult registerResult =server.doRegister(username, passwrod, email);
    LoginResult loginResult = server.doLogin(username, passwrod);
    CreateGameResult gameResult = server.doCreateGame("GameName", "loginResult.getAuthToken()");

    Assertions.assertNotNull(gameResult.getMessage());

  }

  @Test
  @Order(9)
  @DisplayName("List Games Positive test")
  public void positiveListGames() throws DataAccessException, IOException{
    ClearResult result = server.doClear();

    String username = "jisu";
    String passwrod = "flflflfl";
    String email = "fleh1221@gmail.com";

    RegisterResult registerResult =server.doRegister(username, passwrod, email);
    LoginResult loginResult = server.doLogin(username, passwrod);
    CreateGameResult gameResult = server.doCreateGame("GameName", loginResult.getAuthToken());
    CreateGameResult gameResult1 = server.doCreateGame("GameName2", loginResult.getAuthToken());

    ListGamesResult gamesResult = server.doListGames(loginResult.getAuthToken());
    Assertions.assertEquals(gamesResult.getListGames().size(), 2);
  }
  @Test
  @Order(10)
  @DisplayName("List Game Negative test")
  public void negativeListGames() throws DataAccessException, IOException{
    ClearResult result = server.doClear();

    String username = "jisu";
    String passwrod = "flflflfl";
    String email = "fleh1221@gmail.com";

    RegisterResult registerResult =server.doRegister(username, passwrod, email);
    LoginResult loginResult = server.doLogin(username, passwrod);
    CreateGameResult gameResult = server.doCreateGame("GameName", loginResult.getAuthToken());
    CreateGameResult gameResult1 = server.doCreateGame("GameName2", loginResult.getAuthToken());

    ListGamesResult gamesResult = server.doListGames(loginResult.getAuthToken());
    Assertions.assertNotEquals(gamesResult.getListGames().size(), 3);
    ListGamesResult gamesResult1 = server.doListGames("sdaf");
    Assertions.assertNotNull(gamesResult1.getMessage());
  }

  @Test
  @Order(11)
  @DisplayName("Join Game Positive test")
  public void positiveJoinGame() throws DataAccessException, IOException{
    ClearResult result = server.doClear();

    String username = "jisu";
    String passwrod = "flflflfl";
    String email = "fleh1221@gmail.com";

    RegisterResult registerResult =server.doRegister(username, passwrod, email);
    LoginResult loginResult = server.doLogin(username, passwrod);
    CreateGameResult gameResult = server.doCreateGame("GameName", loginResult.getAuthToken());

    JoinGameResult joinGameResult = server.doJoinGame(gameResult.getGameID(), "white", loginResult.getAuthToken());

    Assertions.assertNull(joinGameResult.getMessage());
    Game game = gameDAO.find(gameResult.getGameID());
    Assertions.assertEquals(game.getWhiteUsername(), username);

  }
  @Test
  @Order(12)
  @DisplayName("Join Game Negative test")
  public void negativeJoinGame() throws DataAccessException, IOException{
    ClearResult result = server.doClear();

    String username = "jisu";
    String passwrod = "flflflfl";
    String email = "fleh1221@gmail.com";

    RegisterResult registerResult =server.doRegister(username, passwrod, email);
    LoginResult loginResult = server.doLogin(username, passwrod);
    CreateGameResult gameResult = server.doCreateGame("GameName", loginResult.getAuthToken());

    JoinGameResult joinGameResult = server.doJoinGame(123, "white", loginResult.getAuthToken());

    Assertions.assertNotNull(joinGameResult.getMessage());
  }
  @Test
  @Order(13)
  @DisplayName("Clear test")
  public void ClearDatas() throws DataAccessException, IOException{
    ClearResult result = server.doClear();

    String username = "jisu";
    String passwrod = "flflflfl";
    String email = "fleh1221@gmail.com";

    RegisterResult registerResult =server.doRegister(username, passwrod, email);
    LoginResult loginResult = server.doLogin(username, passwrod);
    CreateGameResult gameResult = server.doCreateGame("GameName", loginResult.getAuthToken());
    ClearResult clearResult= server.doClear();

    ListGamesResult listGamesResult =server.doListGames(loginResult.getAuthToken());

    Assertions.assertNotNull(listGamesResult.getMessage());
    Assertions.assertEquals(listGamesResult.getListGames().size(), 0);
    Assertions.assertNull(clearResult.getMessage());

  }
}
