import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
public class Day11MD {
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
        ArrayList<Integer> rows = new ArrayList<Integer>();
        ArrayList<Integer> cols = new ArrayList<Integer>();
        int idx = 0;
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (!line.contains("#"))
                rows.add(idx);
            data.add(line);
            idx++;
        }
        for (int i = 0; i < data.get(0).length(); i++) {
            boolean allEmpty = true;
            for (int j = 0; j < data.size(); j++)
                if (data.get(j).charAt(i) == '#') {
                    allEmpty = false;
                    break;
                }
            if (allEmpty)
                cols.add(i);  
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
                            int count = 0;
                            for (int k = 0; k < rows.size(); k++)
                                if ((rows.get(k) > startRow && rows.get(k) < i) || (rows.get(k) > i && rows.get(k) < startRow))
                                    count++;
                            for (int k = 0; k < cols.size(); k++)
                                if ((cols.get(k) > startCol && cols.get(k) < j) || (cols.get(k) > j && cols.get(k) < startCol))
                                    count++;
                            result += Math.abs(i-startRow)+Math.abs(j-startCol)+999999*count;
                        }
            start = false;
        }
        System.out.println(result);
    }
}