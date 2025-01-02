import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day8 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day8.txt"));
        String parse = scan.nextLine();
        int[] data = new int[parse.length()];
        int width = 25, height = 6;
        int[] countZero = new int[data.length/(width*height)];
        for (int i = 0; i < data.length; i++) {
            data[i] = parse.charAt(i) - '0';
            if (data[i] == 0)
                countZero[i/(width*height)]++;
        }
        int min = Integer.MAX_VALUE, minIdx = 0;
        for (int i = 0; i < countZero.length; i++)
            if (countZero[i] < min) {
                min = countZero[i];
                minIdx = i;
            }
        int numOne = 0, numTwo = 0;
        for (int i = minIdx*(width*height); i < (minIdx+1)*(width*height); i++)
            if (data[i] == 1) 
                numOne++;
            else if (data[i] == 2)
                numTwo++;
        System.out.println(numOne*numTwo);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int idx = i*width+j, color = data[idx];
                while (color == 2) {
                    idx += width*height;
                    color = data[idx];
                }
                if (color == 0)
                    System.out.print(" ");
                else if (color == 1)
                    System.out.print("1");
            }
            System.out.println();
        }
    }
}