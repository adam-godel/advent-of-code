import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day13 {
    private static void printScreen(char[][] screen) throws InterruptedException {
        int ballX = 0, paddleX = 0;
        for (int i = 0; i < screen.length; i++)
            for (int j = 0; j < screen[i].length; j++)
                if (screen[i][j] == '◯')
                    ballX = j;
                else if (screen[i][j] == '▔')
                    paddleX = j;
        if (ballX == 0 || paddleX == 0)
            return;
        System.out.print("\033[H\033[2J");  
        System.out.flush();
        for (char[] row : screen) {
            for (char pos : row)
                System.out.print(pos);
            System.out.println();
        }
        Thread.sleep(4);
    }
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Scanner scan = new Scanner(new File("day13.txt"));
        char[][] screen = new char[20][38];
        IntcodeArcade ic = new IntcodeArcade(scan.nextLine(), screen);
        // Intcode ic = new Intcode(scan.nextLine());
        ic.program[0] = 2; boolean init = false;
        ic.inputs.add(-1l);
        while (ic.getPaused() != -1) {
            ic.execute(ic.getPaused());
            int x = (int)ic.latestOutput();
            ic.execute(ic.getPaused());
            int y = (int)ic.latestOutput();
            ic.execute(ic.getPaused());
            int id = (int)ic.latestOutput();
            if (x == -1 && y == 0)
                System.out.println("SCORE: " + id);
            else if (id == 0)
                screen[y][x] = ' ';
            else if (id == 1)
                screen[y][x] = '█';
            else if (id == 2)
                screen[y][x] = '▆';
            else if (id == 3)
                screen[y][x] = '▔';
            else if (id == 4)
                screen[y][x] = '◯';
            if (init)
                printScreen(screen);
            if (y == screen.length-1 && x == screen[0].length-1)
                init = true;
        }
        System.out.println("FINAL SCORE: " + ic.latestOutput());
    }
}