package components.labels;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class PlayerLabel extends JLabel {
    
    private String playerName;
    private int playerPosition; // 0=bottom, 1=right, 2=top, 3=left
    private boolean isActive;
    
    private static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 20);
    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color ACTIVE_COLOR = new Color(220, 20, 60); 

    public PlayerLabel(String playerName, int playerPosition) {
        this.playerName = playerName;
        this.playerPosition = playerPosition;
        this.isActive = false;
        
        setText(playerName);
        setFont(DEFAULT_FONT);
        setForeground(DEFAULT_COLOR);
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(false);
    }
    
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        setText(playerName);
        repaint();
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public int getPlayerPosition() {
        return playerPosition;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
        setForeground(active ? ACTIVE_COLOR : DEFAULT_COLOR);
        setFont(new Font(getFont().getName(), active ? Font.BOLD : Font.PLAIN, getFont().getSize()));
        repaint();
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setFontSize(int size) {
        setFont(new Font(getFont().getName(), getFont().getStyle(), size));
        repaint();
    }
    
    public void setCustomColors(Color normalColor, Color activeColor) {
        if (isActive) {
            setForeground(activeColor);
        } else {
            setForeground(normalColor);
        }
        repaint();
    }


 
    



    @Override
    /*protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        double rotationAngle = 0;
        switch (getPlayerPosition()) {
            case 0: 
                rotationAngle = 0;
                break;
            case 1: 
                rotationAngle = Math.toRadians(270);
                break;
            case 2: 
                rotationAngle = Math.toRadians(180);
                break;
            case 3: 
                rotationAngle = Math.toRadians(90);
                break;
        }
        
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        
        
        AffineTransform transform = new AffineTransform();
        transform.rotate(rotationAngle, centerX, centerY);
        g2d.setTransform(transform);
        
        FontMetrics fm = g2d.getFontMetrics(getFont());
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + fm.getAscent()) / 2;
        
        g2d.setColor(isActive ? ACTIVE_COLOR : DEFAULT_COLOR);
        g2d.setFont(getFont());
        g2d.drawString(getText(), x, y);
        
        g2d.dispose();
    }*/
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                // turn on anti-aliasing
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                //angle based on position
                double angle;
                switch (getPlayerPosition()) {
                    case 1: angle = Math.toRadians(270); break;   
                    case 2: angle = Math.toRadians(180);   break; 
                    case 3: angle = Math.toRadians(90);    break; 
                    default: angle = 0;                          
                }

                int cx = getWidth()  / 2 ;
                int cy = getHeight() / 2 ;
                g2.rotate(angle, cx - 6, cy);

                
                super.paintComponent(g2);
            } finally {
                g2.dispose();
            }
        }

}