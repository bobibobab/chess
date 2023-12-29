package webSocketMessages.userCommands;

public class ResignCommand extends UserGameCommand{
  private Integer gameID = null;
  public ResignCommand(Integer gameID, String authToken) {
    super(authToken);
    this.gameID = gameID;
  }

  public Integer getGameID() {
    return gameID;
  }
}
