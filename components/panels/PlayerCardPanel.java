package components.panels;

import javax.swing.*;
import java.awt.*;
import components.unoCards.UnoCardButton;
import components.labels.PlayerLabel;


public class PlayerCardPanel extends JPanel {
    
    //for card layout
    private static final int CARD_SPACING = 5; 
    private static final int PANEL_PADDING = 0;
    
    private static final int CARD_WIDTH = 80;  
    private static final int CARD_HEIGHT = 107;
    
    private static final int ROTATED_CARD_WIDTH = CARD_HEIGHT;
    private static final int ROTATED_CARD_HEIGHT = CARD_WIDTH;
    
    public enum Position {
        BOTTOM, LEFT, TOP, RIGHT
    }
    
    private final Position position;
    private final PlayerLabel nameLabel;

    private final boolean isHuman; 
    
   
    public PlayerCardPanel(Position position, boolean isHuman) {
        this.position = position;
        this.isHuman = isHuman;
        this.nameLabel = new PlayerLabel("", position.ordinal());
        
        setOpaque(false);
        setLayout(null); 
        
        add(nameLabel);

        setMinimumSize(getPreferredPanelSize());
        setPreferredSize(getPreferredPanelSize());
    }
    
    
    private Dimension getPreferredPanelSize() {
        switch (position) {
            case BOTTOM:
            case TOP:
                return new Dimension(800, 130);
            case LEFT:
            case RIGHT:
                return new Dimension(130, 600);
            default:
                return new Dimension(800, 130);
        }
    }
    
    public void setPlayerName(String name) {
        nameLabel.setPlayerName(name);
        positionNameLabel();
    }
    
    public void setActive(boolean active) {
        nameLabel.setActive(active);
    }
    
    public void setCards(UnoCardButton[] cards) {
        String playerName = nameLabel.getText();
        boolean isActive = nameLabel.isActive();
        
        removeAll();
        
        add(nameLabel);
        nameLabel.setText(playerName);
        nameLabel.setActive(isActive);
        
        if (cards == null || cards.length == 0) {
            repaint();
            return;
        }
        
        switch (position) {
            case BOTTOM:
            case TOP:
                displayHorizontalCards(cards);
                break;
            case LEFT:
            case RIGHT:
                displayVerticalCards(cards);
                break;
        }
        
        positionNameLabel();
        revalidate();
        repaint();
    }
    
   
    private void displayHorizontalCards(UnoCardButton[] cards) {
        int totalWidth = (CARD_WIDTH * cards.length) + (CARD_SPACING * (cards.length - 1));
        
        int startX = (getWidth() - totalWidth) / 2;
        
        int y;
        if (position == Position.BOTTOM) {
            y = getHeight() - CARD_HEIGHT - 25;
        } else { 
            y = 5;
        }
        
        for (int i = 0; i < cards.length; i++) {
            UnoCardButton card = cards[i];
            card.setFaceUp(isHuman || position != Position.BOTTOM);
            
            if (position == Position.TOP) {
                card.setRotation(180);
            } else {
                card.setRotation(0);
            }
            
            card.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            
            add(card);
            int cardX = startX + (i * (CARD_WIDTH + CARD_SPACING));
            card.setBounds(cardX, y, CARD_WIDTH, CARD_HEIGHT);
        }
    }
    
    
    

    private void displayVerticalCards(UnoCardButton[] cards) {
        int totalHeight = (ROTATED_CARD_HEIGHT * cards.length)
                        + (CARD_SPACING * (cards.length - 1));
                        
        int x = (getWidth()  - ROTATED_CARD_WIDTH ) / 2;
        int startY = (getHeight() - totalHeight) / 2;
    
        for (int i = 0; i < cards.length; i++) {
          UnoCardButton card = cards[i];
          card.setFaceUp(true);
    
          double angle;
          if (position == Position.RIGHT) angle = 270;   
          else  angle = 90;  
          card.setRotation(angle);
    
          card.setBounds(
            x ,
            startY + i * (ROTATED_CARD_HEIGHT + 2),
            CARD_HEIGHT,
            CARD_WIDTH
          );
          add(card);
        }
    }

    
    
    

    private void positionNameLabel() {
        Dimension labelSize = nameLabel.getPreferredSize();
        int px = 0, py = 0;

        switch (position) {
            case LEFT:
                px = 10;
                py = 10;
                break;
            case RIGHT:
                px = getWidth() - labelSize.width - 10;
                py = 10;
                break;
            case TOP:
                px = (getWidth() - labelSize.width) / 2;
                py = 0;
                break;
            case BOTTOM:
                px = (getWidth() - labelSize.width) / 2;
                py = getHeight() - labelSize.height;
                break;
        }

        nameLabel.setBounds(px, py, labelSize.width, labelSize.height);
    }
}