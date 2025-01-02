import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Queue;
import java.util.LinkedList;
public class Day18 {
    private static int numKeys;
    private static class Position implements Comparable<Position> {
        int i, j, steps; ArrayList<Character> bag;
        Position(int i, int j, ArrayList<Character> bag, int steps) {
            this.i = i; this.j = j; this.bag = bag; this.steps = steps;
        }
        public boolean equals(int i, int j, ArrayList<Character> bag) {
            if (i == this.i && j == this.j && bag.size() == this.bag.size()) {
                bag.sort(Comparator.naturalOrder()); this.bag.sort(Comparator.naturalOrder());
                for (int k = 0; k < bag.size(); k++)
                    if (bag.get(k) != this.bag.get(k))
                        return false;
                return true;
            }
            return false;
        }
        public boolean isComplete() {
            return bag.size() == numKeys;
        }
        public int compareTo(Position other) {
            if (bag.size() > other.bag.size())
                return 1;
            if (bag.size() < other.bag.size())
                return -1;
            return 0;
        }
    }
    private static class FourPos {
        Position[] robots = new Position[4]; int active;
        FourPos(int[] i, int[] j, ArrayList<Character> bag, int steps, int active) {
            for (int k = 0; k < i.length; k++)
                robots[k] = new Position(i[0], j[0], bag, steps);
            this.active = active;
        }
        FourPos(Position[] robots, int active) {
            this.robots = robots; this.active = active;
        }
        public boolean equals(Position[] robots, int active) {
            for (int i = 0; i < this.robots.length; i++)
                if (!robots[i].equals(this.robots[i]))
                    return false;
            return this.active == active;
        }
    }
    private static boolean contains(ArrayList<Position> positions, int i, int j, ArrayList<Character> bag) {
        for (Position pos : positions)
            if (pos.equals(i, j, bag))
                return true;
        return false;
    }
    private static boolean isValid(char[][] grid, ArrayList<Position> positions, int i, int j, ArrayList<Character> bag) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || contains(positions, i, j, bag))
            return false;
        if (grid[i][j] == '#' || (grid[i][j] >= 'A' && grid[i][j] <= 'Z' && !bag.contains(Character.toLowerCase(grid[i][j]))))
            return false;
        return true;
    }
    private static boolean contains(ArrayList<FourPos> positions, Position[] robots, int active) {
        for (FourPos pos : positions)
            if (pos.equals(robots, active))
                return true;
        return false;
    }
    private static boolean isValid(char[][] grid, ArrayList<FourPos> positions, Position[] robots, int active) {
        if (contains(positions, robots, active))
            return false;
        for (Position p : robots) {
            if (p.i < 0 || p.i >= grid.length || p.j < 0 || p.j >= grid[p.i].length)
                return false;
            if (grid[p.i][p.j] == '#' || (grid[p.i][p.j] >= 'A' && grid[p.i][p.j] <= 'Z' && !p.bag.contains(Character.toLowerCase(grid[p.i][p.j]))))
                return false;
        }
        return true;
    }
    private static int shortestPath(char[][] grid) {
        ArrayList<Position> positions = new ArrayList<>();
        Queue<Position> queue = new LinkedList<>();
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                if (grid[i][j] == '@')
                    queue.add(new Position(i, j, new ArrayList<>(), 0));
        while (!queue.isEmpty()) {
            Position cur = queue.remove();
            positions.add(cur);
            if (grid[cur.i][cur.j] >= 'a' && grid[cur.i][cur.j] <= 'z' && !cur.bag.contains(grid[cur.i][cur.j]))
                cur.bag.add(grid[cur.i][cur.j]);
            System.out.println(cur.steps);
            if (cur.isComplete())
                return cur.steps;
            if (isValid(grid, positions, cur.i-1, cur.j, cur.bag))
                queue.add(new Position(cur.i-1, cur.j, new ArrayList<>(cur.bag), cur.steps+1));
            if (isValid(grid, positions, cur.i+1, cur.j, cur.bag))
                queue.add(new Position(cur.i+1, cur.j, new ArrayList<>(cur.bag), cur.steps+1));
            if (isValid(grid, positions, cur.i, cur.j-1, cur.bag))
                queue.add(new Position(cur.i, cur.j-1, new ArrayList<>(cur.bag), cur.steps+1));
            if (isValid(grid, positions, cur.i, cur.j+1, cur.bag))
                queue.add(new Position(cur.i, cur.j+1, new ArrayList<>(cur.bag), cur.steps+1));
        }
        return -1;
    }
    private static int shortestFour(char[][] grid) {
        int[] iVal = new int[4], jVal = new int[4]; int idx = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                if (grid[i][j] == '@') {
                    iVal[idx] = i; jVal[idx++] = j;
                }
        ArrayList<FourPos> positions = new ArrayList<>();
        Queue<FourPos> queue = new LinkedList<>();
        queue.add(new FourPos(iVal, jVal, new ArrayList<>(), 0, 0));
        while (!queue.isEmpty()) {
            FourPos curFour = queue.remove();
            positions.add(curFour);
            Position cur = curFour.robots[curFour.active];
            if (grid[cur.i][cur.j] >= 'a' && grid[cur.i][cur.j] <= 'z' && !cur.bag.contains(grid[cur.i][cur.j])) {
                cur.bag.add(grid[cur.i][cur.j]);
                curFour.active = (curFour.active+1) % curFour.robots.length;
            }
            if (cur.isComplete())
                return cur.steps;
            Position[] newRobots = curFour.robots.clone();
            newRobots[curFour.active] = new Position(cur.i-1, cur.j, new ArrayList<>(cur.bag), cur.steps+1);
            if (isValid(grid, positions, curFour.robots, curFour.active))
                queue.add(new FourPos(newRobots, curFour.active));
            newRobots = curFour.robots.clone();
            newRobots[curFour.active] = new Position(cur.i+1, cur.j, new ArrayList<>(cur.bag), cur.steps+1);
            if (isValid(grid, positions, curFour.robots, curFour.active))
                queue.add(new FourPos(newRobots, curFour.active));
            newRobots = curFour.robots.clone();
            newRobots[curFour.active] = new Position(cur.i, cur.j-1, new ArrayList<>(cur.bag), cur.steps+1);
            if (isValid(grid, positions, curFour.robots, curFour.active))
                queue.add(new FourPos(newRobots, curFour.active));
            newRobots = curFour.robots.clone();
            newRobots[curFour.active] = new Position(cur.i, cur.j+1, new ArrayList<>(cur.bag), cur.steps+1);
            if (isValid(grid, positions, curFour.robots, curFour.active))
                queue.add(new FourPos(newRobots, curFour.active));
        }
        return -1;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day18.txt"));
        ArrayList<String> parse = new ArrayList<>();
        while (scan.hasNextLine())
            parse.add(scan.nextLine());
        char[][] grid = new char[parse.size()][parse.get(0).length()];
        for (int i = 0; i < grid.length; i++)
            grid[i] = parse.get(i).toCharArray();
        numKeys = 0;
        for (char[] row : grid)
            for (char space : row)
                if (space >= 'a' && space <= 'z')
                    numKeys++;
        System.out.println(shortestPath(grid));
        System.out.println(shortestFour(grid));
    }
}