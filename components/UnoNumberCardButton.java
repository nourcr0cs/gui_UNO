package components;

import java.awt.*;

public class UnoNumberCardButton extends UnoCardButton {
    private final int number;
    
    public UnoNumberCardButton(UnoColor cardColor, int number) {
        super(cardColor);
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("Number must be between 0 and 9");
        }
        this.number = number;
    }
    
    public int getNumber() {
        return number;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (!isFaceUp) {
            return; //no need for any number (baack face)
        }
        
        Graphics2D cmp = (Graphics2D) g.create();
        cmp.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        // Draw central number
        cmp.setColor(cardColor.getColor());
        cmp.setFont(new Font("Arial", Font.BOLD, height / 4));
        FontMetrics fm = cmp.getFontMetrics();
        String numberText = String.valueOf(number);
        int textWidth = fm.stringWidth(numberText);
        cmp.drawString(numberText, (width - textWidth) / 2, height / 2 + fm.getAscent() / 2);
        
        // Draw number in top-left corner
        cmp.setFont(new Font("Arial", Font.BOLD, height/8));
        cmp.setColor(Color.WHITE);
        cmp.drawString(numberText, width/12, height/6);
        
        // Draw number in bottom-right corner (inverted)
        cmp.translate(width, height);
        cmp.rotate(Math.PI);
        cmp.drawString(numberText, width/12, height/6);
        cmp.rotate(-Math.PI);
        cmp.translate(-width, -height);
        
        cmp.dispose();
    }
}