package components;

import javax.swing.*;
import java.awt.*;

public class UnoBackgroundGrid extends JFrame {
    
    // The teal/turquoise color from the UNO background
    private static final Color UNO_TEAL = new Color(33, 86, 90);
    private static final Color GRID_LINE_COLOR = new Color(43, 96, 100);
    private static final int GRID_SIZE = 110; // Size of each grid cell
    
    public UnoBackgroundGrid() {
        setTitle("UNO Background Grid");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        
        add(new GridPanel());
        
        setVisible(true);
    }
    
    private class GridPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            int width = getWidth();
            int height = getHeight();
            
            // Fill the background with the teal color
            g2d.setColor(UNO_TEAL);
            g2d.fillRect(0, 0, width, height);
            
            // Draw the grid lines
            g2d.setColor(GRID_LINE_COLOR);
            g2d.setStroke(new BasicStroke(1));
            
            // Draw horizontal grid lines
            for (int y = 0; y <= height; y += GRID_SIZE) {
                g2d.drawLine(0, y, width, y);
            }
            
            // Draw vertical grid lines
            for (int x = 0; x <= width; x += GRID_SIZE) {
                g2d.drawLine(x, 0, x, height);
            }
        }
    }
    
    public static void main(String[] args) {
        // Run the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new UnoBackgroundGrid();
        });
    }
}