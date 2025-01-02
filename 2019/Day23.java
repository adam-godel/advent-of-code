import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day23 {
    private static boolean allEmpty(boolean[] queues) {
        for (boolean isEmpty : queues)
            if (!isEmpty)
                return false;
        return true;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day23.txt"));
        String input = scan.nextLine();
        IntcodeNetwork[] ic = new IntcodeNetwork[50];
        for (int i = 0; i < ic.length; i++) {
            ic[i] = new IntcodeNetwork(input);
            ic[i].inputs.add((long)i);
        }
        long natX = -1, natY = -1, lastY = 0; boolean firstY = false;
        boolean[] queues = new boolean[ic.length];
        while (true) {
            for (int i = 0; i < ic.length; i++) {
                long[] result;
                do {
                    result = ic[i].execute(ic[i].getPaused());
                } while (result != null);
                queues[i] = ic[i].output.isEmpty();
                while (!ic[i].output.isEmpty()) {
                    int idx = (int)(long)ic[i].output.remove(); long x = ic[i].output.remove(), y = ic[i].output.remove();
                    if (idx == 255) {
                        natX = x; natY = y;
                        if (!firstY) {
                            System.out.println(y);
                            firstY = true;
                        }
                    } else {
                        ic[idx].inputs.add(x); ic[idx].inputs.add(y);
                    }
                }
            }
            if (allEmpty(queues)) {
                if (natY == lastY) {
                    System.out.println(natY);
                    break;
                }
                ic[0].inputs.add(natX); ic[0].inputs.add(natY);
                lastY = natY;
            }
        }
    }
}