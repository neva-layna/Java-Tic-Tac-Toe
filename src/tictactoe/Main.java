package tictactoe;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public enum Step {
        X,
        O,
        XO,
        NOBODY
    }

    public  static void drawField(char[] cells) {
        int index = 1;
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                if (j == 0 || j == 5) {
                    System.out.print("| ");
                }
                if (j % 2 == 0) {
                    System.out.print(cells[index - 1] + " ");
                    index++;
                }
            }
            System.out.print("\n");
        }
        System.out.println("---------");
    }

    public static int countXO(char[] XO) {
        int xCounter = 0;
        int oCounter = 0;

        for (char c : XO) {
            xCounter = c == 'X' ? ++xCounter : xCounter;
            oCounter = c == 'O' ? ++oCounter : oCounter;
        }
        return xCounter - oCounter;
    }
    public static boolean vertWin(char[][] xo, char sym) {
        boolean win = false;
        for (int i = 0; i < 3; i++) {
            if (xo[0][i] == sym) {
                win = true;
                for (int j = 0; j < 3; j++) {
                    if (xo[j][i] != sym) {
                        win = false;
                        break;
                    }
                }
            }
        }
        return win;
    }
    public static boolean horzWin(char[][] xo, char sym) {
        boolean win = false;
        for (int i = 0; i < 3; i++) {
            if (xo[i][0] == sym) {
                win = true;
                for (int j = 0; j < 3; j++) {
                    if (xo[i][j] != sym){
                        win = false;
                        break;
                    }
                }
            }
        }
        return win;
    }
    public static boolean diagWin(char[][] xo, char sym) {
        return (xo[0][0] == sym && xo[1][1] == sym && xo[2][2] == sym) || (xo[0][2] == sym && xo[1][1] == sym && xo[2][0] == sym);
    }
    public static char[][] arrayFiller(char[] cells) {
        char[][] xo = new char[3][3];
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                xo[i][j] = cells[k];
                k++;
            }
        }
        return xo;
    }
    public static Step winsXO(char[] cells) {
        char[][] xo = arrayFiller(cells);
        Step xWin = Step.XO;
        Step oWin = Step.XO;
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                xo[i][j] = cells[k];
                k++;
            }
        }
        xWin = vertWin(xo, 'X') || (horzWin(xo, 'X') || (diagWin(xo, 'X'))) ? Step.X : Step.XO;
        oWin = vertWin(xo, 'O') || (horzWin(xo, 'O') || (diagWin(xo, 'O'))) ? Step.O : Step.XO;
        if (xWin == Step.X && oWin == Step.O) {
            return Step.XO;
        } else if (xWin == Step.X) {
            return Step.X;
        } else if (oWin == Step.O) {
            return Step.O;
        } else {
            return Step.NOBODY;
        }
    }

    public static boolean drawFinish(char[] cells) {
        for (char cell : cells) {
            if (cell == '_') {
                return false;
            }
        }
        return true;
    }

    public static boolean gameFinish(char[] cells) {
        if (Math.abs(countXO(cells)) > 2 || winsXO(cells) == Step.XO) {
            System.out.println("Impossible");
            return false;
        } else if (winsXO(cells) == Step.X) {
            System.out.println("X wins");
            return false;
        }  else if (winsXO(cells) == Step.O) {
            System.out.println("O wins");
            return false;
        } else if (drawFinish(cells)) {
            System.out.println("Draw");
            return false;
        } else {
            return true;
        }
    }
    public static Integer tryParse(String num) {
        try {
            return Integer.parseInt(num);
        } catch(NumberFormatException e) {
            return null;
        }
    }
    public static int[] cordFiller(String line) {
        String[] lineArray = line.split("\\s+");
        int[] cord = new int[2];
        for (int i = 0; i < lineArray.length; i++) {
            if (tryParse(lineArray[i]) != null) {
                cord[i] = tryParse(lineArray[i]) - 1;
                if (cord[i] < 0 || cord[i] > 2) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    return null;
                }
            } else {
                System.out.println("You should enter numbers!");
                return null;
            }
            if (i > 2) {
                System.out.println("You should enter only 2 numbers!");
                return null;
            }
        }
        cord[1] = cord[1] == 2 ? 0 : cord[1] == 0 ? 2 : 1;
        return cord;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] cells = new char[9];
        String line;
        Arrays.fill(cells, '_');
        int[] cord;
        Step whoseStep = Step.X;
        drawField(cells);
        do {
            whoseStep = countXO(cells) == 0 ? Step.X : Step.O;
            System.out.print("Enter the coordinates: ");
            line = scanner.nextLine();
            if ((cord = cordFiller(line)) != null) {
                if (cells[cord[1] * 3 + cord[0]] == '_') {
                    cells[cord[1] * 3 + cord[0]] = whoseStep == Step.X ? 'X' : 'O';
                    drawField(cells);
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            }
        } while (gameFinish(cells));
    }
}
