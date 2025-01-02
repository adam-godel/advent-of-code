import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class Day14 {
    public static int cycle(char[][] data) {
        int result = 0;
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                if (data[i][j] == 'O') {
                    int row = i;
                    while (row > 0 && data[row-1][j] == '.') {
                        data[row][j] = '.';
                        data[--row][j] = 'O';
                    }
                }
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                if (data[i][j] == 'O') {
                    int col = j;
                    while (col > 0 && data[i][col-1] == '.') {
                        data[i][col] = '.';
                        data[i][--col] = 'O';
                    }
                }
        for (int i = data.length-1; i >= 0; i--)
            for (int j = 0; j < data[i].length; j++)
                if (data[i][j] == 'O') {
                    int row = i;
                    while (row < data.length-1 && data[row+1][j] == '.') {
                        data[row][j] = '.';
                        data[++row][j] = 'O';
                    }
                }
        for (int i = 0; i < data.length; i++)
            for (int j = data[i].length-1; j >= 0; j--)
                if (data[i][j] == 'O') {
                    int col = j;
                    while (col < data[i].length-1 && data[i][col+1] == '.') {
                        data[i][col] = '.';
                        data[i][++col] = 'O';
                    }
                }
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                if (data[i][j] == 'O')
                    result += data.length-i;
        return result;
    }
    public static int tiltNorth(char[][] data, int i, int j) {
        while (i > 0 && data[i-1][j] == '.') {
            data[i][j] = '.';
            data[--i][j] = 'O';
        }
        return i;
    }
    private static Map<String, Integer> map = new HashMap<>();
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day14.txt"));
        ArrayList<String> parseData = new ArrayList<>();
        while (scan.hasNextLine())
            parseData.add(scan.nextLine());
        char[][] data = new char[100][100];
        for (int i = 0; i < parseData.size(); i++)
            data[i] = parseData.get(i).toCharArray();
        ArrayList<Integer> values = new ArrayList<>();
        int startPeriod = 0; int endPeriod = 0;
        for (int i = 0; i < 1000000000; i++) {
            String key = "";
            for (char[] c : data)
                for (char d : c)
                    key += d;
            if (map.containsKey(key)) {
                startPeriod = map.get(key);
                endPeriod = i;
                break;
            }
            int cycle = cycle(data);
            map.put(key, i);
            values.add(cycle);
        }
        System.out.println(values.get((1000000000-startPeriod) % (endPeriod-startPeriod) + startPeriod-1));
    }
}
