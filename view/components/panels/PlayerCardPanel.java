package view.components.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;

import view.components.unoCards.UnoCardButton;

import java.awt.*;

public class PlayerCardPanel extends JPanel {
    
    private static final int CARD_SPACING = 5; 
    private static final int CARD_OVERLAP = 30; 
    
    private static final int CARD_WIDTH = 80;  
    private static final int CARD_HEIGHT = 107;
    
    private static final Color ACTIVE_BORDER_COLOR = new Color(255, 0, 64); // Cool red
    private static final Color INACTIVE_BORDER_COLOR = new Color(255, 255, 255); // White
    private static final int BORDER_THICKNESS = 3;
    
    public enum Position {
        BOTTOM, LEFT, TOP, RIGHT
    }
    
    private final Position position;
    private final boolean isHuman; 
    private JPanel cardContainer; 
    
    public PlayerCardPanel(Position position, boolean isHuman) {
        this.position = position;
        this.isHuman = isHuman;
        
        setOpaque(false);
        setLayout(new BorderLayout());
        
        setBorder(new LineBorder(INACTIVE_BORDER_COLOR, BORDER_THICKNESS));
        
        createCardContainer();
        add(cardContainer, BorderLayout.CENTER);

        setMinimumSize(getPreferredPanelSize());
        setPreferredSize(getPreferredPanelSize());
    }
    
    private void createCardContainer() {
        cardContainer = new JPanel() {
            @Override
            public void doLayout() {
                if (getComponentCount() == 0) return;
                
                int numCards = getComponentCount();
                
                switch (position) {
                    case BOTTOM:
                    case TOP:
                        layoutHorizontalCards(numCards);
                        break;
                    case LEFT:
                    case RIGHT:
                        layoutVerticalCards(numCards);
                        break;
                }
            }
            
            private void layoutHorizontalCards(int numCards) {
                int totalWidth = getWidth();
                int totalAvailableWidth = totalWidth - (2 * CARD_SPACING);
                
                int effectiveCardWidth = CARD_WIDTH;
                int overlapPerCard = 0;
                
                if (numCards * CARD_WIDTH > totalAvailableWidth) {
                    overlapPerCard = ((numCards * CARD_WIDTH) - totalAvailableWidth) / (numCards - 1);
                    if (overlapPerCard > CARD_WIDTH - CARD_OVERLAP) {
                        overlapPerCard = CARD_WIDTH - CARD_OVERLAP;
                    }
                }
                
                effectiveCardWidth = CARD_WIDTH - overlapPerCard;
                
                // Calculate starting X position to center the cards
                int totalUsedWidth = (numCards * CARD_WIDTH) - (overlapPerCard * (numCards - 1));
                int startX = (totalWidth - totalUsedWidth) / 2;
                
                int margin = 2;
                
                for (int i = 0; i < numCards; i++) {
                    Component card = getComponent(i);
                    card.setBounds(
                        startX + (i * effectiveCardWidth) - margin,
                        (getHeight() - CARD_HEIGHT) / 2 - margin,
                        CARD_WIDTH + (margin * 2),
                        CARD_HEIGHT + (margin * 2)
                    );
                }
            }
            
            private void layoutVerticalCards(int numCards) {
                int totalHeight = getHeight();
                int totalAvailableHeight = totalHeight - (2 * CARD_SPACING);
                
                int verticalCardWidth = CARD_HEIGHT; 
                int verticalCardHeight = CARD_WIDTH;  
                
                // Calculate overlap if needed
                int effectiveCardHeight = verticalCardHeight;
                int overlapPerCard = 0;
                
                if (numCards * verticalCardHeight > totalAvailableHeight) {
                    overlapPerCard = ((numCards * verticalCardHeight) - totalAvailableHeight) / (numCards - 1);
                    if (overlapPerCard > verticalCardHeight - CARD_OVERLAP) {
                        overlapPerCard = verticalCardHeight - CARD_OVERLAP;
                    }
                }
                
                effectiveCardHeight = verticalCardHeight - overlapPerCard;
                
                // Calculate starting Y position to center the cards
                int totalUsedHeight = (numCards * verticalCardHeight) - (overlapPerCard * (numCards - 1)) ;
                int startY = (totalHeight - totalUsedHeight) / 2;
                
                int marginX = 15; 
                int marginY = 2; 
                
                for (int i = 0; i < numCards; i++) {
                    Component card = getComponent(i);
                    
                    card.setBounds(
                        (getWidth() - verticalCardHeight) / 2 - marginX,
                        startY + (i * effectiveCardHeight) - marginY,
                        verticalCardHeight + (marginX * 2) ,  
                        verticalCardWidth + (marginY * 2)   
                    );
                }
            }
        };
        
        cardContainer.setOpaque(false);
        cardContainer.setLayout(null); 
    }
    
    private Dimension getPreferredPanelSize() {
        switch (position) {
            case BOTTOM:
            case TOP:
                return new Dimension(800, 130);
            case LEFT:
            case RIGHT:
                return new Dimension(160, 700);
            default:
                return new Dimension(800, 130);
        }
    }
    
    public void setActive(boolean active) {
        if (active) {
            setBorder(new LineBorder(ACTIVE_BORDER_COLOR, BORDER_THICKNESS));
        } else {
            setBorder(new LineBorder(INACTIVE_BORDER_COLOR, BORDER_THICKNESS));
        }
        
        repaint();
    }
    
    public void setCards(UnoCardButton[] cards) {
        // Clear existing cards
        cardContainer.removeAll();
        
        if (cards == null || cards.length == 0) {
            revalidate();
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
        
        revalidate();
        repaint();
    }
    
    private void displayHorizontalCards(UnoCardButton[] cards) {
        for (UnoCardButton card : cards) {
            // Note: Face up/down is now managed by the GameWithCards class
            
            if (position == Position.TOP) {
                card.setRotation(180);
            } else {
                card.setRotation(0);
            }
            
            card.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            card.setMinimumSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            card.setMaximumSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            
            cardContainer.add(card);
        }
    }
    
    private void displayVerticalCards(UnoCardButton[] cards) {
        for (UnoCardButton card : cards) {
            // Note: Face up/down is now managed by the GameWithCards class
            
            double angle = (position == Position.RIGHT) ? 270 : 90;
            card.setRotation(angle);
            
            card.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            card.setMinimumSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            card.setMaximumSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            
            cardContainer.add(card);
        }
    }
    
   
    public boolean isHumanPlayer() {
        return isHuman;
    }
}