
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Game {

    private List<Player> players;
    private Deck deck;
    private Card topCard;
    private int numberPlayer11;
    private int indexOfCurrentPlayer = 0;
    private Stack<Card> pileOfGame;
    private boolean turnReversed = false;
    private Player winner;
    private String colorToPlay;
    private boolean skip;
    private Scanner scanner;

    public Game(Scanner scanner) {
        this.scanner = scanner;
        this.pileOfGame = new Stack<>();
        this.deck = new Deck();
        this.players = new ArrayList<>();
    }

    public Deck getDeck() {
        return this.deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Stack<Card> getPileOfGame() {
        return this.pileOfGame;
    }

    public Card getTopCard() {
        return this.topCard;
    }

    public void setTopCard(Card topCard) {
        this.topCard = topCard;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public String getColorToPlay() {
        return colorToPlay;
    }

    public void setColorToPlay(String colorToPlay) {
        this.colorToPlay = colorToPlay;
    }

    public boolean isTurnReversed() {
        return turnReversed;
    }


    ///////////////////////










    public void startGame() {
        System.out.println("\t\t*********** Let's Play UNO! ***********");
    
        initPlayers(players);
        shuffleDeck();
        distributeCards();
    
        if (players == null || players.isEmpty()) {
            throw new IllegalStateException("Cannot start the game without players!");
        }
    
        System.out.println("Game starting...");
        System.out.println("Number of players: " + players.size());
    
        for (Player player : players) {
            System.out.println("Player: " + player.getName());
        }
    
        indexOfCurrentPlayer = 0;
        this.topCard = deck.drawingFromDeck(pileOfGame);
        if (this.topCard == null) {
            throw new IllegalStateException("Deck is empty. Cannot start the game!");
        }
        pileOfGame.push(topCard);
        
        colorToPlay = topCard.getColor(); // Initialize the color to play
    
        System.out.println("Deck initialized with cards: " + deck.size());
        System.out.println("Top card initialized: " + this.topCard);
    
        while (winner == null) {
            skip = false;
            Card topDiscardPile = pileOfGame.peek();
    
            Player currentPlayer = getCurrentPlayer();

            //topDiscardPile.effectCard(currentPlayer, deck, this);

            int playableCards = scanPlayableCards(currentPlayer, topDiscardPile, colorToPlay);
    
            System.out.printf("\nCurrent Player in turn: %s\n", currentPlayer.getName());
            displayGameState();
    
            /*if (playableCards == 0) {
                System.out.printf("%s currently doesn't have any playable cards. Draws 1 card from the Draw Pile...\n", currentPlayer.getName());
                drawCards(1, currentPlayer);
                Card drawnCard = currentPlayer.getHand().peek();
                currentPlayer.setHasDrawn1Card(true);
    
                System.out.print("\nCard drawn: ");
                printCard(drawnCard);
    
                if (drawnCard.isPlayable(topDiscardPile, colorToPlay)) {
                    // If the drawn card is playable, allow the player to play it
                    
                    System.out.println("You drew a playable card! You can choose to play it or skip your turn.");
                
                    // Display the player's hand before prompting for input
                    if (currentPlayer instanceof Human) {
                        displayPlayerHand(currentPlayer); // Display the player's hand
 

                      



                        
                        System.out.println("Enter the number of the card you want to play (1-" + currentPlayer.getHandSize() + ") or 'skip' to skip your turn:");

                        String input = new Scanner(System.in).nextLine().trim();

                        if (!input.equalsIgnoreCase("skip")) {
                            try {
                                int cardIndex = Integer.parseInt(input) - 1;
                                if (isValidCardIndex(cardIndex, currentPlayer)) {
                                    Card selectedCard = currentPlayer.getHand().get(cardIndex);
                                    if (selectedCard.isPlayable(topDiscardPile, colorToPlay)) {
                                        pileOfGame.push(selectedCard);
                                        topCard = selectedCard;
                                        colorToPlay = selectedCard.getColor();
                                        currentPlayer.removeFromHand(selectedCard);




                                        topCard.effectCard(currentPlayer, deck, this);



                                    } else {
                                        System.out.println("Error: That card cannot be played on " + topDiscardPile);
                                    }
                                } else {
                                    System.out.println("Error: Invalid card index.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Please enter a valid number or 'skip'.");
                            }
                        }
                    } else if (currentPlayer instanceof Bot) {
                        // Bot will always play the drawn card if it's playable
                        String color=topCard.getColor(); 
                        Card playedCard = currentPlayer.makeMove(topDiscardPile,color);
                        
                        if (playedCard != null) {
                            pileOfGame.push(playedCard);
                            topCard = playedCard;
                            colorToPlay = playedCard.getColor();
                            currentPlayer.removeFromHand(playedCard);
                        }
                    }
                } else {
                    // If the drawn card is not playable, skip the player's turn
                    System.out.println("( Card drawn is not playable. Skipping turn... )");
                    skip = true; // Set skip flag to true
                }
            } 
            
            */














            
            
            
            
/* 

            if (playableCards == 0) {
                System.out.printf("%s currently doesn't have any playable cards. Draws 1 card from the Draw Pile...\n", currentPlayer.getName());
                drawCards(1, currentPlayer);
                Card drawnCard = currentPlayer.getHand().peek(); // Last card added
            
                if (drawnCard.isPlayable(topDiscardPile, colorToPlay)) {
                    System.out.print("\nCard drawn: ");
                    printCard(drawnCard);
            
                    if (currentPlayer instanceof Human) {
                        displayPlayerHand(currentPlayer);
                        System.out.println("You drew a playable card! You can choose to play it or skip your turn.");
                        System.out.println("Enter the number of the card you want to play (1-" + currentPlayer.getHandSize() + ") or 'skip' to skip your turn:");
            
                        String input = scanner.nextLine().trim();
            
                     /*  if (!input.equalsIgnoreCase("skip")) {
                            try {
                                int cardIndex = Integer.parseInt(input) - 1;
                                if (isValidCardIndex(cardIndex, currentPlayer)) {
                                    Card selectedCard = currentPlayer.getHand().get(cardIndex);
                                    if (selectedCard.isPlayable(topDiscardPile, colorToPlay)) {
                                        pileOfGame.push(selectedCard);
                                        topCard = selectedCard;
                                        colorToPlay = selectedCard.getColor();
                                        currentPlayer.removeFromHand(selectedCard);
                                        selectedCard.effectCard(currentPlayer, deck, this);
                                    } else {
                                        System.out.println("Error: That card cannot be played.");
                                    }
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input.");
                            }
 }// hadi /* 











 if (!input.equalsIgnoreCase("skip")) {
    try {
        int cardIndex = Integer.parseInt(input) - 1;
        if (isValidCardIndex(cardIndex, currentPlayer)) {
            Card selectedCard = currentPlayer.getHand().get(cardIndex);
            if (selectedCard == drawnCard && selectedCard.isPlayable(topDiscardPile, colorToPlay)) {
                pileOfGame.push(selectedCard);
                topCard = selectedCard;
                colorToPlay = selectedCard.getColor();
                currentPlayer.removeFromHand(selectedCard);
                selectedCard.effectCard(currentPlayer, deck, this);
                currentPlayer.setHasDrawn1Card(false); // Réinitialiser l’état
                continue; // Passer au joueur suivant
            } else {
                System.out.println("Error: You can only play the drawn card or skip.");
            }
        }
    } catch (NumberFormatException e) {
        System.out.println("Invalid input.");
    }
}

            
                    } else if (currentPlayer instanceof Bot) {
                        // Bot will always play it if it's playable
                        System.out.println(currentPlayer.getName() + " plays drawn card: " + drawnCard);
                        pileOfGame.push(drawnCard);
                        topCard = drawnCard;
                        colorToPlay = drawnCard.getColor();
                        currentPlayer.removeFromHand(drawnCard);

                        drawnCard.effectCard(currentPlayer, deck, this);
                    }
            
                } else {
                    System.out.print("\nCard drawn: ");
                    printCard(drawnCard);
                    System.out.println("( Card drawn is not playable. Skipping turn... )");
                }
            
                currentPlayer.setHasDrawn1Card(false); // Reset draw flag if you use it
                continue; // Move to next player
            }
            
            */
            
            
            
            
            
            
            
            
            
            
            if (playableCards == 0) {
                System.out.printf("%s currently doesn't have any playable cards. Draws 1 card from the Draw Pile...\n", currentPlayer.getName());
                
                drawCards(1, currentPlayer);
                Card drawnCard = currentPlayer.getHand().peek(); // Carte tirée
                System.out.print("\nCard drawn: ");
                printCard(drawnCard);
            
                if (drawnCard.isPlayable(topDiscardPile, colorToPlay)) {
                    // SI la carte piochée est jouable
                    if (currentPlayer instanceof Human) {
                        displayPlayerHand(currentPlayer);
                        System.out.println("You drew a playable card! You can choose to play it or skip your turn.");
                        System.out.println("Enter the number of the card you want to play (1-" + currentPlayer.getHandSize() + ") or 'skip' to skip your turn:");
                        
                        String input = scanner.nextLine().trim();
            
                        if (!input.equalsIgnoreCase("skip")) {
                            try {
                                int cardIndex = Integer.parseInt(input) - 1;
                                Card selectedCard = currentPlayer.getHand().get(cardIndex);
                                // Le joueur ne peut jouer que la carte piochée
                                if (selectedCard == drawnCard && selectedCard.isPlayable(topDiscardPile, colorToPlay)) {
                                    pileOfGame.push(selectedCard);
                                    topCard = selectedCard;
                                    colorToPlay = selectedCard.getColor();
                                    currentPlayer.removeFromHand(selectedCard);
                                    selectedCard.effectCard(currentPlayer, deck, this);
                                } else {
                                    System.out.println("You can only play the drawn card.");
                                }
                            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                                System.out.println("Invalid input.");
                            }
                        }
            
                    } else if (currentPlayer instanceof Bot) {
                        // Le bot joue directement la carte piochée
                        System.out.println(currentPlayer.getName() + " plays drawn card: " + drawnCard);
                        pileOfGame.push(drawnCard);
                        topCard = drawnCard;
                        colorToPlay = drawnCard.getColor();
                        currentPlayer.removeFromHand(drawnCard);
                        drawnCard.effectCard(currentPlayer, deck, this);
                    }
            
                } else {
                    // Si la carte piochée n'est pas jouable, le tour est fini
                    System.out.println("( Card drawn is not playable. Skipping turn... )");
                }
            
                // Fin du tour : on passe au joueur suivant
                continue;
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            else {
                // Existing logic for when the player has playable cards
                if (currentPlayer instanceof Human) {
                    //displayPlayerHand(currentPlayer);
                    
                } else if (currentPlayer instanceof Bot) {
                    System.out.println(currentPlayer.getName() + " is thinking...");
                    try {
                        Thread.sleep(1500); // Delay for 1.5 seconds before the bot plays
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                

                String color=topCard.getColor(); 
                Card playedCard = currentPlayer.makeMove(topDiscardPile,color);
                if (playedCard != null) {
                    pileOfGame.push(playedCard);
                    topCard = playedCard;
                    colorToPlay = playedCard.getColor();
                    currentPlayer.removeFromHand(playedCard);


                    //to make sure the wild effect is doing the job 
                    if (playedCard instanceof SpecialCard || playedCard instanceof WildCard) {
                        playedCard.effectCard(currentPlayer, deck, this);
                    }

                    
                } else {

                    drawCards(1, currentPlayer);
                }
            }
    
            checkWinner(currentPlayer);
    
            // Add a delay after the bot plays
            if (currentPlayer instanceof Bot) {
                try {
                    Thread.sleep(2000); // Delay for 2 seconds after the bot plays
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(2000); // Delay for 2 seconds after the bot plays
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    
            /*// Move to the next player
            if (turnReversed && numberPlayer11 > 1) {
                if (skip) {
                    System.out.printf("%s was skipped.\n", currentPlayer.getPrev().getName());
                    indexOfCurrentPlayer = (indexOfCurrentPlayer - 2 + numberPlayer11) % numberPlayer11;
                } else {
                    indexOfCurrentPlayer = (indexOfCurrentPlayer - 1 + numberPlayer11) % numberPlayer11;
                }
            } else {
                if (skip) {
                    System.out.printf("%s was skipped.\n", currentPlayer.getNext().getName());
                    indexOfCurrentPlayer = (indexOfCurrentPlayer + 2) % numberPlayer11;
                } else {
                    indexOfCurrentPlayer = (indexOfCurrentPlayer + 1) % numberPlayer11;
                }
            }*/






             // ✅ Avancer au joueur suivant, sauf si skipEffect() l'a déjà fait
    if (!skip) {
        advanceToNextPlayer(false);
    } else {
        //skip = false; // reset flag
        advanceToNextPlayer(true);
    }
    
            // Check if the deck is empty and no player can make a move
            if (deck.isEmpty() && !canAnyPlayerMakeMove()) {
                System.out.println("No more cards in the deck and no player can make a move. Game over!");
                break;
            }
        }
    
        if (winner != null) {
            System.out.printf("""
                    %s has no more cards left.
                    
                    GAME OVER!
                    WINNER: %s
                    """, winner.getName(), winner.getName());
        } else {
            System.out.println("Game ended in a draw!");
        }
    }





    
    // Helper method to check if any player can make a move
    private boolean canAnyPlayerMakeMove() {
        for (Player player : players) {
            if (scanPlayableCards(player, pileOfGame.peek(), colorToPlay) > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidCardIndex(int index, Player player) {
        return index >= 0 && index < player.getHandSize();
    }


    public void initPlayers(List<Player> playerList) {
        int numberOfPlayers = 0;
    
        // Keep asking for the number of players until a valid number (2-4) is entered
        while (numberOfPlayers < 2 || numberOfPlayers > 4) {
            System.out.println("Enter the number of players (2-4):");
            numberOfPlayers = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
            if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                System.out.println("Invalid number of players. Please enter a number between 2 and 4.");
            }
        }
    
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter the type of player " + (i + 1) + " (Human/Ai):");
            String type = scanner.nextLine().trim();
    
            if (type.equalsIgnoreCase("Human")) {
                System.out.println("Enter the name of Human player " + (i + 1) + ":");
                String name = scanner.nextLine().trim();
                playerList.add(new Human(null, null, name));
            } else if (type.equalsIgnoreCase("Ai")) {
                playerList.add(new Bot(null, null, "Bot " + (i + 1)));
            } else {
                System.out.println("Invalid player type. Defaulting to AI.");
                playerList.add(new Bot(null, null, "Bot " + (i + 1)));
            }
        }
    
        // Link players in a circular manner
        for (int i = 0; i < playerList.size(); i++) {
            Player currentPlayer = playerList.get(i);
            Player prevPlayer = playerList.get((i - 1 + playerList.size()) % playerList.size());
            Player nextPlayer = playerList.get((i + 1) % playerList.size());
            currentPlayer.setPrev(prevPlayer);
            currentPlayer.setNext(nextPlayer);
        }
    
        this.numberPlayer11 = players.size();
    }

        public void shuffleDeck() {
        deck.shuffleDeck();
        }

    

    public void distributeCards() {
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                Card card = deck.drawingFromDeck(pileOfGame);
                if (card != null) {
                    player.addCard(card);
                } else {
                    throw new IllegalStateException("Not enough cards in the deck to distribute!");
                }
            }
        }
    }

    public Player getCurrentPlayer() {
        return players.get(indexOfCurrentPlayer);
    }


    public int scanPlayableCards(Player player, Card topDiscardPile, String colorToPlay) {
        int playableCards = 0;
        for (Card card : player.getHand()) {
            if (card.isPlayable(topDiscardPile, colorToPlay)) playableCards++;
        }
        return playableCards;
    }

    
    public void drawCards(int quantity, Player player) {
        for (int i = 0; i < quantity; i++) {
            if (deck.isEmpty()) {
                reshuffleDeck();
            }
            Card drawnCard = deck.drawingFromDeck(pileOfGame);
            player.addCard(drawnCard);
        }
    }

    public void reshuffleDeck() {
        Card topCard = pileOfGame.pop();
        deck.getDeck().addAll(pileOfGame); // get acccess to the list
        pileOfGame.clear();
        pileOfGame.push(topCard);
        deck.shuffleDeck();
    }

    public void checkWinner(Player player) {
        if (player.getHand().isEmpty()) {
            winner = player;
        }
    }

    public void displayGameState() {
        System.out.println("*************************************************************");
        System.out.printf("        Draw Pile card count: %d, Discard Pile card count: %d\n", deck.size(), pileOfGame.size());
        System.out.printf("                        Top Card of Discard Pile: %s\n", topCard);
        System.out.println("*************************************************************");
    }
    
    public void displayPlayerHand(Player player) {
        System.out.println("***** SELECT CARD TO PLAY *****");
        System.out.println(player.getName() + "'s Hand:");
        Stack<Card> hand = player.getHand();
        for (int i = 0; i < hand.size(); i++) {
            System.out.printf("        [%d] %s\n", i + 1, hand.get(i));
        }
    }

    public void printCard(Card card) {
        System.out.println(card);
    }

    /*public void skipEffect(Player player) {
        int currentIndex = players.indexOf(player);
        int direction = turnReversed ? -1 : 1;
        
        // Skip the next player by advancing the index by 2
        currentIndex = (currentIndex + 2 * direction) % players.size();
        player = players.get(currentIndex);
    }*/






/* 
    public void skipEffect(Player currentPlayer) {
        int direction = turnReversed ? -1 : 1;
    
        // Avance de deux positions dans la bonne direction
        indexOfCurrentPlayer = (players.indexOf(currentPlayer) + 2 * direction + players.size()) % players.size();
    
        System.out.printf("%s is skipped!\n", getCurrentPlayer().getName());
    }*/




















    public void skipEffect(Player currentPlayer) {
        int direction = turnReversed ? -1 : 1;
    
        // Trouver le joueur qui sera sauté AVANT de modifier l’index
        int skippedIndex = (players.indexOf(currentPlayer) + direction + players.size()) % players.size();
        Player skippedPlayer = players.get(skippedIndex);
    
        // Maintenant on avance de deux positions (on saute ce joueur)
        indexOfCurrentPlayer = (players.indexOf(currentPlayer) + 2 * direction + players.size()) % players.size();
    
        System.out.printf("%s is skipped!\n", skippedPlayer.getName()); // ✅ Affichage correct
    }
    

    



    public void draw2Effect(Player player, Deck deck) {
        
        for (int i = 0; i < 2; i++) {
            player.drawCard(deck, this.getPileOfGame());
        }
        System.out.println(player.getName() + " has drawn 2 cards!");
    }

    public void reverseEffect() {
        turnReversed = !turnReversed; // Toggle the direction of play

        System.out.println("The direction of play has been reversed!");
    }

    public void wildEffect(Player player, Deck deck) {

        for (int i = 0; i < 4; i++) {

            player.drawCard(deck, this.getPileOfGame());
        }
        System.out.println(player.getName() + " has drawn 4 cards!");

        // Allow the current player to choose a new color
        String newColor = chooseColor();
        System.out.println("The color is now " + newColor + ".");

        // Set the new color on the top card
        Card topCard = getTopCard();
        if (topCard != null) {
            topCard.setColor(newColor);
        } else {
            System.out.println("Error: No top card available to set the color.");
        }
    }

    private String chooseColor() {
        System.out.println("Choose a new color to play (Yellow, Red, Blue, Green):");
        while (true) {
            String color = scanner.nextLine().trim();
            if (color.equalsIgnoreCase("Yellow") || color.equalsIgnoreCase("Red") ||
                color.equalsIgnoreCase("Blue") || color.equalsIgnoreCase("Green")) {
                return color; // Valid color entered
            } else {
                System.out.println("Invalid color. Please try again: Yellow, Red, Blue, or Green.");
            }
        }
    }











    public void advanceToNextPlayer(boolean skip) {
        int direction = turnReversed ? -1 : 1;
        int steps = skip ? 2 : 1;
        indexOfCurrentPlayer = (indexOfCurrentPlayer + direction * steps + players.size()) % players.size();
    }

    

    public void skipCurrentPlayer(String reason) {
        System.out.println(getCurrentPlayer().getName() + " is skipped! " + reason);
        advanceToNextPlayer(false);
    }
    



}
