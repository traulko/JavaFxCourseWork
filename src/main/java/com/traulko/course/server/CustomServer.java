package com.traulko.course.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

public class CustomServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server started!");
            while (true) {
                ServerConnection serverConnection = new ServerConnection(serverSocket);
                new Thread(() -> {
                    String request = serverConnection.readLine();
                    System.out.println(request);
                    String response = "1";
                    serverConnection.writeLine(response);
                    System.out.println(response);
                    try {
                        serverConnection.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}