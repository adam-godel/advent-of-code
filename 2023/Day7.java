import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
public class Day7 {
    private static int getVal(char c) {
        if (c == 'A')
            return 14;
        if (c == 'K')
            return 13;
        if (c == 'Q')
            return 12;
        if (c == 'J')
            return 0;
        if (c == 'T')
            return 10;
        return c-'0';
    }
    private static int getRank(ArrayList<Integer> val, ArrayList<String> rank, String data) {
        int maxSum = 0;
        for (int i = 0; i < data.length(); i++) {
            int[] occur = new int[data.length()];
            String temp = data.replace('J', data.charAt(i));
            for (int j = 0; j < temp.length(); j++)
                for (int k = 0; k < temp.length(); k++)
                    if (temp.charAt(j) == temp.charAt(k))
                        occur[j]++;
            if (Arrays.stream(occur).sum() > maxSum)
                maxSum = Arrays.stream(occur).sum();
        }
        int i;
        for (i = 0; i < val.size(); i++) {
            if (maxSum < val.get(i)) {
                break;
            } else if (maxSum == val.get(i)) {
                String other = rank.get(i);
                boolean dataGreater = false;
                for (int j = 0; j < other.length(); j++) {
                    if (data.charAt(j) == other.charAt(j))
                        continue;
                    dataGreater = getVal(data.charAt(j)) > getVal(other.charAt(j));
                    break;
                }
                if (!dataGreater)
                    break;
            }
        }
        rank.add(i, data);
        val.add(i, maxSum);
        return i;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day7.txt"));
        ArrayList<String> rank = new ArrayList<String>();
        ArrayList<Integer> val = new ArrayList<Integer>();
        ArrayList<Integer> bid = new ArrayList<Integer>();
        while (scan.hasNextLine()) {
            String[] data = scan.nextLine().split(" ");
            int i = getRank(val, rank, data[0]);
            bid.add(i, Integer.parseInt(data[1]));
        }
        int result = 0;
        for (int i = 0; i < bid.size(); i++)
            result += bid.get(i)*(i+1);
        System.out.println(result);
        scan.close();
    }
}
