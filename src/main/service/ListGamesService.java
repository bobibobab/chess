package service;

import RequestResponses.ListGamesRequest;
import RequestResponses.ListGamesResult;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;

/**
 * ListGamesService
 */

public class ListGamesService {
  AuthDAO authDAO = new AuthDAO();
  GameDAO gameDAO = new GameDAO();
  /**
   * Gives a list of all games.
   * @return GamesList
   */
  public ListGamesResult listGames (ListGamesRequest req){

    ListGamesResult result = new ListGamesResult();
    try{
      if(authDAO.findAuth(req.getAuthToken()) == null){
        //unauthorized
        result.setMessage("Error: unauthorized");
      }else{
        //List games
        result.setListGames(gameDAO.findAll());
      }
    }catch (DataAccessException e){
      result.setMessage("Error: Data Access Error");
    }

    return result;
  }

}
