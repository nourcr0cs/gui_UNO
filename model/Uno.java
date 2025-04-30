import java.util.Scanner;

public class Uno {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        Game game = new Game(scanner); 
        game.startGame(); 
    }
} 