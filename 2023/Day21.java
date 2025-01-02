import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
public class Day21 {
    private static class Position {
        int i; int j; int steps;
        Position(int i, int j, int steps) {
            this.i = i; this.j = j; this.steps = steps;
        }
    }
    private static boolean stepTarget(Queue<Position> queue, long target) {
        for (Position p : queue)
            if (p.steps <= target)
                return false;
        return true;
    }
    private static boolean containsDup(LinkedList<Position> list, Position p) {
        for (Position pos : list)
            if (pos.i == p.i && pos.j == p.j)
                return true;
        return false;
    }
    private static int findSteps(char[][] data, Position[][] pos, long target) {
        for (int i = 0; i < pos.length; i++)
            for (int j = 0; j < pos[i].length; j++)
                pos[i][j] = new Position(i, j, 0);
        Queue<Position> queue = new LinkedList<>();
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                if (data[i][j] == 'S') {
                    queue.add(pos[i][j]);
                    break;
                }
        LinkedList<Position> result = new LinkedList<>();
        while (!stepTarget(queue, target)) {
            Position cur = queue.remove();
            if (cur.steps % 2 == 0 && !containsDup(result, cur))
                result.add(cur);
            if (cur.i-1 >= 0 && data[cur.i-1][cur.j] != '#' && pos[cur.i-1][cur.j].steps == 0) {
                pos[cur.i-1][cur.j].steps = cur.steps+1;
                queue.add(pos[cur.i-1][cur.j]);
            }
            if (cur.i+1 < data.length && data[cur.i+1][cur.j] != '#' && pos[cur.i+1][cur.j].steps == 0) {
                pos[cur.i+1][cur.j].steps = cur.steps+1;
                queue.add(pos[cur.i+1][cur.j]);
            }
            if (cur.j-1 >= 0 && data[cur.i][cur.j-1] != '#' && pos[cur.i][cur.j-1].steps == 0) {
                pos[cur.i][cur.j-1].steps = cur.steps+1;
                queue.add(pos[cur.i][cur.j-1]);
            }
            if (cur.j+1 < data.length && data[cur.i][cur.j+1] != '#' && pos[cur.i][cur.j+1].steps == 0) {
                pos[cur.i][cur.j+1].steps = cur.steps+1;
                queue.add(pos[cur.i][cur.j+1]);
            }
        }
        return result.size();
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day21.txt"));
        ArrayList<String> parseData = new ArrayList<>();
        while (scan.hasNextLine())
            parseData.add(scan.nextLine());
        char[][] data = new char[parseData.size()][parseData.size()];
        for (int i = 0; i < parseData.size(); i++)
            data[i] = parseData.get(i).toCharArray();
        char[][] largerData = new char[data.length*5][data.length*5];
        for (int i = 0; i < largerData.length; i++)
            for (int j = 0; j < largerData.length; j++)
                if (data[i%data.length][j%data.length] == 'S' && (i != largerData.length/2 || j != largerData.length/2))
                    largerData[i][j] = '.';
                else
                    largerData[i][j] = data[i%data.length][j%data.length];
        Position[][] pos = new Position[largerData.length][largerData.length];
        for (int i = 0; i < largerData.length; i++)
            for (int j = 0; j < largerData[i].length; j++)
                pos[i][j] = new Position(i, j, 0);
        long x = 26501365;
        long x1 = x % data.length, x2 = x1+data.length, x3 = x2+data.length;
        long y1 = findSteps(largerData, pos, x1), y2 = findSteps(largerData, pos, x2), y3 = findSteps(largerData, pos, x3);
        long y = (((x-x2) * (x-x3)) / ((x1-x2) * (x1-x3)) * y1 +
        ((x-x1) * (x-x3)) / ((x2-x1) * (x2-x3)) * y2 +
        ((x-x1) * (x-x2)) / ((x3-x1) * (x3-x2)) * y3);
        System.out.println(y);
    }
}
