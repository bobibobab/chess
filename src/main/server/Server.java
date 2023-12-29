package server;


import com.sun.net.httpserver.HttpServer;
import service.LoginService;
import spark.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;
import service.*;



public class Server {
  private HttpServer server;
  private static final int MAX_WAITING_CONNECTIONS = 12;
  private final WebSocketHandler webSocketHandler = new WebSocketHandler();

  public static void main(String[] args){
    new Server().run();
  }// end of main

  private void run() {
    System.out.println("Initializing HTTP Server");


    Spark.port(8080);

    //Register a directory for hosting static files.
    Spark.externalStaticFileLocation("web");

    Spark.webSocket("/connect", webSocketHandler);

    Spark.delete("/db",  ((request, response) -> new ClearHandler().handler(request, response)));
    Spark.post("/user", ((request, response) -> new RegisterHandler().handler(request, response)));
    Spark.post("/session", ((request, response) -> new LoginHandler().handler(request, response)));
    Spark.delete("/session", ((request, response) -> new LogoutHandler().handler(request, response)));
    Spark.get("/game", ((request, response) -> new ListGamesHandler().handler(request, response)));
    Spark.post("/game", ((request, response) -> new CreateGameHandler().handler(request, response)));
    Spark.put("/game", ((request, response) -> new JoinGameHandler().handler(request, response)));

    System.out.println("Starting server");

  }


}
