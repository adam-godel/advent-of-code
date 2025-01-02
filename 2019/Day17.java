import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day17 {
    private static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
    private static int numIntersect(char[][] grid) {
        int result = 0;
        for (int i = 1; i < grid.length-1; i++)
            for (int j = 1; j < grid[i].length-1; j++) {
                if (grid[i][j] == '#') {
                    int count = 0;
                    if (grid[i-1][j] == '#') count++;
                    if (grid[i+1][j] == '#') count++;
                    if (grid[i][j-1] == '#') count++;
                    if (grid[i][j+1] == '#') count++;
                    if (count == 4)
                        result += i*j;
                }
            }
        return result;
    }
    private static boolean isValid(char[][] grid, int i, int j, int dir) {
        if (dir == NORTH && i-1 >= 0 && i-1 < grid.length && j >= 0 && j < grid[i].length && grid[i-1][j] == '#')
            return true;
        if (dir == EAST && i >= 0 && i < grid.length && j+1 >= 0 && j+1 < grid[i].length && grid[i][j+1] == '#')
            return true;
        if (dir == SOUTH && i+1 >= 0 && i+1 < grid.length && j >= 0 && j < grid[i].length && grid[i+1][j] == '#')
            return true;
        if (dir == WEST && i >= 0 && i < grid.length && j-1 >= 0 && j-1 < grid[i].length && grid[i][j-1] == '#')
            return true;
        return false;
    }
    private static String getInstructions(char[][] grid) {
        int curI = 0, curJ = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                if (grid[i][j] == '^') {
                    curI = i; curJ = j; break;
                }
        String result = "L,"; int dir = WEST, contPath = 0;
        while (curI != 12 || curJ != 4) {
            if (isValid(grid, curI, curJ, dir)) {
                contPath++;
                if (dir == NORTH) curI--;
                else if (dir == EAST) curJ++;
                else if (dir == SOUTH) curI++;
                else if (dir == WEST) curJ--;
            } else {
                int dirOrig = dir;
                for (int i = 0; i < 4; i++) {
                    if (i == (dir+2)%4)
                        continue;
                    if (isValid(grid, curI, curJ, i)) {
                        dir = i; break;
                    }
                }
                result += contPath + ","; contPath = 0;
                if ((dir > dirOrig && !(dir == WEST && dirOrig == NORTH)) || (dir == NORTH && dirOrig == WEST))
                    result += "R,";
                else
                    result += "L,";
            }
        }
        result += contPath;
        return result;
    }
    private static void addToInput(Intcode ic, String s) {
        char[] arr = s.toCharArray();
        for (char c : arr)
            ic.inputs.add((long)c);
        ic.inputs.add(10l);
    }
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Scanner scan = new Scanner(new File("day17.txt"));
        Intcode ic = new Intcode(scan.nextLine());
        ic.execute();
        long[] output = ic.getOutput();
        char[][] grid = new char[49][55]; int idx = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++) {
                while (output[idx] == 10) 
                    idx++;
                grid[i][j] = (char)output[idx++];
            }
        for (char[] c : grid) {
            for (char d : c)
                System.out.print(d);
            System.out.println();
        }
        System.out.println(numIntersect(grid));
        String instructions = getInstructions(grid);
        String A = "L,12,L,8,L,8";
        String B = "L,12,R,4,L,12,R,6";
        String C = "R,4,L,12,L,12,R,6";
        String main = instructions.replace(A, "A").replace(B, "B").replace(C, "C");
        scan = new Scanner(new File("day17.txt"));
        ic = new Intcode(scan.nextLine());
        addToInput(ic, main); addToInput(ic, A); addToInput(ic, B); addToInput(ic, C);
        ic.inputs.add((long)'y'); ic.inputs.add(10l);
        ic.program[0] = 2; ic.execute(); int countNL = 0; idx = 0;
        for (long l : ic.getOutput()) {
            if (countNL >= 2 && idx < ic.getOutput().length-1) {
                Thread.sleep(100);
                System.out.print("\033[H\033[2J");  
                System.out.flush();
            }
            if (l == 10) countNL++;
            else countNL = 0;
            System.out.print((char)l);
            idx++;
        }
        System.out.println(ic.latestOutput());
    }
}