package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class UnoBackgroundGrid extends JFrame {
    
    private static final Color UNO_TEAL = new Color(33, 86, 90);
    private static final Color GRID_LINE_COLOR = new Color(43, 96, 100);
    private static final int GRID_SIZE = 110; //one grid
    
    private JLayeredPane lp;
    private GridPanel gridPanel;
    
    public UnoBackgroundGrid() {
        setTitle("UNO Background Grid");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //using layered pane to maintain all components
        lp = new JLayeredPane();
        lp.setLayout(null); //removing layouts
        setContentPane(lp);
        
        gridPanel = new GridPanel();
        gridPanel.setBounds(0, 0, getWidth(), getHeight());
        lp.add(gridPanel, JLayeredPane.DEFAULT_LAYER);
        
        // Add a listener to handle window resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Update the size of the grid panel when the window is resized
                gridPanel.setBounds(0, 0, getWidth(), getHeight());
                lp.revalidate();
                lp.repaint();
            }
        });
    }
    
    private class GridPanel extends JPanel {
        public GridPanel() {
            setOpaque(true);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D cmp = (Graphics2D) g;
            
            int width = getWidth();
            int height = getHeight();
            
            //fill the background
            cmp.setColor(UNO_TEAL);
            cmp.fillRect(0, 0, width, height);
    
            cmp.setColor(GRID_LINE_COLOR);
            cmp.setStroke(new BasicStroke(1));
            //h grid lines
            for (int y = 0; y <= height; y += GRID_SIZE) {
                cmp.drawLine(0, y, width, y);
            }
            
            //v grid lines
            for (int x = 0; x <= width; x += GRID_SIZE) {
                cmp.drawLine(x, 0, x, height);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UnoBackgroundGrid frame = new UnoBackgroundGrid();
            frame.setVisible(true);
        });
    }
}