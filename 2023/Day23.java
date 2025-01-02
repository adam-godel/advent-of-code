import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class Day23 {
    private static final int RIGHT = 0; private static final int LEFT = 1;
    private static final int DOWN = 2; private static final int UP = 3;
    private static class Position {
        int i; int j;
        Position(int i, int j) {
            this.i = i; this.j = j;
        }
    }
    private static long max(long a, long b) {
        if (a > b) return a; 
        return b;
    }
    private static boolean isValid(char[][] data, boolean[][] visited, int i, int j, int dir) {
        if (i < 0 || i >= data.length || j < 0 || j >= data.length || data[i][j] == '#' || visited[i][j])
            return false;
        /*if (data[i][j] == '.')
            return true;
        if ((data[i][j] == 'v' && dir == DOWN) || (data[i][j] == '>' && dir == RIGHT))
            return true;
        return false;*/
        return true;
    }
    private static long DFS(char[][] data, boolean[][] visited, Position p) {
        long result = 0;
        visited[p.i][p.j] = true;
        if (isValid(data, visited, p.i-1, p.j, UP))
            result = max(result, DFS(data, visited, new Position(p.i-1, p.j))+1);
        if (isValid(data, visited, p.i+1, p.j, DOWN))
            result = max(result, DFS(data, visited, new Position(p.i+1, p.j))+1);
        if (isValid(data, visited, p.i, p.j-1, LEFT))
            result = max(result, DFS(data, visited, new Position(p.i, p.j-1))+1);
        if (isValid(data, visited, p.i, p.j+1, RIGHT))
            result = max(result, DFS(data, visited, new Position(p.i, p.j+1))+1);
        visited[p.i][p.j] = false;
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day23.txt"));
        ArrayList<String> parseData = new ArrayList<>();
        while (scan.hasNextLine())
            parseData.add(scan.nextLine());
        char[][] data = new char[parseData.size()][parseData.size()];
        for (int i = 0; i < parseData.size(); i++)
            data[i] = parseData.get(i).toCharArray();
        boolean[][] visited = new boolean[parseData.size()][parseData.size()];
        System.out.println(DFS(data, visited, new Position(0, 1)));
    }
}
