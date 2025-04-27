package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AnimatedUnoLabel extends JLabel {
    
    private Timer animationTimer;
    private int originalY;
    private double animationAngle = 0;
    private int animationAmplitude = 10; 
    private int animationSpeed = 10; 
    
   
    public AnimatedUnoLabel() {
        super("UNO", SwingConstants.CENTER);
        
        setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 90));
        
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
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        FontMetrics fm = g2d.getFontMetrics(getFont());
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() - textHeight) / 2 + fm.getAscent();
        
        g2d.setFont(getFont());
        g2d.setColor(new Color(0, 0, 0, 250)); 
        
        int shadowOffset = 4;
        for (int i = 1; i <= 3; i++) {
            g2d.drawString(getText(), x + shadowOffset, y + shadowOffset + i);
        }
        
        g2d.setColor(Color.WHITE);
        for (int i = 1; i <= 2; i++) {
            g2d.drawString(getText(), x - i, y);     
            g2d.drawString(getText(), x + i, y);     
            g2d.drawString(getText(), x, y - i);     
            g2d.drawString(getText(), x, y + i);     
        }
        
        g2d.setColor(new Color(255, 204, 20)); // UNO Yellow
        g2d.drawString(getText(), x, y);
        
        g2d.dispose();
    }
}