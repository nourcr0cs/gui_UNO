package view.components.panels;

import javax.swing.*;
import java.awt.*;

public class IconPanel extends JPanel {
    public IconPanel() {
        setPreferredSize(new Dimension(120, 120));
        setMinimumSize(new Dimension(120, 120));
        setMaximumSize(new Dimension(120, 120));
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(new Color(0, 200, 230));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(15, 15, getWidth() - 30, getHeight() - 30, 100, 100);
        
        g2d.setColor(Color.WHITE);
        int diameter = getWidth() / 2;
        g2d.fillOval(getWidth()/2 - diameter/2, getHeight()/2 - diameter/2, diameter, diameter);
    }
}
