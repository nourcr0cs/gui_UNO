package view.frames;

import javax.swing.*;

import view.components.UnoColor;
import view.components.panels.CardPilesPanel;
import view.components.panels.PlayerCardPanel;
import view.components.unoCards.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameWithCards extends GameSpaceFrame {
    
    private PlayerCardPanel[] playerCardPanels;
    private CardPilesPanel cardPilesPanel;
    private int activePlayerIndex = 0; 
    private UnoCardButton[][] allPlayerCards; // Store all player cards
    
    private static final int CARD_WIDTH = 60;
    private static final int CARD_HEIGHT = 96;
    private static final int BOTTOM_PANEL_MARGIN = 50; // Increased margin for bottom panel
    
    public GameWithCards() {
        super();
        setTitle("UNO Game With Card Panels");
        
        allPlayerCards = new UnoCardButton[4][];
        initializeCardPanels();
        initializeCardPiles();
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updatePanelPositions();
            }
        });
        
        updatePanelPositions();
        updateActivePlayer(0); 
    }
    
    private void initializeCardPanels() {
        playerCardPanels = new PlayerCardPanel[4];
        
        // Create panel for each player with appropriate orientation
        playerCardPanels[0] = new PlayerCardPanel(PlayerCardPanel.Position.BOTTOM, true);
        playerCardPanels[1] = new PlayerCardPanel(PlayerCardPanel.Position.RIGHT, false);
        playerCardPanels[2] = new PlayerCardPanel(PlayerCardPanel.Position.TOP, false);
        playerCardPanels[3] = new PlayerCardPanel(PlayerCardPanel.Position.LEFT, false);
        
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
        
        // Position bottom panel with increased margin from bottom
        Dimension bottomSize = playerCardPanels[0].getPreferredSize();
        playerCardPanels[0].setBounds(
            (frameWidth - bottomSize.width) / 2,
            frameHeight - bottomSize.height - BOTTOM_PANEL_MARGIN, // Increased margin
            bottomSize.width,
            bottomSize.height
        );
        
        // Position right panel with simpler centering - increased margin from right edge
        Dimension rightSize = playerCardPanels[1].getPreferredSize();
        int rightPanelY = (frameHeight - rightSize.height) / 2;
        playerCardPanels[1].setBounds(
            frameWidth - rightSize.width - 30, // Increased margin from 20 to 30
            rightPanelY,
            rightSize.width,
            rightSize.height
        );
        
        // Position top panel
        Dimension topSize = playerCardPanels[2].getPreferredSize();
        playerCardPanels[2].setBounds(
            (frameWidth - topSize.width) / 2,
            20,
            topSize.width,
            topSize.height
        );
        
        // Position left panel with simpler centering - increased margin from left edge
        Dimension leftSize = playerCardPanels[3].getPreferredSize();
        int leftPanelY = (frameHeight - leftSize.height) / 2;
        playerCardPanels[3].setBounds(
            30, // Increased margin from 20 to 30
            leftPanelY,
            leftSize.width,
            leftSize.height
        );
        
        // Position card piles
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
            // Store the cards for this player
            allPlayerCards[playerIndex] = cards;
            
            // Update the cards display with appropriate face-up/face-down state
            updatePlayerCardsFacing(playerIndex);
        }
    }
    
    private void updatePlayerCardsFacing(int playerIndex) {
        if (allPlayerCards[playerIndex] == null) return;
        
        // Determine if these cards should be face up
        boolean isFaceUp = (playerIndex == activePlayerIndex) || 
                          (playerIndex == 0 && playerCardPanels[0].isHumanPlayer());
        
        // Set face up/down state for cards before displaying them
        for (UnoCardButton card : allPlayerCards[playerIndex]) {
            card.setFaceUp(isFaceUp);
        }
        
        //display the cards in the panel
        playerCardPanels[playerIndex].setCards(allPlayerCards[playerIndex]);
    }
    
    public void setDiscardPileCard(UnoCardButton card) {
        cardPilesPanel.setDiscardPileCard(card);
    }
    
    public void updateActivePlayer(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < playerCardPanels.length) {
            //update active status for all panels
            for (int i = 0; i < playerCardPanels.length; i++) {
                playerCardPanels[i].setActive(i == playerIndex);
            }
            
            //set the active player index
            activePlayerIndex = playerIndex;
            
            //update the face up/down state for all player cards
            for (int i = 0; i < playerCardPanels.length; i++) {
                updatePlayerCardsFacing(i);
            }
        }
    }
    
    public void nextPlayer() {
        updateActivePlayer((activePlayerIndex + 1) % playerCardPanels.length);
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
                int cardCount = 20;
                frame.setPlayerCards(i, createRandomCards(cardCount));
            }
            
            UnoCardButton discardCard = createRandomCards(1)[0];
            frame.setDiscardPileCard(discardCard);
            
            frame.updateActivePlayer(0);
            
            frame.setVisible(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            
            // Demo: Change active player every few seconds
            new Timer(3000, e -> frame.nextPlayer()).start();
        });
    }
}