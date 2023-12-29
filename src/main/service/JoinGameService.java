package service;

import RequestResponses.JoinGameRequest;
import RequestResponses.JoinGameResult;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.AuthToken;
import model.Game;

/**
 * JoinGameService Class
 */

public class JoinGameService {

  AuthDAO authDAO = new AuthDAO();
  GameDAO gameDAO = new GameDAO();
  /**
   * Verifies that the specified game exists,
   * and, if a color is specified, add the caller as the requested color to the game
   * If no color is specified the user is joined as an observer.
   * @param gameRequest
   * @return return the result of JoinGameResult
   */
  public JoinGameResult joinGame(JoinGameRequest gameRequest){
    JoinGameResult result = new JoinGameResult();
    try{
      Game game = gameDAO.find(gameRequest.getGameID());
      AuthToken authToken = authDAO.findAuth(gameRequest.getAuthTokean());
    if(game == null || game.getGameID() != gameRequest.getGameID()){
      //bad request
      result.setMessage("Error: bad request");
    }else if (authDAO.findAuth(gameRequest.getAuthTokean()) == null){
      // unauthorized
      result.setMessage("Error: unauthorized");
    }else if (gameRequest.getPlayerColor() != null && game.getWhiteUsername() != null && gameRequest.getPlayerColor().equalsIgnoreCase("white")){
      // already taken
      result.setMessage("Error: already taken");
    }else if(gameRequest.getPlayerColor() != null && game.getBlackUsername() != null && gameRequest.getPlayerColor().equalsIgnoreCase("black")){
      result.setMessage("Error: already taken");
    }
    else{
      // success
      if(gameRequest.getPlayerColor() == null){
        result.setMessage("Observer added");
      }else if (gameRequest.getPlayerColor().equalsIgnoreCase("black")){
        game.setBlackUsername(authToken.getUsername());
      }else if (gameRequest.getPlayerColor().equalsIgnoreCase("white")){
        game.setWhiteUsername(authToken.getUsername());
      }
      gameDAO.updateGame(gameRequest.getGameID(), game);
    }
    }catch (DataAccessException e){
      result.setMessage("Error: Data Access Error");
    }
    return result;
  }

}
