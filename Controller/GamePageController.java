package Controller;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Model.Bot;
import Model.Card;
import Model.Game;
import Model.Human;
import Model.Player;
import Model.SimpleCard;
import Model.SpecialCard;
import Model.WildCard;
import View.FramesToBePanelsLater.MainGameWithCards;
import View.components.UnoColor;
import View.components.unoCards.UnoActionCardButton;
import View.components.unoCards.UnoCardButton;
import View.components.unoCards.UnoNumberCardButton;
import View.components.unoCards.UnoWildCardButton;

//import view.components;

public class GamePageController {
	private Game game;
	private MainGameWithCards view; // ou GameView selon le nom de ta classe vue

	public GamePageController(Game game, MainGameWithCards view) {
		this.game = game;
		this.view = view;
		game.StartGame();
	}

	/*
	 * public void onCardClicked(UnoCardButton button) { Card modelCard =
	 * button.getAssociatedCard(); // 👈 transforme en carte modèle
	 * 
	 * Player current = game.getCurrentPlayer();
	 * 
	 * if (!current.getHand().contains(modelCard)) return;
	 * 
	 * if (modelCard.isPlayable(game.getTopCard(), game.getColorToPlay())) { //
	 * logique métier current.removeCard(modelCard);
	 * game.getPileOfGame().push(modelCard); game.setTopCard(modelCard);
	 * game.setColorToPlay(modelCard.getColor());
	 * 
	 * modelCard.effectCard(current, game.getDeck(), game);
	 * 
	 * // met à jour la vue view.setDiscardPileCard(button); // on peut réutiliser
	 * le bouton cliqué view.setPlayerCards(0,
	 * generateCardButtons(current.getHand())); // recréer les boutons
	 * view.updateActivePlayer(game.getCurrentPlayerIndex());
	 * 
	 * checkNextTurnOrWin(); } }
	 */

	// bch n7wl objet card lel bouton card bch y9der yclicki 3lih

	/*
	 * public UnoCardButton convertCardToButton(Card card) { UnoCardButton btn;
	 * 
	 * if (card instanceof SimpleCard) { SimpleCard simple = (SimpleCard) card; btn
	 * = new UnoNumberCardButton(simple.getColor(), simple.getNumber()); } else if
	 * (card instanceof SpecialCard) { SpecialCard special = (SpecialCard) card; btn
	 * = new UnoActionCardButton(special.getColor(), special.getActionType()); }
	 * else if (card instanceof WildCard) { WildCard wild = (WildCard) card; btn =
	 * new UnoWildCardButton(wild.getWildType()); } else { throw new
	 * IllegalArgumentException("Type de carte inconnu"); }
	 * 
	 * btn.setAssociatedCard(card); return btn; }
	 */

	// Méthode pour convertir un UnoCardButton ---> Card

	public Card convertCardButton(UnoCardButton button) {
		if (button instanceof UnoNumberCardButton) {
			UnoNumberCardButton numberButton = (UnoNumberCardButton) button;
			// Utiliser la valeur numérique et la couleur comme des String
			return new SimpleCard(Integer.toString(numberButton.getNumber()), // La valeur de la carte
					numberButton.getCardColor().toString() // Convertir la couleur en String
			);

		} else if (button instanceof UnoActionCardButton) {
			UnoActionCardButton actionButton = (UnoActionCardButton) button;
			// Utiliser des chaînes de caractères pour les types d'actions
			return new SpecialCard(actionButton.getActionType().toString(), // Le type d'action comme chaîne de
																			// caractères
					actionButton.getCardColor().toString() // Convertir la couleur en String
			);

		} else if (button instanceof UnoWildCardButton) {
			UnoWildCardButton wildButton = (UnoWildCardButton) button;
			// Utiliser "Wild" pour la carte spéciale et la couleur (si sélectionnée)
			return new WildCard(wildButton.getWildType().toString() // Le type de carte Wild comme chaîne de caractères
			);
		}

		// Si le type de bouton n'est pas reconnu
		throw new IllegalArgumentException("Unknown UnoCardButton type");
	}

	/*
	 * // Convert UnoColor to Modell.Color private Color toModelColor(UnoColor
	 * unoColor) { switch (unoColor) { case RED: return Color.RED; case GREEN:
	 * return Color.GREEN; case BLUE: return Color.BLUE; case YELLOW: return
	 * Color.YELLOW; default: return Color.BLACK; } }
	 */

	private UnoColor toUnoColor(String color) {
		switch (color.toUpperCase()) {
		case "RED":
			return UnoColor.RED;
		case "GREEN":
			return UnoColor.GREEN;
		case "BLUE":
			return UnoColor.BLUE;
		case "YELLOW":
			return UnoColor.YELLOW;
		case "BLACK":
			return UnoColor.BLACK;
		default:
			throw new IllegalArgumentException("Unknown color: " + color);
		}
	}

// Convertir un Card → UnoCardButton
	/*
	 * public UnoCardButton convertCardToButton(Card card) { UnoCardButton btn;
	 * 
	 * if (card instanceof SimpleCard) { SimpleCard simple = (SimpleCard) card;
	 * UnoColor color = toUnoColor(simple.getColor()); btn = new
	 * UnoNumberCardButton(color, Integer.parseInt(simple.getValue()));
	 * 
	 * } else if (card instanceof SpecialCard) { SpecialCard special = (SpecialCard)
	 * card; UnoColor color = toUnoColor(special.getColor());
	 * UnoActionCardButton.ActionType type =
	 * UnoActionCardButton.ActionType.valueOf(special.getValue().toUpperCase()); btn
	 * = new UnoActionCardButton(color, type);
	 * 
	 * } else if (card instanceof WildCard) { WildCard wild = (WildCard) card;
	 * UnoWildCardButton.WildType type =
	 * UnoWildCardButton.WildType.valueOf(wild.getValue().toUpperCase()); btn = new
	 * UnoWildCardButton(type);
	 * 
	 * } else { throw new IllegalArgumentException("Type de carte inconnu"); }
	 * 
	 * btn.setAssociatedCard(card); return btn; }
	 */

	public UnoCardButton convertCardToButton(Card card) {

		if (card == null) {
			System.err.println("❌ Carte NULL détectée à convertCardToButton !");
			Thread.dumpStack(); // te montre l'origine dans la console
			throw new IllegalArgumentException("Carte null reçue !");
		}

		if (card instanceof SimpleCard) {
			SimpleCard simple = (SimpleCard) card;
			UnoColor color = toUnoColor(simple.getColor());
			int number;
			try {
				number = Integer.parseInt(simple.getValue());
			} catch (NumberFormatException e) {
				number = 0; // Valeur par défaut si erreur
			}
			// return new UnoNumberCardButton(color, number);

			UnoNumberCardButton button = new UnoNumberCardButton(color, number);
			button.setAssociatedCard(card); // ✅ ici
			return button;

		} else if (card instanceof SpecialCard) {
			SpecialCard special = (SpecialCard) card;
			UnoColor color = toUnoColor(special.getColor());
			String value = special.getValue().toUpperCase().replace(" ", "").replace("PLUS", "+");

			UnoActionCardButton.ActionType type;
			switch (value) {
			case "SKIP" -> type = UnoActionCardButton.ActionType.SKIP;
			case "REVERSE" -> type = UnoActionCardButton.ActionType.REVERSE;
			case "+2", "DRAW2" -> type = UnoActionCardButton.ActionType.DRAW2;

			default -> type = UnoActionCardButton.ActionType.SKIP; // fallback
			}

			// return new UnoActionCardButton(color, type);

			UnoActionCardButton button = new UnoActionCardButton(color, type);
			button.setAssociatedCard(card); // ✅ ici
			return button;

		} else if (card instanceof WildCard) {
			WildCard wild = (WildCard) card;
			String value = wild.getValue().toUpperCase().replace(" ", "").replace("PLUS", "+");

			UnoWildCardButton.WildType type;
			switch (value) {
			case "WILD", "COLOR" -> type = UnoWildCardButton.WildType.WILD;
			case "+4", "DRAW4", "PLUS4" -> type = UnoWildCardButton.WildType.DRAW4;

			default -> type = UnoWildCardButton.WildType.WILD; // fallback
			}

			// return new UnoWildCardButton(type);

			UnoWildCardButton button = new UnoWildCardButton(type);
			button.setAssociatedCard(card); // ✅ ici
			return button;

		}

		System.err.println("Carte inconnue : " + card + " — class = " + card.getClass().getName());

		// Jamais atteint si les classes sont bien définies
		throw new IllegalArgumentException("Type de carte inconnu : " + card);
	}

	public UnoCardButton[] generateCardButtons(List<Card> hand) {
		UnoCardButton[] buttons = new UnoCardButton[hand.size()];
		for (int i = 0; i < hand.size(); i++) {
			buttons[i] = convertCardToButton(hand.get(i)); // ✅ Ici c’est bon maintenant
		}
		return buttons;
	}

	/*
	 * public void onCardClicked(Card card) { Player current =
	 * game.getCurrentPlayer(); Card topCard = game.getTopCard();
	 * 
	 * if (!current.isMoveValid(card, topCard, game.getColorToPlay())) { return; //
	 * Clique invalide }
	 * 
	 * // Signaler la carte jouée à la logique du jeu
	 * game.jouerCarteDepuisUI(current, card); // ← méthode à créer dans Game (si
	 * elle n'existe pas déjà)
	 * 
	 * // Rafraîchir l'interface view.updateTopCard(game.getTopCard());
	 * view.updateHands(game.getPlayers());
	 * 
	 * 
	 * 
	 * view.updateActivePlayer(game.getCurrentPlayerIndex());
	 * 
	 * 
	 * if (current.hasNoCardsLeft()) { view.showGameOver(current.getName()); return;
	 * }
	 * 
	 * game.advanceToNextPlayer(false);
	 * view.updateActivePlayer(game.getCurrentPlayerIndex());
	 * 
	 * // Laisser le jeu gérer le tour suivant if (game.getCurrentPlayer()
	 * instanceof Bot) { jouerTourBot(); } }
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
	 * private void jouerTourBot() { Player bot = game.getCurrentPlayer(); if (!(bot
	 * instanceof Bot)) return;
	 * 
	 * new Timer().schedule(new TimerTask() {
	 * 
	 * @Override public void run() { Card topCard = game.getTopCard(); String
	 * colorToPlay = game.getColorToPlay();
	 * 
	 * // 1. Le bot cherche une carte jouable Card move =
	 * bot.selectCardToPlay(topCard, colorToPlay);
	 * 
	 * if (move != null) { // ✅ Il peut jouer → joue directement
	 * onCardClicked(move); } else { // ❌ Il ne peut pas jouer → pioche Card drawn =
	 * game.getDeck().drawingFromDeck(game.getPileOfGame()); bot.addCard(drawn);
	 * view.updateHands(game.getPlayers());
	 * 
	 * // 2. Vérifie si la carte piochée est jouable if (drawn.isPlayable(topCard,
	 * colorToPlay)) { // ✅ Elle est jouable → joue immédiatement
	 * onCardClicked(drawn); } else { // ❌ Elle n’est pas jouable → passer au joueur
	 * suivant game.advanceToNextPlayer(false);
	 * view.updateActivePlayer(game.getCurrentPlayerIndex());
	 * 
	 * // 🔁 Recommence si le suivant est aussi un bot if (game.getCurrentPlayer()
	 * instanceof Bot) { jouerTourBot(); // récursif mais avec Timer donc pas
	 * bloquant } } } } }, 1500); // délai pour effet visuel }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public void onDrawClicked() { Player current = game.getCurrentPlayer();
	 * 
	 * // Tirer une carte Card drawnCard =
	 * game.getDeck().drawingFromDeck(game.getPileOfGame());
	 * current.addCard(drawnCard);
	 * 
	 * // Mettre à jour la main dans la vue view.updateHands(game.getPlayers());
	 * 
	 * // Vérifier si la carte piochée est jouable if
	 * (drawnCard.isPlayable(game.getTopCard(), game.getColorToPlay())) { // Si
	 * c’est un bot → jouer directement if (current instanceof Bot) {
	 * onCardClicked(drawnCard); } else { // Si humain, laisser le joueur jouer
	 * manuellement System.out.println("Carte piochée jouable. Tu peux la jouer.");
	 * } } else { // Si la carte n’est pas jouable, passer au joueur suivant
	 * game.advanceToNextPlayer(false);
	 * view.updateActivePlayer(game.getCurrentPlayerIndex());
	 * 
	 * // Si prochain joueur est un bot, jouer automatiquement if
	 * (game.getCurrentPlayer() instanceof Bot) { jouerTourBot(); } } }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public void gererTourJoueur() { Player current = game.getCurrentPlayer();
	 * Card topCard = game.getTopCard(); String colorToPlay = game.getColorToPlay();
	 * 
	 * if (current instanceof Bot) { jouerTourBot(); // bot gère tout seul } else {
	 * if (current.hasPlayableCard(topCard, colorToPlay)) { // Joue automatiquement
	 * la première carte jouable Card cardToPlay = current.selectCardToPlay(topCard,
	 * colorToPlay); onCardClicked(cardToPlay); } else { // Sinon, il doit piocher
	 * onDrawClicked(); } } }
	 */

	/*
	 * public void appliquerTour(Card card) { Player current =
	 * game.getCurrentPlayer();
	 * 
	 * // Appliquer la carte au jeu game.jouerCarteDepuisUI(current, card);
	 * 
	 * // Mettre à jour l’interface view.updateTopCard(game.getTopCard());
	 * view.updateHands(game.getPlayers());
	 * 
	 * if (current.hasNoCardsLeft()) { view.showGameOver(current.getName()); return;
	 * }
	 * 
	 * boolean skipNext = false;
	 * 
	 * // 💥 Gestion des effets if (card instanceof SpecialCard) { String value =
	 * card.getValue().toUpperCase();
	 * 
	 * if (value.equals("SKIP")) { view.showEffect("🚫 " +
	 * game.getNextPlayer().getName() + " est sauté !"); skipNext = true;
	 * 
	 * } else if (value.equals("+2")) { Player next = game.getNextPlayer();
	 * next.drawCard(game.getDeck(), game.getPileOfGame());
	 * next.drawCard(game.getDeck(), game.getPileOfGame());
	 * view.updateHands(game.getPlayers()); view.showEffect(next.getName() +
	 * " pioche +2 !"); skipNext = true; }
	 * 
	 * } else if (card instanceof WildCard) { String value =
	 * card.getValue().toUpperCase();
	 * 
	 * if (value.equals("+4")) { Player next = game.getNextPlayer(); for (int i = 0;
	 * i < 4; i++) next.drawCard(game.getDeck(), game.getPileOfGame());
	 * view.updateHands(game.getPlayers()); view.showEffect(next.getName() +
	 * " pioche +4 !"); skipNext = true; }
	 * 
	 * if (current instanceof Human) { view.showColorChooser(chosenColor -> {
	 * card.setColor(chosenColor); game.setColorToPlay(chosenColor);
	 * view.showEffect("🎨 Couleur : " + chosenColor);
	 * game.advanceToNextPlayer(skipNext);
	 * view.updateActivePlayer(game.getCurrentPlayerIndex()); gererTourJoueur(); //
	 * continue le jeu }); return; // Attente du clic } else { String autoColor =
	 * current.chooseColor(); // implémente ça si besoin card.setColor(autoColor);
	 * game.setColorToPlay(autoColor); view.showEffect("🎨 Couleur : " + autoColor);
	 * } }
	 * 
	 * // ✅ Tour suivant game.advanceToNextPlayer(skipNext);
	 * view.updateActivePlayer(game.getCurrentPlayerIndex()); gererTourJoueur(); //
	 * Recommence }
	 */

	public void appliquerTour(Card card) {
		Player current = game.getCurrentPlayer();

		// Appliquer la carte
		game.jouerCarteDepuisUI(current, card);

		// Mise à jour de l'interface
		view.updateTopCard(game.getTopCard());
		view.updateHands(game.getPlayers());

		// Vérification de victoire
		if (current.hasNoCardsLeft()) {
			view.showGameOver(current.getName());
			return;
		}

		// ✅ skipNext doit être "effectivement final" → on utilise un tableau
		final boolean[] skipNext = new boolean[1]; // [0] = false par défaut

		// Effets des cartes spéciales
		if (card instanceof SpecialCard) {
			String value = card.getValue().toUpperCase();

			if (value.equals("SKIP")) {
				view.showEffect("🚫 " + game.getNextPlayer().getName() + " est sauté !");
				skipNext[0] = true;

			} else if (value.equals("+2")) {
				Player next = game.getNextPlayer();
				next.drawCard(game.getDeck(), game.getPileOfGame());
				next.drawCard(game.getDeck(), game.getPileOfGame());
				view.updateHands(game.getPlayers());
				view.showEffect(next.getName() + " pioche +2 !");
				skipNext[0] = true;
			}

		} else if (card instanceof WildCard) {
			String value = card.getValue().toUpperCase();

			if (value.equals("+4")) {
				Player next = game.getNextPlayer();
				for (int i = 0; i < 4; i++) {
					next.drawCard(game.getDeck(), game.getPileOfGame());
				}
				view.updateHands(game.getPlayers());
				view.showEffect(next.getName() + " pioche +4 !");
				skipNext[0] = true;
			}

			if (current instanceof Human) {
				view.showColorChooser(chosenColor -> {
					card.setColor(chosenColor);
					game.setColorToPlay(chosenColor);
					view.showEffect("🎨 Couleur : " + chosenColor);

					// continuer le tour après le choix
					game.advanceToNextPlayer(skipNext[0]);
					view.updateActivePlayer(game.getCurrentPlayerIndex());
					gererTourJoueur();
				});
				return; // On attend le clic de l'humain, donc on sort ici
			} else {
				// Choix automatique pour bot
				String autoColor = current.chooseColor();
				card.setColor(autoColor);
				game.setColorToPlay(autoColor);
				view.showEffect("🎨 Couleur : " + autoColor);
			}
		}

		// Tour suivant
		game.advanceToNextPlayer(skipNext[0]);
		view.updateActivePlayer(game.getCurrentPlayerIndex());

		// Continuer avec le prochain joueur (bot ou humain)
		gererTourJoueur();
	}

	/*
	 * 
	 * public void onCardClicked(Card card) { Player current =
	 * game.getCurrentPlayer(); if (!current.isMoveValid(card, game.getTopCard(),
	 * game.getColorToPlay())) return;
	 * 
	 * appliquerTour(card); // ✅ délègue la logique }
	 * 
	 */

	public void onCardClicked(Card card) {
		Player current = game.getCurrentPlayer();

		if (card == null) {
			System.err.println("❌ Erreur : carte cliquée est null");
			return;
		}

		// ✅ Vérifie si la carte est jouable
		boolean valide = current.isMoveValid(card, game.getTopCard(), game.getColorToPlay());
		if (!valide) {
			System.out.println("❌ Mouvement invalide : " + card.getValue() + " sur " + game.getTopCard().getValue()
					+ " avec couleur " + game.getColorToPlay());
			view.showEffect("❌ Carte non jouable !");
			return;
		}

		// ✅ Si tout est bon, jouer la carte
		System.out.println("✅ Carte jouée : " + card.getValue() + " " + card.getColor());
		appliquerTour(card);
	}

	private void jouerTourBot() {
		Player bot = game.getCurrentPlayer();
		if (!(bot instanceof Bot))
			return;

		new Timer().schedule(new TimerTask() {
			public void run() {
				Card topCard = game.getTopCard();
				String colorToPlay = game.getColorToPlay();

				Card move = bot.selectCardToPlay(topCard, colorToPlay);

				if (move != null) {
					appliquerTour(move);
				} else {
					Card drawn = game.getDeck().drawingFromDeck(game.getPileOfGame());
					bot.addCard(drawn);
					view.updateHands(game.getPlayers());

					if (drawn.isPlayable(topCard, colorToPlay)) {
						appliquerTour(drawn);
					} else {
						game.advanceToNextPlayer(false);
						view.updateActivePlayer(game.getCurrentPlayerIndex());
						gererTourJoueur();
					}
				}
			}
		}, 1200);
	}

	public void onDrawClicked() {
		Player current = game.getCurrentPlayer();
		Card drawn = game.getDeck().drawingFromDeck(game.getPileOfGame());
		current.addCard(drawn);

		view.updateHands(game.getPlayers());

		if (drawn.isPlayable(game.getTopCard(), game.getColorToPlay())) {
			if (current instanceof Bot) {
				appliquerTour(drawn);
			} else {
				System.out.println("Carte jouable. Tu peux la jouer.");
			}
		} else {
			game.advanceToNextPlayer(false);
			view.updateActivePlayer(game.getCurrentPlayerIndex());
			gererTourJoueur();
		}
	}

	/*
	 * public void gererTourJoueur() { Player current = game.getCurrentPlayer();
	 * 
	 * if (current instanceof Bot) { jouerTourBot(); }
	 * 
	 * }
	 */

	public void gererTourJoueur() {
		Player current = game.getCurrentPlayer();
		Card topCard = game.getTopCard();
		String colorToPlay = game.getColorToPlay();

		// 🔄 Affiche la main actuelle à chaque début de tour
		view.updateHands(game.getPlayers());
		view.updateTopCard(topCard);
		view.updateActivePlayer(game.getCurrentPlayerIndex());

		if (current instanceof Bot) {
			// Si c'est un bot → jouer automatiquement
			jouerTourBot();
		} else {
			// Si c’est un joueur humain :
			if (!current.hasPlayableCard(topCard, colorToPlay)) {
				// 👎 Aucune carte jouable → pioche automatiquement
				onDrawClicked();
			}
			// 👍 Sinon : attendre que le joueur clique lui-même sur une carte
			// (rien à faire ici, clic géré par listener depuis la vue)
		}
	}

	public void restartGame() {
		// Créer une nouvelle instance de Game
		Game newGame = new Game();

		// Ajouter les mêmes joueurs (tu peux cloner, ou réinitialiser leurs mains)
		for (Player p : game.getPlayers()) {
			Player nouveau = (p instanceof Bot) ? new Bot(null, null, p.getName()) : new Human(null, null, p.getName());
			newGame.addPlayer(nouveau);
		}

		// Redémarrer la logique
		this.game = newGame;
		view.setGame(newGame); // IMPORTANT : mettre à jour la vue avec le nouveau Game
		newGame.StartGame(); // distribuer, mélanger, etc.
		view.updateHands(newGame.getPlayers());
		view.updateTopCard(newGame.getTopCard());
		view.updateActivePlayer(newGame.getCurrentPlayerIndex());
	}

}
