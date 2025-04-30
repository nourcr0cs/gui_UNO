package view.components.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class HowToPlayButton extends JPanel {
    
    private boolean hover = false;
    private final JLabel textLabel;
    private final InfoIconPanel iconPanel;
    private final Color defaultTextColor = new Color(220, 220, 220);
    private final Color hoverTextColor = Color.WHITE;
    private final HowToPlayButtonListener listener;
    
    
    //Interface for button click events
    public interface HowToPlayButtonListener {
        void onButtonClicked();
    }
    
   
    public HowToPlayButton(HowToPlayButtonListener listener) {
        this.listener = listener;
        
        setOpaque(false);
        setLayout(new BorderLayout(5, 0));
        
        // Create the info icon panel
        iconPanel = new InfoIconPanel();
        
        // Create the text label
        textLabel = new JLabel("How to Play") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                if (hover) {
                    // Draw underline when hovering
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    g2d.setColor(getForeground());
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(getText());
                    
                    int y = getHeight() - 3;
                    g2d.drawLine(0, y, textWidth, y);
                    
                    g2d.dispose();
                }
            }
        };
        
        textLabel.setForeground(defaultTextColor);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        // Add mouse listeners for hover effects
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setHover(true);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setHover(false);
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (HowToPlayButton.this.listener != null) {
                    HowToPlayButton.this.listener.onButtonClicked();
                }
            }
        };
        
        addMouseListener(mouseAdapter);
        textLabel.addMouseListener(mouseAdapter);
        iconPanel.addMouseListener(mouseAdapter);
        
        add(iconPanel, BorderLayout.WEST);
        add(textLabel, BorderLayout.CENTER);
    }
    
   
    private void setHover(boolean hover) {
        this.hover = hover;
        textLabel.setForeground(hover ? hoverTextColor : defaultTextColor);
        setCursor(hover ? new Cursor(Cursor.HAND_CURSOR) : new Cursor(Cursor.DEFAULT_CURSOR));
        iconPanel.setHover(hover);
        repaint();
    }
    
    
    private class InfoIconPanel extends JPanel {
        private boolean hover = false;
        
        public InfoIconPanel() {
            setOpaque(false);
            // Increase the size slightly to ensure the icon has enough space
            setPreferredSize(new Dimension(18, 18));
        }
        
        public void setHover(boolean hover) {
            this.hover = hover;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Get panel dimensions
            int width = getWidth();
            int height = getHeight();
            
            // Calculate icon size and position for perfect centering
            int size = Math.min(width, height) - 2; // Subtract 2 for buffer
            int x = (width - size) / 2;
            int y = (height - size) / 2;
            
            // Set the color based on hover state
            g2d.setColor(hover ? hoverTextColor : defaultTextColor);
            
            // Draw properly centered circle with a slightly thicker stroke
            g2d.setStroke(new BasicStroke(1.0f));
            g2d.drawOval(x, y, size, size);
            
            int dotX = x + size / 2 - 1;
            int dotY = y + size / 4;
            int dotSize = Math.max(2, size / 6);
            
            g2d.fillOval(dotX, dotY, dotSize, dotSize);
            
            int bodyWidth = Math.max(2, size / 6);
            int bodyHeight = size / 3;
            int bodyX = x + size / 2 - bodyWidth / 2;
            int bodyY = y + size / 2;
            
            g2d.fillRoundRect(bodyX, bodyY, bodyWidth, bodyHeight, 2, 2);
            
            g2d.dispose();
        }
    }
}