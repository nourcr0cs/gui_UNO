
package view.components.unoCards;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import view.components.UnoColor;

public class UnoActionCardButton extends UnoCardButton {
    public enum ActionType {
        SKIP, REVERSE, DRAW_TWO
    }

    private final ActionType actionType;
    
    public UnoActionCardButton(UnoColor cardColor, ActionType actionType) {
        super(cardColor);
        this.actionType = actionType;
    }
    
    public ActionType getActionType() {
        return actionType;
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
        
        //draw the action symbol based on type
        switch (actionType) {
            case SKIP:
                drawSkipSymbol(cmp, width, height);
                break;
            case REVERSE:
                drawReverseSymbol(cmp, width, height);
                break;
            case DRAW_TWO:
                drawDrawTwoSymbol(cmp, width, height);
                break;
        }
        
        cmp.dispose();
    }
    
    private void drawSkipSymbol(Graphics2D cmp, int width, int height) {
        int centerX = width / 2;
        int centerY = height / 2;
        int symbolSize = Math.min(width, height) / 3;
        
        //draw large white circle with blue border
        cmp.setColor(Color.WHITE);
        cmp.fillOval(centerX - symbolSize/2, centerY - symbolSize/2, symbolSize, symbolSize);
        
        //save the original clip
        Shape originalClip = cmp.getClip();
        
        //create a clip for the circle to contain the line
        cmp.setClip(new Ellipse2D.Double(centerX - symbolSize/2, centerY - symbolSize/2, symbolSize, symbolSize));
        
        //draw the diagonal line across (now clipped to stay inside the circle)
        cmp.setColor(cardColor.getColor());
        cmp.setStroke(new BasicStroke(symbolSize/5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
        
        //use a slightly shorter line to ensure it stays inside
        double diagonalLength = symbolSize * 0.9;
        cmp.drawLine(
            centerX - (int)(diagonalLength/2), centerY - (int)(diagonalLength/2), 
            centerX + (int)(diagonalLength/2), centerY + (int)(diagonalLength/2)
        );
        
        cmp.setClip(originalClip);
        
        //cmp.setStroke(new BasicStroke(symbolSize/10f));
        cmp.drawOval(centerX - symbolSize/2, centerY - symbolSize/2, symbolSize, symbolSize);
        
        //draw small corner symbols (prohibition signs)
        int cornerSize = height / 12;
        
        //top-left corner
        cmp.setColor(Color.WHITE);
        cmp.fillOval(width/12, height/12, cornerSize , cornerSize);
        
        originalClip = cmp.getClip();
        cmp.setClip(new Ellipse2D.Double(width/12, height/12, cornerSize, cornerSize));
        
        cmp.setColor(cardColor.getColor());
        cmp.setStroke(new BasicStroke(cornerSize/5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
        
        double cornerDiagonal = cornerSize * 0.9;
        cmp.drawLine(
            width/12 + (int)((cornerSize - cornerDiagonal)/2), 
            height/12 + (int)((cornerSize - cornerDiagonal)/2), 
            width/12 + (int)((cornerSize + cornerDiagonal)/2), 
            height/12 + (int)((cornerSize + cornerDiagonal)/2)
        );
        
        cmp.setClip(originalClip);
        
        //black border for top-left corner
        cmp.setColor(Color.BLACK);
        cmp.setStroke(new BasicStroke(0.3f));
        cmp.drawOval(width/12, height/12, cornerSize, cornerSize);
        
        //buttom corner
        cmp.setColor(Color.WHITE);
        cmp.fillOval(width - width/12 - cornerSize, height - height/12 - cornerSize, cornerSize, cornerSize);
        
        originalClip = cmp.getClip();

        cmp.setClip(new Ellipse2D.Double(width - width/12 - cornerSize, height - height/12 - cornerSize, cornerSize, cornerSize));
        
        cmp.setColor(cardColor.getColor());
        cmp.setStroke(new BasicStroke(cornerSize/5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
        
        //use shorter line for corner symbol too
        cmp.drawLine(
            width - width/12 - cornerSize + (int)((cornerSize - cornerDiagonal)/2), 
            height - height/12 - cornerSize + (int)((cornerSize - cornerDiagonal)/2), 
            width - width/12 - cornerSize + (int)((cornerSize + cornerDiagonal)/2), 
            height - height/12 - cornerSize + (int)((cornerSize + cornerDiagonal)/2)
        );
        
        cmp.setClip(originalClip);
        
        //black border for bottom-right corner
        cmp.setColor(Color.BLACK);
        cmp.setStroke(new BasicStroke(0.3f));
        cmp.drawOval(width - width/12 - cornerSize, height - height/12 - cornerSize, cornerSize, cornerSize);
    }
    
    private void drawReverseSymbol(Graphics2D cmp, int width, int height) {
        int centerX = width / 2;
        int centerY = height / 2;
        int symbolSize = Math.min(width, height) / 3;
        
        //Draw white oval that matches the card (no black border)
        cmp.setColor(Color.WHITE);
        cmp.fillOval(centerX - symbolSize, centerY - symbolSize, symbolSize * 2, symbolSize * 2);
        
        // Rotate the graphics context slightly for the main symbol
        Graphics2D cmpRotated = (Graphics2D) cmp.create();
        cmpRotated.translate(centerX, centerY);
        cmpRotated.rotate(Math.PI / 8); // Rotate by 22.5 degrees
        cmpRotated.translate(-centerX, -centerY);
        
        //Draw simple back and forth arrows in the center with card color
        drawSimpleArrows(cmpRotated, centerX, centerY, symbolSize, cardColor.getColor());
        cmpRotated.dispose();
        
        //Draw small corner arrows in white (in the same direction as the center)
        int cornerSize = height / 5;
        
        //Top-left corner arrows (white) - match direction of center
        Graphics2D cmpCorner1 = (Graphics2D) cmp.create();
        cmpCorner1.translate(width/8, height/8);
        cmpCorner1.rotate(Math.PI / 8);
        cmpCorner1.translate(-width/8, -height/8);
        drawSimpleArrows(cmpCorner1, width/8 + 3, height/8, cornerSize, Color.WHITE);
        cmpCorner1.dispose();
        
        //Bottom-right corner arrows (inverted but same direction)
        Graphics2D cmpCorner2 = (Graphics2D) cmp.create();
        cmpCorner2.translate(width - width/8, height - height/8 );
        cmpCorner2.rotate(Math.PI / 8 + Math.PI); 
        cmpCorner2.translate(-(width - width/8), -(height - height/8));
        drawSimpleArrows(cmpCorner2, width - width/8 + 3, height - height/8, cornerSize, Color.WHITE);
        cmpCorner2.dispose();
    }
    
    private void drawSimpleArrows(Graphics2D cmp, int x, int y, int size, Color arrowColor) {
        int arrowWidth = size / 2;
        int arrowHeight = size / 8;
        int arrowHeadSize = size / 8;
        
        cmp.setColor(arrowColor);
        cmp.setStroke(new BasicStroke(size/10f));
        
        
        //Arrow body
        cmp.drawLine(x - arrowWidth/2, y - arrowHeight, x + arrowWidth/2 - arrowHeadSize, y - arrowHeight);
        
        //arrow head
        int[] xPoints1 = {x + arrowWidth/2 - arrowHeadSize, x + arrowWidth/2, x + arrowWidth/2 - arrowHeadSize};
        int[] yPoints1 = {y - arrowHeight - arrowHeadSize, y - arrowHeight, y - arrowHeight + arrowHeadSize};
        cmp.drawPolyline(xPoints1, yPoints1, 3);
        
        //Bottom arrow (pointing left)
        cmp.drawLine(x + arrowWidth/2, y + arrowHeight, x - arrowWidth/2 + arrowHeadSize, y + arrowHeight);
        
        int[] xPoints2 = {x - arrowWidth/2 + arrowHeadSize, x - arrowWidth/2, x - arrowWidth/2 + arrowHeadSize};
        int[] yPoints2 = {y + arrowHeight - arrowHeadSize, y + arrowHeight, y + arrowHeight + arrowHeadSize};
        cmp.drawPolyline(xPoints2, yPoints2, 3);
    }
    
    private void drawDrawTwoSymbol(Graphics2D cmp, int width, int height) {
        int centerX = width / 2;
        int centerY = height / 2;
        
        //draw two card outlines
        int cardWidth = width / 5;
        int cardHeight = height / 4;
        int offset = cardWidth / 3;
        
        //first card (bottom/background)
        cmp.setColor(cardColor.getColor());
        roundedRect(cmp, centerX - cardWidth/2 - offset/2, centerY - cardHeight/2 + offset/2, 
                cardWidth, cardHeight, 5);
        
        //second card (top/foreground) - in white with colored outline
        cmp.setColor(Color.WHITE);
        roundedRect(cmp, centerX - cardWidth/2 + offset/2, centerY - cardHeight/2 - offset/2, 
                cardWidth, cardHeight, 5);
        cmp.setColor(cardColor.getColor());
        cmp.setStroke(new BasicStroke(2f));
        roundedRectOutline(cmp, centerX - cardWidth/2 + offset/2, centerY - cardHeight/2 - offset/2, 
                cardWidth, cardHeight, 5);
        
        //draw +2 in top-left corner
        cmp.setFont(new Font("Arial", Font.BOLD, height/8));
        cmp.setColor(Color.WHITE);
        cmp.drawString("+2", width/12, height/6 );
        
        //draw +2 in bottom-right corner (inverted)
        cmp.translate(width, height);
        cmp.rotate(Math.PI);
        cmp.drawString("+2", width/12, height/6);
        cmp.rotate(-Math.PI);
        cmp.translate(-width, -height);
    }
    
    //for the +2
    private void roundedRect(Graphics2D cmp, int x, int y, int width, int height, int radius) {
        cmp.fillRoundRect(x, y, width, height, radius, radius);
    }
    
    private void roundedRectOutline(Graphics2D cmp, int x, int y, int width, int height, int radius) {
        cmp.drawRoundRect(x, y, width, height, radius, radius);
    }
}