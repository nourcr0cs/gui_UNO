package Modell;

public class SimpleCard extends Card {

    private String type = "simple"; // Ajoutez cette ligne



    public SimpleCard(String value, String color) {
        super(value, color, "simple");
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







    // zdtha bch tmdli  numero bch ndirha f controller bch y7tha fcarta s7i7a
    public int getNumber() {
        try {
            return Integer.parseInt(this.getValue());
        } catch (NumberFormatException e) {
            return -1; // ou autre valeur spéciale pour indiquer une erreur
        }
    }
    


}

