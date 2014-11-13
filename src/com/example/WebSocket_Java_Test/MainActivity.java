package com.example.WebSocket_Java_Test;

import android.app.Activity;
import android.os.Bundle;
import org.java_websocket.drafts.Draft_17;

import java.net.URI;
import java.util.HashMap;

public class MainActivity extends Activity {

    private static final String SERVER_URI = "ws://10.1.34.109:8080/"; // "wss://talkserver-test1.talk.hoccer.de/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        URI serverUri = URI.create(SERVER_URI);

        HashMap<String, String> httpHeaders = new HashMap<String, String>();
        httpHeaders.put("Sec-WebSocket-Protocol", "com.hoccer.talk.v5.bson");

        MyWebSocketClient client = new MyWebSocketClient(serverUri, new Draft_17(), httpHeaders);
        client.connect();
    }
}
