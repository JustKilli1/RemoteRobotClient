package server;

import database.DBAccessLayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket socket;
    private boolean isActive;
    private BufferedReader input;
    private DBAccessLayer sql;
    private ExecutorService connections = Executors.newCachedThreadPool();

    public Server() {
        this.sql = new DBAccessLayer();
    }

    public void start() {
        isActive = true;
        try {
            socket = new ServerSocket(7832);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(isActive) {
                            Socket clientSocket = socket.accept();
                            connections.execute(new InputHandler(clientSocket, sql));
                        }
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        } catch(Exception ex) {

        }
    }

    public void stop() {
        isActive = false;
    }

    private class InputHandler implements Runnable {

        private Socket clientSocket;
        private BufferedReader input;
        private DBAccessLayer sql;

        public InputHandler(Socket clientSocket, DBAccessLayer sql) {
            this.clientSocket = clientSocket;
            this.sql = sql;
        }

        @Override
        public void run() {
            try {
                input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
                String message;
                while(!((message = input.readLine()).equalsIgnoreCase("/quit"))) {
                    if(message.contains("MEASURE")) {
                        sql.sendMeasureToDatabase(MeasureData.parse(message).get());
                    }
                }
            } catch(Exception ex) {

            }
        }
    }


}
