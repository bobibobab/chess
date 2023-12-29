package webSocketMessages.userCommands;

public class LeaveCommand extends UserGameCommand{
  private Integer gameID = null;
  public LeaveCommand(Integer gameID, String authToken) {
    super(authToken);
    this.gameID = gameID;
  }

  public Integer getGameID() {
    return gameID;
  }
}
