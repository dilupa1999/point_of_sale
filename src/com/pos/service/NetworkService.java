package com.pos.service;

import java.net.InetSocketAddress;
import java.net.Socket;

public class NetworkService {

    /**
     * Checks if internet is available by attempting to connect to a reliable host
     * (Google DNS).
     * 
     * @return true if connectivity is established, false otherwise.
     */
    public static boolean isInternetAvailable() {
        try (Socket socket = new Socket()) {
            int timeoutMs = 2000; // 2 seconds timeout
            socket.connect(new InetSocketAddress("8.8.8.8", 53), timeoutMs);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
