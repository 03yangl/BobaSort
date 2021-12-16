public class BobaSort {
    // game board
    private BobaCreate game;

    // creates new game board
    public BobaSort() {
        game = new BobaCreate();
    }

    // prints out the rules to the game
    public void rules() {
        StdOut.println("The rules are simple:");
        StdOut.println("1. There cannot be more than 4 boba drink \"pods\".");
        StdOut.println("2. In the end, all the boba pearls must be at the bottom"
                               + "of the drinks, with the left side of the tube"
                               + " being the bottom.");
        StdOut.println("3. In the end, all the boba drink pods must have"
                               + "the same color");
        StdOut.println("4. When moving pods around to sort the drinks, you"
                               + " cannot pour the pod unless the color matches"
                               + " the color of the pod in the recieving tube.");
        StdOut.println("5. Type \"STOP\" to end the game.");
        StdOut.println("6. Type \"RULES\" to access the rules again.");
        StdOut.println("7: Enter boba drink pour from + space "
                               + "+ boba drink pour to "
                               + "ex: 1 3.");
    }

    // prints level of the game
    public void level(int level) {
        StdOut.println("Level: " + level);
    }

    // prints the game board every time
    public String toString() {
        return game.toString();
    }

    // main method that actually runs the game
    public static void main(String[] args) {
        int from;
        int to;
        int level = 1;
        BobaSort bobaSort = new BobaSort();
        StdOut.println("Welcome to BobaSort!");
        StdOut.println("Your goal is to sort all the boba drinks into matching"
                               + "tubes!");
        bobaSort.rules();
        bobaSort.level(level);
        bobaSort.game.setGame();
        // loop until there is no more inputs from StdIn
        while (StdIn.hasNextLine()) {
            // loop until the this level of game is completely solved
            while (!bobaSort.game.completeSorting()) {
                // taking input from StdIn
                String input = StdIn.readLine();

                // if user types "STOP" like the rules, then the game stops
                if (input.equals("STOP")) {
                    break;
                }

                // printing the rules again if user requests
                if (input.equals("RULES")) {
                    bobaSort.rules();
                    input = StdIn.readLine();
                }
                // https://www.w3schools.com/java/java_try_catch.asp
                try {
                    from = Integer.parseInt(String.valueOf(input.charAt(0)));
                    to = Integer.parseInt(String.valueOf(input.charAt(2)));
                    bobaSort.game.solveTubes(from - 1, to - 1);
                    bobaSort.game.setGame();
                }
                catch (IllegalArgumentException wrongInput) {
                    System.out.println("Wrong Input format.");
                }
                catch (IndexOutOfBoundsException outOfBoundsException) {
                    System.out.println("Please enter a pair of numbers.");
                }
            }

            // clear the screen after completing each level
            StdDraw.clear();
            
            // update level after successfully completing a game
            level++;
            bobaSort.level(level);

            // presenting a new game
            bobaSort = new BobaSort();
            bobaSort.game.setGame();
        }

    }
}

