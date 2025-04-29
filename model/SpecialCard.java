
       // cards like Skip/Draw two/Reverse
       public  class SpecialCard extends Card {


        private String type = "special"; // Ajoutez cette ligne


        @Override
        public String getType() {
            return type; // Retourne "simple" au lieu d'une variable non définie
        }


        public SpecialCard(String value, String color) {
            super(value, color);
        }

        @Override
        
        public void effectCard(Player player, Deck deck, Game game) {
                Player nextPlayer = player.getNext();
            switch (this.getValue()) {
                case "Skip":
                    game.skipEffect(player); // Call skipEffect
                    break;
                case "+2":
                    game.draw2Effect(nextPlayer, deck); // Call draw2Effect
                    break;
                case "Reverse":
                    game.reverseEffect(); // Call reverseEffect
                    break;
            }
        }

        @Override
        public boolean isPlayable(Card topDiscardPile, String colorToPlay) {
            return this.getColor().equals(colorToPlay) || 
                this.getValue().equals(topDiscardPile.getValue());
        }

        @Override
    public String toString() {
    String colorCode;
    switch (getColor()) {
        case "Red":
            colorCode = ConsoleColors.RED;
            break;
        case "Green":
            colorCode = ConsoleColors.GREEN;
            break;
        case "Blue":
            colorCode = ConsoleColors.BLUE;
            break;
        case "Yellow":
            colorCode = ConsoleColors.YELLOW;
            break;
        case "Wild":
            colorCode = ConsoleColors.MAGENTA;
            break;
        default:
            colorCode = ConsoleColors.WHITE;
    }
return colorCode + "(" + getColor() + ") " + getValue() + ConsoleColors.RESET;
}
    }
