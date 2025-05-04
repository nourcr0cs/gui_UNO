package View.components.panels;

import javax.swing.JPanel;
import java.awt.*;


public class GridPanel extends JPanel {

    private static final Color UNO_TEAL = new Color(23, 91, 95);
    private static final Color GRID_LINE_COLOR = new Color(43, 96, 100);
    private static final int GRID_SIZE = 110;
        
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D cmp = (Graphics2D) g;

            int width = getWidth();
            int height = getHeight();

            cmp.setColor(UNO_TEAL);
            cmp.fillRect(0, 0, width, height);

            cmp.setColor(GRID_LINE_COLOR);
            cmp.setStroke(new BasicStroke(1));

            for (int y = 0; y <= height; y += GRID_SIZE) {
                cmp.drawLine(0, y, width, y);
            }

            for (int x = 0; x <= width; x += GRID_SIZE) {
                cmp.drawLine(x, 0, x, height);
            }
        }
    
}
