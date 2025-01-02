import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day21 {
    private static void inputASCII(Intcode ic, String input) {
        System.out.println(input);
        for (int i = 0; i < input.length(); i++)
            ic.inputs.add((long)input.charAt(i));
        ic.inputs.add((long)'\n');
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day21.txt"));
        Intcode ic = new Intcode(scan.nextLine());
        inputASCII(ic, "NOT C J");
        inputASCII(ic, "AND D J");
        inputASCII(ic, "AND H J");
        inputASCII(ic, "NOT B T");
        inputASCII(ic, "AND D T");
        inputASCII(ic, "OR T J");
        inputASCII(ic, "NOT A T");
        inputASCII(ic, "OR T J");
        inputASCII(ic, "RUN");
        ic.execute();
        for (long l : ic.getOutput())
            if (l < 255)
                System.out.print((char)l);
        if (ic.latestOutput() > 10)
            System.out.println(ic.latestOutput());
    }
}