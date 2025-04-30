// Human Player class

import java.util.Scanner;



class Human extends Player {

    private final Scanner scanner;

    // Constructor
    Human(Player prev, Player next, String name) {
        super(prev, next, name);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Card makeMove(Card topCard,String colorToPlay) {
        while (true) {
            
            
             System.out.println("\n" + getName() + "'s Turn - Your Hand:");
            for (int i = 0; i < getHand().size(); i++) {
                System.out.println((i + 1) + ": " + getHand().get(i));
            }
            System.out.println("Top card: " + topCard);
            System.out.println("Enter the number of the card you want to play (1-" + getHandSize() + ") or 'draw' to draw a card:");









            String input = getInput();
            if (input.equalsIgnoreCase("draw")) {
                // The player wants to draw a card
                return null;
            }

            try {
                int cardIndex = Integer.parseInt(input) - 1; //special card index to avoid duplicate
                if (isValidCardIndex(cardIndex)) {
                    Card selectedCard = getHand().get(cardIndex);
                    if (isMoveValid(selectedCard, topCard, colorToPlay)) {
                        {
                        // The player wants to play this card
                        return selectedCard;
                         }} else {
                        System.out.println("Error: That card cannot be played on " + topCard);
                    }
                } else {
                    System.out.println("Error: Please enter a number between 1 and " + getHandSize());
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number or 'draw'.");
            }
        }
    }

    // Helper method to get input
    private String getInput() {
        return scanner.nextLine().trim();
    }

    // Helper method to validate card index
    private boolean isValidCardIndex(int index) {
        return index >= 0 && index < getHandSize();
    }

    @Override
    public String chooseColor() {
        System.out.print("""
            Enter choice of color to play next turn:
            [1] Blue
            [2] Green
            [3] Red
            [4] Yellow
            """);
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 4) break;
                System.out.println("Invalid choice!");
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a number between 1-4.");
            }
        }
        return switch (choice) {
            case 1 -> "Blue";
            case 2 -> "Green";
            case 3 -> "Red";
            case 4 -> "Yellow";
            default -> "Blue"; // Default case (shouldn't be reached)
        };
    }




    
    
    
}
