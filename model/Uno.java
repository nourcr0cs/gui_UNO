import java.util.Scanner;

public class Uno {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create a Scanner instance
        Game game = new Game(scanner); // Pass the scanner to the Game constructor
        game.startGame(); // Start the game
    }
} 