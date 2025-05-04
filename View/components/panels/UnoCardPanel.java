package View.components.panels;

import javax.swing.*;
import java.awt.*;


 public class UnoCardPanel extends JPanel {
    public UnoCardPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Position ajustée pour correspondre à l'image
        int x = -10;
        int y = 80;
        int width = 170;
        int height = 240;
        int arcWidth = 20;
        int arcHeight = 20;
        
        // Rotation légère pour effet "volatile"
        g2d.rotate(Math.toRadians(-15), x + width/2, y + height/2);

        // Carte noire principale
        g2d.setColor(Color.BLACK);
        g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight);

        // Bordure blanche
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight);

        // Ovale blanc central
        int ovalWidth = width - 40;
        int ovalHeight = height / 2;
        int ovalX = x + (width - ovalWidth) / 2;
        int ovalY = y + (height - ovalHeight) / 2 - 10;
        g2d.setColor(Color.WHITE);
        g2d.fillOval(ovalX, ovalY, ovalWidth, ovalHeight);
        
        // Texte "UNO" dans l'ovale
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        FontMetrics fm = g2d.getFontMetrics();
        String unoText = "UNO";
        int textWidth = fm.stringWidth(unoText);
        int textX = ovalX + (ovalWidth - textWidth) / 2;
        int textY = ovalY + ovalHeight/2 + fm.getAscent()/2 - 5;
        g2d.drawString(unoText, textX, textY);
    }
}
