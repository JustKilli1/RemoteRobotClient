package server;

import base.Main;
import database.DBAccessLayer;
import database.DBHandler;

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
    private DBHandler dbHandler;
    private ExecutorService connections = Executors.newCachedThreadPool();

    public Server() {
        this.sql = new DBAccessLayer();
        dbHandler = new DBHandler(sql);
    }

    public void start() {
        isActive = true;
        try {
            socket = new ServerSocket(Main.getGroundPort());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while(isActive) {
                            System.out.println("Waiting on Port " + Main.getGroundPort() + " for connections");
                            Socket clientSocket = socket.accept();
                            connections.execute(new InputHandler(clientSocket, sql));
                            System.out.println("New Robot Connected");
                        }
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        } catch(Exception ex) {
            ex.printStackTrace();
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
                while((message = input.readLine()) != null) {
                    if(message.contains("MEASURE")) {
                        int newId = dbHandler.getLastId() + 1;
                        sql.sendMeasureToDatabase(newId, MeasureData.parse(message).get());
                    }
                }
                System.out.println("Connection closed");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}
