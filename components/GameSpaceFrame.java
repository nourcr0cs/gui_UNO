package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameSpaceFrame extends UnoBackgroundGrid {
    
    private static final Color FLOW_INDICATOR_COLOR = Color.WHITE;
    private boolean gameDirectionClockwise = true; // Default game direction
    
    private CirclePanel circlePanel;
    private GameFlowIndicator flowIndicator;
    private JLabel playerLabel;
    
    public GameSpaceFrame() {
        super(); 
        setTitle("UNO Game Space");
        
    
        circlePanel = new CirclePanel();
        int circlePanelSize = 400;
        int circleX = (getWidth() - circlePanelSize) / 2;
        int circleY = (getHeight() - circlePanelSize) / 2;
        circlePanel.setBounds(circleX, circleY, circlePanelSize, circlePanelSize);
        getLayeredPane().add(circlePanel, JLayeredPane.PALETTE_LAYER);
        
        flowIndicator = new GameFlowIndicator(FLOW_INDICATOR_COLOR, 3);
        int flowSize = 60;
        int flowX = (getWidth() - flowSize) / 2;
        int flowY = circleY + 30; 
        flowIndicator.setBounds(flowX, flowY, flowSize, flowSize);
        flowIndicator.setOpaque(false);
        getLayeredPane().add(flowIndicator, JLayeredPane.MODAL_LAYER);
        
        playerLabel = new JLabel("you");
        playerLabel.setFont(new Font("Arial", Font.BOLD, 40));
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        int labelWidth = 100;
        int labelHeight = 50;
        int labelX = (getWidth() - labelWidth) / 2;
        int labelY = circleY + circlePanelSize + 10;
        playerLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
        getLayeredPane().add(playerLabel, JLayeredPane.MODAL_LAYER);
        
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newCircleX = (getWidth() - circlePanel.getWidth()) / 2;
                int newCircleY = (getHeight() - circlePanel.getHeight()) / 2;
                circlePanel.setBounds(newCircleX, newCircleY, circlePanel.getWidth(), circlePanel.getHeight());
                
                int newFlowX = (getWidth() - flowIndicator.getWidth()) / 2;
                int newFlowY = newCircleY + 30; // Keep inside the top of the circle
                flowIndicator.setBounds(newFlowX, newFlowY, flowIndicator.getWidth(), flowIndicator.getHeight());
                
                int newLabelX = (getWidth() - playerLabel.getWidth()) / 2;
                int newLabelY = newCircleY + circlePanel.getHeight() + 10;
                playerLabel.setBounds(newLabelX, newLabelY, playerLabel.getWidth(), playerLabel.getHeight());
                
                Component[] components = getLayeredPane().getComponentsInLayer(JLayeredPane.DRAG_LAYER);
                for (Component comp : components) {
                    if (comp instanceof JPanel) {
                        comp.setBounds(0, getHeight() - 80, getWidth(), 50);
                    }
                }
                
                getLayeredPane().revalidate();
                getLayeredPane().repaint();
            }
        });
    }
    
    
    public void reverseGameDirection() {
        gameDirectionClockwise = !gameDirectionClockwise;
        flowIndicator.toggleDirection();
        
        System.out.println("Game direction reversed: " + 
                          (gameDirectionClockwise ? "Clockwise" : "Counter-clockwise"));
    }
    
    
    public boolean isGameDirectionClockwise() {
        return gameDirectionClockwise;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameSpaceFrame frame = new GameSpaceFrame();
            
            JButton reverseButton = new JButton("Play Reverse Card");
            reverseButton.addActionListener(e -> frame.reverseGameDirection());
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);
            buttonPanel.add(reverseButton);
            buttonPanel.setBounds(0, frame.getHeight() - 80, frame.getWidth(), 50);
            frame.getLayeredPane().add(buttonPanel, JLayeredPane.DRAG_LAYER);
            
            frame.setVisible(true);
        });
    }
}