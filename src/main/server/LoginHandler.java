package server;

import RequestResponses.LoginRequest;
import RequestResponses.LoginResult;
import com.google.gson.Gson;
import service.LoginService;
import spark.*;


public class LoginHandler {

  public String handler(Request req, Response res){
    Gson gson = new Gson();

    LoginRequest request = gson.fromJson(req.body(), LoginRequest.class);
    LoginService service = new LoginService();
    LoginResult result = service.login(request);
    String josnString = gson.toJson(result);

    if(result.success){
      res.status(200);
    }else if (result.getMessage().equals("Error: unauthorized")){
      res.status(401);
    }else{
      res.status(500);
    }

    return josnString;
  }
}
