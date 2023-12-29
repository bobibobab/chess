package passoffTests.serverTests;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import passoffTests.TestFactory;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
public class ServiceTest {
  private static final int HTTP_OK = 200;
  private static final int HTTP_BAD_REQUEST = 400;
  private static final int HTTP_UNAUTHORIZED = 401;
  private static final int HTTP_FORBIDDEN = 403;

  private static TestModels.TestUser existingUser;
  private static TestModels.TestUser newUser;
  private static TestModels.TestCreateRequest createRequest;
  private static TestServerFacade serverFacade;
  private String existingAuth;

  @BeforeAll
  public static void init() {
    existingUser = new TestModels.TestUser();
    existingUser.username = "fleh1221";
    existingUser.password = "songjisu";
    existingUser.email = "urim@thummim.net";

    newUser = new TestModels.TestUser();
    newUser.username = "testUsername";
    newUser.password = "testPassword";
    newUser.email = "testEmail";

    createRequest = new TestModels.TestCreateRequest();
    createRequest.gameName = "testGame";

    serverFacade = new TestServerFacade("localhost", TestFactory.getServerPort());
  }

  @BeforeEach
  public void setup() {
    serverFacade.clear();

    TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
    registerRequest.username = existingUser.username;
    registerRequest.password = existingUser.password;
    registerRequest.email = existingUser.email;

    //one user already logged in
    TestModels.TestLoginRegisterResult regResult = serverFacade.register(registerRequest);
    existingAuth = regResult.authToken;
  }

  @Test
  @Order(1)
  @DisplayName("LoginService positive test")
  public void LoginServicePositiveTest() {
    TestModels.TestLoginRequest loginRequest = new TestModels.TestLoginRequest();
    loginRequest.username = existingUser.username;
    loginRequest.password = existingUser.password;

    TestModels.TestLoginRegisterResult loginResult = serverFacade.login(loginRequest);
    Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
    Assertions.assertEquals(existingUser.username, loginResult.username ,"Username is not matched");
  }// end of loginServiceTest
  @Test
  @Order(2)
  @DisplayName("LoginService negative test")
  public void LoginServiceNegativeTest() {
    TestModels.TestLoginRequest loginRequest = new TestModels.TestLoginRequest();
    loginRequest.username = existingUser.username;
    loginRequest.password = existingUser.password;

    TestModels.TestLoginRegisterResult loginResult = serverFacade.login(loginRequest);
    Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
    Assertions.assertNotEquals("JisuSong", loginResult.username, "Wrong username is matched" );
  }// end of loginServiceTest
  @Test
  @Order(3)
  @DisplayName("LogoutService positive test")
  public void LogoutPositiveTest() {
    TestModels.TestResult result = serverFacade.logout(existingAuth);

    Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
    Assertions.assertTrue(result.success, "Response didn't return successful");
    Assertions.assertFalse(result.message != null &&
                    result.message.toLowerCase(Locale.ROOT).contains("error"),
            "Response gave an error message");
  }// end of logoutPositiveTest
  @Test
  @Order(4)
  @DisplayName("LogoutService Negative test")
  public void LogoutNegativeTest() {
    serverFacade.logout(existingAuth);
    TestModels.TestResult result = serverFacade.logout(existingAuth);

    Assertions.assertEquals(HTTP_UNAUTHORIZED, serverFacade.getStatusCode(),
            "Server response code was not 401 Unauthorized");
    Assertions.assertFalse(result.success, "Response didn't return not successful");
    Assertions.assertTrue(result.message.toLowerCase(Locale.ROOT).contains("error"),
            "Response did not return error message");
  }// end of logoutNegativeTest

  @Test
  @Order(5)
  @DisplayName("Register Positive test")
  public void RegisterPositiveTest(){
    String myUserName = "suckerPower123";
    String myPassword = "Ihateeveryone";
    String myEmail = "suckerWorld123@gmail.com";

    TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
    registerRequest.username = myUserName;
    registerRequest.password = myPassword;
    registerRequest.email = myEmail;

    TestModels.TestLoginRegisterResult regResult = serverFacade.register(registerRequest);
    existingAuth = regResult.authToken;
    Assertions.assertEquals(regResult.username, myUserName, "Your username is not matched");
  }

  @Test
  @Order(6)
  @DisplayName("Register Negative test")
  public void RegisterNegativeTest(){
    String myUserName = "suckerPower";
    String myPassword = "Ihateeveryone";
    String myEmail = "suckerWorld123@gmail.com";

    TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
    registerRequest.username = myUserName;
    registerRequest.password = myPassword;
    registerRequest.email = myEmail;

    TestModels.TestLoginRegisterResult regResult = serverFacade.register(registerRequest);
    existingAuth = regResult.authToken;
    Assertions.assertNotEquals(regResult.username, "nah", "Wrong registered username is matched");
  }

  @Test
  @Order(7)
  @DisplayName("Create Positive test")
  public void CreateGamePositiveTest(){
    createRequest = new TestModels.TestCreateRequest();
    createRequest.gameName = "I will beat you.";
    TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, existingAuth);

    Assertions.assertNotNull(createResult.gameID, "Result did not return a game ID");
    Assertions.assertTrue(createResult.gameID > 0, "Result returned invalid game ID");
  }
  @Test
  @Order(8)
  @DisplayName("Create Negative test")
  public void CreateGameNegativeTest(){
    createRequest = new TestModels.TestCreateRequest();
    createRequest.gameName = "I will beat you.";
    TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, "existingAuth");

    Assertions.assertNull(createResult.gameID, "Bad Result returned");
  }

  @Test
  @Order(9)
  @DisplayName("Join Positive test")
  public void JoinPositiveTest(){
    TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, existingAuth);

    TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
    joinRequest.gameID = createResult.gameID;
    joinRequest.playerColor = ChessGame.TeamColor.BLACK;

    TestModels.TestResult joinResult = serverFacade.verifyJoinPlayer(joinRequest, existingAuth);
    TestModels.TestListResult listResult = serverFacade.listGames(existingAuth);

    Assertions.assertEquals(1, listResult.games.length);
    Assertions.assertEquals(existingUser.username, listResult.games[0].blackUsername, "Added the Wrong team");
    Assertions.assertNull(listResult.games[0].whiteUsername, "Added the Wrong team");
  }
  @Test
  @Order(10)
  @DisplayName("Join Negative test")
  public void JoinNegativeTest(){
    TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, existingAuth);
    // add black
    TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
    joinRequest.gameID = createResult.gameID;
    joinRequest.playerColor = ChessGame.TeamColor.BLACK;

    TestModels.TestResult joinResult = serverFacade.verifyJoinPlayer(joinRequest, existingAuth);
    TestModels.TestListResult listResult = serverFacade.listGames(existingAuth);

    Assertions.assertNotEquals(2, listResult.games.length,"Wrong number of games");
    Assertions.assertNotEquals(existingUser.username, listResult.games[0].whiteUsername, "white is supposed to be null");
    Assertions.assertNotNull(listResult.games[0].blackUsername, "Black was not added");
  }

  @Test
  @Order(11)
  @DisplayName("List Positive test")
  public void ListPositiveTest(){
    //register a few users to create games
    TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
    registerRequest.username = "a";
    registerRequest.password = "A";
    registerRequest.email = "a.A";
    TestModels.TestLoginRegisterResult userA = serverFacade.register(registerRequest);

    registerRequest.username = "b";
    registerRequest.password = "B";
    registerRequest.email = "b.B";
    TestModels.TestLoginRegisterResult userB = serverFacade.register(registerRequest);

    //Create Game
    createRequest.gameName = "Game1";
    TestModels.TestCreateResult game1 = serverFacade.createGame(createRequest, userA.authToken);

    createRequest.gameName = "Game2";
    TestModels.TestCreateResult game2 = serverFacade.createGame(createRequest, userB.authToken);

    //create expected entry items
    Collection<TestModels.TestListResult.TestListEntry> expectedList = new HashSet<>();

    //game 1
    TestModels.TestListResult.TestListEntry entry = new TestModels.TestListResult.TestListEntry();
    entry.gameID = game1.gameID;
    entry.gameName = "Game1";
    entry.blackUsername = null;
    entry.whiteUsername = null;
    expectedList.add(entry);

    //game 2
    entry = new TestModels.TestListResult.TestListEntry();
    entry.gameID = game2.gameID;
    entry.gameName = "Game2";
    entry.blackUsername = null;
    entry.whiteUsername = null;
    expectedList.add(entry);

    TestModels.TestListResult listResult = serverFacade.listGames(existingAuth);
    Collection<TestModels.TestListResult.TestListEntry> returnedList =
            new HashSet<>(Arrays.asList(listResult.games));
    Assertions.assertEquals(expectedList, returnedList, "Returned Games list was incorrect");
  }

  @Test
  @Order(12)
  @DisplayName("List Negative test")
  public void ListNegativeTest(){
    //register a few users to create games
    TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
    registerRequest.username = "jisu";
    registerRequest.password = "I am stupid";
    registerRequest.email = "studpid";
    TestModels.TestLoginRegisterResult userA = serverFacade.register(registerRequest);

    registerRequest.username = "whatup";
    registerRequest.password = "I am very stupid";
    registerRequest.email = "studpid123";
    TestModels.TestLoginRegisterResult userB = serverFacade.register(registerRequest);

    //Create Game
    createRequest.gameName = "Game1";
    TestModels.TestCreateResult game1 = serverFacade.createGame(createRequest, userA.authToken);

    createRequest.gameName = "Game2";
    TestModels.TestCreateResult game2 = serverFacade.createGame(createRequest, userB.authToken);

    createRequest.gameName = "Game3";
    TestModels.TestCreateResult game3 = serverFacade.createGame(createRequest, userB.authToken);

    //create expected entry items
    Collection<TestModels.TestListResult.TestListEntry> expectedList = new HashSet<>();

    //game 1
    TestModels.TestListResult.TestListEntry entry = new TestModels.TestListResult.TestListEntry();
    entry.gameID = game1.gameID;
    entry.gameName = "Game1";
    entry.blackUsername = null;
    entry.whiteUsername = null;
    expectedList.add(entry);

    //game 2
    entry = new TestModels.TestListResult.TestListEntry();
    entry.gameID = game2.gameID;
    entry.gameName = "Game2";
    entry.blackUsername = null;
    entry.whiteUsername = null;
    expectedList.add(entry);

    entry = new TestModels.TestListResult.TestListEntry();
    entry.gameID = game1.gameID;
    entry.gameName = "StupidGame";
    entry.blackUsername = null;
    entry.whiteUsername = null;
    expectedList.add(entry);

    TestModels.TestListResult listResult = serverFacade.listGames(existingAuth);
    Collection<TestModels.TestListResult.TestListEntry> returnedList =
            new HashSet<>(Arrays.asList(listResult.games));
    Assertions.assertNotEquals(expectedList, returnedList, "Wrong game is inserted in the list");
  }

  @Test
  @Order(13)
  @DisplayName("Clear Test")
  public void clearTest() {
    TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
    registerRequest.username = "jisu";
    registerRequest.password = "I am stupid";
    registerRequest.email = "studpid";
    TestModels.TestLoginRegisterResult userA = serverFacade.register(registerRequest);

    createRequest.gameName = "Game1";
    TestModels.TestCreateResult game1 = serverFacade.createGame(createRequest, userA.authToken);


    TestModels.TestResult clearResult = serverFacade.clear();
    //No login
    TestModels.TestLoginRequest loginRequest = new TestModels.TestLoginRequest();
    loginRequest.username = registerRequest.username;
    loginRequest.password = registerRequest.password;
    TestModels.TestLoginRegisterResult loginResult = serverFacade.login(loginRequest);
    Assertions.assertNull(loginResult.username);

    //No list
    userA = serverFacade.register(registerRequest);
    TestModels.TestListResult listResult = serverFacade.listGames(userA.authToken);
    Assertions.assertEquals(0, listResult.games.length,"Clear is not wroking");
  }

}
