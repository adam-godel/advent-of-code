import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
public class Day11 {
    private static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day11.txt"));
        Intcode ic = new Intcode(scan.nextLine());
        HashMap<String, Integer> positions = new HashMap<>();
        int[][] map = new int[10][100];
        int x = 0, y = 0, dir = UP;
        ic.inputs.add(1l);
        while (ic.getPaused() != -1) {
            ic.execute(ic.getPaused());
            int color = (int)ic.latestOutput();
            positions.put(x + " " + y, color);
            map[map.length-1-(y+map.length/2)][x+map[0].length/2] = color;
            if (ic.getPaused() == -1)
                break;
            ic.execute(ic.getPaused());
            int turn = (int)ic.latestOutput();
            dir = turn == 0 ? (dir-1)%4 : (dir+1)%4;
            if (dir < 0) dir += 4;
            if (dir == LEFT) x--;
            else if (dir == UP) y++;
            else if (dir == RIGHT) x++;
            else if (dir == DOWN) y--;
            if (positions.get(x + " " + y) == null)
                ic.inputs.add(0l);
            else
                ic.inputs.add((long)positions.get(x + " " + y));
        }
        System.out.println(positions.size());
        for (int[] row : map) {
            for (int pos : row)
                if (pos == 1) 
                    System.out.print("X");
                else
                    System.out.print(" ");
            System.out.println();
        }
    }
}