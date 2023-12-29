package service;

import RequestResponses.ClearResult;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import model.User;

/**
 * Class of ClearService
 */

public class ClearService {

  AuthDAO authDAO = new AuthDAO();
  UserDAO userDAO = new UserDAO();
  GameDAO gameDAO = new GameDAO();
  /**
   * Clears the database. Removes all users, games, and authTokens.
   * @return
   */

  public ClearResult clear(){
    ClearResult result = new ClearResult();
    try{
      //clear all data
      userDAO.clear();
      authDAO.clear();
      gameDAO.clear();
    }catch (DataAccessException e){
      result.setMessage("Error: Data Access Error");
    }
    return result;
  }
}
