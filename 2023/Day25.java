import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
public class Day25 {
    private static int BFSCount(HashMap<String, LinkedList<String>> map, String start) {
        LinkedList<String> visited = new LinkedList<>();
        Queue<String> queue = new LinkedList<>(); queue.add(start);
        int count = 0;
        while (!queue.isEmpty()) {
            String cur = queue.remove();
            if (!visited.contains(cur)) count++;
            visited.add(cur);
            for (String s : map.get(cur))
                if (!visited.contains(s))
                    queue.add(s);
        }
        return count;
    }
    private static void BFSDest(HashMap<String, LinkedList<String>> map, String start, String end) {
        HashMap<String, Boolean> visited = new HashMap<>();
        HashMap<String, String> sources = new HashMap<>();
        Queue<String> queue = new LinkedList<>(); queue.add(start);
        while (!queue.isEmpty()) {
            String cur = queue.remove();
            visited.put(cur, true);
            if (cur.equals(end)) {
                String trav = cur;
                while (trav != null) {
                    if (map.get(trav) != null) map.get(trav).remove(sources.get(trav));
                    if (map.get(sources.get(trav)) != null) map.get(sources.get(trav)).remove(trav);
                    trav = sources.get(trav);
                }
                return;
            }
            for (String s : map.get(cur))
                if (visited.get(s) == null || !visited.get(s)) {
                    sources.put(s, cur);
                    queue.add(s);
                }
        }
    }
    private static int minCut(HashMap<String, LinkedList<String>> map) {
        String start = (String)map.keySet().toArray()[(int)(Math.random()*map.keySet().size())];
        String end = (String)map.keySet().toArray()[(int)(Math.random()*map.keySet().size())];
        for (int i = 0; i < 3; i++)
            BFSDest(map, start, end);
        int startCount = BFSCount(map, start), endCount = BFSCount(map, end);
        return startCount*endCount;
    }
    public static void main(String[] args) throws FileNotFoundException {
        int result = 0, squareSize = 0;
        do {
            Scanner scan = new Scanner(new File("day25.txt"));
            HashMap<String, LinkedList<String>> map = new HashMap<>();
            while (scan.hasNextLine()) {
                String[] data = scan.nextLine().replace(":","").split(" ");
                if (map.get(data[0]) == null)
                    map.put(data[0], new LinkedList<>());
                for (int i = 1; i < data.length; i++) {
                    map.get(data[0]).add(data[i]);
                    if (map.get(data[i]) == null)
                        map.put(data[i], new LinkedList<>());
                    map.get(data[i]).add(data[0]);
                }
                squareSize++;
            }
            squareSize *= squareSize;
            result = minCut(map);
        } while (result == squareSize);
        System.out.println(result);
    }
}
