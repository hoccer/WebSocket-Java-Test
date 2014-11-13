package com.example.WebSocket_Java_Test;

import android.app.Activity;
import android.os.Bundle;
import org.java_websocket.drafts.Draft_17;

import javax.net.ssl.*;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.HashMap;

public class MainActivity extends Activity {

    private static final String SERVER_URI = "ws://10.1.34.109:8443/"; // "wss://talkserver-test1.talk.hoccer.de/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        URI serverUri = URI.create(SERVER_URI);

        HashMap<String, String> httpHeaders = new HashMap<String, String>();
        httpHeaders.put("Sec-WebSocket-Protocol", "com.hoccer.talk.v5.bson");

        MyWebSocketClient client = new MyWebSocketClient(serverUri, new Draft_17(), httpHeaders);

        try {
            // get the KeyStore
            KeyStore ks = KeyStore.getInstance("BKS");
            // load our keys into it
            InputStream in = getResources().openRawResource(R.raw.ssl_bks);
            try {
                ks.load(in, "password".toCharArray());
            } finally {
                in.close();
            }

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, null);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            SSLSocketFactory factory = sslContext.getSocketFactory();

            client.setSocket(factory.createSocket());
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
