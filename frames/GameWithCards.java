package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import components.UnoColor;
import components.panels.CardPilesPanel;
import components.panels.PlayerCardPanel;
import components.unoCards.*;


public class GameWithCards extends GameSpaceFrame {
    
    private PlayerCardPanel[] playerCardPanels;
    
    private CardPilesPanel cardPilesPanel;
    
    private static final int CARD_WIDTH = 60;  // Reduced from 100
    private static final int CARD_HEIGHT = 96; // Reduced from 160, keeping proportions
    
    public GameWithCards() {
        super();
        setTitle("UNO Game With Card Panels");
        
        initializeCardPanels();
        
        initializeCardPiles();
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updatePanelPositions();
            }
        });
        
        updatePanelPositions();
    }
    
    
    private void initializeCardPanels() {
        playerCardPanels = new PlayerCardPanel[4];
        
        //create panel for each player with appropriate orientation
        playerCardPanels[0] = new PlayerCardPanel(PlayerCardPanel.Position.BOTTOM, true); 
        playerCardPanels[1] = new PlayerCardPanel(PlayerCardPanel.Position.RIGHT, true);
        playerCardPanels[2] = new PlayerCardPanel(PlayerCardPanel.Position.TOP, true);
        playerCardPanels[3] = new PlayerCardPanel(PlayerCardPanel.Position.LEFT, true);
        
        for (PlayerCardPanel panel : playerCardPanels) {
            getLayeredPane().add(panel, JLayeredPane.DEFAULT_LAYER);
        }
    }
    
    
    private void initializeCardPiles() {
        cardPilesPanel = new CardPilesPanel();
        getLayeredPane().add(cardPilesPanel, JLayeredPane.DEFAULT_LAYER);
    }
    
   
    private void updatePanelPositions() {
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        
        Dimension bottomSize = playerCardPanels[0].getPreferredSize();
        playerCardPanels[0].setBounds(
            (frameWidth - bottomSize.width) / 2,
            frameHeight - bottomSize.height - 20,
            bottomSize.width,
            bottomSize.height
        );
        
        // Position right panel
        Dimension rightSize = playerCardPanels[1].getPreferredSize();
        playerCardPanels[1].setBounds(
            frameWidth - rightSize.width - 20,
            (frameHeight - rightSize.height) / 2,
            rightSize.width,
            rightSize.height
        );
        
        Dimension topSize = playerCardPanels[2].getPreferredSize();
        playerCardPanels[2].setBounds(
            (frameWidth - topSize.width) / 2,
            20,
            topSize.width,
            topSize.height
        );
        
        //position left panel
        Dimension leftSize = playerCardPanels[3].getPreferredSize();
        playerCardPanels[3].setBounds(
            20,
            (frameHeight - leftSize.height) / 2,
            leftSize.width,
            leftSize.height
        );
        
        Dimension pileSize = cardPilesPanel.getPreferredSize();
        cardPilesPanel.setBounds(
            (frameWidth - pileSize.width) / 2,
            (frameHeight - pileSize.height) / 2,
            pileSize.width,
            pileSize.height
        );
    }
    
   
    public void setPlayerCards(int playerIndex, UnoCardButton[] cards) {
        if (playerIndex >= 0 && playerIndex < playerCardPanels.length) {
            playerCardPanels[playerIndex].setCards(cards);
        }
    }
    
    
    public void setDiscardPileCard(UnoCardButton card) {
        cardPilesPanel.setDiscardPileCard(card);
    }
    
    
    public static UnoCardButton[] createRandomCards(int count) {
        UnoCardButton[] cards = new UnoCardButton[count];
        UnoColor[] colors = {UnoColor.RED, UnoColor.BLUE, UnoColor.GREEN, UnoColor.YELLOW};
        
        Dimension smallerCardSize = new Dimension(CARD_WIDTH, CARD_HEIGHT);
        
        for (int i = 0; i < count; i++) {
            UnoColor randomColor = colors[(int)(Math.random() * colors.length)];
            int type = (int)(Math.random() * 10);
            
            if (type < 7) {
                int number = (int)(Math.random() * 10);
                cards[i] = new UnoNumberCardButton(randomColor, number);
                cards[i].setPreferredSize(smallerCardSize);
            } else if (type < 9) {
                
                int actionType = (int)(Math.random() * 3);
                UnoActionCardButton.ActionType[] types = UnoActionCardButton.ActionType.values();
                cards[i] = new UnoActionCardButton(randomColor, types[actionType]);
                cards[i].setPreferredSize(smallerCardSize);
            } else {
                //wild card
                int wildType = (int)(Math.random() * 2);
                UnoWildCardButton.WildType[] types = UnoWildCardButton.WildType.values();
                cards[i] = new UnoWildCardButton(types[wildType]);
                cards[i].setPreferredSize(smallerCardSize);
            }
        }
        
        return cards;
    }
    
  
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWithCards frame = new GameWithCards();
            frame.setSize(1000, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
           
            
            for (int i = 0; i < 4; i++) {
                frame.setPlayerCards(i, createRandomCards(7));
            }
            
            /*UnoNumberCardButton discardCard = new UnoNumberCardButton(UnoColor.RED, 5);
            discardCard.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            frame.setDiscardPileCard(discardCard);*/
            
            
            frame.setVisible(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        });
    }
}