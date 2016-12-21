package team.ziz.websocket.basic;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.*;


public class ChatWebSocket extends WebSocketServer {
    public static int portNumber = 4444;

    public static Map<String, List<WebSocket>> socketMap = new HashMap<String, List<WebSocket>>();
    
//    public static void main(String[] args) {
//        InetSocketAddress address = new InetSocketAddress(portNumber);
//        ChatWebSocket socket = new ChatWebSocket(address);
//        socket.start();
//    }

    public ChatWebSocket(InetSocketAddress address) {
        super(address);
    }

    /**
     *
     * @param address
     */
    public ChatWebSocket(InetSocketAddress address, int decoders) {
        super(address, decoders);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("onOpen:" + handshake.getResourceDescriptor());
        String desc = handshake.getResourceDescriptor().substring(1);
        if(socketMap.get(desc) == null){
            List<WebSocket> list = new ArrayList<WebSocket>();
            list.add(conn);
            socketMap.put(desc, list);
        } else {
            socketMap.get(desc).add(conn);
        }
    }


    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("onClose");
        Set<String> setList = socketMap.keySet();
        for (String string : setList) {
            if (socketMap.get(string).contains(conn)) {
                socketMap.get(string).remove(conn);
                if (socketMap.get(string).isEmpty()) {
                }
                break;
            }
        }
    }


    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("onMessage:" + message);
        List<WebSocket> socketList = socketMap.get(message);

        if(socketList == null){
            System.out.println("socketList is empty");
            return;
        }

        for (WebSocket webSocket : socketList) { 
            webSocket.send("~Got message~" + message);
        }  
    }


    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("onError");
        Set<String> setList = socketMap.keySet();
        for (String string : setList) {
            if (socketMap.get(string).contains(conn)) {
                socketMap.get(string).remove(conn);
                if (socketMap.get(string).isEmpty()) {
                    socketMap.remove(string);
                }
                break;
            }
        }
    }
    
    public void sendInOneChannel(String channel,String message){
        List<WebSocket> list = socketMap.get(channel);
        for (WebSocket webSocket : list) {
            webSocket.send(message);
        }
    }
    
    public void sendInAllChannel(String message){
        Set<String> setList = socketMap.keySet();
        for (String string : setList) {
            List<WebSocket> list = socketMap.get(string);
            for (WebSocket webSocket : list) {
                webSocket.send(message);
            }
        }
    }
}  