import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day25 {
    private static void inputASCII(Intcode ic, String input) {
        for (int i = 0; i < input.length(); i++)
            ic.inputs.add((long)input.charAt(i));
        ic.inputs.add((long)'\n');
    }
    @SuppressWarnings({"resource"})
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day25.txt"));
        IntcodeNetwork ic = new IntcodeNetwork(scan.nextLine());
        scan = new Scanner(System.in);
        System.out.print("\033[H\033[2J");  
        System.out.flush();
        while (ic.getPaused() != -1) {
            long[] result = ic.execute(ic.getPaused());
            while (!ic.output.isEmpty())
                System.out.print((char)(long)ic.output.remove());
            if (result == null) {
                String input = scan.nextLine();
                inputASCII(ic, input);
                System.out.print("\033[H\033[2J");  
                System.out.flush();
            }
        }
    }
}