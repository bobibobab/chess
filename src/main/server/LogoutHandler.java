package server;


import RequestResponses.LogoutResult;
import RequestResponses.LogoutRequest;
import com.google.gson.Gson;
import service.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler{
  public String handler(Request req, Response res){
    Gson gson = new Gson();

    String authToken = req.headers("Authorization");

    LogoutRequest request = new LogoutRequest();
    request.setAuthToken(authToken);
    LogoutService service = new LogoutService();
    LogoutResult result = service.logout(request);
    String josnString = gson.toJson(result);

    if(result.getMessage() == null){
      res.status(200);
    }else if(result.getMessage().equals("Error: unauthorized")){
      res.status(401);
    }else{
      res.status(500);
      result.setMessage("Error: description");
    }
    return josnString;
  }
}
