package components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class GameFlowIndicator extends JPanel {
    private Color arrowColor;
    private int thickness;
    private boolean clockwise = true; 
    
    public GameFlowIndicator() {
        this(Color.BLACK, 20);
    }
    
    public GameFlowIndicator(Color arrowColor, int thickness) {
        this.arrowColor = arrowColor;
        this.thickness = thickness;
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.WHITE);
    }
    
    public void setArrowColor(Color color) {
        this.arrowColor = color;
        repaint();
    }
    
    public void setThickness(int thickness) {
        this.thickness = thickness;
        repaint();
    }
    
    
    public void setClockwise(boolean clockwise) {
        this.clockwise = clockwise;
        repaint();
    }
    
   
    public void toggleDirection() {
        this.clockwise = !this.clockwise;
        repaint();
    }
    
    public boolean isClockwise() {
        return clockwise;
    }
    



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        g2d.setColor(arrowColor);
        g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        Path2D.Double arrowPath = new Path2D.Double();
        
        if (clockwise) {
            int startX = width / 4;
            int startY = 3 * height / 4;
            int endX   = 3 * width / 4;
            int endY   = height / 4;
            int controlX = width / 5;
            int controlY = height / 4;
            
            arrowPath.moveTo(startX, startY);
            arrowPath.quadTo(controlX, controlY, endX, endY);
            g2d.draw(arrowPath);
            
            int capSize = thickness;
            g2d.fillOval(startX - capSize/2, startY - capSize/2, capSize, capSize);
            g2d.fillOval(endX   - capSize/2, endY   - capSize/2, capSize, capSize);
            
            //arrow head
            int arrowSize = thickness * 3;
            double dx = endX - controlX;
            double dy = endY - controlY;
            double angle = Math.atan2(dy, dx);
            int x1 = (int) (endX - arrowSize * Math.cos(angle - Math.PI / 6));
            int y1 = (int) (endY - arrowSize * Math.sin(angle - Math.PI / 6));
            int x2 = (int) (endX - arrowSize * Math.cos(angle + Math.PI / 6));
            int y2 = (int) (endY - arrowSize * Math.sin(angle + Math.PI / 6));
            
            g2d.drawLine(endX, endY, x1, y1);
            g2d.drawLine(endX, endY, x2, y2);
        } else {
            //change direction
            int startX = 3 * width / 4;
            int startY = 3 * height / 4;
            int endX   = width / 4;
            int endY   = height / 4;
            int controlX = 4 * width / 5;
            int controlY = height / 4;
            
            arrowPath.moveTo(startX, startY);
            arrowPath.quadTo(controlX, controlY, endX, endY);
            g2d.draw(arrowPath);
            
            int capSize = thickness;
            g2d.fillOval(startX - capSize/2, startY - capSize/2, capSize, capSize);
            g2d.fillOval(endX   - capSize/2, endY   - capSize/2, capSize, capSize);
            
            //arrow head
            int arrowSize = thickness * 3;
            double dx = endX - controlX;
            double dy = endY - controlY;
            double angle = Math.atan2(dy, dx);
            int x1 = (int) (endX - arrowSize * Math.cos(angle - Math.PI / 6));
            int y1 = (int) (endY - arrowSize * Math.sin(angle - Math.PI / 6));
            int x2 = (int) (endX - arrowSize * Math.cos(angle + Math.PI / 6));
            int y2 = (int) (endY - arrowSize * Math.sin(angle + Math.PI / 6));
            
            g2d.drawLine(endX, endY, x1, y1);
            g2d.drawLine(endX, endY, x2, y2);
        }
        
        g2d.dispose();
    }
    
    
    
}