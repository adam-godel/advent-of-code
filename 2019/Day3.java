import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
public class Day3 {
    private static class Position {
        int x, y, idx, steps;
        Position(int x, int y, int idx, int steps) {
            this.x = x; this.y = y; this.idx = idx; this.steps = steps;
        }
    }
    private static void trace(HashMap<String, Position> positions, ArrayList<Position> intersect, String[] data, int idx) {
        int curX = 0, curY = 0, steps = 0;
        for (String s : data) {
            for (int i = 0; i < Integer.parseInt(s.substring(1)); i++) {
                if (s.charAt(0) == 'U') curY++;
                if (s.charAt(0) == 'D') curY--;
                if (s.charAt(0) == 'R') curX++;
                if (s.charAt(0) == 'L') curX--;
                Position p = new Position(curX, curY, idx, ++steps);
                if (positions.get(curX + " " + curY) == null) {
                    positions.put(p.x + " " + p.y, p);
                } else if (positions.get(curX + " " + curY).idx != idx) {
                    p.steps += positions.get(p.x + " " + p.y).steps;
                    intersect.add(p);
                }
            }
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day3.txt"));
        HashMap<String, Position> positions = new HashMap<>();
        ArrayList<Position> intersect = new ArrayList<>();
        int idx = 0;
        while (scan.hasNextLine()) {
            String[] data = scan.nextLine().split(",");
            trace(positions, intersect, data, idx++);
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < intersect.size(); i++)
            if (intersect.get(i).steps < min)
                min = intersect.get(i).steps;
        System.out.println(min);
    }
}
