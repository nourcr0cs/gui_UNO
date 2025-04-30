public class SimpleCard extends Card {

    private String type = "simple"; // Ajoutez cette ligne
    public SimpleCard(String value, String color) {
        super(value, color);
    }

    

    @Override
    public void effectCard(Player player, Deck deck, Game game) {
        // Simple cards have no effect
    }

    @Override
    public boolean isPlayable(Card topDiscardPile, String colorToPlay) {
        return this.getColor().equals(colorToPlay) || 
               this.getValue().equals(topDiscardPile.getValue());
    }

   @Override
    public String getType() {
        return type;
    }
}

