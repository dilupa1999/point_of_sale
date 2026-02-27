package com.pos.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class AuthService {

    private static final String API_BASE_URL = "http://127.0.0.1:8000/api";

    /**
     * Authenticates a user via the Laravel API.
     * 
     * @param email    The user's email.
     * @param password The user's password.
     * @return A JSON string response from the server if successful, or an error
     *         message.
     * @throws Exception if an error occurs during the request.
     */
    public static String login(String email, String password) throws Exception {
        java.net.URL url = java.net.URI.create(API_BASE_URL + "/login").toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int code = conn.getResponseCode();
        StringBuilder response = new StringBuilder();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream(),
                        StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        return response.toString();
    }
}
