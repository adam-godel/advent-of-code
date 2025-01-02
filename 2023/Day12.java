import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
public class Day12 {
    private static String processBroken(String data) {
        int count = 0; String result = "";
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '#') {
                count++;
            } else if (count > 0) {
                result += count + ",";
                count = 0;
            }
        }
        if (count > 0)
            result += count + ",";
        result = result.length() > 0 ? result.substring(0, result.length()-1) : "";
        return result;
    }
    private static int countBroken(String data, String counts) {
        return processBroken(data).equals(counts) ? 1 : 0;
    }
    public static int arrangementsIter(String data, String counts) {
        int count = 0;
        for (int i = 0; i < data.length(); i++)
            if (data.charAt(i) == '?')
                count++;
        int result = 0; int perm = (int)Math.pow(2, count);
        for (int i = 0; i < perm; i++) {
            String test = data;
            for (int j = 0; j < count; j++) {
                if (i / (int)Math.pow(2, j) % 2 == 0)
                    test = test.replaceFirst("\\?", "\\.");
                else
                    test = test.replaceFirst("\\?", "\\#");
            }
            result += countBroken(test, counts);
        }
        return result;
    }
    public static long arrangementsRecur(String data, int[] counts, int i) {
        int sum = 0;
        for (int j = i; j < counts.length; j++)
            sum += counts[j];
        if (sum > data.length())
            return 0;
        if (data.charAt(0) == '.')
            return arrangementsMemo(data.substring(1), counts, i);
        long check1 = 0, check2 = 0;
        if (data.charAt(0) == '?')
            check1 = arrangementsMemo(data.substring(1), counts, i);
        if (data.substring(0, counts[i]).replace(".", "").length() == counts[i] && (data.length() <= counts[i] || data.charAt(counts[i]) != '#'))
            check2 = data.length() > counts[i] ? arrangementsMemo(data.substring(counts[i]+1), counts, i+1) : (i == counts.length-1 ? 1 : 0);
        return check1+check2;
    }
    private static Map<String, Long> memo = new HashMap<String, Long>();
    private static long arrangementsMemo(String data, int[] counts, int i) {
        if (i == counts.length)
            return data.replace("#","").equals(data) ? 1 : 0;
        String key = data+counts[i];
        /*if (memo.containsKey(key))
            return memo.get(key);*/
        long result = arrangementsRecur(data, counts, i);
        memo.put(key, result);
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day12.txt"));
        long sum = 0;
        while (scan.hasNextLine()) {
            String[] data = scan.nextLine().split(" ");
            String[] parseCounts = data[1].split(",");
            int[] counts = new int[5*parseCounts.length];
            for (int i = 0; i < counts.length; i++)
                counts[i] = Integer.parseInt(parseCounts[i % parseCounts.length]);
            sum += arrangementsMemo((data[0]+"?").repeat(4)+data[0], counts, 0);
        }
        System.out.println(sum);
        scan.close();
    }
}
