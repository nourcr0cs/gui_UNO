package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Custom button component for the UNO game start screen
 */
public class StartButton extends JLabel {
    
    private final Color normalColor = Color.WHITE;
    private final Color hoverColor = new Color(220, 220, 255);
    private final StartButtonListener listener;
    
    public interface StartButtonListener {
        void onButtonClicked();
    }
    
   
    public StartButton(String text, StartButtonListener listener) {
        super(text, SwingConstants.CENTER);
        this.listener = listener;
        
        setFont(new Font("Arial", Font.BOLD, 48));
        setForeground(normalColor);
        
        //make it clickable
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        //mouse interactions
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (StartButton.this.listener != null) {
                    StartButton.this.listener.onButtonClicked();
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(hoverColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(normalColor);
            }
        });
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        g2d.setColor(getForeground());
        g2d.setFont(getFont());
        g2d.drawString(getText(), 0, getFont().getSize());
        
        g2d.dispose();
    }
}