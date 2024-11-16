package main;

import java.io.*;
import java.net.*;

class GameHandler extends Thread {
    private Socket player1;
    private Socket player2;
    private PrintWriter out1;
    private PrintWriter out2;
    private BufferedReader in1;
    private BufferedReader in2;

    public GameHandler(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void run() {
        try {
            // Set up I/O streams for both players
            in1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
            out1 = new PrintWriter(player1.getOutputStream(), true);
            in2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
            out2 = new PrintWriter(player2.getOutputStream(), true);

            // Inform both players of the game start and their roles
            out1.println("Game started. You are Player 1 (White). Make your move.");
            out2.println("Game started. You are Player 2 (Black). Waiting for Player 1's move.");

            boolean player1Turn = true;

            // Game loop for exchanging moves
            while (true) {
                if (player1Turn) {
                    String move1 = in1.readLine();
                    if (move1 == null) {
                        System.out.println("Player 1 disconnected.");
                        out2.println("Player 1 has disconnected. Game over.");
                        break;
                    }
                    System.out.println("Player 1 move: " + move1);
                    out2.println("Player 1's move: " + move1); // Send move to Player 2
                    out1.println("Waiting for Player 2's move."); // Update Player 1 to wait
                    player1Turn = false;
                } else {
                    String move2 = in2.readLine();
                    if (move2 == null) {
                        System.out.println("Player 2 disconnected.");
                        out1.println("Player 2 has disconnected. Game over.");
                        break;
                    }
                    System.out.println("Player 2 move: " + move2);
                    out1.println("Player 2's move: " + move2); // Send move to Player 1
                    out2.println("Waiting for Player 1's move."); // Update Player 2 to wait
                    player1Turn = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (player1 != null) player1.close();
                if (player2 != null) player2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
