package com.traulko.course.server;

import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CommandProvider;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.Batch;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Optional;


public class Worker implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(Worker.class);
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;

    public Worker(Socket socket) throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }
    @Override
    public void run() {
        try {
            Batch requestBatch = (Batch) inputStream.readObject();
            String batchCommand = requestBatch.getBatchMap().get(RequestParameter.COMMAND_NAME).toString();
            Optional<CustomCommand> commandOptional = CommandProvider.defineCommand(batchCommand);
            CustomCommand command = commandOptional.orElseThrow(IllegalArgumentException::new);
            Batch responseBatch = command.execute(requestBatch);
            outputStream.writeObject(responseBatch);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.ERROR, "Error while running worker", e);
        } finally {
            disconnect();
        }
    }

    private void disconnect() {
        try {
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Error while disconnecting", e);
        }
    }
}
