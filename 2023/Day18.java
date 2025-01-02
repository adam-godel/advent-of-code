import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class Day18 {
    private static long shoelace(String[] dir, int[] len) {
        long[] x = new long[dir.length], y = new long[dir.length];
        long xCur = 0, yCur = 0;
        for (int i = 0; i < dir.length; i++) {
            x[i] = xCur + (dir[i].equals("L") ? -1*len[i] : dir[i].equals("R") ? len[i] : 0);
            y[i] = yCur + (dir[i].equals("D") ? -1*len[i] : dir[i].equals("U") ? len[i] : 0);
            xCur = x[i]; yCur = y[i];
        }
        long area = 0;
        for (int i = 0; i < dir.length; i++) {
            area += x[i]*y[(i+1) % dir.length] - y[i]*x[(i+1) % dir.length];
        }
        area = Math.abs(area)/2;
        return area;
    }
    private static long perimeter(int[] len) {
        long perimeter = 0;
        for (int i : len)
            perimeter += i;
        return perimeter;
    }
    private static int hexToDec(String s) {
        int length = s.length(), base = 1, result = 0;
        for (int i = length-1; i >= 0; i--) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                result += (s.charAt(i)-'0') * base;
            } else if (s.charAt(i) >= 'a' && s.charAt(i) <= 'f') {
                result += (s.charAt(i)-'a'+10) * base;
            }
            base *= 16;
        }
        return result;
    }
    private static String valToDir(char i) {
        if (i == '0') return "R";
        else if (i == '1') return "D";
        else if (i == '2') return "L";
        else if (i == '3') return "U";
        throw new IllegalArgumentException();
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day18.txt"));
        ArrayList<String> parseData = new ArrayList<>();
        while (scan.hasNextLine())
            parseData.add(scan.nextLine());
        Object[][] data = new Object[parseData.size()][];
        for (int i = 0; i < data.length; i++) {
            data[i] = parseData.get(i).split(" ");
        }
        String[] dir = new String[data.length];
        for (int i = 0; i < dir.length; i++) {
            dir[i] = (String)data[i][0];
        }
        int[] len = new int[data.length];
        for (int i = 0; i < dir.length; i++)
            len[i] = Integer.parseInt((String)data[i][1]);
        long area = shoelace(dir, len)+perimeter(len)/2+1;
        System.out.println(area);

        dir = new String[data.length]; len = new int[data.length];
        for (int i = 0; i < dir.length; i++) {
            String s = (String)data[i][2];
            s = s.substring(2, s.length()-1);
            dir[i] = valToDir(s.charAt(s.length()-1));
            len[i] = hexToDec(s.substring(0, s.length()-1));
        }
        area = shoelace(dir, len)+perimeter(len)/2+1;
        System.out.println(area);
    }
}
