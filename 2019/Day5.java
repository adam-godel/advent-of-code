import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day5 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day5.txt"));
        Intcode ic = new Intcode(scan.nextLine());
        ic.execute();
        ic.output();
    }
}