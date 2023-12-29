package dataAccess;

import model.User;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * The class of UserDAO
 */
public class UserDAO {
  private static HashMap<String , User> databaseUser = new HashMap<>();
  Database db = new Database();

  public UserDAO() {
    try{
      createTable();
    }
    catch (DataAccessException e){
    }
  }

  public void createTable()throws DataAccessException{
    try(var conn = db.getConnection()){
      var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
      createDbStatement.executeUpdate();

      conn.setCatalog("chess");

      var createAuthTokenTable ="""
              CREATE TABLE IF NOT EXISTS user(
                  email VARCHAR(255) NOT NULL,
                  password VARCHAR(255) NOT NULL,
                  username VARCHAR(255) NOT NULL,
                  PRIMARY KEY (username)
              )""";

      try(var createTableStatement = conn.prepareStatement(createAuthTokenTable)){
        createTableStatement.executeUpdate();
      }
    }catch(SQLException e){
      throw new DataAccessException("Data Access Error");
    }
  }
  /**
   * a method to insert user into the database.
   * @param user
   * @throws DataAccessException
   */
  public void insertUser(User user) throws DataAccessException{
    String username = user.getUserName();
    String password = user.getPassword();
    String email = user.getEmail();
    var conn = db.getConnection();
    try(var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES(?, ?, ?)")){
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      preparedStatement.setString(3, email);
      preparedStatement.executeUpdate();
      conn.close();
    } catch (SQLException e) {
      throw new DataAccessException("Error: Data Access Exception");
    }

    databaseUser.put(user.getUserName(), user);
  }

  /**
   * a method to find a user
   * @param username
   * @throws DataAccessException
   * @return User object
   */
  public User findUser(String username) throws DataAccessException{
    var conn = db.getConnection();
    User user = null;
    try(var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM user WHERE username=?")){
      preparedStatement.setString(1, username);
      try(var sel = preparedStatement.executeQuery()){
        if(sel.next()){
          user = new User(sel.getString("username"),sel.getString("password"), sel.getString("email"));
        }
      }
      conn.close();
    } catch (SQLException e) {
      return null;
    }
    return user;
  }

  /**
   * A method to delete all users.
   * @throws DataAccessException
   */
  public void clear() throws DataAccessException{
    var conn = db.getConnection();

    try(var preparedStatement = conn.prepareStatement("TRUNCATE TABLE user")){
      preparedStatement.executeUpdate();
      conn.close();
    }catch (SQLException e){
      throw new DataAccessException("Data Access Error");
    }
  }

}
