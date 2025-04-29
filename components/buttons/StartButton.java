package components.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class StartButton extends JButton {
    
    private final Color normalColor = Color.WHITE;
    private final Color hoverColor = new Color(255, 215, 0); // Gold color
    private final StartButtonListener listener;
    private boolean hover = false;
    
    public interface StartButtonListener {
        void onButtonClicked();
    }
    
    public StartButton(String text, StartButtonListener listener) {
        super(text);
        setHorizontalAlignment(SwingConstants.CENTER);
        this.listener = listener;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
        setFocusPainted(false);
        setContentAreaFilled(false);
        
        setFont(new Font("Arial", Font.BOLD, 55));
        setForeground(normalColor);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (StartButton.this.listener != null) {
                    StartButton.this.listener.onButtonClicked();
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                setForeground(hoverColor);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                setForeground(normalColor);
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        if (hover) {
            RoundRectangle2D roundRect = new RoundRectangle2D.Float(0, 0, width - 1, height - 1, 20, 20);
            
            Color baseColor = new Color(100, 162, 232);
            Color baseColorBrighter = new Color(33, 86, 90);
            Color baseColorDarker = new Color(33, 86, 90);
            
            // Button gradient background
            GradientPaint gradient = new GradientPaint(
                0, 0, baseColorBrighter,
                0, height, baseColorDarker
            );
            
            g2d.setPaint(gradient);
            g2d.fill(roundRect);
            
            // Add glossy highlight effect
            GradientPaint glossGradient = new GradientPaint(
                0, 0, new Color(255, 255, 255, 180),
                0, height / 2, new Color(255, 255, 255, 0)
            );
            
            g2d.setPaint(glossGradient);
            g2d.fill(new RoundRectangle2D.Float(2, 2, width - 5, height / 2 - 2, 18, 18));
            
            g2d.setColor(baseColorDarker.darker());
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(roundRect);
            
            FontMetrics fm = g2d.getFontMetrics(getFont());
            int textWidth = fm.stringWidth(getText());
            int textHeight = fm.getHeight();
            int textX = (width - textWidth) / 2;
            int textY = (height - textHeight) / 2 + fm.getAscent();
            
            // Text shadow
            g2d.setColor(new Color(0, 0, 0, 60));
            g2d.setFont(getFont());
            g2d.drawString(getText(), textX + 2, textY + 2);
            
            // Actual text
            g2d.setColor(getForeground());
            g2d.drawString(getText(), textX, textY);
        } else {
            // Regular state - just the text
            FontMetrics fm = g2d.getFontMetrics(getFont());
            int textX = (width - fm.stringWidth(getText())) / 2;
            int textY = ((height - fm.getHeight()) / 2) + fm.getAscent();
            
            g2d.setColor(getForeground());
            g2d.setFont(getFont());
            g2d.drawString(getText(), textX, textY);
        }
        
        g2d.dispose();
    }
    
    @Override
    public Dimension getPreferredSize() {
        FontMetrics fm = getFontMetrics(getFont());
        int width = fm.stringWidth(getText()) + 40; 
        int height = fm.getHeight() + 20;
        return new Dimension(width, height);
    }
}