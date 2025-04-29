
public abstract class Card {
    private String value;
       private String color;
       private String type;

       public Card(String value, String color) {
           this.value = value;
           this.color = color;
           
       }

       public String getValue() {
           return value;
       }

       public String getColor() {
           return color;
       }

       public String getType() {
           return type;
       }

       public void setColor(String color ){
           this.color=color;
       }

       // will use this logic later on i will just leave it here until then
       /*public boolean isCompatible(Card comparator) {
           return (comparator.getType().equals("Wild") ||
                   comparator.getColor().equals(this.color) ||
                   comparator.getValue().equals(this.value));
       }*/

       // to check the value of top card to know which player is the next
       // this specialcards do not allow the next player to play
       public boolean nextNextPlayer(Card card) {
           return card.getType().equalsIgnoreCase("skip") ||
                   card.getType().equalsIgnoreCase("draw") ||
                   card.getType().equalsIgnoreCase("wild") ||
                   card.getValue().equals("+2");
       }

       // to check if we should to reverse the game

       public boolean isReverseCard() {
           return this.getType().equalsIgnoreCase("special") &&
                   this.getValue().equalsIgnoreCase("Reverse");
       }


       public abstract void effectCard(Player player, Deck deck,Game game);
       abstract boolean isPlayable(Card topDiscardPile, String colorToPlay);


       // to specify the effect of card
       public int checkCardType() {
           // Check if the card is a Skip card
           if (this.getValue().equalsIgnoreCase("Skip")) {
               return 1;
           }
           // Check if the card is a +2 card
           if (this.getValue().equals("+2")) {
               return 2;
           }
           // Check if the card is a +4 Wild card
           if (this.getValue().equals("+4") && this.getType().equalsIgnoreCase("Wild")) {
               return 3;
           }
           // If none of the above, return 0
           return 0;
       }
       @Override
       public String toString() {
       String colorCode;
       switch (color) {
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
   return colorCode + "(" + color + ") " + value + ConsoleColors.RESET;
}


       

       // cards like ChangeColor and Draw four
       /*public static class WildCard extends Card {
           public WildCard(String value) {
               super(value, "none", "wild");
           }



           @Override

           public void effectCard(Player player,Deck deck,Game game){
           
           // use the methode of class game 
           game.wildEffect(player, deck);
           
           }
       }*/

       
   
}