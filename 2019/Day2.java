import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day2 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day2.txt"));
        String input = scan.nextLine();
        for (int i = 0; i <= 99; i++) {
            for (int j = 0; j <= 99; j++) {
                Intcode ic = new Intcode(input);
                ic.program[1] = i; ic.program[2] = j;
                if (ic.execute()[0] == 19690720)
                    System.out.println(100*i+j);
            }
        }
    }
}
