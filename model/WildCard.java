    // WildCard subclass (Wild and Wild +4 cards)
    public  class WildCard extends Card {

        private String type = "wild"; // Ajoutez cette ligne


        @Override
        public String getType() {
            return type; // Retourne "simple" au lieu d'une variable non définie
        }
       
        public WildCard(String value) {
            super(value, "Wild");
        }

    

        


        @Override
    public void effectCard(Player player, Deck deck, Game game) {
    String chosenColor = player.chooseColor();
    this.setColor(chosenColor);
    game.setColorToPlay(chosenColor);
    System.out.println(player.getName() + " a choisi la couleur : " + chosenColor);

    if (this.getValue().equals("+4")) {
        Player nextPlayer = player.getNext();

        for (int i = 0; i < 4; i++) {
            nextPlayer.addToHand(deck.drawCard());
        }

        System.out.println(nextPlayer.getName() + " has draw 4 cards");
        game.skipEffect(player); // ✅ Corrigé ici
    }
}

        /*public void effectCard(Player player, Deck deck, Game game) {
            // Set the new color using the player's implementation
            String chosenColor = player.chooseColor();



            this.setColor(chosenColor); // IMPORTANT


            game.setColorToPlay(chosenColor); // Set active color
            System.out.println(player.getName() + " a choisi la couleur : " + chosenColor);


            if (this.getValue().equals("+4")) {
                Player nextPlayer = player.getNext();
                // Force next player to draw 4 cards and skip
                for (int i = 0; i < 4; i++) {
                    nextPlayer.addToHand(deck.drawCard());
                }

                System.out.println(player.getName() + " has  draw 4 cards " );
                
                game.skipEffect(nextPlayer);

               

            }
                }*/
    
        /*public void effectCard(Player player, Deck deck, Game game) {
            if (this.getValue().equals("+4")) {
                // Determine the next player
                Player nextPlayer = player.getNext();
                //game.isTurnReversed() ? player.getPrev() : 

                // Force the next player to draw 4 cards and lose their turn
                System.out.println(nextPlayer.getName() + " must draw 4 cards and lose their turn!");

                for (int i = 0; i < 4; i++) {
                    Card drawnCard = deck.drawCard();
                    nextPlayer.addToHand(drawnCard);
                }
                game.skipEffect(nextPlayer); // Skip the next player's turn

                // Prompt the current player to choose a new color
                String chosenColor = chooseColor(player, game);
                game.setColorToPlay(chosenColor); // Set the new color
                System.out.println(player.getName() + " chose the color " + chosenColor + ".");
            } else {
                // Handle regular Wild card (choose color only)
                String chosenColor = chooseColor(player, game);
                game.setColorToPlay(chosenColor);
                System.out.println(player.getName() + " chose the color " + chosenColor + ".");
            }
        }*/

            // Helper method to choose a color
            public String chooseColor(Player player, Game game) {
                if (player instanceof Human) {
                    return player.chooseColor();
                } else {
                    return player.chooseColor();
                }
            }
    
        @Override
        public boolean isPlayable(Card topDiscardPile, String colorToPlay) {
            // Wild cards are always playable
            return true;
        }
    
        @Override
        public String toString() {
            return "(Wild) " + getValue();
        }
    
        public void playWild(Player player, Game game) throws InterruptedException {
            String color;
            if (player instanceof Human) {
                color = player.chooseColor();
            } else {
                color = player.chooseColor();
            }
    
            Thread.sleep(600);
            System.out.printf("%s chose the color %s\n", player.getName(), color);
            game.setColorToPlay(color);
            Thread.sleep(1500);
        }
    
        /*public boolean attemptPlayWildDraw4(Player player, String colorToPlay, Game game) throws InterruptedException {
            boolean isSafe = true;
            boolean playSuccess = true;
    
            for (Card card : player.getHand()) {
                if (card.getColor().equals(colorToPlay)) {
                    isSafe = false;
                    break;
                }
            }
    
            if (!isSafe && player instanceof Human) {
                if (showWarningMsg) {
                    System.out.println("""
                        Caution: You are attempting to play a "Wild Draw 4" card while having other cards that match the current color being played.
                                 The next player might choose to accept your play drawing their 4 cards and losing their turn, Or they may challenge you.
                                 If they lose the challenge, they will draw and additional of 2 cards (6 total) and lose their turn.
                                 If they win, you will draw 4 cards instead while they get to play their turn next.
                                 You get to choose the next color to be played regardless of the outcome.
                                 Do you still want to play the card?
                                    [1] Yes
                                    [2] No
                                    [3] Yes (Don't show this message again)
                                    [4] No (Don't show this message again)
                        """);
                    int choice;
                    while (true) {
                        try {
                            choice = Integer.parseInt(game.getScanner().nextLine());
                            if (choice < 1 || choice > 4) throw new RuntimeException();
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid choice!");
                        }
                    }
    
                    switch (choice) {
                        case 1 -> playWildDraw4(player, colorToPlay, game);
                        case 2 -> playSuccess = false;
                        case 3 -> {
                            playWildDraw4(player, colorToPlay, game);
                            showWarningMsg = false;
                        }
                        case 4 -> {
                            playSuccess = false;
                            showWarningMsg = false;
                        }
                    }
                } else {
                    playWildDraw4(player, colorToPlay, game);
                }
            } else {
                playWildDraw4(player, colorToPlay, game);
            }
            return playSuccess;
        }
    
        private void playWildDraw4(Player player, String colorToPlay, Game game) throws InterruptedException {
            Player nextPlayer;
            if (game.isTurnReversed()) {
                nextPlayer = player.getPrev();
            } else {
                nextPlayer = player.getNext();
            }
    
            System.out.printf("%s played a Wild Draw 4.\n", player.getName());
            Thread.sleep(1500);
    
            if (nextPlayer instanceof Human) {
                System.out.print("""
                            Do you want to challenge?
                                [1] Yes
                                [2] No (Draw 4 and lose your turn)
                            """);
                int choice;
                while (true) {
                    try {
                        choice = Integer.parseInt(game.getScanner().nextLine());
                        if (choice != 1 && choice != 2) throw new RuntimeException();
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid choice!");
                    }
                }
    
                if (choice == 1) {
                    challenge(player, colorToPlay, game);
                } else {
                    System.out.printf("%s draws 4 cards and loses turn.\n", nextPlayer.getName());
                    game.drawCards(4, nextPlayer);
                    game.skipEffect(nextPlayer);
                    Thread.sleep(1500);
                }
            } else {
                int random = (int) (Math.random() * 10) + 1;
                if (random <= 3 && !player.hasDrawn1Card()) {
                    challenge(player, colorToPlay, game);
                } else {
                    System.out.printf("%s draws 4 cards and loses turn.\n", nextPlayer.getName());
                    Thread.sleep(1500);
                    game.drawCards(4, nextPlayer);
                    game.skipEffect(nextPlayer);
                }
            }
    
            String color;
            if (player instanceof Human) {
                color = userChooseColor(game.getScanner());
            } else {
                color = botChooseColor((Bot) player);
            }
    
            System.out.printf("%s chose color %s\n", player.getName(), color);
            game.setColorToPlay(color);
            Thread.sleep(1500);
        }
    
        private void challenge(Player player, String colorToPlay, Game game) throws InterruptedException {
            boolean isSafe = true;
            for (Card card : player.getHand()) {
                if (card.getColor().equals(colorToPlay)) {
                    isSafe = false;
                    break;
                }
            }
    
            Player challenger;
            if (game.isTurnReversed()) {
                challenger = player.getPrev();
            } else {
                challenger = player.getNext();
            }
    
            System.out.printf("%s wants to challenge %s for playing a Wild Draw 4 card!\n", challenger.getName(), player.getName());
            Thread.sleep(2500);
            player.displayHand();
    
            if (!isSafe) {
                System.out.printf("%s won the challenge and gets to play next turn. %s gets to draw 4 cards instead.\n",
                        challenger.getName(), player.getName());
                game.drawCards(4, player);
            } else {
                System.out.printf("%s lost the challenge and gets to draw 6 cards in total while still losing turn.\n", challenger.getName());
                game.drawCards(6, challenger);
                game.skipEffect(challenger);
            }
            Thread.sleep(2500);
        }*/
    
        /*protected String userChooseColor(Scanner scanner) {
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
                    if (choice < 1 || choice > 4) throw new RuntimeException();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid choice!");
                }
            }
            return switch (choice) {
                case 1 -> "Blue";
                case 2 -> "Green";
                case 3 -> "Red";
                case 4 -> "Yellow";
                default -> throw new IllegalStateException("Unexpected value: " + choice);
            };
        }
    
        private String botChooseColor(Bot bot) {
            int blueCardsCount = 0;
            int greenCardsCount = 0;
            int redCardsCount = 0;
            int yellowCardsCount = 0;
    
            for (Card card : bot.getHand()) {
                switch (card.getColor()) {
                    case "Blue" -> blueCardsCount++;
                    case "Green" -> greenCardsCount++;
                    case "Red" -> redCardsCount++;
                    case "Yellow" -> yellowCardsCount++;
                }
            }
    
            int[] cardColorCount = {blueCardsCount, greenCardsCount, redCardsCount, yellowCardsCount};
    
            int max = blueCardsCount;
            int botChoice = 1;
    
            for (int i = 0; i < cardColorCount.length - 1; i++) {
                if (max < cardColorCount[i + 1]) {
                    max = cardColorCount[i + 1];
                    botChoice = i + 2;
                }
            }
    
            return switch (botChoice) {
                case 1 -> "Blue";
                case 2 -> "Green";
                case 3 -> "Red";
                case 4 -> "Yellow";
                default -> throw new IllegalStateException("Unexpected value: " + botChoice);
            };
        }
    }

    

// Method to allow a human player to choose a color
/*protected String userChooseColor(Scanner scanner) {
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
            if (choice < 1 || choice > 4) throw new RuntimeException();
            break;
        } catch (Exception e) {
            System.out.println("Invalid choice!");
        }
    }
    return switch (choice) {
        case 1 -> "Blue";
        case 2 -> "Green";
        case 3 -> "Red";
        case 4 -> "Yellow";
        default -> throw new IllegalStateException("Unexpected value: " + choice);
    };
}*/
    }
