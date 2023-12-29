package service;

import RequestResponses.RegisterRequest;
import RequestResponses.RegisterResult;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.*;

import java.util.UUID;

/**
 * RegisterService Class
 */

public class RegisterService {
  /**
   * Register a new user's name, password, and email into the database.
   * @param regRequest
   * @return RegisterResult object
   */
  public RegisterResult register(RegisterRequest regRequest){
//use try and catch
    //Do I need to create authDAO for register?? I do not think so.
    AuthDAO authDAO = new AuthDAO();
    UserDAO userDAO = new UserDAO();

    RegisterResult result = new RegisterResult();
    try {
      if (regRequest.getEmail() == null || regRequest.getPassword() == null || regRequest.getUsername() == null) {
        result.setMessage("Error: bad request");
      } else if (userDAO.findUser(regRequest.getUsername()) != null) {
        //already taken
        // how can I create DAO class?
        result.setMessage("Error: already taken");
      } else {
        //if success
        User user=new User(regRequest.getUsername(), regRequest.getPassword(), regRequest.getEmail());
        String authToke=UUID.randomUUID().toString();
        AuthToken authToken=new AuthToken(regRequest.getUsername(), authToke);

        //insert auth and user into DAO
        authDAO.insertAuth(authToken);
        userDAO.insertUser(user);

        //set user
        result.setUsername(regRequest.getUsername());
        result.setAuthToken(authToke);
        result.success=true;
      }
    }
    catch (DataAccessException e){
      result.setMessage("Error: trouble access data");
    }
    return result;
  }// end of register
}
