package View.components.panels;

import javax.swing.*;

import View.components.buttons.RoundedButton;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class OutroPage extends JPanel {
    private Image backgroundImage;

    public OutroPage(String winnerName, Map<String, List<String>> playersLogs, JFrame frame) {
    	
    	backgroundImage = new ImageIcon("C:/Users/HP/eclipse-workspace/gui_UNO-main/view/images/outro.jpg").getImage(); 
    	this.setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null)
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                setOpaque(false);
            }
        };
        mainPanel.setOpaque(false);
        mainPanel.setMinimumSize(new Dimension(600, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(50, 10, 0, 10);

        JLabel winnerLabel = new JLabel("The Winner is: ");
        JLabel winnerNameLabel = new JLabel(winnerName + "!");
        winnerLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
        winnerLabel.setForeground(Color.WHITE);
        winnerNameLabel.setFont(new Font("Arial Black", Font.BOLD, 50));
        winnerNameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(winnerLabel, gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 15, 35, 15);
        mainPanel.add(winnerNameLabel, gbc);

        // Vertical button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        RoundedButton restartButton = new RoundedButton("Restart");
        RoundedButton logsButton = new RoundedButton("Logs");
        RoundedButton exitButton = new RoundedButton("Exit");

        for (RoundedButton btn : new RoundedButton[]{restartButton, logsButton, exitButton}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(200, 40));
            buttonPanel.add(btn);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        gbc.gridy = 3;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Actions
        restartButton.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "Restart")
        );

        logsButton.addActionListener(e -> {
            frame.setContentPane(new LogsTable(winnerName, playersLogs, frame));
            frame.revalidate();
            frame.repaint();
        });

        exitButton.addActionListener(e -> System.exit(0));
    }
}