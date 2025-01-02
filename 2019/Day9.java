import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day9 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day9.txt"));
        Intcode ic = new Intcode(scan.nextLine());
        ic.execute();
        ic.output();
    }
}