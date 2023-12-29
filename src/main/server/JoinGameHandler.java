package server;


import RequestResponses.JoinGameRequest;
import RequestResponses.JoinGameResult;
import com.google.gson.Gson;
import service.JoinGameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler{

  public String handler(Request req, Response res){
    Gson gson = new Gson();

    JoinGameRequest request = gson.fromJson(req.body(), JoinGameRequest.class);
    request.setAuthTokean(req.headers("Authorization"));
    JoinGameService service = new JoinGameService();
    JoinGameResult result = service.joinGame(request);
    String josnString = gson.toJson(result);

    if(result.getMessage() == null){
      res.status(200);
    }else if(result.getMessage().equals("Observer added")){
      res.status(200);
    }else if(result.getMessage().equals("Error: bad request")){
      res.status(400);
    }else if(result.getMessage().equals("Error: unauthorized")){
      res.status(401);
    }else if (result.getMessage().equals("Error: already taken")){
      res.status(403);
    }else{
      res.status(500);
      result.setMessage("Error: description");
    }

    return josnString;
  }
}
