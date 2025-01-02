import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Day8 {
    private static long gcd(long n1, long n2) {
        if (n2 == 0)
            return n1;
        return gcd(n2, n1 % n2);
    }
    private static long lcm(long a, long b) {
        return a*b / gcd(a, b);
    }
    private static long lcm(int[] input) {
        long result = input[0];
        for (int i : input) 
            result = lcm(result, i);
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day8.txt"));
        String seq = scan.nextLine();
        scan.nextLine();
        HashMap<String, String[]> map = new HashMap<String, String[]>();
        ArrayList<String> keys = new ArrayList<String>();
        while (scan.hasNextLine()) {
            String[] data = scan.nextLine().split("[^\\w']+");
            map.put(data[0], new String[]{data[1], data[2]});
            if (data[0].charAt(data[0].length()-1) == 'A')
                keys.add(data[0]);
        }
        int[] counts = new int[keys.size()];
        int i = 0;
        for (int j = 0; j < keys.size(); j++) {
            int count = 0;
            while (keys.get(j).charAt(keys.get(j).length()-1) != 'Z') {
                if (seq.charAt(i) == 'L')
                    keys.add(j, map.get(keys.remove(j))[0]);
                else if (seq.charAt(i) == 'R')
                    keys.add(j, map.get(keys.remove(j))[1]);
                i = (i+1) % seq.length();
                count++;
            }
            counts[j] = count;
        }
        System.out.println(lcm(counts));
    }
}
