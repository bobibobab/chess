package DAOTest;

import dataAccess.UserDAO;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class UserDAOTest {
  @Test
  @Order(1)
  @DisplayName("UserDAO Insert positive test")
  public void UserDAOInsertTest() throws DataAccessException {
    UserDAO userDAO= new UserDAO();
    userDAO.clear();
    userDAO.insertUser(new User("ji", "d", "SD"));

    User user= userDAO.findUser("ji");
    Assertions.assertNotNull(user);
  }// end of loginServiceTest

  @Test
  @Order(2)
  @DisplayName("UserDAO Insert negativve test")
  public void AuthDAOInsertTest2() throws DataAccessException {
    UserDAO userDAO= new UserDAO();
    userDAO.clear();
    userDAO.insertUser(new User("ji", "d", "SD"));

    User user = userDAO.findUser("ji");
    Assertions.assertNotEquals(user.getUserName(), "jissss");
  }// end of loginServiceTest
  @Test
  @Order(3)
  @DisplayName("UserDAO Find Positive test")
  public void AuthDAOFindTest() throws DataAccessException {
    UserDAO userDAO= new UserDAO();
    userDAO.clear();
    userDAO.insertUser(new User("ji", "d", "SD"));
    userDAO.insertUser(new User("jiiii", "d", "SD"));
    userDAO.insertUser(new User("jissssss", "d", "SD"));

    User user = userDAO.findUser("ji");
    Assertions.assertEquals(user.getUserName(), "ji");
  }// end of loginServiceTest
  @Test
  @Order(4)
  @DisplayName("UserDAO Find negativve test")
  public void AuthDAOFindTest2() throws DataAccessException {
    UserDAO userDAO= new UserDAO();
    userDAO.clear();
    userDAO.insertUser(new User("ji", "d", "SD"));
    userDAO.insertUser(new User("jiiii", "d", "SD"));
    userDAO.insertUser(new User("jissssss", "d", "SD"));

    User user = userDAO.findUser("ji");
    Assertions.assertNotEquals(user.getUserName(), "jilllll");
  }// end of loginServiceTest
  @Test
  @Order(5)
  @DisplayName("UserDAO Clear test")
  public void AuthDAOClearTest() throws DataAccessException {
    UserDAO userDAO= new UserDAO();
    userDAO.clear();
    userDAO.insertUser(new User("ji", "d", "SD"));
    userDAO.insertUser(new User("jiiii", "d", "SD"));
    userDAO.insertUser(new User("jissssss", "d", "SD"));

    userDAO.clear();
    User user = userDAO.findUser("ji");
    Assertions.assertNull(user,"The auth is not cleared.");
  }// end of loginServiceTest
}
