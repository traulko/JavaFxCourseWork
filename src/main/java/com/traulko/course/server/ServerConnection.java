package com.traulko.course.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnection implements Closeable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public ServerConnection(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.bufferedReader = this.createReader();
            this.bufferedWriter = this.createWriter();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerConnection(ServerSocket server) {
        try {
            this.socket = server.accept();
            this.bufferedReader = this.createReader();
            this.bufferedWriter = this.createWriter();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readLine() {
        try {
            return this.bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error while reading line", e);
        }
    }

    public void writeLine(String message) {
        try {
            this.bufferedWriter.write(message);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error while reading line", e);
        }
    }

    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    private BufferedReader createReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    @Override
    public void close() throws IOException {
        this.socket.close();
        this.bufferedReader.close();
        this.bufferedWriter.close();
    }
}
