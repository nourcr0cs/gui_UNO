/******************* Supposed to be Players Selecting Panel in Main Frame ***********************/
package View.components.panels;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import View.components.buttons.PlayerButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import View.components.buttons.RoundedButton;


public class PlayersSelectPanel extends JPanel {

    private final PlayerButton[] playerButtons = new PlayerButton[4];
    private final RoundedButton startGameButton;
    private final HashMap<Integer, String> playerNames = new HashMap<>();
    private final HashMap<Integer, String> playerTypes = new HashMap<>();

    public PlayersSelectPanel() {
        setLayout(new BorderLayout());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        add(layeredPane, BorderLayout.CENTER);

        // Background
        GridPanel grid = new GridPanel();
        grid.setBounds(0, 0, 1000, 800);
        layeredPane.add(grid, JLayeredPane.DEFAULT_LAYER);

        // Title
        JLabel title = new JLabel("Choose the players", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial Black", Font.BOLD, 50));
        title.setBounds(200, 100, 600, 60);
        layeredPane.add(title, JLayeredPane.PALETTE_LAYER);

        // Player Buttons
        int buttonY = 280;
        int spacing = 30;
        int buttonWidth = 180;
        int startX = (1000 - (4 * buttonWidth + 3 * spacing)) / 2;

        String[] labels = {"you", "human<br>/bot", "human<br>/bot", "human<br>/bot"};

        for (int i = 0; i < 4; i++) {
            PlayerButton button = new PlayerButton(labels[i]);
            button.setBounds(startX + i * (buttonWidth + spacing), buttonY, buttonWidth, 220);
            layeredPane.add(button, JLayeredPane.PALETTE_LAYER);
            playerButtons[i] = button;

            final int index = i;
            if (i != 0) {
                button.addActionListener(e -> showPlayerDialog(index));
            } else {
                playerNames.put(0, "You");
                playerTypes.put(0, "Human");
            }
        }

        // UNO card image (optional decoration)
        UnoCardPanel cardPanel = new UnoCardPanel();
        cardPanel.setBounds(0, 600, 200, 200);
        layeredPane.add(cardPanel, JLayeredPane.PALETTE_LAYER);

        // Start Game button
        startGameButton = new RoundedButton("Start Game");
        startGameButton.setBounds(800, 700, 150, 50);
        layeredPane.add(startGameButton, JLayeredPane.PALETTE_LAYER);
    }

    private void showPlayerDialog(int index) {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        JTextField nameField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Human", "Bot"});
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(typeBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Player " + (index + 1),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String type = (String) typeBox.getSelectedItem();
            if (!name.isEmpty()) {
                playerNames.put(index, name);
                playerTypes.put(index, type);
                playerButtons[index].setText("<html>" + name + "<br>(" + type + ")</html>");
            }
        }
    }

    // Getters for controller
    public RoundedButton getStartGameButton() {
        return startGameButton;
    }

    public PlayerButton[] getPlayerButtons() {
        return playerButtons;
    }

    public HashMap<Integer, String> getPlayerNames() {
        return playerNames;
    }

    public HashMap<Integer, String> getPlayerTypes() {
        return playerTypes;
    }

}
