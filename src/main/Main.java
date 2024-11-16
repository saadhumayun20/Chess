package main;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(new Color(20, 20, 20));
        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(1000, 1000));

        frame.setLocationRelativeTo(null);

        Board board = new Board();
        frame.add(board);
        frame.setVisible(true);

        // Connect to the chess server after setting up the UI
            // Use "localhost" if running the server on the same machine
            String serverAddress = "localhost"; // Change this to the server's IP if needed
            new ChessClient(serverAddress); // Start the client connection

    }
}
