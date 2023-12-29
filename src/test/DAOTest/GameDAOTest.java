package DAOTest;

import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.AuthToken;
import model.Game;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class GameDAOTest {
  @Test
  @Order(1)
  @DisplayName("GameDAO Insert positive test")
  public void GameDAOInsertPositive() throws DataAccessException {
    GameDAO gameDAO = new GameDAO();
    gameDAO.clear();

    gameDAO.insertGame(new Game("HitMe"));
    Game game = gameDAO.find(1);
    Assertions.assertNotNull(game);
  }// end of loginServiceTest
  @Test
  @Order(2)
  @DisplayName("GameDAO Insert Negative test")
  public void GameDAOInsertNegative() throws DataAccessException {
    GameDAO gameDAO = new GameDAO();
    gameDAO.clear();

    gameDAO.insertGame(new Game("HitMe"));
    Game game = gameDAO.find(13);
    Assertions.assertNull(game);
  }// end of loginServiceTest
  @Test
  @Order(3)
  @DisplayName("GameDAO Find Positive test")
  public void GameDAOFindTest() throws DataAccessException {
    GameDAO gameDAO = new GameDAO();
    gameDAO.clear();

    gameDAO.insertGame(new Game("HitMe"));
    Game game = gameDAO.find(1);
    Assertions.assertEquals(game.getGameName(), "HitMe");
  }// end of loginServiceTest
  @Test
  @Order(4)
  @DisplayName("GameDAO Find Negative test")
  public void GameDAOFindTest2() throws DataAccessException {
    GameDAO gameDAO = new GameDAO();
    gameDAO.clear();

    gameDAO.insertGame(new Game("HitMe"));
    Game game = gameDAO.find(13);
    Assertions.assertNull(game);
  }// end of loginServiceTest

  @Test
  @Order(5)
  @DisplayName("GameDAO Find All Positive test")
  public void GameDAOFindAllTest1() throws DataAccessException {
    GameDAO gameDAO = new GameDAO();
    gameDAO.clear();
    Game g1 = new Game("HitMe");
    Game g2 = new Game("or");
    Game g3 = new Game("a");

    gameDAO.insertGame(g1);
    gameDAO.insertGame(g2);
    gameDAO.insertGame(g3);
    ArrayList<Game> expectedGames = new ArrayList<>();
    expectedGames.add(g1);
    expectedGames.add(g2);
    expectedGames.add(g3);
    HashMap<Integer, Game> resultGames = gameDAO.findAll();

    Assertions.assertEquals(resultGames.size(), expectedGames.size());
  }// end of GameDAOFindAllTest1

  @Test
  @Order(6)
  @DisplayName("GameDAO Find All Negative test")
  public void GameDAOFindAllTest2() throws DataAccessException {
    GameDAO gameDAO = new GameDAO();
    gameDAO.clear();
    Game g1 = new Game("HitMe");
    Game g2 = new Game("or");
    Game g3 = new Game("a");

    gameDAO.insertGame(g1);
    gameDAO.insertGame(g2);
    gameDAO.insertGame(g3);
    ArrayList<Game> expectedGames = new ArrayList<>();
    expectedGames.add(g1);
    expectedGames.add(g2);
    HashMap<Integer, Game> resultGames = gameDAO.findAll();

    Assertions.assertNotEquals(resultGames.size(), expectedGames.size());
  }// end of GameDAOFindAllTest2

  @Test
  @Order(7)
  @DisplayName("GameDAO Find Negative test")
  public void GameDAOUpdateTest() throws DataAccessException {
    GameDAO gameDAO = new GameDAO();
    gameDAO.clear();
    Game g1 = new Game("HitMe");
    Game g2 = new Game("or");
    Game g3 = new Game("a");

    gameDAO.insertGame(g1);
    gameDAO.insertGame(g2);
    gameDAO.insertGame(g3);
    g2.setWhiteUsername("Jisu Song");
    gameDAO.updateGame(2, g2);
    Game check = gameDAO.find(2);
    Assertions.assertEquals(check.getWhiteUsername(), g2.getWhiteUsername());
  }// end of loginServiceTest

  @Test
  @Order(8)
  @DisplayName("GameDAO Find Negative test")
  public void GameDAOUpdateTest2() throws DataAccessException {
    GameDAO gameDAO = new GameDAO();
    gameDAO.clear();
    Game g1 = new Game("HitMe");
    Game g2 = new Game("or");
    Game g3 = new Game("a");

    gameDAO.insertGame(g1);
    gameDAO.insertGame(g2);
    gameDAO.insertGame(g3);
    g2.setWhiteUsername("Jisu Song");
    gameDAO.updateGame(3, g3);
    Game check = gameDAO.find(2);
    Assertions.assertNotEquals(check.getWhiteUsername(), g2.getWhiteUsername());
  }// end of loginServiceTest

  @Test
  @Order(9)
  @DisplayName("GameDAO Clear test")
  public void GameDAOClearTest() throws DataAccessException {
    GameDAO gameDAO = new GameDAO();
    gameDAO.clear();
    Game g1 = new Game("HitMe");
    Game g2 = new Game("or");
    Game g3 = new Game("a");

    gameDAO.insertGame(g1);
    gameDAO.insertGame(g2);
    gameDAO.insertGame(g3);

    gameDAO.clear();
    HashMap<Integer, Game> games = gameDAO.findAll();
    Assertions.assertEquals(games.size(), 0);
  }// end of loginServiceTest
}
