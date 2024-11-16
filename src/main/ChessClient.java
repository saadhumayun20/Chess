package main;

import java.io.*;
import java.net.*;

public class ChessClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ChessClient(String serverAddress) {
        try {
            socket = new Socket(serverAddress, 12345);
            System.out.println("Connected to the server at " + serverAddress);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Start a thread to listen for moves from the other player
            new Thread(new IncomingMovesListener()).start();

            // Capture user moves and send them to the server
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String move;
            while ((move = userInput.readLine()) != null) {
                sendMove(move); // Send move to server
                System.out.println("You made the move: " + move);
            }
        } catch (IOException e) {
            System.out.println("Error connecting to server.");
            e.printStackTrace();
        }
    }

    private class IncomingMovesListener implements Runnable {
        public void run() {
            try {
                String incomingMove;
                while ((incomingMove = in.readLine()) != null) {
                    System.out.println(incomingMove); // Display move from the opponent or server messages
                }
            } catch (IOException e) {
                System.out.println("Connection to the server lost.");
                e.printStackTrace();
            }
        }
    }

    public void sendMove(String move) {
        out.println(move); // Sends the move to the server
        out.flush(); // Ensure it's sent immediately
    }

    public static void main(String[] args) {
        new ChessClient("localhost"); // Replace "localhost" with the server IP if remote
    }
}
