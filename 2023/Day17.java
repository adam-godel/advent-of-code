import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.PriorityQueue;
public class Day17 {
    private static class Position implements Comparable<Position> {
        int i; int j; int dir; int steps; int cost;
        Position(int i, int j, int dir, int steps, int cost) {
            this.i = i; this.j = j; this.dir = dir; 
            this.steps = steps; this.cost = cost;
        }
        public boolean equals(Position other) {
            return i == other.i && j == other.j;
        }
        public boolean strongEquals(Position other) {
            return i == other.i && j == other.j && dir == other.dir && steps == other.steps;
        }
        public int compareTo(Position other) {
            if (cost > other.cost) 
                return 1;
            else if (cost < other.cost)
                return -1;
            return 0;
        }
    }
    private static final int RIGHT = 0; private static final int LEFT = 1;
    private static final int DOWN = 2; private static final int UP = 3;
    private static int minCost(int[][] data) {
        PriorityQueue<Position> pq = new PriorityQueue<>();
        Position startR = new Position(0, 0, RIGHT, 1, data[0][0]);
        Position startD = new Position(0, 0, DOWN, 1, data[0][0]);
        Position end = new Position(data.length-1, data[0].length-1, -1, -1, 0);
        pq.add(startR); pq.add(startD);
        ArrayList<Position> prev = new ArrayList<>();
        while (!pq.isEmpty()) {
            Position cur = pq.remove();
            if (cur.equals(end) && cur.steps >= 4)
                return cur.cost-data[data.length-1][data[0].length-1];
            boolean inPrev = false;
            for (Position p : prev)
                if (p.strongEquals(cur)) {
                    inPrev = true;
                    break;
                }
            if (inPrev)
                continue;
            prev.add(cur);
            if (cur.steps < 10) {
                if (cur.dir == RIGHT && cur.j+1 < data[0].length)
                    pq.add(new Position(cur.i, cur.j+1, RIGHT, cur.steps+1, cur.cost+data[cur.i][cur.j+1]));
                else if (cur.dir == LEFT && cur.j-1 >= 0)
                    pq.add(new Position(cur.i, cur.j-1, LEFT, cur.steps+1, cur.cost+data[cur.i][cur.j-1]));
                else if (cur.dir == DOWN && cur.i+1 < data.length)
                    pq.add(new Position(cur.i+1, cur.j, DOWN, cur.steps+1, cur.cost+data[cur.i+1][cur.j]));
                else if (cur.dir == UP && cur.i-1 >= 0)
                    pq.add(new Position(cur.i-1, cur.j, UP, cur.steps+1, cur.cost+data[cur.i-1][cur.j]));
            }
            if (cur.steps >= 4)
                if (cur.dir == RIGHT || cur.dir == LEFT) {
                    if (cur.i+1 < data.length)
                        pq.add(new Position(cur.i+1, cur.j, DOWN, 1, cur.cost+data[cur.i+1][cur.j]));
                    if (cur.i-1 >= 0)
                        pq.add(new Position(cur.i-1, cur.j, UP, 1, cur.cost+data[cur.i-1][cur.j]));
                } else if (cur.dir == DOWN || cur.dir == UP) {
                    if (cur.j+1 < data[0].length)
                        pq.add(new Position(cur.i, cur.j+1, RIGHT, 1, cur.cost+data[cur.i][cur.j+1]));
                    if (cur.j-1 >= 0)
                        pq.add(new Position(cur.i, cur.j-1, LEFT, 1, cur.cost+data[cur.i][cur.j-1]));
                }
        }
        throw new ArithmeticException();
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day17.txt"));
        ArrayList<String> parseData = new ArrayList<>();
        while (scan.hasNextLine())
            parseData.add(scan.nextLine());
        int[][] data = new int[parseData.size()][parseData.get(0).length()];
        for (int i = 0; i < parseData.size(); i++)
            for (int j = 0; j < parseData.get(i).length(); j++)
                data[i][j] = parseData.get(i).charAt(j) - '0';
        System.out.println(minCost(data));
    }
}
