import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day1 {
    public static String toInt(String s) {
        String[][] replace = {{"one", "o1e"}, {"two", "t2o"}, {"three", "t3e"}, {"four", "f4r"}, {"five", "f5e"}, {"six", "s6x"}, {"seven", "s7n"}, {"eight", "e8t"}, {"nine", "n9e"}};
        for (String[] u : replace)
            s = s.replace(u[0], u[1]);
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            char charAt = s.charAt(i);
            if (charAt >= '0' && charAt <= '9')
                result += charAt;
        }
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day1.txt"));
        int result = 0;
        while (scan.hasNextLine()) {
            String data = toInt(scan.nextLine());
            data = data.charAt(0) + "" + data.charAt(data.length()-1);
            result += Integer.parseInt(data);
        }
        System.out.println(result);
        scan.close();
    }
}