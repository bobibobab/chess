package service;

import RequestResponses.LogoutRequest;
import RequestResponses.LogoutResult;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;

/**
 * LogoutService class
 */
public class LogoutService {
  AuthDAO authDAO = new AuthDAO();
  /**
   * Logs out the user represented by the authToken.
   * @param logoutReq
   * @return Logout result
   */
  public LogoutResult logout(LogoutRequest logoutReq){
    LogoutResult result = new LogoutResult();
    try{
      AuthToken authToken = authDAO.findAuth(logoutReq.getAuthToken());
      if(authToken == null){
        //Unauthorized
        result.setMessage("Error: unauthorized");
      }else{
        //Log out the user represented by the authToken
        authDAO.remove(logoutReq.getAuthToken());
      }
    }catch (DataAccessException e){
      result.setMessage("Error: Data Access Error");
    }
    return result;
  }

}
