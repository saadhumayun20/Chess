package main;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChessServer {
    private static final int PORT = 12345;
    private static final Queue<Socket> waitingPlayers = new LinkedList<>();

    public static void main(String[] args) {
        System.out.println("Chess Server started. Waiting for players...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Player connected: " + clientSocket.getInetAddress());

                synchronized (waitingPlayers) {
                    waitingPlayers.add(clientSocket);

                    // If two players are connected, start a game
                    if (waitingPlayers.size() >= 2) {
                        Socket player1 = waitingPlayers.poll();
                        Socket player2 = waitingPlayers.poll();
                        System.out.println("Starting a new game between " + player1.getInetAddress() + " and " + player2.getInetAddress());
                        new GameHandler(player1, player2).start();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
