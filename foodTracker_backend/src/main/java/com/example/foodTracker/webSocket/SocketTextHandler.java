package com.example.foodTracker.webSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketTextHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessionList=new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception
    {
        sessionList.add(session);
        System.out.println("New Session: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        String payload = message.getPayload();
        JSONObject jsonObject = new JSONObject(payload);

        for(WebSocketSession s:sessionList)
        {
            if(s.isOpen() && s.getId()!=session.getId()){
                System.out.println("s="+s.getId());
                s.sendMessage(new TextMessage(jsonObject.get("user") + " has logged in!"));
            }


        }

    }

}