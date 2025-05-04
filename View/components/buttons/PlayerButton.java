package View.components.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import View.components.panels.PlayerIconPanel;

public class PlayerButton extends JPanel {
    private final PlayerIconPanel iconPanel;
    private final JLabel label;
    private ActionListener actionListener;

    public PlayerButton(String text) {
        setLayout(null); // Absolute positioning
        setOpaque(false);

        // Icon panel
        iconPanel = new PlayerIconPanel();
        iconPanel.setBounds(30, 10, 120, 120);
        add(iconPanel);

        // Text label
        label = new JLabel(formatHtml(text), SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 25));
        label.setBounds(0, 140, 180, 60);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);

        // Mouse listener for icon (acts like a button)
        iconPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (actionListener != null) {
                    actionListener.actionPerformed(new ActionEvent(PlayerButton.this, ActionEvent.ACTION_PERFORMED, null));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                iconPanel.setHovered(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                iconPanel.setHovered(false);
            }
        });
    }

    // Sets new label text
    public void setText(String text) {
        label.setText(formatHtml(text));
    }

    // Adds an ActionListener for icon click
    public void addActionListener(ActionListener listener) {
        this.actionListener = listener;
    }

    // Helper to wrap label in HTML formatting
    private String formatHtml(String text) {
        return "<html><center>" + text.replace("<br>", "<br>") + "</center></html>";
    }
}
