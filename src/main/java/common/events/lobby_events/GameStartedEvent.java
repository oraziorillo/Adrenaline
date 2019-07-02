package common.events.lobby_events;

import common.dto_model.LobbyDTO;

public class GameStartedEvent extends LobbyEvent {
   
   public GameStartedEvent(LobbyDTO lobby, boolean censored) {
      super( lobby, censored );
   }
   
   public GameStartedEvent(LobbyDTO lobbyDTO){
      super(lobbyDTO);
   }
   
   @Override
   public String toString() {
      return "Game Started!";
   }
   
   @Override
   public LobbyEvent censor() {
      return new GameStartedEvent(lobby,true);
   }
}
