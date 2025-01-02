import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
public class Day10 {
    private static class Position implements Comparable<Position> {
        int i, j; double theta;
        Position(int i, int j, double theta) {
            this.i = i; this.j = j; this.theta = theta;
        }
        public int compareTo(Position other) {
            if (theta < other.theta)
                return -1;
            if (theta > other.theta)
                return 1;
            return 0;
        }
    }
    private static int vaporize200(char[][] data, ArrayList<Position> positions, int startI, int startJ) {
        int num = 0; Position cur = null;
        while (num < 200 && positions.size() > 0) {
            positions.sort(Comparator.naturalOrder());
            for (Position p : positions) {
                cur = p;
                System.out.println(cur.i + " " + cur.j + " " + cur.theta);
                data[p.i][p.j] = '.';
                num++;
                if (num == 200)
                    break;
            }
            positions = new ArrayList<>(); numAsteroids(data, positions, startI, startJ);
        }
        System.out.println(cur.i + " " + cur.j);
        return cur.i+cur.j*100;
    }
    private static int numAsteroids(char[][] data, ArrayList<Position> positions, int startI, int startJ) {
        ArrayList<Double> thetas = new ArrayList<>();
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++) {
                double theta = Math.atan2(startI-i, startJ-j) - Math.PI/2;
                theta = theta < 0 ? theta + 2*Math.PI : theta;
                if (data[i][j] == '#') {
                    if (!thetas.contains(theta)) {
                        thetas.add(theta);
                        positions.add(new Position(i, j, theta));
                    } else {
                        Position comp = null;
                        for (Position p : positions)
                            if (theta == p.theta)
                                comp = p;
                        if (Math.abs(i-startI)+Math.abs(j-startJ) < Math.abs(comp.i-startI)+Math.abs(comp.j-startJ)) {
                            positions.remove(comp);
                            positions.add(new Position(i, j, theta));
                        }
                    }
                }
            }
        return thetas.size();
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day10.txt"));
        ArrayList<String> parse = new ArrayList<>();
        while (scan.hasNextLine())
            parse.add(scan.nextLine());
        char[][] data = new char[parse.size()][parse.get(0).length()];
        for (int i = 0; i < data.length; i++)
            data[i] = parse.get(i).toCharArray();
        ArrayList<Position> positions = new ArrayList<>();
        int max = 0, maxI = 0, maxJ = 0;
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                if (data[i][j] == '#') {
                    int result = numAsteroids(data, positions, i, j);
                    if (result > max) {
                        max = result;
                        maxI = i; maxJ = j;
                    }
                }
        System.out.println(max);
        positions = new ArrayList<>(); numAsteroids(data, positions, maxI, maxJ);
        System.out.println(vaporize200(data, positions, maxI, maxJ));
    }
}