package com.traulko.course.client;

import com.traulko.course.entity.Batch;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection {
    public static Batch getConnectionResult(Batch requestBatch) {
        Batch result = null; // TODO: 26.11.2020
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeObject(requestBatch);
            result = (Batch) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
