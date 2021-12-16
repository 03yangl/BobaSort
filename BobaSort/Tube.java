import java.awt.Color;
import java.awt.Font;

public class Tube {
    // color table that associates string representation with human language
    private static final ST<String, String> COLORTABLE =
            new ST<String, String>();
    // color of the boba will always be a constant black
    private static final Color BOBACOLOR = Color.black;
    // at most 4 colors will be in the tube.
    // this can be changed to change difficulty of the game
    private static final int CAPACITY = 4;
    // this is the possible colors that the boba drinks will have
    private static final Color[] COLORARRAY = {
            Color.red, Color.green,
            Color.yellow, Color.blue,
            Color.pink, Color.orange,
            Color.gray, Color.cyan,
            Color.magenta
    };
    // the stack of colors of drinks
    private Stack<Color> colorStack;
    // the topmost color in the stack used for comparisons
    private Color topColor;

    // the job of Tube is to create a stack of colors that represents a tube
    public Tube(Stack<Color> colors) {
        // default initializations
        colorStack = colors;
        topColor = topColor();

        // human language version of colors
        String[] colorNames = {
                "red", "green", "yellow", "blue", "pink",
                "orange", "gray", "cyan", "magenta"
        };

        // tilling the symbol table with these colors
        // the key is the string representation that Color.toString() returns
        // the value is the actual name of the color
        for (int i = 0; i < COLORARRAY.length; i++) {
            COLORTABLE.put(COLORARRAY[i].toString(), colorNames[i]);
        }

        // boba color will have it's own key to be named boba instead of black
        COLORTABLE.put(BOBACOLOR.toString(), "boba");
    }

    // returns number of items in the stack (fullness of the tube)
    public int fillLevel() {
        return colorStack.size();
    }

    // checks if the tube is at max capacity
    public boolean isFull() {
        return fillLevel() == CAPACITY;
    }

    // checks if the tube has no drink
    public boolean isEmpty() {
        return colorStack.isEmpty();
    }

    // return the color at the top of the stack
    public Color topColor() {
        if (colorStack.isEmpty()) {
            return null;
        }
        else {
            return colorStack.peek();
        }
    }

    // returns color of boba
    public static Color getBobacolor() {
        return BOBACOLOR;
    }

    // returns a copy of the color array
    public static Color[] getColorarray() {
        Color[] temp = new Color[COLORARRAY.length];
        for (int i = 0; i < COLORARRAY.length; i++) {
            temp[i] = COLORARRAY[i];
        }
        return temp;
    }

    // returns the capacity
    public static int getCapacity() {
        return CAPACITY;
    }

    // returns the stack of colors
    public Stack<Color> getColorStack() {
        return colorStack;
    }

    // checking if the tube is in a solved state
    // boba is at the bottom and colors all match
    public boolean isSolved() {
        // creating two temps, one to check with and one to
        // make a copy of original stack
        Stack<Color> temp1 = new Stack<>();
        Stack<Color> temp2 = new Stack<>();
        Color pop;

        // while the stack still has colors, copy it
        while (!colorStack.isEmpty()) {
            pop = colorStack.pop();

            // these stacks will be reversed
            temp1.push(pop);
            temp2.push(pop);
        }
        // correct the reverse of temp to reset colorStack
        while (!temp1.isEmpty()) {
            colorStack.push(temp1.pop());
        }
        // all colors have to match the one on top
        Color same = topColor;
        // fill level of temporary tube
        int tempFillLevel = temp2.size();
        // if the tube is not full or empty, it is unsolved by default
        if (!(tempFillLevel == 4 || tempFillLevel == 0)) {
            return false;
        }
        // while the temp stack isn't empty, run these checks
        while (!temp2.isEmpty()) {
            if (tempFillLevel == 4 && temp2.pop() == BOBACOLOR) {
                return true;
            }
            else if (temp2.pop() != same) {
                return false;
            }
            tempFillLevel--;
        }
        return true;
    }

    // drawing the tubes
    public void drawTube(double x) {
        // two temp stacks so the original stays the same
        Stack<Color> temp1 = new Stack<>();
        Stack<Color> temp2 = new Stack<>();
        Color pop;

        // while the stack still has colors, copy it
        while (!colorStack.isEmpty()) {
            pop = colorStack.pop();

            // these stacks will be reversed
            temp1.push(pop);
            temp2.push(pop);
        }

        // correct the reverse of temp to reset colorStack
        while (!temp1.isEmpty()) {
            colorStack.push(temp1.pop());
        }
        int temp2Size = temp2.size();

        // draw four rectangles representing each color everytime
        for (int i = 0; i < CAPACITY; i++) {
            // drawing the layout of the squares
            // dimension of each color: 5 x 5
            // dimension of each tube: 5 x 20
            StdDraw.setPenColor(Color.black);
            StdDraw.rectangle(x, 30 + i * 5, 2.5, 2.5);
            // draw the color if there is color
            if (i < temp2Size) {
                Color penColor = temp2.pop();
                // if color is boba, then present the text boba
                if (penColor == BOBACOLOR) {
                    Font font = new Font("Arial", Font.BOLD, 8);
                    StdDraw.setFont(font);
                    StdDraw.text(x, 30 + i * 5, "BOBA");
                }
                else {
                    StdDraw.setPenColor(penColor);
                    StdDraw.filledRectangle(x, 30 + i * 5, 2.5, 2.5);
                }
            }
            // fill the rectangle with white if there is no color in the stack
            else {
                StdDraw.setPenColor(Color.white);
                StdDraw.filledRectangle(x, 30 + i * 5, 2.5, 2.5);
            }
        }
    }

    // toString method that prints out the string representation of each tube
    // object with the left side being the bottom
    public String toString() {
        // two temp stacks so the original stays the same
        Stack<Color> temp1 = new Stack<>();
        Stack<Color> temp2 = new Stack<>();
        Color pop;

        // while the stack still has colors, copy it
        while (!colorStack.isEmpty()) {
            pop = colorStack.pop();
            // these stacks will be reversed
            temp1.push(pop);
            temp2.push(pop);
        }

        // correct the reverse of temp to reset colorStack
        while (!temp1.isEmpty()) {
            colorStack.push(temp1.pop());
        }

        // creating tubes with stringBuilder
        StringBuilder str = new StringBuilder();

        // brackets will represent ends
        str.append("[ ");

        // use symbol table to return actual color names
        while (!temp2.isEmpty()) {
            str.append(COLORTABLE.get(temp2.pop().toString())).append(" ");
        }

        str.append("]");

        return str.toString();
    }

    // main to test all methods
    public static void main(String[] args) {
        Stack<Color> c1 = new Stack<>();
        c1.push(Color.blue);
        c1.push(Color.blue);
        c1.push(Color.red);
        Tube t1 = new Tube(c1);
        StdDraw.setScale(0, 100);
        t1.drawTube(10);
        StdOut.println(t1.fillLevel());
        StdOut.println(t1.isEmpty());
        StdOut.println(t1.isFull());
        StdOut.println(t1.topColor());
        StdOut.println(t1.isSolved());
        StdOut.println(t1.toString());
        StdOut.println(Tube.getBobacolor());
        StdOut.println(Tube.getColorarray());
        StdOut.println(Tube.getCapacity());
        StdOut.println(t1.getColorStack());
        Stack<Color> c2 = new Stack<>();

        Tube t2 = new Tube(c2);
        StdOut.println(t2.isSolved());
        t2.drawTube(20);

    }

}
