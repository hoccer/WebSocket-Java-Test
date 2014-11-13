package com.example.WebSocket_Java_Test;

import android.app.Activity;
import android.os.Bundle;
import org.java_websocket.drafts.Draft_17;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
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
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted( final X509Certificate[] chain, final String authType ) {
                }
                @Override
                public void checkServerTrusted( final X509Certificate[] chain, final String authType ) {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            } };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, null);
            SSLSocketFactory factory = sslContext.getSocketFactory();

            client.setSocket(factory.createSocket());
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
