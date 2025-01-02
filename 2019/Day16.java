import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day16 {
    private static int[] FFT(int[] input, int offset) {
        int[] base = {0, 1, 0, -1};
        int[] result = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            int val = 0, idx = 0;
            for (int j = i; j < input.length; j++) {
                if ((j+1) % (i+1) == 0)
                    idx = (idx+1) % base.length;
                val += input[j]*base[idx];
            }
            result[i] = Math.abs(val) % 10;
        }
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day16.txt"));
        String parse = scan.nextLine();
        int[] data = new int[parse.length()*10000];
        for (int i = 0; i < data.length; i++)
            data[i] = parse.charAt(i % parse.length()) - '0';
        String offsetParse = "";
        for (int i = 0; i < 7; i++)
            offsetParse += data[i];
        int offset = Integer.parseInt(offsetParse);
        for (int i = 0; i < 100; i++)
            data = FFT(data, offset);
        for (int i = offset; i < offset+8; i++)
            System.out.print(data[i]);
        System.out.println();
    }
}