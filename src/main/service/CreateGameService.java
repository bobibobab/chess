package service;

import RequestResponses.CreateGameRequest;
import RequestResponses.CreateGameResult;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.AuthToken;
import model.Game;

/**
 * Class of CreateGameService
 */

public class CreateGameService {
  AuthDAO authDAO = new AuthDAO();
  GameDAO gameDAO = new GameDAO();
  /**
   * Creates a new game
   * @param createGameReq
   * @return Result resultCreateGame with a new game
   */
  public CreateGameResult createGame(CreateGameRequest createGameReq){
    CreateGameResult result = new CreateGameResult();
    try{
      if(createGameReq.getGameName() == null){
        //bad request
        result.setMessage("Error: bad request");
      }else if(authDAO.findAuth(createGameReq.getAuthToken()) == null){
        //unauthorized
        result.setMessage("Error unauthorized");
      }else{
        //If success
        Game game = new Game(createGameReq.getGameName());
        gameDAO.insertGame(game);
        result.setGameID(game.getGameID());
      }
    }catch (DataAccessException e){
      result.setMessage("Error: Data Access Error");
    }
    return result;
  }

}
