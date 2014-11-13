package com.example.WebSocket_Java_Test;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.FrameBuilder;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

/**
 * WebSocketClient used for testing.
 */
public class MyWebSocketClient extends org.java_websocket.client.WebSocketClient {

    public MyWebSocketClient(URI uri, Draft draft, Map<String, String> httpHeaders) {
        super(uri, draft, httpHeaders, 0);
    }

    @Override
    public void onMessage(String message) {
        send(message);
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Error: ");
        ex.printStackTrace();
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Open: " + handshake.getHttpStatusMessage());
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Closed: " + code + " " + reason);
    }

    @Override
    public void onWebsocketMessageFragment(WebSocket conn, Framedata frame) {
        FrameBuilder builder = (FrameBuilder) frame;
        builder.setTransferemasked(true);
        getConnection().sendFrame(frame);
    }
}
