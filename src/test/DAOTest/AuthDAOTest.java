package DAOTest;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import model.AuthToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class AuthDAOTest {
  @Test
  @Order(1)
  @DisplayName("AuthDAO Insert positive test")
  public void AuthDAOInsertTest() throws DataAccessException {
    AuthDAO authDAO = new AuthDAO();
    authDAO.clear();
    authDAO.insertAuth(new AuthToken("ji", "dddddddd"));

    AuthToken auth = authDAO.findAuth("dddddddd");
    Assertions.assertNotNull(auth);
  }// end of loginServiceTest

  @Test
  @Order(2)
  @DisplayName("AuthDAO Insert negativve test")
  public void AuthDAOInsertTest2() throws DataAccessException {
    AuthDAO authDAO = new AuthDAO();
    authDAO.clear();
    authDAO.insertAuth(new AuthToken("jisu", "dddddddd"));

    AuthToken auth = authDAO.findAuth("dddddddd");
    Assertions.assertNotEquals(auth.getUsername(), "ji");
  }// end of loginServiceTest
  @Test
  @Order(3)
  @DisplayName("AuthDAO Find Positive test")
  public void AuthDAOFindTest() throws DataAccessException {
    AuthDAO authDAO = new AuthDAO();
    authDAO.clear();
    authDAO.insertAuth(new AuthToken("jisu", "dddddddd"));

    AuthToken auth = authDAO.findAuth("dddddddd");
    Assertions.assertEquals(auth.getUsername(), "jisu");
  }// end of loginServiceTest
  @Test
  @Order(4)
  @DisplayName("AuthDAO Find negativve test")
  public void AuthDAOFindTest2() throws DataAccessException {
    AuthDAO authDAO = new AuthDAO();
    authDAO.clear();
    authDAO.insertAuth(new AuthToken("jisu", "dddddddd"));

    AuthToken auth = authDAO.findAuth("dddddddd");
    Assertions.assertNotEquals(auth.getUsername(), "ji");
  }// end of loginServiceTest

  @Test
  @Order(5)
  @DisplayName("AuthDAO Remove Positive test")
  public void AuthDAORemoveTest() throws DataAccessException {
    AuthDAO authDAO = new AuthDAO();
    authDAO.clear();
    authDAO.insertAuth(new AuthToken("jisu", "dddddddd"));

    authDAO.remove("dddddddd");
    AuthToken auth = authDAO.findAuth("dddddddd");
    Assertions.assertNull(auth,"The auth is not removed.");
  }// end of loginServiceTest

  @Test
  @Order(6)
  @DisplayName("AuthDAO Remove Negative test")
  public void AuthDAORemoveTest2() throws DataAccessException {
    AuthDAO authDAO = new AuthDAO();
    authDAO.clear();
    authDAO.insertAuth(new AuthToken("jisu", "dddddddd"));

    authDAO.remove("ddddd");
    AuthToken auth = authDAO.findAuth("dddddddd");
    Assertions.assertNotNull(auth,"The auth is removed.");
  }// end of loginServiceTest

  @Test
  @Order(7)
  @DisplayName("AuthDAO Remove Negative test")
  public void AuthDAOClearTest() throws DataAccessException {
    AuthDAO authDAO = new AuthDAO();
    authDAO.clear();
    authDAO.insertAuth(new AuthToken("jisu", "dddddddd"));

    authDAO.clear();
    AuthToken auth = authDAO.findAuth("dddddddd");
    authDAO.clear();
    Assertions.assertNull(auth,"The auth is not cleared.");
  }// end of loginServiceTest
}
