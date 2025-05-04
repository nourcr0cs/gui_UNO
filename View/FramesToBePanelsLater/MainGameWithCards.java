/********************* Supposed to be the Game Panel in Main Frame ***********************************/
package View.FramesToBePanelsLater;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Controller.GamePageController;
import Model.Card;
import Model.Player;
import View.components.UnoColor;
import View.components.panels.CardPilesPanel;
import View.components.panels.ColorChooserOverlay;
import View.components.panels.PlayerCardPanel;
import View.components.unoCards.UnoActionCardButton;
import View.components.unoCards.UnoCardBackButton;
import View.components.unoCards.UnoCardButton;
import View.components.unoCards.UnoNumberCardButton;
import View.components.unoCards.UnoWildCardButton;

public class MainGameWithCards extends GameSpaceFrame {

	private PlayerCardPanel[] playerCardPanels;
	private CardPilesPanel cardPilesPanel;
	private int activePlayerIndex = 0;
	private UnoCardButton[][] allPlayerCards; // Store all player cards

	private GamePageController controller; // 🔹 Le lien vers le contrôleur

	// methode bch nconicti m3a controller
	public void setController(GamePageController controller) {
		this.controller = controller;
	}

	private static final int CARD_WIDTH = 60;
	private static final int CARD_HEIGHT = 96;
	private static final int BOTTOM_PANEL_MARGIN = 50; // Increased margin for bottom panel

	/*
	 * public GameWithCards() { super(); setTitle("UNO Game With Card Panels");
	 * 
	 * allPlayerCards = new UnoCardButton[4][]; initializeCardPanels();
	 * initializeCardPiles();
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * addComponentListener(new ComponentAdapter() {
	 * 
	 * @Override public void componentResized(ComponentEvent e) {
	 * updatePanelPositions(); } });
	 * 
	 * updatePanelPositions(); updateActivePlayer(0); }
	 */

	public MainGameWithCards() {
		super(); // appel du GameSpaceFrame
		setTitle("UNO Game With Card Panels");

		setSize(1000, 700); // ✅ TRES IMPORTANT
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // ✅ centré
		setLayout(null); // si tu veux placer manuellement

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
		playerCardPanels[0].setBounds((frameWidth - bottomSize.width) / 2,
				frameHeight - bottomSize.height - BOTTOM_PANEL_MARGIN, // Increased margin
				bottomSize.width, bottomSize.height);

		// Position right panel with simpler centering - increased margin from right
		// edge
		Dimension rightSize = playerCardPanels[1].getPreferredSize();
		int rightPanelY = (frameHeight - rightSize.height) / 2;
		playerCardPanels[1].setBounds(frameWidth - rightSize.width - 30, // Increased margin from 20 to 30
				rightPanelY, rightSize.width, rightSize.height);

		// Position top panel
		Dimension topSize = playerCardPanels[2].getPreferredSize();
		playerCardPanels[2].setBounds((frameWidth - topSize.width) / 2, 20, topSize.width, topSize.height);

		// Position left panel with simpler centering - increased margin from left edge
		Dimension leftSize = playerCardPanels[3].getPreferredSize();
		int leftPanelY = (frameHeight - leftSize.height) / 2;
		playerCardPanels[3].setBounds(30, // Increased margin from 20 to 30
				leftPanelY, leftSize.width, leftSize.height);

		// Position card piles
		Dimension pileSize = cardPilesPanel.getPreferredSize();
		cardPilesPanel.setBounds((frameWidth - pileSize.width) / 2, (frameHeight - pileSize.height) / 2, pileSize.width,
				pileSize.height);
	}

	public void setPlayerCards(int playerIndex, UnoCardButton[] cards) {
		if (playerIndex >= 0 && playerIndex < playerCardPanels.length) {
			// Store the cards for this player
			allPlayerCards[playerIndex] = cards;

			// bch nzid event listener m3a controller ---------------------------

			if (playerIndex == 0) { // humain
				for (UnoCardButton cardButton : cards) {
					cardButton.addActionListener(e -> {
						if (controller != null) {

							controller.onCardClicked(cardButton.getAssociatedCard()); // 👉 envoyer le bouton, pas
																						// décider

						}
					});
				}
			}

			// Update the cards display with appropriate face-up/face-down state
			updatePlayerCardsFacing(playerIndex);
		}
	}

	public void updatePlayerCardsFacing(int playerIndex) {
		if (allPlayerCards[playerIndex] == null)
			return;

		// Determine if these cards should be face up
		boolean isFaceUp = (playerIndex == activePlayerIndex)
				|| (playerIndex == 0 && playerCardPanels[0].isHumanPlayer());

		// Set face up/down state for cards before displaying them
		for (UnoCardButton card : allPlayerCards[playerIndex]) {
			card.setFaceUp(isFaceUp);
		}

		// display the cards in the panel
		playerCardPanels[playerIndex].setCards(allPlayerCards[playerIndex]);
	}

	public void setDiscardPileCard(UnoCardButton card) {
		cardPilesPanel.setDiscardPileCard(card);
	}

	public void updateActivePlayer(int playerIndex) {
		if (playerIndex >= 0 && playerIndex < playerCardPanels.length) {
			// update active status for all panels
			for (int i = 0; i < playerCardPanels.length; i++) {
				playerCardPanels[i].setActive(i == playerIndex);
			}

			// set the active player index
			activePlayerIndex = playerIndex;

			// update the face up/down state for all player cards
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
		UnoColor[] colors = { UnoColor.RED, UnoColor.BLUE, UnoColor.GREEN, UnoColor.YELLOW };

		Dimension smallerCardSize = new Dimension(CARD_WIDTH, CARD_HEIGHT);

		for (int i = 0; i < count; i++) {
			UnoColor randomColor = colors[(int) (Math.random() * colors.length)];
			int type = (int) (Math.random() * 10);

			if (type < 7) {
				int number = (int) (Math.random() * 10);
				cards[i] = new UnoNumberCardButton(randomColor, number);
				cards[i].setPreferredSize(smallerCardSize);
			} else if (type < 9) {
				int actionType = (int) (Math.random() * 3);
				UnoActionCardButton.ActionType[] types = UnoActionCardButton.ActionType.values();
				cards[i] = new UnoActionCardButton(randomColor, types[actionType]);
				cards[i].setPreferredSize(smallerCardSize);
			} else {
				// wild card
				int wildType = (int) (Math.random() * 2);
				UnoWildCardButton.WildType[] types = UnoWildCardButton.WildType.values();
				cards[i] = new UnoWildCardButton(types[wildType]);
				cards[i].setPreferredSize(smallerCardSize);
			}
		}

		return cards;
	}

	/*
	 * // bch ybdel top card public void updateTopCard(Card card) { if (controller
	 * != null) { UnoCardButton button = controller.convertCardToButton(card);
	 * setDiscardPileCard(button); // méthode déjà existante dans GameWithCards } }
	 * 
	 * /* public void updateHands(java.util.List<Modell.Player> players) { for (int
	 * i = 0; i < players.size(); i++) { Modell.Player player = players.get(i);
	 * UnoCardButton[] buttons = controller.generateCardButtons(player.getHand());
	 * setPlayerCards(i, buttons); } }
	 */

	public void updateTopCard(Card card) {
		if (card == null) {
			System.err.println("❌ ERREUR : carte null reçue dans updateTopCard()");
			return; // on ne fait rien pour éviter le crash
		}

		if (controller != null) {
			try {
				UnoCardButton button = controller.convertCardToButton(card);
				setDiscardPileCard(button); // méthode déjà existante
			} catch (Exception e) {
				System.err.println("❌ Exception dans convertCardToButton : " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void showGameOver(String winnerName) {
		JOptionPane.showMessageDialog(this, "🏆 Le joueur gagnant est : " + winnerName + " 🎉", "Fin de la partie",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void showColorChooser(Consumer<String> onColorChosen) {
		final ColorChooserOverlay[] overlayRef = new ColorChooserOverlay[1];

		ColorChooserOverlay overlay = new ColorChooserOverlay(color -> {
			remove(overlayRef[0]); // maintenant la variable est bien visible
			revalidate();
			repaint();
			onColorChosen.accept(color); // déclenche le retour vers le contrôleur
		});

		overlayRef[0] = overlay;

		add(overlay);
		setComponentZOrder(overlay, 0); // optionnel : mettre au premier plan
		revalidate();
		repaint();
	}

	/*
	 * public void showColorChooser(Consumer<String> callback) { ColorChooserOverlay
	 * overlay = new ColorChooserOverlay(callback); this.add(overlay); // `this` =
	 * JFrame ou JPanel principal this.repaint(); this.revalidate(); }
	 */

	JLabel effectLabel = new JLabel("", SwingConstants.CENTER);

	public void initEffectLabel() {
		effectLabel.setFont(new Font("Arial", Font.BOLD, 18));
		effectLabel.setForeground(Color.WHITE);
		effectLabel.setOpaque(true);
		effectLabel.setBackground(new Color(0, 0, 0, 150));
		effectLabel.setVisible(false);
		add(effectLabel, BorderLayout.NORTH); // ou autre layout adapté
	}

	public void showEffect(String message) {
		effectLabel.setText(message);
		effectLabel.setVisible(true);

		Timer timer = new Timer(2000, e -> effectLabel.setVisible(false));
		timer.setRepeats(false);
		timer.start();
	}

	/*
	 * public void updateHands(List<Player> players) { for (int i = 0; i <
	 * players.size(); i++) { Player player = players.get(i); if (player instanceof
	 * Human) { UnoCardButton[] buttons =
	 * controller.generateCardButtons(player.getHand()); setPlayerCards(i, buttons);
	 * // méthode qui ajoute les boutons dans un JPanel } else { setPlayerCards(i,
	 * new UnoCardButton[0]); // rien pour les bots } } }
	 */

	/*
	 * public void updateHands(List<Player> players) { for (int i = 0; i <
	 * players.size(); i++) { Player player = players.get(i);
	 * 
	 * if (player == game.getCurrentPlayer()) { // Joueur courant → afficher ses
	 * vraies cartes UnoCardButton[] buttons =
	 * controller.generateCardButtons(player.getHand()); setPlayerCards(i, buttons);
	 * } else { // Autres joueurs → afficher dos de carte (ex: 7 cartes) int count =
	 * player.getHand().size(); UnoCardButton[] hidden = new UnoCardButton[count];
	 * for (int j = 0; j < count; j++) { hidden[j] = new UnoCardBackButton(); //
	 * bouton avec image du dos de carte } setPlayerCards(i, hidden); } } }
	 */

	public void updateHands(List<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);

			// Joueur actuel → afficher main réelle
			if (player == game.getCurrentPlayer()) {
				List<Card> hand = player.getHand();
				UnoCardButton[] buttons = controller.generateCardButtons(hand);
				setPlayerCards(i, buttons); // ajoute les boutons dans le bon JPanel

				// ✅ Ajouter le clic pour jouer la carte
				for (UnoCardButton button : buttons) {
					Card card = button.getAssociatedCard();
					button.addActionListener(e -> {
						if (controller != null && card != null) {
							controller.onCardClicked(card);
						}
					});
				}
			}
			// Autres joueurs → cartes face cachée
			else {
				int count = player.getHand().size();
				UnoCardButton[] hidden = new UnoCardButton[count];

				for (int j = 0; j < count; j++) {
					hidden[j] = new UnoCardBackButton(); // dos de carte
				}

				setPlayerCards(i, hidden); // on les affiche face cachée
			}
		}
	}

	private Model.Game game;

	public void setGame(Model.Game game) {
		this.game = game;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			MainGameWithCards frame = new MainGameWithCards();
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