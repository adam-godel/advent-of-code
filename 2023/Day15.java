import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
public class Day15 {
    private static int hashAlgorithm(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '=' || s.charAt(i) == '-')
                break;
            count += s.charAt(i);
            count *= 17; count %= 256;
        }
        return count;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day15.txt"));
        String[] data = scan.nextLine().split(",");
        HashMap<Integer, LinkedList<String>> map = new HashMap<>();
        for (String s : data) {
            int key = hashAlgorithm(s);
            if (s.contains("-") && map.containsKey(key)) {
                for (String label : map.get(key))
                    if (s.split("-")[0].equals(label.split("=")[0])) {
                        map.get(key).remove(label);
                        break;
                    }
            } else if (s.contains("=")) {
                if (map.get(key) == null)
                    map.put(key, new LinkedList<String>());
                boolean notInList = true;
                for (int i = 0; i < map.get(key).size(); i++) {
                    if (s.split("=")[0].equals(map.get(key).get(i).split("=")[0])) {
                        map.get(key).set(i, s);
                        notInList = false;
                    }
                }
                if (notInList)
                    map.get(key).add(map.get(key).size(), s);
            }
        }
        int result = 0;
        for (int i = 0; i < 256; i++) {
            if (map.get(i) == null)
                continue;
            LinkedList<String> list = map.get(i) != null ? map.get(i) : new LinkedList<>();
            for (int j = 0; j < list.size(); j++) {
                String s = list.get(j);
                result += (i+1)*(j+1)*(s.charAt(s.length()-1)-'0');
            }
        }
        System.out.println(result);
    }
}
