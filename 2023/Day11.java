import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
public class Day11 {
    private static class Item {
        int row; int col; long dist;
        Item(int row, int col, long dist) {
            this.row = row;
            this.col = col;
            this.dist = dist;
        }
    }
    private static long minDistance(char[][] grid, int i, int j) {
        Item source = new Item(i, j, 0);
        Queue<Item> queue = new LinkedList<Item>();
        queue.add(source);
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        visited[source.row][source.col] = true;
        while (!queue.isEmpty()) {
            Item p = queue.remove();
            if (grid[p.row][p.col] == 'd')
                return p.dist;
            if (isValid(p.row-1, p.col, grid, visited)) {
                if (grid[p.row-1][p.col] == '-')
                    queue.add(new Item(p.row-1, p.col, p.dist+999999));
                else
                    queue.add(new Item(p.row-1, p.col, p.dist+1));
                visited[p.row-1][p.col] = true;
            }
            if (isValid(p.row+1, p.col, grid, visited)) {
                if (grid[p.row+1][p.col] == '-')
                    queue.add(new Item(p.row+1, p.col, p.dist+999999));
                else
                    queue.add(new Item(p.row+1, p.col, p.dist+1));
                visited[p.row+1][p.col] = true;
            }
            if (isValid(p.row, p.col-1, grid, visited)) {
                if (grid[p.row][p.col-1] == '-')
                    queue.add(new Item(p.row, p.col-1, p.dist+999999));
                else
                    queue.add(new Item(p.row, p.col-1, p.dist+1));
                visited[p.row][p.col-1] = true;
            }
            if (isValid(p.row, p.col+1, grid, visited)) {
                if (grid[p.row][p.col+1] == '-')
                    queue.add(new Item(p.row, p.col+1, p.dist+999999));
                else
                    queue.add(new Item(p.row, p.col+1, p.dist+1));
                visited[p.row][p.col+1] = true;
            }
        }
        return -1;
    }
    private static boolean isValid(int row, int col, char[][] grid, boolean[][] visited) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && !visited[row][col];
    }
    private static boolean toContinue(char[][] grid) {
        for (char[] c : grid)
            for (char d : c)
                if (d == '#')
                    return true;
        return false;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day11.txt"));
        ArrayList<String> data = new ArrayList<String>();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (!line.contains("#"))
                data.add(line.replace('.', '-'));
            data.add(line);
        }
        for (int i = 0; i < data.get(0).length(); i++) {
            boolean allEmpty = true;
            for (int j = 0; j < data.size(); j++)
                if (data.get(j).charAt(i) == '#') {
                    allEmpty = false;
                    break;
                }
            if (allEmpty) {
                for (int j = 0; j < data.size(); j++) {
                    String s = data.remove(j);
                    data.add(j, s.substring(0, i) + "-" + s.substring(i));
                }
                i++;
            }
                    
        }
        char[][] grid = new char[data.size()][data.get(0).length()];
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                grid[i][j] = data.get(i).charAt(j);
        int startRow = 0; int startCol = 0; boolean start = false;
        long result = 0;
        while (toContinue(grid)) {
            for (int i = 0; i < grid.length; i++)
                for (int j = 0; j < grid[i].length; j++)
                    if (grid[i][j] == '#')
                        if (!start) {
                            grid[i][j] = 's';
                            startRow = i; startCol = j;
                            start = true;
                        } else {
                            grid[i][j] = 'd';
                            result += minDistance(grid, startRow, startCol);
                            grid[i][j] = '#';
                        }
            start = false;
        }
        System.out.println(result);
    }
}