package view.components.labels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.io.File;

public class AnimatedUnoLogo extends JLabel {
    
    private Timer animationTimer;
    private int originalY;
    private double animationAngle = 0;
    private int animationAmplitude = 10; 
    private int animationSpeed = 10;
    private Image logoImage;
    
    public AnimatedUnoLogo() {
        // Try multiple approaches to load the UNO logo image
        try {
            // Approach 1: Try using class resource (relative to class location)
            URL imageUrl = getClass().getResource("/view/images/uno-logo.png");
            
            // Approach 2: Try looking in the root of the classpath
            if (imageUrl == null) {
                imageUrl = getClass().getResource("/menu-icon.png");
            }
            
            // Approach 3: Try looking relative to the current class
            if (imageUrl == null) {
                imageUrl = getClass().getResource("menu-icon.png");
            }
            
            // Approach 4: Try checking if file exists directly (for development environments)
            if (imageUrl == null) {
                File file = new File("src/components/labels/menu-icon.png");
                if (file.exists()) {
                    imageUrl = file.toURI().toURL();
                }
            }
            
            // If we found the image, load it
            if (imageUrl != null) {
                logoImage = new ImageIcon(imageUrl).getImage();
                System.out.println("Successfully loaded UNO logo from: " + imageUrl);
            } else {
                System.err.println("WARNING: Could not find UNO logo image. Make sure menu-icon.png exists in the correct location.");
            }
        } catch (Exception e) {
            System.err.println("Error loading UNO logo image: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Set up animation timer
        animationTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAnimation();
            }
        });
    }
    
    public void startAnimation() {
        originalY = getY();
        animationTimer.start();
    }
    
    public void stopAnimation() {
        animationTimer.stop();
        setLocation(getX(), originalY);
    }
    
    private void updateAnimation() {
        animationAngle += 0.1;
        int newY = originalY + (int)(Math.sin(animationAngle) * animationAmplitude);
        
        setLocation(getX(), newY);
        repaint();
    }
    
    public void setAnimationAmplitude(int amplitude) {
        this.animationAmplitude = amplitude;
    }
    
    public void setAnimationSpeed(int speed) {
        if (speed > 0) {
            animationTimer.setDelay(150 - speed * 10);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (logoImage != null) {
            // Calculate the centered position for the image
            int imageWidth = Math.min(getWidth(), 350);
            int imageHeight = Math.min(getHeight(), 150);
            
            // Draw the image centered
            int x = (getWidth() - imageWidth) / 2;
            int y = (getHeight() - imageHeight) / 2;
            g2d.drawImage(logoImage, x, y, imageWidth, imageHeight, this);
        } else {
            // Fallback if image can't be loaded - draw placeholder text with UNO colors
            g2d.setFont(new Font("Arial", Font.BOLD, 48));
            g2d.setColor(Color.RED);
            FontMetrics fm = g2d.getFontMetrics();
            String text = "UNO";
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() - textHeight) / 2 + fm.getAscent();
            
            // Draw text with outline
            g2d.setColor(Color.YELLOW);
            g2d.fillRoundRect(x-10, y-fm.getAscent(), textWidth+20, textHeight+5, 15, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x-10, y-fm.getAscent(), textWidth+20, textHeight+5, 15, 15);
            g2d.setColor(Color.RED);
            g2d.drawString(text, x, y);
        }
        
        g2d.dispose();
    }
    
    @Override
    public Dimension getPreferredSize() {
        if (logoImage != null) {
            // Set preferred size based on aspect ratio of the image
            int width = 300; // You can adjust this base width
            int height = width * logoImage.getHeight(this) / logoImage.getWidth(this);
            return new Dimension(width, height);
        } else {
            return new Dimension(300, 120); // Default size if no image
        }
    }
}