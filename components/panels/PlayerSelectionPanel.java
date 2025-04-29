package components.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PlayerSelectionPanel extends JPanel {
    private int humanPlayers;
    private int botPlayers;
    private JButton backButton;
    private JButton startButton;

public PlayerSelectionPanel(int humans, int bots) {
    this.humanPlayers = humans;
    this.botPlayers = bots;
    int totalPlayers = humans + bots;
    
    setLayout(new BorderLayout());
    setBackground(new Color(0, 128, 128)); // Teal background
    
    // Create title
    JLabel titleLabel = new JLabel("Choose the players", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
    titleLabel.setForeground(Color.WHITE);
    add(titleLabel, BorderLayout.NORTH);
    
    // Create panel for player icons
    JPanel playersPanel = new JPanel(new GridLayout(1, totalPlayers, 20, 0));
    playersPanel.setBackground(new Color(0, 128, 128));
    playersPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
    
    // Add player icons
    for (int i = 0; i < totalPlayers; i++) {
        IconPlayer playerIcon;
        if (i == 0) {
            playerIcon = new IconPlayer();
        } else if (i < humans) {
            playerIcon = new IconPlayer();
        } else {
            playerIcon = new IconPlayer();
        }
        playersPanel.add(playerIcon);
    }
    
    add(playersPanel, BorderLayout.CENTER);
    
    // Button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(0, 128, 128));
    
    // Back button
    backButton = new JButton("Back");
    backButton.setFont(new Font("Arial", Font.BOLD, 18));
    
    // Start game button
    startButton = new JButton("Start Game");
    startButton.setFont(new Font("Arial", Font.BOLD, 18));
    
    buttonPanel.add(backButton);
    buttonPanel.add(Box.createHorizontalStrut(30));
    buttonPanel.add(startButton);
    
    add(buttonPanel, BorderLayout.SOUTH);
}

public void setBackButtonListener(ActionListener listener) {
    backButton.addActionListener(listener);
}

public void setStartButtonListener(ActionListener listener) {
    startButton.addActionListener(listener);
}
}

