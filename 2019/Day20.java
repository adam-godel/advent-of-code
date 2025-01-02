import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
public class Day20 {
    private static class Position {
        int i, j, steps, level;
        Position(int i, int j, int steps, int level) {
            this.i = i; this.j = j; this.steps = steps; this.level = level;
        }
    }
    private static int curI = 0, curJ = 0, curLevel = 0;
    private static boolean teleport(String portal, char[][] grid, int oldI, int oldJ) {
        int origI = curI, origJ = curJ;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                if (grid[i][j] >= 'A' && grid[i][j] <= 'Z' && (Math.abs(i-oldI) > 2 || Math.abs(j-oldJ) > 2) && portal.equals(generateString(grid, i, j))) {
                    if (i-1 >= 0 && grid[i-1][j] == '.') {
                        curI = i-1; curJ = j;
                    } else if (i+1 < grid.length && grid[i+1][j] == '.') {
                        curI = i+1; curJ = j;
                    } else if (j-1 >= 0 && grid[i][j-1] == '.') {
                        curI = i; curJ = j-1;
                    } else if (j+1 < grid[i].length && grid[i][j+1] == '.') {
                        curI = i; curJ = j+1;
                    } else if (i-2 >= 0 && grid[i-2][j] == '.') {
                        curI = i-2; curJ = j;
                    } else if (i+2 < grid.length && grid[i+2][j] == '.') {
                        curI = i+2; curJ = j;
                    } else if (j-2 >= 0 && grid[i][j-2] == '.') {
                        curI = i; curJ = j-2;
                    } else if (j+2 < grid[i].length && grid[i][j+2] == '.') {
                        curI = i; curJ = j+2;
                    }
                    if (curI <= 2 || curI >= grid.length-3 || curJ <= 2 || curJ >= grid[curI].length-3) {
                        curLevel++;
                    } else if (curLevel-1 >= 0) {
                        curLevel--;
                    } else {
                        curI = origI; curJ = origJ;
                        return false;
                    }
                    return true;
                }
        return false;
    }
    private static boolean isValid(char[][] grid, ArrayList<Integer>[][] visited, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || grid[i][j] == '#' || (visited[i][j] != null && visited[i][j].contains(curLevel)))
            return false;
        if (grid[i][j] == '.') {
            curI = i; curJ = j;
            return true;
        }
        if (grid[i][j] >= 'A' && grid[i][j] <= 'Z') {
            String portal = generateString(grid, i, j);
            return teleport(portal, grid, i, j);
        }
        return false;
    }
    private static String generateString(char[][] grid, int i, int j) {
        if (i-1 >= 0 && grid[i-1][j] >= 'A' && grid[i-1][j] <= 'Z')
            return grid[i-1][j] + "" + grid[i][j];
        if (i+1 < grid.length && grid[i+1][j] >= 'A' && grid[i+1][j] <= 'Z')
            return grid[i][j] + "" + grid[i+1][j];
       if (j-1 >= 0 && grid[i][j-1] >= 'A' && grid[i][j-1] <= 'Z')
            return grid[i][j-1] + "" + grid[i][j];
        if (j+1 < grid[i].length && grid[i][j+1] >= 'A' && grid[i][j+1] <= 'Z')
            return grid[i][j] + "" + grid[i][j+1];
        return null;
    }
    @SuppressWarnings("unchecked")
    private static int shortestPath(char[][] grid) {
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                if (j-2 >= 0 && grid[i][j-1] == 'A' && grid[i][j-2] == 'A' && grid[i][j] == '.') {
                    curI = i; curJ = j; break;
                }
        ArrayList<Integer>[][] visited = new ArrayList[grid.length][grid[0].length];
        Queue<Position> queue = new LinkedList<>(); 
        queue.add(new Position(curI, curJ, 0, 0));
        while (!queue.isEmpty()) {
            Position cur = queue.remove();
            if (cur.i+2 < grid.length && grid[cur.i+1][cur.j] == 'Z' && grid[cur.i+2][cur.j] == 'Z' && cur.level == 0)
                return cur.steps;
            if (visited[cur.i][cur.j] != null && visited[cur.i][cur.j].contains(cur.level))
                continue;
            if (visited[cur.i][cur.j] == null)
                visited[cur.i][cur.j] = new ArrayList<>();
            visited[cur.i][cur.j].add(cur.level);
            curI = cur.i; curJ = cur.j; curLevel = cur.level;
            if (isValid(grid, visited, curI-1, curJ))
                queue.add(new Position(curI, curJ, cur.steps+1, curLevel));
            curI = cur.i; curJ = cur.j; curLevel = cur.level;
            if (isValid(grid, visited, curI+1, curJ))
                queue.add(new Position(curI, curJ, cur.steps+1, curLevel));
            curI = cur.i; curJ = cur.j; curLevel = cur.level;
            if (isValid(grid, visited, curI, curJ-1))
                queue.add(new Position(curI, curJ, cur.steps+1, curLevel));
            curI = cur.i; curJ = cur.j; curLevel = cur.level;
            if (isValid(grid, visited, curI, curJ+1))
                queue.add(new Position(curI, curJ, cur.steps+1, curLevel));
        }
        return -1;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day20.txt"));
        ArrayList<String> parse = new ArrayList<>();
        while (scan.hasNextLine())
            parse.add(scan.nextLine());
        char[][] grid = new char[parse.size()][parse.get(0).length()];
        for (int i = 0; i < grid.length; i++)
            grid[i] = parse.get(i).toCharArray();
        System.out.println(shortestPath(grid));
    }
}
