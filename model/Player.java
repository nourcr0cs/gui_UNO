
import java.util.Stack;


public abstract class Player {
    private String name;
    private Stack<Card> hand;
    protected Player prev; // Reference to the previous player
    protected Player next; // Reference to the next player
    private boolean hasDrawn1Card; // Tracks if the player has drawn a card

    // Constructor
    Player(Player prev, Player next, String name) {
        this.prev = prev;
        this.next = next;
        this.name = name;
        this.hand = new Stack<>();
        this.hasDrawn1Card = false;
    }

    // Getter for name
    public String getName() {
        return this.name;
    }

    // Getter for hand
    public Stack<Card> getHand() {
        return this.hand;
    }

    // Getter for hand size
    public int getHandSize() {
        return this.hand.size();
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Add a card to the player's hand
    public void addCard(Card card) {
        this.hand.push(card);
    }

    // Remove a card from the player's hand
    public void removeCard(Card card) {
        this.hand.remove(card);
    }

    // Check if the player has no cards left
    public boolean hasNoCardsLeft() {
        return this.hand.isEmpty();
    }

    // Check if the player has a playable card
    public boolean hasPlayableCard(Card topCard, String colorToPlay) {
        for (Card card : this.hand) {
            if (isMoveValid(card, topCard, colorToPlay)) {
                return true;
            }
        }
        return false;
    }
    

    public boolean isMoveValid(Card card, Card topCard, String colorToPlay) {
        if (card instanceof WildCard) {
            return true; // Wild toujours jouable
        }
        return card.getColor().equals(colorToPlay) || 
               card.getValue().equals(topCard.getValue());
    }
    
    

    // Remove a card from the player's hand
    public void removeFromHand(Card card) {
        this.hand.remove(card);
    }

    // Draw a card from the deck
    public boolean drawCard(Deck deck, Stack<Card> pileOfGame) {
        if (deck.isEmpty()) {
            return false;
        }
        Card card = deck.drawingFromDeck(pileOfGame);
        this.hand.push(card);
        return true;
    }

    // Select a card to play
    public Card selectCardToPlay(Card topCard,String color) {
        for (Card card : this.hand) {
            if (isMoveValid(card, topCard,color)) {
                return card;
            }
        }
        return null;
    }

    // Abstract method for making a move
    public abstract Card makeMove(Card topCard,String color);


    /* 
    // Display the player's hand
    public void displayHand() {
        System.out.println(this.name + "'s hand: " + this.hand);
    }*/

    //Getter for hasDrawn1Card
    public boolean hasDrawn1Card() {
        return this.hasDrawn1Card;
    }

    // Setter for hasDrawn1Card
    public void setHasDrawn1Card(boolean hasDrawn1Card) {
        this.hasDrawn1Card = hasDrawn1Card;
    }

    // Getter for next player
    public Player getNext() {
        return this.next;
    }

    // Setter for next player
    public void setNext(Player next) {
        this.next = next;
    }

    // Getter for previous player
    public Player getPrev() {
        return this.prev;
    }

    // Setter for previous player
    public void setPrev(Player prev) {
        this.prev = prev;
    }

    // Method to add a card to the player's hand
    public void addToHand(Card card) {
        this.hand.push(card);
    }

    @Override
    public String toString() {
        return "Player{name='" + name + "', hand=" + hand + '}';
    }

    public abstract String chooseColor();
}


