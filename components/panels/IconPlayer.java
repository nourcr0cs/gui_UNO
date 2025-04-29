package components.panels;

import javax.swing.*;
import java.awt.*;

public class IconPlayer extends JPanel {
    private final Color BUTTON_COLOR = new Color(0, 180, 200);
    private final Color HOVER_COLOR = new Color(0, 200, 220);
    private boolean isHovered = false;

    public IconPlayer() {
        setOpaque(false);
    }

    public void setHovered(boolean hovered) {
        isHovered = hovered;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Dessiner le bouton sous forme de rectangle arrondi
        g2d.setColor(isHovered ? HOVER_COLOR : BUTTON_COLOR);
        g2d.fillRoundRect(0, 0, width, height, 30, 30);

        // Dessiner le cercle de profil (encadré)
        int profileSize = Math.min(width, height) - 20;
        int profileX = (width - profileSize) / 2;
        int profileY = (height - profileSize) / 2;
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(profileX, profileY, profileSize, profileSize);

        // Dessiner la tête (cercle)
        int headSize = profileSize / 3;
        int headX = (width - headSize) / 2;
        int headY = height / 5;
        g2d.setColor(Color.WHITE);
        g2d.fillOval(headX, headY, headSize, headSize);

        // Corps
        int bodyWidth = width / 2;
        int bodyHeight = height / 3;
        int bodyX = (width - bodyWidth) / 2;
        int bodyY = headY + headSize + 10;
        g2d.setColor(Color.WHITE);
        g2d.fillArc(bodyX, bodyY, bodyWidth, bodyHeight, 0, 180);
    }
}
