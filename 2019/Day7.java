import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
public class Day7 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day7.txt"));
        String input = scan.nextLine(); long max = 0;
        for (int sim = 0; sim < 10000; sim++) {
            Intcode[] ic = {new Intcode(input), new Intcode(input), new Intcode(input), new Intcode(input), new Intcode(input)};
            long result = 0;
            Long[] arr = new Long[]{5l, 6l, 7l, 8l, 9l};
            ArrayList<Long> order = new ArrayList<>(Arrays.asList(arr));
            Collections.shuffle(order);
            for (int i = 0; i < ic.length; i++)
                ic[i].inputs.add(order.get(i));
            ic[0].inputs.add(0l);
            int i = 0;
            while (ic[4].getPaused() != -1) {
                ic[i].execute(ic[i].getPaused());
                ic[(i+1) % 5].inputs.add(ic[i].latestOutput());
                i = (i+1) % 5;
            }
            result = ic[4].latestOutput();
            if (result > max)
                max = result;
        }
        System.out.println(max);
    }
}