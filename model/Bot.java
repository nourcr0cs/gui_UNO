

import java.util.Random;



// Bot Player class
class Bot extends Player {
    private final static String[] botNames = {
            "Kevin",
            "Whispy",
            "Aurora",
    };

    // Constructor
    Bot(Player prev, Player next, String name) {
        super(prev, next, name);
    }

    static Bot[] generateBots(int total) {
        Bot[] bots = new Bot[total];

        // Create bots
        for (int i = 0; i < bots.length; i++) {
            bots[i] = new Bot(null, null, "[BOT] " + botNames[i]);
        }

        // Link Bots
        for (int i = 0; i < bots.length - 1; i++) {
            bots[i].next = bots[i + 1];
            bots[i + 1].prev = bots[i];
        }

        return bots;
    }

    @Override
    public Card makeMove(Card topCard,String colorToPlay) {
        Card cardToPlay = selectCardToPlay(topCard,colorToPlay);
        if (cardToPlay != null) {
            System.out.println(this.getName() + " plays: " + cardToPlay);
            return cardToPlay;
        } else {
            System.out.println(this.getName() + " has no playable cards and draws a card.");
            return null;
        }
    }

    /* 
    @Override
        public String chooseColor() {
            int blue = 0, green = 0, red = 0, yellow = 0;
            for (Card card : getHand()) {
                String color = card.getColor();
                switch (color) {
                    case "Blue" -> blue++;
                    case "Green" -> green++;
                    case "Red" -> red++;
                    case "Yellow" -> yellow++;
                }
            }
            int max = Math.max(Math.max(blue, green), Math.max(red, yellow));
            if (max == blue) return "Blue";
            if (max == green) return "Green";
            if (max == red) return "Red";
            return "Yellow";
        }
    }
*/

public String chooseColor() {


String[] colors = {"Red", "Green", "Blue", "Yellow"};
String chosenColor = colors[new Random().nextInt(colors.length)];

return chosenColor;

}}