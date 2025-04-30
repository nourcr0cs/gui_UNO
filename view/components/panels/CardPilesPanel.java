package view.components.panels;

import javax.swing.*;

import view.components.UnoColor;
import view.components.unoCards.UnoCardButton;

import java.awt.*;

public class CardPilesPanel extends JPanel {
    
    private UnoCardButton drawPileCard;
    private UnoCardButton discardPileCard;
    
    private static final int CARD_WIDTH = 80;  
    private static final int CARD_HEIGHT = 106; 
    
    private static final int PILE_SPACING = 35; 
    
    
    public CardPilesPanel() {
        setOpaque(false);
        setLayout(null); 
        
        drawPileCard = new UnoCardButton(UnoColor.BLACK);
        drawPileCard.setFaceUp(false);
        drawPileCard.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        add(drawPileCard);
        
        int preferredWidth = (CARD_WIDTH * 2) + PILE_SPACING ; 
        int preferredHeight = CARD_HEIGHT + 10; 
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        
        updateCardPositions();
    }
    
    
    private void updateCardPositions() {
        int totalWidth = (2 * CARD_WIDTH) + PILE_SPACING ;
        int startX = (getWidth() - totalWidth) / 2;
        int y = (getHeight() - CARD_HEIGHT) / 2;
        
        // Position draw pile
        drawPileCard.setBounds(startX, y, CARD_WIDTH, CARD_HEIGHT);
        
        // Position discard pile if it exists
        if (discardPileCard != null) {
            discardPileCard.setBounds(startX + CARD_WIDTH + PILE_SPACING, y, CARD_WIDTH, CARD_HEIGHT);
        }
    }
    
   
    public void setDiscardPileCard(UnoCardButton card) {
        // Remove current discard pile card if exists
        if (discardPileCard != null) {
            remove(discardPileCard);
        }
        
        discardPileCard = card;
        if (card != null) {
            card.setFaceUp(true); // Discard pile always face up
            card.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            add(card);
        }
        
        updateCardPositions();
        revalidate();
        repaint();
    }
    
    
    public UnoCardButton getDrawPileCard() {
        return drawPileCard;
    }
    
  
    public UnoCardButton getDiscardPileCard() {
        return discardPileCard;
    }
    
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        updateCardPositions();
    }
}