import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
public class Day10 {
    private static class Pipe {
        int line; int i; char c; int count;
        Pipe(int line, int i, char c, int count) {
            this.line = line;
            this.i = i;
            this.c = c;
            this.count = count;
        }
    }
    private static boolean isValid(String[] data, int line, int i, int[][] counts, String orient) {
        if (orient.equals("RIGHT"))
            return i+1 < data[line].length() && counts[line][i+1] == 0 ? "-7J".contains(""+data[line].charAt(i+1)) : false;
        else if (orient.equals("LEFT"))
            return i-1 >= 0 && counts[line][i-1] == 0 ? "-LF".contains(""+data[line].charAt(i-1)) : false;
        else if (orient.equals("UP"))
            return line-1 >= 0 && counts[line-1][i] == 0 ? "|F7".contains(""+data[line-1].charAt(i)) : false;
        else
            return line+1 < data.length && counts[line+1][i] == 0 ? "|LJ".contains(""+data[line+1].charAt(i)) : false;
    }
    private static int pipePath(String[] data, int line, int i, int[][] counts) {
        Queue<Pipe> queue = new LinkedList<Pipe>();
        char c = data[line].charAt(i);
        boolean[] initS = {isValid(data, line, i, counts, "RIGHT"), isValid(data, line, i, counts, "LEFT"), isValid(data, line, i, counts, "UP"), isValid(data, line, i, counts, "DOWN")};
        if (initS[0] && initS[2]) c = 'L'; if (initS[0] && initS[3]) c = 'F'; if (initS[1] && initS[2]) c = 'J'; if (initS[1] && initS[3]) c = '7'; if (initS[0] && initS[1]) c = '-'; if (initS[2] && initS[3]) c = '|';
        Pipe p = new Pipe(line, i, c, 0);
        queue.add(p);
        counts[line][i] = -1;
        while (!queue.isEmpty()) {
            p = queue.remove();
            line = p.line; i = p.i; c = p.c;
            if ("-LF".contains(""+c) && isValid(data, line, i, counts, "RIGHT")) {
                counts[line][i+1] = p.count+1;
                queue.add(new Pipe(line, i+1, data[line].charAt(i+1), p.count+1));
            }
            if ("-7J".contains(""+c) && isValid(data, line, i, counts, "LEFT")) {
                counts[line][i-1] = p.count+1;
                queue.add(new Pipe(line, i-1, data[line].charAt(i-1), p.count+1));
            }
            if ("|LJ".contains(""+c) && isValid(data, line, i, counts, "UP")) {
                counts[line-1][i] = p.count+1;
                queue.add(new Pipe(line-1, i, data[line-1].charAt(i), p.count+1));
            }
            if ("|F7".contains(""+c) && isValid(data, line, i, counts, "DOWN")) {
                counts[line+1][i] = p.count+1;
                queue.add(new Pipe(line+1, i, data[line+1].charAt(i), p.count+1));
            }
        }
        return p.count;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day10.txt"));
        String[] data = new String[140]; int[][] counts = new int[140][140];
        int line = 0; int i = 0;
        while (scan.hasNextLine()) {
            data[line++] = scan.nextLine();
        }
        for (int j = 0; j < data.length; j++)
            if (data[j].contains("S")) {
                line = j;
                i = data[j].indexOf("S");
            }
        System.out.println(pipePath(data, line, i, counts));
        boolean[][] inside = new boolean[140][140];
        for (int h = 0; h < counts.length; h++)
            for (int j = 0; j < counts[h].length; j++)
                if (counts[h][j] != 0) 
                    for (int k = j+1; k < counts[h].length; k++)
                        if (counts[h][k] == 0) {
                            int count = 0;
                            for (int l = 0; l < k; l++)
                                if ("|LJ".contains(""+data[h].charAt(l)) && counts[h][l] != 0)
                                    count++;
                            if (count % 2 != 0)
                                inside[h][k] = true;
                        } else {
                            break;
                        }
        int count = 0;
        for (boolean[] j : inside)
            for (boolean k : j)
                if (k) count++;
        System.out.println(count);
    }
}
