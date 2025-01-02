import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
public class Day14 {
    private static int preWaitlist(HashMap<String, String[]> reverseRxn, HashMap<String, Integer> reactantVal, HashMap<String, Integer> waitlist, String key) {
        int[] num = new int[reverseRxn.get(key).length];
        String[] reactants = new String[num.length];
        for (int i = 0; i < num.length; i++) {
            num[i] = Integer.parseInt(reverseRxn.get(key)[i].split(" ")[0]);
            reactants[i] = reverseRxn.get(key)[i].split(" ")[1];
        }
        int result = 0;
        for (int i = 0; i < reactants.length; i++) {
            if (reactants[i].equals("ORE")) {
                result += num[i];
            } else {
                if (waitlist.get(reactants[i]) == null)
                    waitlist.put(reactants[i], 0);
                int numRxn = (num[i]+waitlist.get(reactants[i]))/reactantVal.get(reactants[i]);
                waitlist.replace(reactants[i], (num[i]+waitlist.get(reactants[i]))%reactantVal.get(reactants[i]));
                for (int j = 0; j < numRxn; j++)
                    result += preWaitlist(reverseRxn, reactantVal, waitlist, reactants[i]);
            }
        }
        return result;
    }
    private static int oreRequired(HashMap<String, String[]> reverseRxn, HashMap<String, Integer> reactantVal, HashMap<String, Integer> waitlist) {
        int result = preWaitlist(reverseRxn, reactantVal, waitlist, "FUEL");
        Queue<String> queue = new LinkedList<>(); queue.add("FUEL");
        LinkedList<String> visited = new LinkedList<>();
        while (!queue.isEmpty()) {
            String cur = queue.remove();
            int[] num = new int[reverseRxn.get(cur).length];
            String[] reactants = new String[num.length];
            for (int i = 0; i < num.length; i++) {
                num[i] = Integer.parseInt(reverseRxn.get(cur)[i].split(" ")[0]);
                reactants[i] = reverseRxn.get(cur)[i].split(" ")[1];
            }
            for (int i = 0; i < reactants.length; i++) {
                if (waitlist.get(reactants[i]) != null && waitlist.get(reactants[i]) > 0 && !visited.contains(reactants[i])) {
                    result += preWaitlist(reverseRxn, reactantVal, waitlist, reactants[i]);
                    visited.add(reactants[i]);
                }
                if (!reactants[i].equals("ORE"))
                    queue.add(reactants[i]);
            }
        }
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("ex.txt"));
        HashMap<String, String[]> reverseRxn = new HashMap<>();
        HashMap<String, Integer> reactantVal = new HashMap<>();
        HashMap<String, Integer> waitlist = new HashMap<>();
        while (scan.hasNextLine()) {
            String[] parse = scan.nextLine().split(" => ");
            String[] reactants = parse[0].split(", ");
            reverseRxn.put(parse[1].split(" ")[1], reactants);
            reactantVal.put(parse[1].split(" ")[1], Integer.parseInt(parse[1].split(" ")[0]));
        }
        long ore = oreRequired(reverseRxn, reactantVal, waitlist);
        System.out.println(ore);
        long fuel = 1000000000000l/ore; long val = 3*fuel/2; long lower = fuel, upper = 2*fuel;
        while (true) {
            long result = 0;
            for (int i = 0; i < val; i++)
                result += oreRequired(reverseRxn, reactantVal, waitlist);
            if (result < 1000000000000l) {
                upper = result; val = (lower+upper)/2;
            } else if (result > 1000000000000l) {
                lower = result; val = (lower+upper)/2;
            } else {
                break;
            }
        }
        System.out.println(val);
    }
}