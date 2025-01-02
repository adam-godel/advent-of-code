import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Day1 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day1.txt"));
        int sum = 0;
        while (scan.hasNextLine()) {
            int mass = Integer.parseInt(scan.nextLine());
            int val = mass/3-2, fuel = val;
            while (val/3-2 > 0) {
                val = val/3-2;
                fuel += val;
            }
            sum += fuel;
        }
        System.out.println(sum);
    }
}