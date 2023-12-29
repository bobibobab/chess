package dataAccess;

import chess.*;
import com.google.gson.*;
import model.Game;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class of GameDAO
 */
public class GameDAO {

  Database db = new Database();
  private static HashMap<Integer, Game> list = new HashMap<>();
  private static int gameID = 1;

  public GameDAO() {
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
              CREATE TABLE IF NOT EXISTS game(
                  gameID INT NOT NULL AUTO_INCREMENT,
                  whiteUsername VARCHAR(255),
                  blackUsername VARCHAR(255),
                  gameName VARCHAR(255) NOT NULL,
                  chessGame TEXT,
                  PRIMARY KEY (gameID)
              )""";

      try(var createTableStatement = conn.prepareStatement(createAuthTokenTable)){
        createTableStatement.executeUpdate();
      }
    }catch(SQLException e){
      throw new DataAccessException("Data Access Error");
    }
  }
  /**
   * A method for inserting a new game into the database
   * @param game
   * @throws DataAccessException
   */
  public void insertGame(Game game)throws DataAccessException{
    var conn = db.getConnection();
    try(var preparedStatement = conn.prepareStatement("INSERT INTO game ( whiteUsername, blackUsername, gameName, chessGame) VALUES(?, ?, ?, ?)")){
      preparedStatement.setString(1, game.getWhiteUsername());
      preparedStatement.setString(2, game.getBlackUsername());
      preparedStatement.setString(3, game.getGameName());
      Gson gson = new Gson();
      preparedStatement.setString(4, gson.toJson(game.getGame()));
      preparedStatement.executeUpdate();
      game.setGameID(gameID);
      gameID++;
      conn.close();
    } catch (SQLException e) {
      throw new DataAccessException("Error: Data Access Exception");
    }
  }

  /**
   * A method for retrieving a specified game from the database by game ID
   * @param gameID
   * @throws DataAccessException
   * @return game
   */
  public Game find(int gameID)throws DataAccessException{
    var conn = db.getConnection();
    Game game = null;
    try(var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName, chessGame FROM game WHERE gameID=?")){
      preparedStatement.setInt(1, gameID);
      try(var sel = preparedStatement.executeQuery()){
        Gson gson = new Gson();
        if(sel.next()){
          game = new Game();
          var builder = new GsonBuilder();
          builder.registerTypeAdapter(ChessGame.class, new ChessGameAdapter());
          builder.registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter());
          builder.registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter());

          var chessGame = builder.create().fromJson(sel.getString("chessGame"), ChessGame.class);
          game.setGameName(sel.getString("gameName"));
          game.setGame(chessGame);
          game.setGameID(sel.getInt("gameID"));
          game.setBlackUsername(sel.getString("blackUsername"));
          game.setWhiteUsername(sel.getString("whiteUsername"));
          return game;
        }
      }
      conn.close();
    } catch (SQLException e) {
      throw new DataAccessException("Data Access Error");
    }
    return null;
  }

  /**
   * A method for retrieving all games from the database
   * @throws DataAccessException
   * @return HashMap stored all games
   */
  public HashMap<Integer, Game> findAll ()throws DataAccessException{
    // How can I find all of it and how can I get gameID?
    var conn = db.getConnection();
    Game game = null;
    try(var preparedStatement = conn.prepareStatement("SELECT * FROM game")){
      try(var sel = preparedStatement.executeQuery()){
        while(sel.next()){
          game = new Game(sel.getString("gameName"));
          game.setGameID(sel.getInt("gameID"));
          game.setBlackUsername(sel.getString("blackUsername"));
          game.setWhiteUsername(sel.getString("whiteUsername"));
          list.put(sel.getInt("gameID"), game);
        }
      }
      conn.close();
    } catch (SQLException e) {
      throw new DataAccessException("Data Access Error");
    }
    return list;
  }

  /**
   * A method for updating a chessGame in the database.
   * It should replace the chessGame string corresponding to a given gameID with a new ChessGame String.
   * @param gameID
   * @throws DataAccessException
   */
  public void updateGame(int gameID, Game game) throws DataAccessException{
    var conn = db.getConnection();
    try(var preparedStatement = conn.prepareStatement("UPDATE game SET whiteUsername = ?, blackUsername = ?, chessGame = ? WHERE gameID = ? ")){
      preparedStatement.setString(1, game.getWhiteUsername());
      preparedStatement.setString(2, game.getBlackUsername());

      Gson gson = new Gson();
//      builder.registerTypeAdapter(ChessGame.class, new ChessGameAdapter());
//      Gson gson = builder.create();
//      //var chessGame = builder.create().fromJson(gson.toJson(game), ChessGame.class);
//      var chessGame = gson.fromJson(gson.toJson(game.getGame()), ChessGameImpl.class);
//      game.setGame(chessGame);
//
//      game.setGame(game.getGame())
      preparedStatement.setString(3,gson.toJson(game.getGame()));
      preparedStatement.setInt(4,gameID);
      preparedStatement.executeUpdate();
      conn.close();
    }catch(SQLException e){
      throw new DataAccessException("Data Access Error");
    }
  }

  /**
   * A method for clearing all data from the database.
   * @throws DataAccessException
   */
  public void clear() throws DataAccessException{
    var conn = db.getConnection();

    try(var preparedStatement = conn.prepareStatement("TRUNCATE TABLE game")){
      preparedStatement.executeUpdate();
      gameID = 1;
      list.clear();
      conn.close();
    }catch (SQLException e){
      throw new DataAccessException("Data Access Error");
    }

  }
}
