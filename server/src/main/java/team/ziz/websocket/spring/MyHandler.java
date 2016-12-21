package team.ziz.websocket.spring;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class MyHandler implements WebSocketHandler {

   public void afterConnectionEstablished(WebSocketSession session)
         throws Exception {
      // TODO Auto-generated method stub

   }

   public void handleMessage(WebSocketSession session,
         WebSocketMessage<?> message) throws Exception {
      // TODO Auto-generated method stub

   }

   public void handleTransportError(WebSocketSession session,
         Throwable exception) throws Exception {
      // TODO Auto-generated method stub

   }

   public void afterConnectionClosed(WebSocketSession session,
         CloseStatus closeStatus) throws Exception {
      // TODO Auto-generated method stub

   }

   public boolean supportsPartialMessages() {
      // TODO Auto-generated method stub
      return false;
   }

}
