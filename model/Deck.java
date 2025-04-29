import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {
    // Attributes
    private List<Card> deck;
    String[] colors = {"Yellow", "Red", "Blue", "Green"};

    // Constructor
    public Deck() {
        this.deck = new ArrayList<>();
        initDeck();
    }

    // Initialize the deck with all cards
    public void initDeck() {
        // Colored Cards From 0 to 9
        for (int i = 0; i <= 9; i++) {
            this.deck.add(new SimpleCard(Integer.toString(i), "Yellow"));
            this.deck.add(new SimpleCard(Integer.toString(i), "Red"));
            this.deck.add(new SimpleCard(Integer.toString(i), "Blue"));
            this.deck.add(new SimpleCard(Integer.toString(i), "Green"));
        }

        // Because 0 should only be one per color, we start from one (1-9 x2 per color)
        for (int i = 1; i <= 9; i++) {
            this.deck.add(new SimpleCard(Integer.toString(i), "Yellow"));
            this.deck.add(new SimpleCard(Integer.toString(i), "Red"));
            this.deck.add(new SimpleCard(Integer.toString(i), "Blue"));
            this.deck.add(new SimpleCard(Integer.toString(i), "Green"));
        }

        // 4 cards for +4 and change color
        for (int i = 0; i < 4; i++) {
            this.deck.add(new WildCard("multi")); // Change color card
            this.deck.add(new WildCard("+4"));
        }

        // Skip, Reverse, +2
        for (int i = 0; i < 4; i++) {
            String color = colors[i];
            this.deck.add(new SpecialCard("Reverse", color));
            this.deck.add(new SpecialCard("Skip", color));
            this.deck.add(new SpecialCard("+2", color));
        }
    }

    // Shuffle the deck
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    // Draw a card from the deck
    public Card drawingFromDeck(Stack<Card> pileOfCards) {
        if (deck.isEmpty()) {
            if (pileOfCards.size() > 1) {
                System.out.println("Reshuffling discard pile into deck...");
                for (int i = 0; i < pileOfCards.size() - 1; i++) {
                    deck.add(pileOfCards.get(i));
                }
                Card topCard = pileOfCards.peek();
                pileOfCards.clear();
                pileOfCards.push(topCard);
                shuffleDeck();
            } else {
                // If the discard pile is empty or has only one card, reset the deck
                System.out.println("Resetting the deck...");
                resetDeck(); // Reset the deck by reinitializing and shuffling
            }
        }
        return deck.remove(deck.size() - 1);
    }

    // Get the size of the deck
    public int size() {
        return deck.size();
    }

    // Check if the deck is empty
    public boolean isEmpty() {
        return this.deck.isEmpty();
    }

    // Reset the deck
    public void resetDeck() {
        this.deck.clear();
        initDeck();
        shuffleDeck();
    }

    // Getter for the internal deck (List<Card>)
    public List<Card> getDeck() {
        return this.deck;
    }


    public Card drawCard() {
        if (deck.isEmpty()) {
            throw new IllegalStateException("The deck is empty!");
        }
        return deck.remove(deck.size() - 1); // Remove and return the last card in the list
    }

}