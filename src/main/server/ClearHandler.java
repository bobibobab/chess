package server;

import RequestResponses.ClearResult;
import com.google.gson.Gson;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler{

  public String handler(Request req, Response res){
    Gson gson = new Gson();

    ClearService service = new ClearService();
    ClearResult result = service.clear();
    String josnString = gson.toJson(result);

    if(result.getMessage() == null){
      res.status(200);
    }else{
      res.status(500);
    }
    return josnString;
  }

}
