package webSocketMessages.userCommands;

public class JoinObserverCommand extends UserGameCommand{
  private Integer gameID = null;
  public JoinObserverCommand(Integer gameID, String authToken) {
    super(authToken);
    this.gameID = gameID;
  }

  public Integer getGameID(){
    return gameID;
  }

}
