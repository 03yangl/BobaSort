import java.awt.Color;

public class BobaCreate {
    // number of total tubes
    private int nTubes;
    // collection of tube objects that represent the drinks
    private Tube[] bobaTubes;

    // creates the board of solvable mixed tubes
    public BobaCreate() {
        // setting default values
        nTubes = getnTubes();
        bobaTubes = new Tube[nTubes];

        // creating tubes that are solved at fisrt
        for (int i = 0; i < nTubes; i++) {
            Stack<Color> temp = new Stack<Color>();

            // ensures that there is always 2 empty tubes at the start
            if (i < getnColors()) {
                temp.push(Tube.getBobacolor());
                for (int j = 0; j < 3; j++) {
                    temp.push(Tube.getColorarray()[i]);
                }
                bobaTubes[i] = new Tube(temp);
            }
            else {
                bobaTubes[i] = new Tube(temp);
            }
        }

        // randomizes the colors within the tubes
        for (int i = 0; i < 10000; i++) {
            // random tube indexes
            int from = StdRandom.uniform(0, nTubes - 1);
            int to = StdRandom.uniform(0, nTubes - 1);

            // randomly swap the topmost color to other tubes without regard
            // to rules
            if ((!bobaTubes[from].getColorStack().isEmpty())
                    && (bobaTubes[to].fillLevel() < Tube.getCapacity())) {
                bobaTubes[to].getColorStack()
                             .push(bobaTubes[from].getColorStack().pop());
            }
        }

    }


    // gets random number of tubes
    public int getnTubes() {
        return StdRandom.uniform(6, 9);
    }

    // gets number of colors
    public int getnColors() {
        return nTubes - 2;
    }

    // returns a copy of the tubes
    public Tube[] getBobaTubes() {
        Tube[] temp = new Tube[bobaTubes.length];

        for (int i = 0; i < bobaTubes.length; i++) {
            temp[i] = bobaTubes[i];
        }

        return temp;
    }

    // prints message when rules are violated
    public String violatingRules() {
        return "Move is not allowed.";
    }

    // returns a string representation of the tubes
    public String toString() {
        StringBuilder str = new StringBuilder();

        // prints out one tube per line
        for (int i = 0; i < nTubes; i++) {
            str.append(bobaTubes[i].toString()).append("\n");
        }

        return str.toString();
    }

    // method that allows user to move colors from one to the other
    public void solveTubes(int from, int to) {
        // if the colors don't match (unless the pod is a boba or being put into
        // an empty tube, then it is not a valid move
        if ((bobaTubes[from].topColor() != bobaTubes[to].topColor())
                && !(bobaTubes[from].topColor() == Tube.getBobacolor())
                && !(bobaTubes[to].topColor() == Tube.getBobacolor())
                && !(bobaTubes[from].topColor() == null)
                && !(bobaTubes[to].topColor() == null)) {
            StdOut.println(violatingRules());
        }

        // if the tube is full then one cannot put more into the tube
        else if (bobaTubes[to].isFull()) {
            StdOut.println(violatingRules());
        }

        // one cannot pull from an already empty tube
        else if (bobaTubes[from].isEmpty()) {
            StdOut.println(violatingRules());
        }

        // if it passes all those conditions then it is valid and the move
        // is executed
        else {
            bobaTubes[to].getColorStack()
                         .push(bobaTubes[from].getColorStack().pop());
        }

    }

    // draws the board to StdDraw
    public void setGame() {
        StdDraw.setScale(0, 100);
        double space = (80 - (5 * (double) nTubes)) / ((double) nTubes - 1);
        for (int i = 0; i < nTubes; i++) {
            bobaTubes[i].drawTube((i * (5 + space)) + 10);
        }
    }

    // checks if the game is won
    public boolean completeSorting() {
        for (int i = 0; i < nTubes; i++) {
            if (!bobaTubes[i].isSolved()) {
                return false;
            }
        }

        StdOut.println("Congratulations!");
        return true;
    }

    // main to test all methods
    public static void main(String[] args) {
        BobaCreate boba = new BobaCreate();
        StdOut.println(boba.getnColors());
        StdOut.println(boba.getnTubes());
        StdOut.println(boba.getBobaTubes());
        StdOut.println(boba.violatingRules());
        boba.solveTubes(2, 3);
        StdOut.println(boba.toString());
        StdOut.println(boba.getBobaTubes()[1].isSolved());
        boba.setGame();
        StdOut.println(boba.completeSorting());
    }
}
