package view.components.panels;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TPanel extends JPanel {

    private JPanel controlsPanel;
    private JButton revealBtn;

    public TPanel(JFrame parent) {
        setLayout(new BorderLayout());
        setBackground(new Color(35, 30, 50));
        setPreferredSize(new Dimension(100, 40));

        JLabel iconLabel = new JLabel();
        ImageIcon icon = new ImageIcon("C:\\Users\\Administrateur\\Documents\\ma personalzation\\myicon.png");
        Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaledIcon));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
        add(iconLabel, BorderLayout.WEST);
        /*
         * // Title
         * JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
         * titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
         * titleLabel.setForeground(Color.WHITE);
         * add(titleLabel, BorderLayout.CENTER);
         */

        JLabel titleLabel = new JLabel("", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Ensures proper layout and background if needed

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient effect
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(200, 100, 255),
                        getWidth(), getHeight(), new Color(255, 180, 255));
                g2.setPaint(gradient);
                g2.setFont(getFont());

                FontMetrics fm = g2.getFontMetrics();
                String text = getText();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 4;

                // Glow shadow
                g2.setColor(new Color(120, 60, 180, 80));
                for (int i = 0; i < 3; i++) {
                    g2.drawString(text, x - i, y - i);
                }

                // Draw main gradient text
                g2.setPaint(gradient);
                g2.drawString(text, x, y);

                g2.dispose();
            }
        };
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setOpaque(false); // Let the title bar background show through
        titleLabel.setForeground(Color.WHITE); // fallback color if needed
        titleLabel.setPreferredSize(new Dimension(200, 40));

        add(titleLabel, BorderLayout.CENTER);

        // Reveal button
        revealBtn = new JButton();
        revealBtn.setBackground(new Color(160, 90, 180));
        revealBtn.setPreferredSize(new Dimension(20, 20));
        revealBtn.setBorderPainted(false);
        revealBtn.setFocusPainted(false);
        revealBtn.setOpaque(true);
        revealBtn.setContentAreaFilled(true);
        revealBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        revealBtn.setToolTipText("Show Controls");
        revealBtn.setBorder(BorderFactory.createEmptyBorder());
        revealBtn.setUI(new RoundedButtonUI());

        // Control buttons panel
        controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        controlsPanel.setOpaque(false);
        controlsPanel.setVisible(false);

        controlsPanel.add(createCircleControlButton("–", _ -> parent.setState(Frame.ICONIFIED)));
        controlsPanel.add(createCircleControlButton("◻", _ -> {
            if ((parent.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                parent.setExtendedState(JFrame.NORMAL);
            } else {
                parent.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        }));
        controlsPanel.add(createCircleControlButton("X", _ -> System.exit(0)));
        /*
         * revealBtn.addMouseListener(new MouseAdapter() {
         * public void mouseEntered(MouseEvent e) {
         * controlsPanel.setVisible(true);
         * }
         * });
         */
        // Toggle effect on click
        revealBtn.addActionListener(_ -> {
            controlsPanel.setVisible(!controlsPanel.isVisible());
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.setOpaque(false);
        rightPanel.add(controlsPanel);
        rightPanel.add(revealBtn);
        add(rightPanel, BorderLayout.EAST);
    }

    public void backgroundColor(String theme) {
        if (theme.equalsIgnoreCase("black")) {
            setBackground(new Color(0, 0, 0));
        }
        if (theme.equalsIgnoreCase("dark blue")) {
            setBackground(new Color(0, 0, 139));
        }
    }

    private JButton createCircleControlButton(String label, ActionListener action) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(100, 50, 120));
        btn.setPreferredSize(new Dimension(20, 20));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setUI(new RoundedButtonUI());
        btn.addActionListener(action);
        return btn;
    }

    static class RoundedButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            c.setOpaque(false);
            c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = c.getWidth();
            int height = c.getHeight();

            g2.setColor(b.getModel().isPressed() ? b.getBackground().darker()
                    : b.getModel().isRollover() ? b.getBackground().brighter()
                            : b.getBackground());

            g2.fillOval(0, 0, width, height);

            FontMetrics fm = g2.getFontMetrics();
            String text = b.getText();
            int x = (width - fm.stringWidth(text)) / 2;
            int y = (height + fm.getAscent()) / 2 - 3;
            g2.setColor(b.getForeground());
            g2.drawString(text, x, y);
            g2.dispose();
        }
    }

    private Image backgroundImage;

    // Custom method to set the background image
    public void setBackgroundImage(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
        repaint(); // Ask Swing to repaint the panel with the new image
    }

    // Override paintComponent to draw the image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Always call super first
        if (backgroundImage != null) {
            // Draw the image scaled to fit the panel size
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

// i want that when we call this fuction the theme that i set it doesn't apeare
// at all ,
// and the size of the image will be
