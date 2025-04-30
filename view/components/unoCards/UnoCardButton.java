
package view.components.unoCards;


import javax.swing.*;

import view.components.UnoColor;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.AffineTransform;

public class UnoCardButton extends JButton {
    private static final int ARC_WIDTH = 20;
    private static final int ARC_HEIGHT = 20;
    private static final int BORDER_WIDTH = 2;
    
    protected UnoColor cardColor;
    protected boolean isFaceUp;
    private final Dimension cardSize;
    
    protected double rotation = 0;

    public UnoCardButton(UnoColor cardColor, Dimension cardSize) {
        this.cardColor = cardColor;
        this.cardSize = cardSize;
        this.isFaceUp = true;
        setPreferredSize(cardSize);
        
        // Configure button appearance
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        
        // Ensure that the button correctly captures all mouse events
        // even when rotated
        setOpaque(false);
    }
    
    public UnoCardButton(UnoColor cardColor) {
        this(cardColor, new Dimension(50, 110)); 
    }
    
    public void setFaceUp(boolean faceUp) {
        this.isFaceUp = faceUp;
        repaint();
    }
    
    public boolean isFaceUp() {
        return isFaceUp;
    }
    
    public void setCardColor(UnoColor cardColor) {
        this.cardColor = cardColor;
        repaint();
    }
    
    public UnoColor getCardColor() {
        return cardColor;
    }

    public void setRotation(double degrees) {
        this.rotation = degrees;
        repaint();
    }
    
    public double getRotation() {
        return rotation;
    }
    
    @Override
    public void paint(Graphics g) {
        // Create a new graphics context to apply the rotation
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Setup the transform for rotation around the center
        int width = getWidth();
        int height = getHeight();
        
        // Save original transform
        AffineTransform originalTransform = g2d.getTransform();
        
        // Apply rotation around center
        g2d.rotate(Math.toRadians(rotation), width / 2.0, height / 2.0);
        
        // Paint all components with rotation applied
        super.paint(g2d);
        
        // Restore original transform
        g2d.setTransform(originalTransform);
        
        g2d.dispose();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        if (isFaceUp) {
            g2d.setColor(Color.WHITE);
            g2d.fill(new RoundRectangle2D.Double(0, 0, width, height, ARC_WIDTH, ARC_HEIGHT));

            g2d.setColor(cardColor.getColor());
            g2d.fill(new RoundRectangle2D.Double(5, 5, width - 10, height - 10, ARC_WIDTH, ARC_HEIGHT));
            
            g2d.setColor(Color.WHITE);
            
            AffineTransform old = g2d.getTransform();
            g2d.rotate(Math.toRadians(25), width / 2.0, height / 2.0);

            //white oval
            g2d.setColor(Color.WHITE);
            double ovalWidth = width * 0.75;
            double ovalHeight = height * 0.7;
            g2d.fill(new Ellipse2D.Double((width - ovalWidth) / 2, (height - ovalHeight) / 2, ovalWidth, ovalHeight));

            //Restore original transform
            g2d.setTransform(old);
                        
            //number area
            g2d.setColor(Color.WHITE);
            g2d.fill(new Ellipse2D.Double(width * 0.25, height * 0.3, width * 0.5, height * 0.4));
            
        } else {
            g2d.setColor(Color.WHITE);
            g2d.fill(new RoundRectangle2D.Double(0, 0, width, height, ARC_WIDTH, ARC_HEIGHT));

            g2d.setColor(Color.BLACK);
            g2d.fill(new RoundRectangle2D.Double(5, 5, width - 10, height - 10, ARC_WIDTH, ARC_HEIGHT));
            
            //Draw red oval
            g2d.setColor(Color.WHITE);
            AffineTransform old = g2d.getTransform();

            //Rotation param
            g2d.rotate(Math.toRadians(25), width / 2.0, height / 2.0);

            //red oval
            g2d.setColor(Color.RED);
            double ovalWidth = width * 0.75;
            double ovalHeight = height * 0.7;
            g2d.fill(new Ellipse2D.Double((width - ovalWidth) / 2, (height - ovalHeight) / 2, ovalWidth, ovalHeight));

            g2d.setTransform(old);
            
            drawUnoLogo(g2d, width, height);
        }
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(BORDER_WIDTH));
        g2d.draw(new RoundRectangle2D.Double(BORDER_WIDTH / 2, BORDER_WIDTH / 2, 
                width - BORDER_WIDTH, height - BORDER_WIDTH, ARC_WIDTH, ARC_HEIGHT));
        
        g2d.dispose();
    }
   
    private void drawUnoLogo(Graphics2D g2d, int width, int height) {
        int fontSize = height / 6;
        Font unoFont = new Font("Arial Black", Font.BOLD, fontSize);
        
        String unoText = "UNO";
        
        FontMetrics fm = g2d.getFontMetrics(unoFont);
        int textWidth = fm.stringWidth(unoText);
        int textHeight = fm.getHeight();
        int textX = (width - textWidth) / 2;
        int textY = height / 2 + fm.getAscent() / 2;
        
        AffineTransform originalTransform = g2d.getTransform();
        g2d.rotate(Math.toRadians(-20), width / 2.0, height / 2.0);
        
        g2d.setFont(unoFont);
        
        g2d.setColor(Color.BLACK);
        for (int offsetX = -3; offsetX <= 3; offsetX++) {
            for (int offsetY = -3; offsetY <= 3; offsetY++) {
                if (Math.abs(offsetX) > 1 || Math.abs(offsetY) > 1) { 
                    g2d.drawString(unoText, textX + offsetX, textY + offsetY);
                }
            }
        }
        
        // Draw the main yellow text
        g2d.setColor(new Color(255, 215, 0)); 
        g2d.drawString(unoText, textX, textY);
        
        g2d.setColor(new Color(255, 255, 220, 128));
        g2d.drawString(unoText, textX - 1, textY - 1);
        
        g2d.setTransform(originalTransform);
    }
}