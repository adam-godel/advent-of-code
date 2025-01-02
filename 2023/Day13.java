import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
public class Day13 {
    private static ArrayList<Integer> reflect(ArrayList<String> grid) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < grid.get(0).length()-1; i++) {
            boolean reflectCol = true;
            int left = i; int right = i+1;
            while (left >= 0 && right < grid.get(0).length()) {
                String leftStr = "", rightStr = "";
                for (int j = 0; j < grid.size(); j++) {
                    leftStr += grid.get(j).charAt(left);
                    rightStr += grid.get(j).charAt(right);
                }
                if (!leftStr.equals(rightStr)) {
                    reflectCol = false;
                    break;
                }
                left--; right++;
            }
            if (reflectCol)
                result.add(i+1);
        }
        for (int i = 0; i < grid.size()-1; i++) {
            boolean reflectRow = true;
            int top = i; int bottom = i+1;
            while (top >= 0 && bottom < grid.size()) {
                if (!grid.get(top).equals(grid.get(bottom))) {
                    reflectRow = false;
                    break;
                }
                top--; bottom++;
            }
            if (reflectRow)
                result.add(100*(i+1));
        }
        return result;
    }
    private static int adjustSmudge(ArrayList<String> grid) {
        int original = reflect(grid).get(0);
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).length(); j++) {
                String s = grid.remove(i);
                if (s.charAt(j) == '#') {
                    grid.add(i, s.substring(0, j) + "." + s.substring(j+1));
                } else if (s.charAt(j) == '.') {
                    grid.add(i, s.substring(0, j) + "#" + s.substring(j+1));
                }
                ArrayList<Integer> result = reflect(grid);
                if (result.size() > 0) {
                    if (result.get(0) != original)
                        return result.get(0);
                    if (result.size() > 1 && result.get(1) != original)
                        return result.get(1);
                }
                grid.remove(i); grid.add(i, s); 
            }
        }
        throw new ArithmeticException();
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day13.txt"));
        int result = 0;
        ArrayList<String> grid = new ArrayList<>();
        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            if (!data.equals(""))
                grid.add(data);
            if (data.equals("") || !scan.hasNextLine()) {
                result += adjustSmudge(grid);
                grid.clear();
            }
        }
        System.out.println(result);
    }
}