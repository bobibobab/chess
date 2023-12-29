package service;

import RequestResponses.LoginRequest;
import RequestResponses.LoginResult;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthToken;
import model.User;

import java.util.UUID;

/**
 * Login Class
 * One of the service classes represent giving Login information to database.
 */
public class LoginService {
  /**
   * Logs in an existing user.
   * @param logRequest
   * @return a new authToken
   */
  UserDAO userDAO = new UserDAO();
  AuthDAO authDAO = new AuthDAO();
  public LoginResult login(LoginRequest logRequest){
    LoginResult result = new LoginResult();
    //Do I need to bring the authToken from authDAO??

    try{
      User user = userDAO.findUser(logRequest.getUsername());
      if(user == null){
        //Wrong userName
        result.setMessage("Error: unauthorized");
      }else{
          //Check password
          if(user.getPassword().equals(logRequest.getPassword())){
            String authToke=UUID.randomUUID().toString();
            AuthToken authToken=new AuthToken(user.getUserName(), authToke);
            authDAO.insertAuth(authToken);
            result.setUsername(user.getUserName());
            result.setAuthToken(authToke);
            result.success = true;
          }else{
            // Wrong pwd
            result.setMessage("Error: unauthorized");
          }
      }
    }catch (DataAccessException e){
      result.setMessage("Error: Data access error");
    }
    return result;
  }// end of login


}
