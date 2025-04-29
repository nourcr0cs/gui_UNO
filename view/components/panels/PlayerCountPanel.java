package view.components.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PlayerCountPanel extends JPanel {
    private JSpinner humanSpinner;
    private JSpinner botSpinner;
    private JLabel errorLabel;
    private JButton continueButton;
    
    public PlayerCountPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(0, 128, 128)); // Teal background
        
        add(Box.createVerticalStrut(50));
        
        // Title
        JLabel titleLabel = new JLabel("UNO Game Setup");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        
        add(Box.createVerticalStrut(50));
        
        // Instructions
        JLabel instructionLabel = new JLabel("Select number of players (max 4 total)");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(instructionLabel);
        
        add(Box.createVerticalStrut(50));
        
        // Human players selection
        JPanel humanPanel = new JPanel();
        humanPanel.setOpaque(false);
        JLabel humanLabel = new JLabel("Human Players:");
        humanLabel.setFont(new Font("Arial", Font.BOLD, 20));
        humanLabel.setForeground(Color.WHITE);
        
        SpinnerNumberModel humanModel = new SpinnerNumberModel(1, 1, 4, 1);
        humanSpinner = new JSpinner(humanModel);
        humanSpinner.setFont(new Font("Arial", Font.PLAIN, 20));
        humanSpinner.setPreferredSize(new Dimension(60, 30));
        
        humanPanel.add(humanLabel);
        humanPanel.add(humanSpinner);
        humanPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(humanPanel);
        
        add(Box.createVerticalStrut(30));
        
        // Bot players selection
        JPanel botPanel = new JPanel();
        botPanel.setOpaque(false);
        JLabel botLabel = new JLabel("Bot Players:    ");
        botLabel.setFont(new Font("Arial", Font.BOLD, 20));
        botLabel.setForeground(Color.WHITE);
        
        SpinnerNumberModel botModel = new SpinnerNumberModel(1, 0, 3, 1);
        botSpinner = new JSpinner(botModel);
        botSpinner.setFont(new Font("Arial", Font.PLAIN, 20));
        botSpinner.setPreferredSize(new Dimension(60, 30));
        
        botPanel.add(botLabel);
        botPanel.add(botSpinner);
        botPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(botPanel);
        
        add(Box.createVerticalStrut(30));
        
        // Error message (initially hidden)
        errorLabel = new JLabel("Total players must be 2-4");
        errorLabel.setFont(new Font("Arial", Font.BOLD, 18));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(errorLabel);
        
        add(Box.createVerticalStrut(50));
        
        // Continue button
        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.BOLD, 20));
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setBackground(new Color(0, 200, 200));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);
        add(continueButton);
    } 
    
    public int getHumanPlayers() {
        return (int) humanSpinner.getValue();
    }
    
    public int getBotPlayers() {
        return (int) botSpinner.getValue();
    }
    
    public void showError(boolean show) {
        errorLabel.setVisible(show);
    }
    
    public void setContinueButtonListener(ActionListener listener) {
        continueButton.addActionListener(listener);
    }
}
