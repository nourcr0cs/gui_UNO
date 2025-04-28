package components;

import javax.swing.*;
import java.awt.*;


public class PlayerLabel extends JLabel {
    
    private String playerName;
    private int playerPosition; // 0=bottom, 1=right, 2=top, 3=left
    private boolean isActive;
    
    private static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 20);
    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color ACTIVE_COLOR = new Color(255, 215, 0); 

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
    }
    
    
    public void setCustomColors(Color normalColor, Color activeColor) {
        if (isActive) {
            setForeground(activeColor);
        } else {
            setForeground(normalColor);
        }
    }
}