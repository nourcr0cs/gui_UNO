/*package controller;

import Modell.Game;
import Modell.Human;
import Modell.Bot;
import view.frames.GameWithCards;

import java.util.Scanner;
import javax.swing.JFrame;
/* 
public class Teste {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // 1. Créer le jeu avec Scanner
            Scanner scanner = new Scanner(System.in);
            Game game = new Game(scanner);

            // 2. Ajouter des joueurs
            game.addPlayer(new Human("Maroua"));
            game.addPlayer(new Bot("Bot1"));
            game.addPlayer(new Bot("Bot2"));

            // 3. Démarrer le jeu
            game.startGame(); // ✅ Pas StartGame() → Java est sensible à la casse

            // 4. Créer la vue
            GameWithCards view = new GameWithCards();

            // 5. Créer le contrôleur
            GamePageController controller = new GamePageController(game, view);

            // 6. Lier la vue au contrôleur
            view.setController(controller);

            // 🔄 Mettre à jour l'état visuel
            view.updateHands(game.getPlayers());
            view.updateTopCard(game.getTopCard());
            view.updateActivePlayer(game.getCurrentPlayerIndex());

            // 7. Afficher l’interface
            view.setVisible(true);

            // 8. Lancer le tour
            controller.gererTourJoueur();
        });
    }
}   



public class Teste {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // 1. Créer le jeu avec Scanner
            Scanner scanner = new Scanner(System.in);
            Game game = new Game(scanner);

            // 2. Ajouter des joueurs
            game.addPlayer(new Human("Maroua"));
            game.addPlayer(new Bot("Bot1"));
            game.addPlayer(new Bot("Bot2"));
            
            // 3. Démarrer le jeu
            game.startGame();

            // 4. Créer la vue
            GameWithCards view = new GameWithCards();

            // 5. Créer le contrôleur
            GamePageController controller = new GamePageController(game, view);

            // 6. Lier la vue au contrôleur
            view.setController(controller);

            // 7. Mettre à jour l'état visuel (avec le bon nom de méthode)
            view.updateHands(game.getPlayers());  // Assurez-vous que c'est updateHands et non updateHandle
            view.updateTopCard(game.getTopCard());
            view.updateActivePlayer(game.getCurrentPlayerIndex());

            // 8. Afficher l'interface
            view.setSize(1000, 700);
            view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            view.setVisible(true);

            // 9. Lancer le tour
            controller.gererTourJoueur();  // Nom corrigé
        });
    }
}
*/

/* 
package controller;

import Modell.Game;
import Modell.Human;
import Modell.Bot;
import view.frames.GameWithCards;

import java.util.Scanner;

public class Teste {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // 1. Créer le jeu UNO avec Scanner
            Scanner scanner = new Scanner(System.in);
            Game game = new Game(scanner);

            // 2. Ajouter des joueurs
            game.addPlayer(new Human("Maroua"));
            game.addPlayer(new Bot("Bot1"));
            game.addPlayer(new Bot("Bot2"));

            // 3. Démarrer le jeu (pioche + distribution)
            //game.StartGame();

            // 4. Créer la vue UNO
            GameWithCards view = new GameWithCards();
            view.setTitle("UNO - Maroua");
            view.setSize(1000, 700);                 // 👈 Très important
            view.setDefaultCloseOperation(3);        // EXIT_ON_CLOSE
            view.setLocationRelativeTo(null);        // Centre la fenêtre

            // 5. Lier avec le contrôleur
            GamePageController controller = new GamePageController(game, view);
            view.setController(controller);

            // 6. Mettre à jour les éléments graphiques de départ
            view.updateTopCard(game.getTopCard());
            view.updateHands(game.getPlayers());
            view.updateActivePlayer(game.getCurrentPlayerIndex());

            // 7. Afficher l'interface graphique
            view.setVisible(true);

            // 8. Lancer le premier tour
            controller.gererTourJoueur();
        });
    }
}*/





/* 
package controller;

import Modell.Game;
import Modell.Card;
import view.frames.GameWithCards;

import javax.swing.*;
import java.util.Scanner;

public class Teste {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Créer un scanner pour les entrées utilisateur
                Scanner scanner = new Scanner(System.in);

                // Créer le jeu
                Game game = new Game(scanner);

                // Créer l'interface graphique
                GameWithCards gameView = new GameWithCards();

                // Connecter le contrôleur (si nécessaire)
                GamePageController controller = new GamePageController(game, gameView);
                gameView.setController(controller);

                // Lancer le jeu (logique)
                game.StartGame();

                // S'assurer que topCard est bien initialisée
                Card topCard = game.getTopCard();
                if (topCard == null) {
                    System.err.println("❌ topCard est null après StartGame !");
                    JOptionPane.showMessageDialog(null, "Erreur : aucune carte initiale !");
                    return;
                }

                // Afficher la première carte dans l'interface
                gameView.updateTopCard(topCard);

                // Afficher la fenêtre
                JFrame frame = new JFrame("UNO Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(gameView);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            } catch (Exception e) {
                System.err.println("❌ Une erreur est survenue : " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}*/

/* 

package controller;

import Modell.Game;
import Modell.Card;
import view.frames.GameWithCards;

import javax.swing.*;
import java.util.Scanner;

public class Teste {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Scanner scanner = new Scanner(System.in);
                Game game = new Game(scanner);

                // Crée la fenêtre principale (elle hérite déjà de JFrame)
                GameWithCards gameView = new GameWithCards();

                // Contrôleur
                GamePageController controller = new GamePageController(game, gameView);
                gameView.setController(controller);

                // Démarrer le jeu (logique)
                game.StartGame();

                // Sécurité : afficher la carte du dessus si disponible
                Card topCard = game.getTopCard();
                if (topCard != null) {
                    gameView.updateTopCard(topCard);
                } else {
                    System.err.println("⚠ topCard est null après StartGame");
                }

                // Afficher la fenêtre
                gameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameView.setLocationRelativeTo(null);
                gameView.setVisible(true);

            } catch (Exception e) {
                System.err.println("❌ Une erreur est survenue : " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
*/



package controller;

import Modell.Game;
import Modell.Player;
import Modell.Human;
import Modell.Bot;
import Modell.Card;
import view.frames.GameWithCards;

import javax.swing.*;

/* 
public class Teste {
    public static void main(String[] args) {


        SwingUtilities.invokeLater(() -> {
            try {
                // Crée le jeu sans scanner
                Game game = new Game();
      
                // Initialise les joueurs (automatiquement)
                game.addPlayer(new Human(null, null, "Joueur 1"));
                game.addPlayer(new Human(null, null, "Bot 1"));
                game.addPlayer(new Bot(null, null, "Bot 2"));
                game.addPlayer(new Bot(null, null, "Bot 3"));

                // Crée la fenêtre du jeu (hérite de JFrame)
                GameWithCards gameView = new GameWithCards();
                GamePageController controller = new GamePageController(game, gameView);
                gameView.setController(controller);

                // Démarre le jeu (logique)
                game.StartGame();
                
                
                gameView.setGame(game);


                // Affiche la carte du dessus
                Card topCard = game.getTopCard();
                if (topCard != null) {
                    gameView.updateTopCard(topCard);
                } else {
                    System.err.println("⚠️ topCard est null après StartGame");
                }

                // 🟢 Affiche les cartes du joueur dans l’interface
         //gameView.updateHands(game.getCurrentPlayer().getHand();
         gameView.updateHands(game.getPlayers());

                // Affiche la fenêtre
                gameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameView.setLocationRelativeTo(null);
                gameView.setVisible(true);

            } catch (Exception e) {
                System.err.println("❌ Une erreur est survenue : " + e.getMessage());
                e.printStackTrace();
            }
        });




        // ⏱️ Exemple : redémarrer automatiquement après 10 secondes
new java.util.Timer().schedule(new java.util.TimerTask() {
    @Override
    public void run() {
        System.out.println("🔁 Redémarrage du jeu...");
        controller.restartGame(); // ✅ restart automatique
    }
}, 10000); // 10 secondes = 10000 millisecondes

    }







}*/






//package controller;

import Modell.*;
import view.frames.GameWithCards;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Teste {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Créer le jeu
            Game game = new Game();
            GameWithCards gameView = new GameWithCards();

            // 2. Créer le contrôleur en dehors du Timer
            GamePageController controller = new GamePageController(game, gameView);
            gameView.setController(controller);
            gameView.setGame(game);

            


            game.addPlayer(new Human(null, null, "Joueur 1"));
            game.addPlayer(new Bot(null, null, "Bot 1"));
            game.addPlayer(new Bot(null, null, "Bot 2"));
            game.addPlayer(new Bot(null, null, "Bot 3"));

            // 4. Démarrer la partie
            game.StartGame();
            gameView.updateHands(game.getPlayers());
            gameView.updateTopCard(game.getTopCard());
            gameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameView.setVisible(true);

            // 5. Redémarrer automatiquement après 10 secondes
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("🔁 Redémarrage automatique du jeu...");
                    controller.restartGame(); // ✅ fonctionne maintenant
                }
            }, 10000); // 10 secondes
        });
    }
}



