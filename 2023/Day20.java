import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
public class Day20 {
    private static HashMap<String, String[]> map = new HashMap<>();
    private static HashMap<String, Boolean> flipFlop = new HashMap<>();
    private static HashMap<String, LinkedList<Boolean>> conjunction = new HashMap<>();
    private static HashMap<String, Integer> conjLength = new HashMap<>();
    private static long gcd(long n1, long n2) {
        if (n2 == 0)
            return n1;
        return gcd(n2, n1 % n2);
    }
    private static long lcm(long a, long b) {
        return a*b / gcd(a, b);
    }
    private static long lcm(long[] input) {
        long result = input[0];
        for (long i : input)
            result = lcm(result, i);
        return result;
    }
    private static long pushButton(String str) {
        long presses = 0; int idx = 0;
        long[] result = new long[conjLength.get(str)];
        String[] inputs = new String[result.length];
        for (HashMap.Entry<String, String[]> entry : map.entrySet()) {
            String[] output = entry.getValue();
            for (String s : output)
                if (s.equals(str))
                    inputs[idx++] = entry.getKey();
        }
        idx = 0;
        while (idx < result.length) {
            presses++;
            Queue<String> queue = new LinkedList<>();
            queue.add(false+" "+"broadcaster");
            while (!queue.isEmpty()) {
                String[] parse = queue.remove().split(" ");
                boolean signal = Boolean.parseBoolean(parse[0]); String key = parse[1];
                if (!map.containsKey(key))
                    continue;
                if (flipFlop.containsKey(key)) {
                    if (signal) continue;
                    flipFlop.replace(key, !flipFlop.get(key));
                    signal = flipFlop.get(key);
                } else if (conjunction.containsKey(key)) {
                    LinkedList<Boolean> list = conjunction.get(key);
                    if (list.contains(!signal))
                        list.set(list.indexOf(!signal), signal);
                    else if (list.size() < conjLength.get(key))
                        list.add(signal);
                    boolean allHigh = list.size() == conjLength.get(key) ? true : false;
                    for (boolean b : list)
                        if (!b) allHigh = false;
                    if (idx < result.length && key.equals(inputs[idx]) && !allHigh) {
                        result[idx++] = presses;
                        presses = 0;
                        conjunction.replaceAll((k, v) -> new LinkedList<>());
                        flipFlop.replaceAll((k, v) -> false);
                        break;
                    }
                    if (allHigh) signal = false;
                    else signal = true;
                }
                String[] output = map.get(key);
                for (int i = 0; i < output.length; i++)
                    queue.add(signal+" "+output[i]);
            }
        }
        return lcm(result);
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day20.txt"));
        while (scan.hasNextLine()) {
            String s = scan.nextLine();
            if (s.length() == 0)
                break;
            String[] data = s.split(" -> ");
            if (data[0].charAt(0) == '&') {
                conjunction.put(data[0].substring(1), new LinkedList<>());
                conjLength.put(data[0].substring(1), 0);
            }
            else if (data[0].charAt(0) == '%')
                flipFlop.put(data[0].substring(1), false);
            if (!data[0].equals("broadcaster"))
                data[0] = data[0].substring(1);
            map.put(data[0], data[1].split(", "));
        }
        for (HashMap.Entry<String, String[]> entry : map.entrySet()) {
            String[] output = entry.getValue();
            for (String s : output)
                if (conjLength.containsKey(s))
                    conjLength.replace(s, conjLength.get(s)+1);
        }
        System.out.println(pushButton("rm"));
    }
}
