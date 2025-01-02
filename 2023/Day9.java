import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day9 {
    private static int nextValue(int[] data) {
        boolean allZero = true;
        for (int i : data)
            if (i != 0) {
                allZero = false;
                break;
            }
        if (allZero)
            return 0;
        int[] differences = new int[data.length-1];
        for (int i = 0; i < data.length-1; i++)
            differences[i] = data[i+1]-data[i];
        return data[0]-nextValue(differences); // data[data.length-1]+nextValue(differences);
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day9.txt"));
        int result = 0;
        while (scan.hasNextLine()) {
            String[] parseData = scan.nextLine().split(" ");
            int[] data = new int[parseData.length];
            for (int i = 0; i < data.length; i++)
                data[i] = Integer.parseInt(parseData[i]);
            result += nextValue(data);
        }
        System.out.println(result);
    }
}
