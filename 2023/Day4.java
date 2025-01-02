import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
public class Day4 {
    public static int getPoints(String s) {
        int result = 0;

        String[] parseWin = s.split(" \\| ")[0].replace("  ", " ").trim().split(" ");
        String[] parsePersonal = s.split(" \\| ")[1].replace("  ", " ").trim().split(" ");
        int[] win = new int[parseWin.length]; 
        for (int i = 0; i < win.length; i++)
            win[i] = Integer.parseInt(parseWin[i]);
        int[] personal = new int[parsePersonal.length];
        for (int i = 0; i < personal.length; i++)
            personal[i] = Integer.parseInt(parsePersonal[i]);
        
        for (int i = 0; i < personal.length; i++)
            for (int j = 0; j < win.length; j++)
                if (personal[i] == win[j])
                    result++; // result = result == 0 ? 1 : result*2;

        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day4.txt"));
        int[] match = new int[193]; int[] result = new int[193]; int i = 0;
        while (scan.hasNextLine()) {
            String data = scan.nextLine().split(": ")[1];
            match[i] = getPoints(data);
            result[i]++;
            for (int j = i+1; j < i+1+match[i] && j < result.length; j++) {
                result[j] += result[i];
            }
            i++;
        }
        System.out.println(Arrays.stream(result).sum());
        scan.close();
    }
}
