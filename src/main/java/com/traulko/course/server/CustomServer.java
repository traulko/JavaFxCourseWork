package com.traulko.course.server;

import com.traulko.course.controller.command.impl.SignUpCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomServer {
    private static final Logger LOGGER = LogManager.getLogger(CustomServer.class);
    public static final int PORT_WORK = 8080;
    public static final int PORT_STOP = 9002;

    public static void main(String[] args) {
        MultiThreadedServer server = new MultiThreadedServer(PORT_WORK);
        new Thread(server).start();
        System.out.println("Server started");
        try {
            Thread monitor = new StopMonitor(PORT_STOP);
            monitor.start();
            monitor.join();
            LOGGER.log(Level.INFO, "Right after join...");
        } catch (InterruptedException e) {
            LOGGER.log(Level.FATAL, "Error while stating server", e);
        }
        LOGGER.log(Level.INFO, "Server stopped");
        server.stop();
    }
}
