package view.components.unoCards;
import java.awt.*;

import view.components.UnoColor;

public class UnoWildCardButton extends UnoCardButton {

    public enum WildType {
        WILD, WILD_DRAW_FOUR
    }

    private final WildType wildType;
    private UnoColor selectedColor;
    
    public UnoWildCardButton(WildType wildType) {
        super(UnoColor.BLACK);
        this.wildType = wildType;
        this.selectedColor = null;
    }
    
    public WildType getWildType() {
        return wildType;
    }
    
    public void setSelectedColor(UnoColor color) {
        if (color != UnoColor.BLACK) {
            this.selectedColor = color;
            repaint();
        }
    }
    
    public UnoColor getSelectedColor() {
        return selectedColor;
    }
    

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        int w = getWidth(), h = getHeight();
        g2.translate(w/2.0, h/2.0);
        g2.rotate(Math.toRadians(rotation));
        g2.translate(-w/2.0, -h/2.0);

        super.paint(g2);
        g2.dispose();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (!isFaceUp) {
            return;
        }
        
        Graphics2D cmp = (Graphics2D) g.create();
        cmp.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        if (wildType == WildType.WILD) {
            drawWildCard(cmp, width, height);
        } else {
            drawWildDrawFour(cmp, width, height);
        }
        
        cmp.dispose();
    }
    
    private void drawWildCard(Graphics2D cmp, int width, int height) {
        int centerX = width / 2;
        int centerY = height / 2;
        
        int colorCircleSize = Math.min(width, height) / 2;
        int circleRadius = colorCircleSize / 2;
        
        // Draw colored quadrants
        // Red quadrant (top-left)
        cmp.setColor(UnoColor.RED.getColor());
        cmp.fillArc(centerX - circleRadius, centerY - circleRadius, 
                   colorCircleSize, colorCircleSize, 90, 90);
        
        cmp.setColor(UnoColor.BLUE.getColor());
        cmp.fillArc(centerX - circleRadius, centerY - circleRadius, 
                   colorCircleSize, colorCircleSize, 0, 90);
        
        cmp.setColor(UnoColor.YELLOW.getColor());
        cmp.fillArc(centerX - circleRadius, centerY - circleRadius, 
                   colorCircleSize, colorCircleSize, 180, 90);
        
        cmp.setColor(UnoColor.GREEN.getColor());
        cmp.fillArc(centerX - circleRadius, centerY - circleRadius, 
                   colorCircleSize, colorCircleSize, 270, 90);
        
        cmp.setColor(Color.BLACK);
        cmp.setStroke(new BasicStroke(1f));
        cmp.drawOval(centerX - circleRadius, centerY - circleRadius, 
                    colorCircleSize, colorCircleSize);
        
        
        int cornerRadius = height / 20;
        drawMiniColorCircle(cmp, width/12 +3, height/12 - 1 , cornerRadius);
        
        cmp.translate(width, height);
        cmp.rotate(Math.PI);
        drawMiniColorCircle(cmp, width/12 + 3 , height/12 - 1, cornerRadius);
        cmp.rotate(-Math.PI);
        cmp.translate(-width, -height);
    }
    
    private void drawWildDrawFour(Graphics2D cmp, int width, int height) {
        int centerX = width / 2;
        int centerY = height / 2;
        
        //card dimensions
        int cardWidth = width / 6;
        int cardHeight = height / 5;
        int spacing = cardWidth / 3;
        
        if (selectedColor == null) {
            // Yellow card (top)
            cmp.setColor(UnoColor.YELLOW.getColor());
            drawCardWithShadow(cmp, centerX - cardWidth/2, centerY - cardHeight - spacing/2, 
                             cardWidth, cardHeight);
            
            // Blue card (left)
            cmp.setColor(UnoColor.BLUE.getColor());
            drawCardWithShadow(cmp, centerX - cardWidth - spacing, centerY - cardHeight/2, 
                             cardWidth, cardHeight);
            
            // Red card (right)
            cmp.setColor(UnoColor.RED.getColor());
            drawCardWithShadow(cmp, centerX + spacing/2, centerY - cardHeight/2, 
                             cardWidth, cardHeight);
            
            // Green card (bottom)
            cmp.setColor(UnoColor.GREEN.getColor());
            drawCardWithShadow(cmp, centerX - cardWidth/2, centerY + spacing/2, 
                             cardWidth, cardHeight);
        } else {
            //draw single colored card with +4 text
            cmp.setColor(selectedColor.getColor());
            drawCardWithShadow(cmp, centerX - cardWidth/2, centerY - cardHeight/2, 
                             cardWidth, cardHeight);
            
            //draw +4 on the card
            cmp.setColor(Color.WHITE);
            Font boldFont = new Font("Arial", Font.BOLD, cardHeight/3);
            cmp.setFont(boldFont);
            FontMetrics fm = cmp.getFontMetrics();
            String text = "+4";
            int textWidth = fm.stringWidth(text);
            cmp.drawString(text, centerX - textWidth/2, centerY + fm.getAscent()/2 - fm.getHeight()/4);
        }
        
        //corners
        cmp.setFont(new Font("Arial", Font.BOLD, height/8));
        cmp.setColor(Color.WHITE);
        cmp.drawString("+4", width/12, height/6);
        
        cmp.translate(width, height);
        cmp.rotate(Math.PI);
        cmp.drawString("+4", width/12, height/6);
        cmp.rotate(-Math.PI);
        cmp.translate(-width, -height);
    }
    
    private void drawCardWithShadow(Graphics2D cmp, int x, int y, int width, int height) {
        Color cardColor = cmp.getColor();
        
        /* 
        cmp.setColor(Color.BLACK);
        cmp.fillRoundRect(x + 2, y + 2, width, height, 6, 6);*/
        
    
        cmp.setColor(cardColor);
        cmp.fillRoundRect(x, y, width, height, 6, 6);
        
        // Draw black border
        cmp.setColor(Color.white);
        cmp.setStroke(new BasicStroke(1.5f));
        cmp.drawRoundRect(x, y, width, height, 6, 6);
    }
    
    private void drawMiniColorCircle(Graphics2D cmp, int x, int y, int radius) {
        
        cmp.setColor(UnoColor.RED.getColor());
        cmp.fillArc(x, y, radius * 2, radius * 2, 90, 90);
        
        cmp.setColor(UnoColor.BLUE.getColor());
        cmp.fillArc(x, y, radius * 2, radius * 2, 0, 90);
        
        // Yellow quadrant (bottom-left)
        cmp.setColor(UnoColor.YELLOW.getColor());
        cmp.fillArc(x, y, radius * 2, radius * 2, 180, 90);
        
        // Green quadrant (bottom-right)
        cmp.setColor(UnoColor.GREEN.getColor());
        cmp.fillArc(x, y, radius * 2, radius * 2, 270, 90);
        
        // Black border
        cmp.setColor(Color.BLACK);
        cmp.setStroke(new BasicStroke(1f));
        cmp.drawOval(x, y, radius * 2, radius * 2);
    }
}