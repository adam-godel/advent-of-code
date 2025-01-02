import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
public class Day15 {
    private static final long NORTH = 1, SOUTH = 2, WEST = 3, EAST = 4;
    private static class Position {
        int i, j; char c; int steps;
        Position(int i, int j, char c) {
            this.i = i; this.j = j; this.c = c;
        }
        Position(int i, int j, int steps) {
            this.i = i; this.j = j; this.steps = steps;
        }
    }
    private static boolean contains(ArrayList<Position> positions, Position p) {
        for (Position pos : positions)
            if (pos.i == p.i && pos.j == p.j)
                return true;
        return false;
    }
    private static Position search(ArrayList<Position> positions, int i, int j) {
        for (Position pos : positions)
            if (pos.i == i && pos.j == j)
                return pos;
        return null;
    }
    private static Position createPos(int i, int j, long dir, char c) {
        if (dir == NORTH)
            return new Position(i-1, j, c);
        if (dir == SOUTH)
            return new Position(i+1, j, c);
        if (dir == WEST)
            return new Position(i, j-1, c);
        if (dir == EAST)
            return new Position(i, j+1, c);
        return null;
    }
    private static void initGrid(Intcode ic, ArrayList<Position> positions) {
        long dir = NORTH; Position p = new Position(0, 0, 'S'); positions.add(p);
        for (int steps = 0; steps < 5000; steps++) {
            ic.inputs.add(dir);
            ic.execute(ic.getPaused());
            if (ic.latestOutput() == 0) {
                Position wall = createPos(p.i, p.j, dir, '#');
                if (!contains(positions, wall))
                    positions.add(wall);
                if (dir == NORTH) dir = EAST;
                else if (dir == EAST) dir = SOUTH;
                else if (dir == SOUTH) dir = WEST;
                else if (dir == WEST) dir = NORTH;
            } else {
                p = ic.latestOutput() == 1 ? createPos(p.i, p.j, dir, '.') : createPos(p.i, p.j, dir, 'O');
                if (!contains(positions, p))
                    positions.add(p);
                if (dir == NORTH) dir = WEST;
                else if (dir == WEST) dir = SOUTH;
                else if (dir == SOUTH) dir = EAST;
                else if (dir == EAST) dir = NORTH;
            }
        }
    }
    private static boolean isValid(char[][] grid, boolean[][] visited, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || visited[i][j])
            return false;
        return grid[i][j] == '.' || grid[i][j] == 'O';
    }
    private static int shortestPath(char[][] grid) {
        int startI = 0, startJ = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                if (grid[i][j] == 'S') {
                    startI = i; startJ = j; break;
                }
        Position start = new Position(startI, startJ, 0);
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<Position> queue = new LinkedList<>(); queue.add(start);
        while (!queue.isEmpty()) {
            Position cur = queue.remove();
            visited[cur.i][cur.j] = true;
            if (grid[cur.i][cur.j] == 'O')
                return cur.steps;
            if (isValid(grid, visited, cur.i-1, cur.j))
                queue.add(new Position(cur.i-1, cur.j, cur.steps+1));
            if (isValid(grid, visited, cur.i+1, cur.j))
                queue.add(new Position(cur.i+1, cur.j, cur.steps+1));
            if (isValid(grid, visited, cur.i, cur.j-1))
                queue.add(new Position(cur.i, cur.j-1, cur.steps+1));
            if (isValid(grid, visited, cur.i, cur.j+1))
                queue.add(new Position(cur.i, cur.j+1, cur.steps+1));
        }
        return -1;
    }
    private static int floodFill(char[][] grid) {
        int startI = 0, startJ = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                if (grid[i][j] == 'O') {
                    startI = i; startJ = j; break;
                }
        Position start = new Position(startI, startJ, 0);
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<Position> queue = new LinkedList<>(); queue.add(start);
        int maxSteps = 0;
        while (!queue.isEmpty()) {
            Position cur = queue.remove();
            visited[cur.i][cur.j] = true;
            if (cur.steps > maxSteps)
                maxSteps = cur.steps;
            if (isValid(grid, visited, cur.i-1, cur.j))
                queue.add(new Position(cur.i-1, cur.j, cur.steps+1));
            if (isValid(grid, visited, cur.i+1, cur.j))
                queue.add(new Position(cur.i+1, cur.j, cur.steps+1));
            if (isValid(grid, visited, cur.i, cur.j-1))
                queue.add(new Position(cur.i, cur.j-1, cur.steps+1));
            if (isValid(grid, visited, cur.i, cur.j+1))
                queue.add(new Position(cur.i, cur.j+1, cur.steps+1));
        }
        return maxSteps;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day15.txt"));
        Intcode ic = new Intcode(scan.nextLine());
        ArrayList<Position> positions = new ArrayList<>();
        initGrid(ic, positions);
        char[][] grid = new char[41][41];
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid.length; j++) {
                Position p = search(positions, i-21, j-21);
                if (p != null)
                    grid[i][j] = p.c;
            }
        for (char[] row : grid) {
            for (char space : row)
                if (space == 0) System.out.print(' ');
                else System.out.print(space);
            System.out.println();
        }
        System.out.println(shortestPath(grid));
        System.out.println(floodFill(grid));
    }
}