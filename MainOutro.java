/********************* Supposed to be the Outro Panel in Main Frame *******************************/
import javax.swing.*;

import View.components.panels.OutroPage;

import java.awt.Dimension;
import java.util.*;

public class MainOutro {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Ended");   // IMENE
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(800, 530));
            frame.setSize(800, 530);
            frame.setLocationRelativeTo(null);
            
            // values for test only, will be changed via Controller later on
            Map<String, List<String>> logs = new LinkedHashMap<>();
            logs.put("Player 1", List.of("Draw 2", "Skip", "Blue 4", "Red 9"));
            logs.put("Player 2", List.of("Yellow 3", "Reverse", "UNO!", "UNO!", "UNO!", "UNO!", "UNO!", "UNO!", "UNO!", "UNO!", "UNO!", "UNO!","UNO!"));
            logs.put("Player 3", List.of("UNO!", "UNO!", "UNO!", "UNO!", "UNO!", "UNO!", "UNO!", "UNO!"));
            logs.put("Player 4", List.of("UNO!", "UNO!", "UNO!", "UNO!", "UNO!", "UNO!", "UNO!", "UNO!"));
            
            frame.setContentPane(new OutroPage("Player 1", logs, frame));
            frame.setVisible(true);
        });
    }
}