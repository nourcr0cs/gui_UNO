package components.panels;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class CursorLabel extends JFrame {
    
    public CursorLabel() {
        setTitle("Custom Cursor Label");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        
        // Create a custom label with the cursor icon
        JLabel cursorLabel = new CursorIconLabel();
        cursorLabel.setPreferredSize(new Dimension(100, 100));
        
        // Add the label to the frame
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        add(cursorLabel);
    }
    
    // Custom JLabel that paints a cursor icon
    static class CursorIconLabel extends JLabel {
        
        public CursorIconLabel() {
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();
            
            // Scale the cursor to fit the label
            int cursorSize = Math.min(width, height);
            
            // Create the cursor shape
            Path2D cursorShape = new Path2D.Double();
            
            // Arrow points
            double[] xPoints = {0, 0, 0.4, 0.2, 0.4, 0.3, 0.65};
            double[] yPoints = {0, 0.65, 0.3, 0.4, 0.4, 0.2, 0};
            
            // Scale points to the size of the label
            for (int i = 0; i < xPoints.length; i++) {
                xPoints[i] *= cursorSize;
                yPoints[i] *= cursorSize;
            }
            
            // Create the cursor path
            cursorShape.moveTo(xPoints[0], yPoints[0]);
            for (int i = 1; i < xPoints.length; i++) {
                cursorShape.lineTo(xPoints[i], yPoints[i]);
            }
            cursorShape.closePath();
            
            // Center the cursor in the label
            g2d.translate((width - cursorSize) / 2, (height - cursorSize) / 2);
            
            // Draw white border (outline)
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(3f));
            g2d.draw(cursorShape);
            
            // Fill with black
            g2d.setColor(Color.BLACK);
            g2d.fill(cursorShape);
            
            g2d.dispose();
        }
    }
    
    public static void main(String[] args) {
        // Run the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            CursorLabel app = new CursorLabel();
            app.setVisible(true);
        });
    }
}