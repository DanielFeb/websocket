package team.ziz;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.*;


public class ChatWebSocket extends WebSocketServer {

    public static Map<String, List<WebSocket>> socketMap = new HashMap<String, List<WebSocket>>();//Ƶ��-����Ƶ���û��������б�

    /**
     * ���������
     * @param args
     */
    public static void main(String[] args) {
        InetSocketAddress address = new InetSocketAddress(4444);
        ChatWebSocket socket = new ChatWebSocket(address);
        socket.start();//����websocket����������
    }

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
        String desc = handshake.getResourceDescriptor().substring(1);//����ҳ������'ws://127.0.0.1:4444/'+topSid; desc��ȡ���ľ���topSid���˴���desc���ֲ�ͬ��������ӣ����ں���ͨѶ  
        if(socketMap.get(desc) == null){
            List<WebSocket> list = new ArrayList<WebSocket>();
            list.add(conn);//connect�ɹ�ʱ��ִ��  
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
                socketMap.get(string).remove(conn);//ɾ������  
                if (socketMap.get(string).isEmpty()) {//����Ϊ��ʱ���  
                    socketMap.remove(string);
                }
                break;
            }
        }
    }


    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("onMessage:" + message);
        /*List<WebSocket> socketList = socketMap.get(message); 
        for (WebSocket webSocket : socketList) { 
            webSocket.send("���յ�~"); 
        }*/
        sendInAllChannel("~���յ�~");//�����յ���Ϣʱȫ�ַ�����Ϣ  
    }


    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("onError");
        Set<String> setList = socketMap.keySet();
        for (String string : setList) {
            if (socketMap.get(string).contains(conn)) {
                socketMap.get(string).remove(conn);//ɾ������  
                if (socketMap.get(string).isEmpty()) {//����Ϊ��ʱ���  
                    socketMap.remove(string);
                }
                break;
            }
        }
    }

    /**
     * ������Ϣ��ĳһ������ 
     * @param channel
     * @param message
     */
    public void sendInOneChannel(String channel,String message){
        List<WebSocket> list = socketMap.get(channel);
        for (WebSocket webSocket : list) {
            webSocket.send(message);
        }
    }

    /**
     * ������Ϣ���������� 
     * @param message
     */
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