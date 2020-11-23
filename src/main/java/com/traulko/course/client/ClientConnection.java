package com.traulko.course.client;

import com.traulko.course.server.ServerConnection;

import java.io.IOException;

public class ClientConnection {
    public static void getConnection() {
        try (ServerConnection serverConnection = new ServerConnection("127.0.0.1", 8080)) {
            System.out.println("Connected to server!");
            String request = "haha";
            serverConnection.writeLine(request);
            String response = serverConnection.readLine();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
