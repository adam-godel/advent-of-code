import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day2 {
    public static int minPower(String s) {
        String[] games = s.split("; ");
        int maxRed = 0, maxGreen = 0, maxBlue = 0;
        for (String g : games) {
            String[] cubes = g.split(", ");
            for (String c : cubes) {
                int numCubes = Integer.parseInt(c.replaceAll("[^0-9]", ""));
                if (c.contains("red") && numCubes > maxRed)
                    maxRed = numCubes;
                else if (c.contains("green") && numCubes > maxGreen)
                    maxGreen = numCubes;
                else if (c.contains("blue") && numCubes > maxBlue)
                    maxBlue = numCubes;
            }
        }
        return maxRed*maxGreen*maxBlue;
    }
    public static int isValid(String s, int line) {
        String[] games = s.split("; ");
        for (String g : games) {
            String[] cubes = g.split(", ");
            for (String c : cubes) {
                int numCubes = Integer.parseInt(c.replaceAll("[^0-9]", ""));
                if ((c.contains("red") && numCubes > 12) || (c.contains("green") && numCubes > 13) ||
                    (c.contains("blue") && numCubes > 14))
                    return 0;
            }
        }
        return line;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day2.txt"));
        int result = 0, line = 0;
        while (scan.hasNextLine()) {
            String data = scan.nextLine().split(": ")[1];
            result += isValid(data, ++line);
        }
        System.out.println(result);
        scan.close();
    }
}