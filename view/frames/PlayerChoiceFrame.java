package view.frames;

import javax.swing.*;

import view.components.panels.PlayerCountPanel;

import java.awt.*;;

public class PlayerChoiceFrame extends JFrame {
        private PlayerCountPanel countPanel;
        
        public PlayerChoiceFrame() {
            setTitle("UNO Game Setup");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 600);
            setLocationRelativeTo(null);
            
            countPanel = new PlayerCountPanel();
            countPanel.setContinueButtonListener(e -> {
                int humans = countPanel.getHumanPlayers();
                int bots = countPanel.getBotPlayers();
                
                if ((humans + bots) >= 2 && (humans + bots) <= 4) {
                    // Open player selection frame
                    PlayerSelectionFrame selectionFrame = new PlayerSelectionFrame(humans, bots);
                    selectionFrame.setVisible(true);
                    dispose(); // Close this frame
                } else {
                    countPanel.showError(true);
                }
            });
            
            add(countPanel);
        }
    }
