import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
public class Day16 {
    private static class Beam {
        int i; int j; int orient;
        Beam(int i, int j, int orient) {
            this.i = i; this.j = j; this.orient = orient;
        }
    }
    private static final int RIGHT = 0; private static final int LEFT = 1;
    private static final int DOWN = 2; private static final int UP = 3;
    private static int[] adjustIdx(char[][] data, int i, int j, int orient) {
        if (orient == RIGHT && j+1 < data[i].length)
            return new int[]{i, j+1, orient};
        else if (orient == LEFT && j-1 >= 0)
            return new int[]{i, j-1, orient};
        else if (orient == DOWN && i+1 < data.length)
            return new int[]{i+1, j, orient};
        else if (orient == UP && i-1 >= 0)
            return new int[]{i-1, j, orient};
        else
            return null;
    }
    private static int beam(char[][] data, boolean[][] energized, int[][] orients, int i, int j, int orient) {
        Queue<Beam> queue = new LinkedList<>();
        queue.add(new Beam(i, j, orient));
        while (!queue.isEmpty()) {
            Beam b = queue.remove();
            if (energized[b.i][b.j] && b.orient+1 == orients[b.i][b.j])
                continue;
            energized[b.i][b.j] = true;
            orients[b.i][b.j] = b.orient+1;
            if (data[b.i][b.j] == '.') {
                int[] result = adjustIdx(data, b.i, b.j, b.orient);
                if (result != null)
                    queue.add(new Beam(result[0], result[1], result[2]));
            } else if (data[b.i][b.j] == '\\') {
                int[] result = adjustIdx(data, b.i, b.j, (b.orient+2) % 4);
                if (result != null)
                    queue.add(new Beam(result[0], result[1], result[2]));
            } else if (data[b.i][b.j] == '/') {
                int orientResult = b.orient;
                if (b.orient == RIGHT) orientResult += 3;
                else if (b.orient == UP) orientResult -= 3;
                else if (b.orient == LEFT) orientResult++;
                else if (b.orient == DOWN) orientResult--;
                int[] result = adjustIdx(data, b.i, b.j, orientResult);
                if (result != null)
                    queue.add(new Beam(result[0], result[1], result[2]));
            } else if ((data[b.i][b.j] == '|' && b.orient >= DOWN) || (data[b.i][b.j] == '-' && b.orient <= LEFT)) {
                int[] result = adjustIdx(data, b.i, b.j, b.orient);
                if (result != null)
                    queue.add(new Beam(result[0], result[1], result[2]));
            } else if (data[b.i][b.j] == '|') {
                int[] result1 = adjustIdx(data, b.i, b.j, UP);
                int[] result2 = adjustIdx(data, b.i, b.j, DOWN);
                if (result1 != null)
                    queue.add(new Beam(result1[0], result1[1], result1[2]));
                if (result2 != null)
                    queue.add(new Beam(result2[0], result2[1], result2[2]));
            } else if (data[b.i][b.j] == '-') {
                int[] result1 = adjustIdx(data, b.i, b.j, RIGHT);
                int[] result2 = adjustIdx(data, b.i, b.j, LEFT);
                if (result1 != null)
                    queue.add(new Beam(result1[0], result1[1], result1[2]));
                if (result2 != null)
                    queue.add(new Beam(result2[0], result2[1], result2[2]));
            }
        }
        int result = 0;
        for (boolean[] b : energized)
            for (boolean c : b)
                if (c) result++;
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day16.txt"));
        ArrayList<String> parseData = new ArrayList<>();
        while (scan.hasNextLine())
            parseData.add(scan.nextLine());
        char[][] data = new char[parseData.size()][parseData.size()];
        for (int i = 0; i < parseData.size(); i++)
            data[i] = parseData.get(i).toCharArray();
        boolean[][] energized = new boolean[data.length][data[0].length];
        int[][] orients = new int[data.length][data[0].length];
        int max = 0;
        for (int i = 0; i < data.length; i++) {
            energized = new boolean[data.length][data[0].length]; orients = new int[data.length][data[0].length];
            int result = beam(data, energized, orients, i, 0, RIGHT);
            max = result > max ? result : max;
        }
        for (int i = 0; i < data.length; i++) {
            energized = new boolean[data.length][data[0].length]; orients = new int[data.length][data[0].length];
            int result = beam(data, energized, orients, i, data[i].length-1, LEFT);
            max = result > max ? result : max;
        }
        for (int j = 0; j < data[0].length; j++) {
            energized = new boolean[data.length][data[0].length]; orients = new int[data.length][data[0].length];
            int result = beam(data, energized, orients, 0, j, DOWN);
            max = result > max ? result : max;
        }
        for (int j = 0; j < data[data.length-1].length; j++) {
            energized = new boolean[data.length][data[0].length]; orients = new int[data.length][data[0].length];
            int result = beam(data, energized, orients, data.length-1, j, UP);
            max = result > max ? result : max;
        }
        System.out.println(max);
    }
}
