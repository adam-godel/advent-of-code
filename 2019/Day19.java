import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day19 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day19.txt"));
        String input = scan.nextLine();
        int result = 0;
        for (int i = 99; i < 1000; i++) {
            Intcode ic; int j = i-99;
            do {
                ic = new Intcode(input);
                ic.inputs.add((long)++j); ic.inputs.add((long)i);
                ic.execute();
            } while (ic.latestOutput() != 1);
            ic = new Intcode(input);
            ic.inputs.add(j+99l); ic.inputs.add(i-99l);
            ic.execute();
            if (ic.latestOutput() == 1) {
                result = (i-99)+10000*j; 
                break;
            }
        }
        System.out.println(result);
    }
}