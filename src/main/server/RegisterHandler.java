package server;


import RequestResponses.RegisterRequest;
import RequestResponses.RegisterResult;
import com.google.gson.Gson;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler {

  public String handler(Request req, Response res){
    Gson gson = new Gson();
    String st = req.body();
    RegisterRequest request = gson.fromJson(req.body(), RegisterRequest.class);
    RegisterService service = new RegisterService();
    RegisterResult result = service.register(request);
    if(result.success){
      res.status(200);
    }else{
      if(result.getMessage().equals("Error: bad request")){
        res.status(400);
      }else if (result.getMessage().equals("Error: already taken")){
        res.status(403);
      }else{
        res.status(500);
      }
    }
    String josnString = gson.toJson(result);

    return josnString;
  }
}
