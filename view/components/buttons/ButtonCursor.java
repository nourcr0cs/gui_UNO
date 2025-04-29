package view.components.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A component that displays an animated arrow pointing to a button
 * to indicate it can be clicked.
 */
public class ButtonCursor extends JPanel {
    private static final int ANIMATION_DELAY = 40;
    private static final int BOUNCE_RANGE = 10;
    private static final Color ARROW_COLOR = new Color(255, 255, 255, 240);
    private static final Color TEXT_COLOR = new Color(255, 255, 255, 255);
    
    private Timer animationTimer;
    private int bounceOffset = 0;
    private boolean bounceDirection = true; // true = moving right, false = moving left
    private int bounceCounter = 0;
    private float pulseAlpha = 1.0f;
    
    public ButtonCursor() {
        setOpaque(false);
        setPreferredSize(new Dimension(80, 80));
        
        animationTimer = new Timer(ANIMATION_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAnimation();
                repaint();
            }
        });
    }
    
    public void startAnimation() {
        if (!animationTimer.isRunning()) {
            animationTimer.start();
        }
    }
    
    public void stopAnimation() {
        if (animationTimer.isRunning()) {
            animationTimer.stop();
        }
    }
    
    private void updateAnimation() {
        // Create a bouncing effect
        if (bounceDirection) {
            bounceOffset++;
            if (bounceOffset >= BOUNCE_RANGE) {
                bounceDirection = false;
            }
        } else {
            bounceOffset--;
            if (bounceOffset <= 0) {
                bounceDirection = true;
                // Optional: Add a pause at the leftmost position
                bounceCounter++;
                if (bounceCounter % 3 == 0) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        
        // Update pulse alpha
        pulseAlpha = 0.7f + 0.3f * (float)Math.sin(System.currentTimeMillis() / 300.0);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create(); // Create a copy to avoid affecting other components
        
        try {
            // Enable anti-aliasing for smoother drawing
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // Draw the animated arrow and text
            drawClickText(g2d);
            drawArrow(g2d);
        } finally {
            g2d.dispose(); // Clean up the graphics context
        }
    }
    
    private void drawArrow(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();
        
        // Save original composite
        Composite originalComposite = g2d.getComposite();
        
        // Apply pulsing effect only to the arrow
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, pulseAlpha));
        
        // Set arrow color
        g2d.setColor(ARROW_COLOR);
        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        // Calculate arrow position with bounce effect
        int arrowX = width / 2 - 20 + bounceOffset;
        int arrowY = height / 2;
        
        // Draw arrow body
        g2d.drawLine(arrowX, arrowY, arrowX + 30, arrowY);
        
        // Draw arrow head (pointing toward the button)
        int[] xPoints = {arrowX + 40, arrowX + 20, arrowX + 20};
        int[] yPoints = {arrowY, arrowY - 15, arrowY + 15};
        g2d.fillPolygon(xPoints, yPoints, 3);
        
        // Restore original composite
        g2d.setComposite(originalComposite);
    }
    
    private void drawClickText(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();
        
        // Always draw text at full opacity
        g2d.setColor(TEXT_COLOR);
        
        // Use a bolder, more visible font
        Font font = new Font("Arial", Font.BOLD, 14);
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);
        
        String text = "CLICK";
        int textX = (width / 2) - 45 - metrics.stringWidth(text);
        int textY = height / 2 + metrics.getAscent() / 2 - 2;
        
        // Add a subtle glow effect to make text more visible
        g2d.setColor(new Color(0, 0, 0, 120));
        g2d.drawString(text, textX + 1, textY + 1);
        
        // Draw the actual text
        g2d.setColor(TEXT_COLOR);
        g2d.drawString(text, textX, textY);
    }
}