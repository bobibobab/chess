package dataAccess;

import model.AuthToken;
import java.sql.SQLException;

/**
 * AuthDAO class
 */
public class AuthDAO {
  Database db = new Database();

  public AuthDAO() {
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
              CREATE TABLE IF NOT EXISTS authToken(
                  authToken VARCHAR(255) NOT NULL,
                  username VARCHAR(255) NOT NULL,
                  PRIMARY KEY (authToken)
              )""";

      try(var createTableStatement = conn.prepareStatement(createAuthTokenTable)){
        createTableStatement.executeUpdate();
      }
    }catch(SQLException e){
      throw new DataAccessException("Data Access Error");
    }
  }

  /**
   * Insert Auth class into the database
   * @param authToken
   * @throws DataAccessException
   */

  public void insertAuth(AuthToken authToken)throws DataAccessException{
    String token = authToken.getAuthToken();
    String userName =authToken.getUsername();
    var conn = db.getConnection();
    try(var preparedStatement = conn.prepareStatement("INSERT INTO authToken (authToken, username) VALUES(?, ?)")){
      preparedStatement.setString(1, token);
      preparedStatement.setString(2, userName);
      preparedStatement.executeUpdate();
      conn.close();
    } catch (SQLException e) {
      throw new DataAccessException("Error: Data Access Exception");
    }
  }


  /**
   * Find AuthToken class from the database.
   * @param authToken
   * @return AuthToken
   * @throws DataAccessException
   */
  public AuthToken findAuth(String authToken) throws DataAccessException{
    var conn = db.getConnection();
    AuthToken auth = null;
    try(var preparedStatement = conn.prepareStatement("SELECT authToken, username FROM authToken WHERE authToken=?")){
      preparedStatement.setString(1, authToken);
      var sel = preparedStatement.executeQuery();
      if (sel.next()){
        auth = new AuthToken(sel.getString("username"),sel.getString("authToken"));
      }
      conn.close();
    } catch (SQLException e) {
      return null;
    }
    return auth;
  }

  /**
   * Clear Every AuthToken from the database.
   * @throws DataAccessException
   */
  public void clear()throws DataAccessException{
    //Truncate Table 'Name of table' to clear!
    var conn = db.getConnection();

    try(var preparedStatement = conn.prepareStatement("TRUNCATE TABLE authToken")){
      preparedStatement.executeUpdate();
      conn.close();
    }catch (SQLException e){
      throw new DataAccessException("Data Access Error");
    }

  }

  public void remove(String authToken) throws DataAccessException{

    var conn = db.getConnection();
    //how can I put authToken parameter into the where?
    try(var preparedStatement = conn.prepareStatement("DELETE FROM authToken WHERE authToken = ?")){
      preparedStatement.setString(1, authToken);
      preparedStatement.executeUpdate();
      conn.close();
    } catch (SQLException e) {
      throw new DataAccessException("Data Access Error");
    }
  }

}
