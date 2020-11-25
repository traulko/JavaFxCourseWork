package com.traulko.course.controller;

import com.traulko.course.controller.command.CommandProvider;
import com.traulko.course.entity.Batch;
import com.traulko.course.controller.command.CustomCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

public class ServerThreadController extends Thread {
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;

    public ServerThreadController(Socket socket) throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void run() {
        try {
            Batch requestBatch = (Batch) inputStream.readObject();
            String batchCommand = requestBatch.getBatchMap().get(RequestParameter.COMMAND_NAME).toString();
            Optional<CustomCommand> commandOptional = CommandProvider.defineCommand(batchCommand);
            CustomCommand command = commandOptional.orElseThrow(IllegalArgumentException::new);
            String responseBatch = command.execute(requestBatch);
            outputStream.writeObject(responseBatch);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    private void disconnect() {
        try {
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }
}
