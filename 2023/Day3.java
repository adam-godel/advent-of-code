import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day3 {
    private static boolean isSymbol(char c) {
        return (c < '0' || c > '9') && c != '.';
    }
    private static boolean isValid(String prev, String data, String next, int i) {
        while (i < data.length() && data.charAt(i) >= '0' && data.charAt(i) <= '9') {
            if ((i-1 >= 0 && ((prev != null && isSymbol(prev.charAt(i-1))) || isSymbol(data.charAt(i-1)) || (next != null && isSymbol(next.charAt(i-1))))) ||
                (i+1 < data.length() && ((prev != null && isSymbol(prev.charAt(i+1))) || isSymbol(data.charAt(i+1)) || (next != null && isSymbol(next.charAt(i+1))))) ||
                (((prev != null && isSymbol(prev.charAt(i))) || (next != null && isSymbol(next.charAt(i))))))
                return true;
            i++;
        }
        return false;
    }
    private static int hasTwoAdj(String prev, String data, String next, int i) {
        int count = 0;
        int result = 1;
        if (prev != null) {
            if (i-1 >= 0 && prev.charAt(i-1) >= '0' && prev.charAt(i-1) <= '9') {
                count++;
                if (i+1 < data.length() && (prev.charAt(i) < '0' || prev.charAt(i) > '9') && prev.charAt(i+1) >= '0' && prev.charAt(i+1) <= '9') {
                    result *= (i-3 >= 0 && prev.charAt(i-2) >= '0' && prev.charAt(i-2) <= '9') ? Integer.parseInt(prev.substring(i-3, i).replaceAll("[^0-9]", "")) : Integer.parseInt(prev.substring(i-2, i).replaceAll("[^0-9]", ""));
                    result *= (i+3 < data.length() && prev.charAt(i+2) >= '0' && prev.charAt(i+2) <= '9') ? Integer.parseInt(prev.substring(i+1, i+4).replaceAll("[^0-9]", "")) : Integer.parseInt(prev.substring(i+1, i+3).replaceAll("[^0-9]", ""));
                    count++;
                } else if (i+1 < data.length() && prev.charAt(i+1) >= '0' && prev.charAt(i+1) <= '9') {
                    result *= i-2 >= 0 ? Integer.parseInt(prev.substring(i-2, i+2).replaceAll("[^0-9]", "")) : Integer.parseInt(prev.substring(i-1, i+2).replaceAll("[^0-9]", ""));
                } else {
                    result *= (i-3 >= 0 && prev.charAt(i-2) >= '0' && prev.charAt(i-2) <= '9') ? Integer.parseInt(prev.substring(i-3, i+2).replaceAll("[^0-9]", "")) : Integer.parseInt(prev.substring(i-2, i+2).replaceAll("[^0-9]", ""));
                }
            } else if (prev.charAt(i) >= '0' && prev.charAt(i) <= '9') {
                result *= (i+2 < data.length() && prev.charAt(i+1) >= '0' && prev.charAt(i+1) <= '9') ? Integer.parseInt(prev.substring(i, i+3).replaceAll("[^0-9]", "")) : Integer.parseInt(prev.substring(i, i+2).replaceAll("[^0-9]", ""));
                count++;
            } else if (i+1 < data.length() && prev.charAt(i+1) >= '0' && prev.charAt(i+1) <= '9') {
                result *= (i+3 < data.length() && prev.charAt(i+2) >= '0' && prev.charAt(i+2) <= '9') ? Integer.parseInt(prev.substring(i+1, i+4).replaceAll("[^0-9]", "")) : Integer.parseInt(prev.substring(i+1, i+3).replaceAll("[^0-9]", ""));
                count++;
            }
        }
        if (next != null) {
            if (i-1 >= 0 && next.charAt(i-1) >= '0' && next.charAt(i-1) <= '9') {
                count++;
                if (i+1 < data.length() && (next.charAt(i) < '0' || next.charAt(i) > '9') && next.charAt(i+1) >= '0' && next.charAt(i+1) <= '9') {
                    result *= (i-3 >= 0 && next.charAt(i-2) >= '0' && next.charAt(i-2) <= '9') ? Integer.parseInt(next.substring(i-3, i).replaceAll("[^0-9]", "")) : Integer.parseInt(next.substring(i-2, i).replaceAll("[^0-9]", ""));
                    result *= (i+3 < data.length() && next.charAt(i+2) >= '0' && next.charAt(i+2) <= '9') ? Integer.parseInt(next.substring(i+1, i+4).replaceAll("[^0-9]", "")) : Integer.parseInt(next.substring(i+1, i+3).replaceAll("[^0-9]", ""));
                    count++;
                } else if (i+1 < data.length() && next.charAt(i+1) >= '0' && next.charAt(i+1) <= '9') {
                    result *= i-2 >= 0 ? Integer.parseInt(next.substring(i-2, i+2).replaceAll("[^0-9]", "")) : Integer.parseInt(next.substring(i-1, i+2).replaceAll("[^0-9]", ""));
                } else {
                    result *= (i-3 >= 0 && next.charAt(i-2) >= '0' && next.charAt(i-2) <= '9') ? Integer.parseInt(next.substring(i-3, i+2).replaceAll("[^0-9]", "")) : Integer.parseInt(next.substring(i-2, i+2).replaceAll("[^0-9]", ""));
                }
            } else if (next.charAt(i) >= '0' && next.charAt(i) <= '9') {
                result *= (i+2 < data.length() && next.charAt(i+1) >= '0' && next.charAt(i+1) <= '9') ? Integer.parseInt(next.substring(i, i+3).replaceAll("[^0-9]", "")) : Integer.parseInt(next.substring(i, i+2).replaceAll("[^0-9]", ""));
                count++;
            } else if (i+1 < data.length() && next.charAt(i+1) >= '0' && next.charAt(i+1) <= '9') {
                result *= (i+3 < data.length() && next.charAt(i+2) >= '0' && next.charAt(i+2) <= '9') ? Integer.parseInt(next.substring(i+1, i+4).replaceAll("[^0-9]", "")) : Integer.parseInt(next.substring(i+1, i+3).replaceAll("[^0-9]", ""));
                count++;
            }
        }
        if (i-1 >= 0 && data.charAt(i-1) >= '0' && data.charAt(i-1) <= '9') {
            result *= (i-3 >= 0 && data.charAt(i-2) >= '0' && data.charAt(i-2) <= '9') ? Integer.parseInt(data.substring(i-3, i).replaceAll("[^0-9]", "")) : Integer.parseInt(data.substring(i-2, i).replaceAll("[^0-9]", ""));
            count++;
        }
        if (i+1 < data.length() && data.charAt(i+1) >= '0' && data.charAt(i+1) <= '9') {
            result *= (i+3 < data.length() && data.charAt(i+2) >= '0' && data.charAt(i+2) <= '9') ? Integer.parseInt(data.substring(i+1, i+4).replaceAll("[^0-9]", "")) : Integer.parseInt(data.substring(i+1, i+3).replaceAll("[^0-9]", ""));
            count++;
        }
        if (count == 2)
            return result;
        return 0;
    }
    public static int gearRatio(String prev, String data, String next) {
        int result = 0;
        for (int i = 0; i < data.length(); i++)
            if (data.charAt(i) == '*')
                result += hasTwoAdj(prev, data, next, i);
        return result;
    }
    public static int adjacent(String prev, String data, String next) {
        int result = 0;
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) >= '0' && data.charAt(i) <= '9' && isValid(prev, data, next, i)) {
                if (i+2 < data.length() && data.charAt(i+1) >= '0' && data.charAt(i+1) <= '9' && data.charAt(i+2) >= '0' && data.charAt(i+2) <= '9') {
                    result += Integer.parseInt(data.substring(i, i+3));
                    i += 2;
                } else if (i+1 < data.length() && data.charAt(i+1) >= '0' && data.charAt(i+1) <= '9') {
                    result += Integer.parseInt(data.substring(i, i+2));
                    i++;
                } else {
                    result += Integer.parseInt(data.substring(i, i+1));
                }
            }
        }
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day3.txt"));
        int result = 0;
        String prev = null, data = scan.nextLine(), next = scan.nextLine();
        while (scan.hasNextLine()) {
            result += gearRatio(prev, data, next);
            prev = data;
            data = next;
            next = scan.nextLine();
        }
        while (data != null) {
            result += gearRatio(prev, data, next);
            prev = data;
            data = next;
            next = null;
        }
        System.out.println(result);
        scan.close();
    }
}
