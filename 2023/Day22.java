import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
public class Day22 {
    private static HashMap<Integer, ArrayList<Cube>> zMap = new HashMap<>();
    private static class Cube {
        int x1, y1, z1, x2, y2, z2;
        Cube(int x1, int y1, int z1, int x2, int y2, int z2) {
            this.x1 = x1; this.x2 = x2; 
            this.y1 = y1; this.y2 = y2; 
            this.z1 = z1; this.z2 = z2;
            for (int i = z1; i <= z2; i++) {
                if (zMap.get(i) == null)
                    zMap.put(i, new ArrayList<>());
                zMap.get(i).add(this);
            }
        }
        Cube(int x1, int y1, int z1, int x2, int y2, int z2, boolean clone) {
            this.x1 = x1; this.x2 = x2; 
            this.y1 = y1; this.y2 = y2; 
            this.z1 = z1; this.z2 = z2;
        }
    }
    private static boolean overlapXY(Cube c1, Cube c2) {
        return ((c1.x1 >= c2.x1 && c1.x1 <= c2.x2) || (c1.x2 >= c2.x1 && c1.x2 <= c2.x2)) && ((c1.y1 >= c2.y1 && c1.y1 <= c2.y2) || (c1.y2 >= c2.y1 && c1.y2 <= c2.y2));
    }
    private static int fall(ArrayList<Cube> cubes, Cube toRemove) {
        ArrayList<Cube> orig = new ArrayList<>(); int idx = -1;
        HashMap<Integer, ArrayList<Cube>> origMap = new HashMap<>();
        if (toRemove != null) {
            for (Cube c : cubes)
                orig.add(new Cube(c.x1, c.y1, c.z1, c.x2, c.y2, c.z2, true));
            for (Map.Entry<Integer, ArrayList<Cube>> entry : zMap.entrySet()) {
                ArrayList<Cube> newList = new ArrayList<Cube>();
                for (Cube c : entry.getValue())
                    newList.add(new Cube(c.x1, c.y1, c.z1, c.x2, c.y2, c.z2, true));
                origMap.put(entry.getKey(), newList);
            }
            for (int i = toRemove.z1; i <= toRemove.z2; i++)
                zMap.get(i).remove(toRemove);
            idx = cubes.indexOf(toRemove);
            cubes.remove(toRemove);
        }
        for (Cube c : cubes) {
            while (c.z1 > 1) {
                if (zMap.get(c.z1-1) != null) {
                    ArrayList<Cube> atZ = zMap.get(c.z1-1);
                    boolean stop = false;
                    for (Cube cz : atZ)
                        if (overlapXY(c, cz) || overlapXY(cz, c)) {
                            stop = true;
                            break;
                        }
                    if (stop)
                        break;
                }
                zMap.get(c.z2).remove(c);
                c.z1--; c.z2--;
                if (zMap.get(c.z1) == null)
                    zMap.put(c.z1, new ArrayList<>());
                zMap.get(c.z1).add(c);
            }
        }
        int count = 0;
        if (toRemove != null) {
            for (int i = 0; i < cubes.size(); i++) {
                int j = (i < idx || idx == -1) ? i : i+1;
                if (cubes.get(i).z1 != orig.get(j).z1 || cubes.get(i).z2 != orig.get(j).z2)
                    count++;
            }
            cubes = orig; zMap = origMap;
        }
        return count;
    }
    private static long getSupport(ArrayList<Cube> cubes) {
        long result = 0;
        for (Cube c : cubes) {
            boolean supported = true;
            ArrayList<Cube> atZ = zMap.get(c.z2+1) != null ? zMap.get(c.z2+1) : new ArrayList<>();
            ArrayList<Cube> atUZ = zMap.get(c.z2) != null ? zMap.get(c.z2) : new ArrayList<>();
            for (Cube cz : atZ)
                if (overlapXY(c, cz) || overlapXY(cz, c)) {
                    supported = false;
                    for (Cube cuz : atUZ)
                        if (cuz != c && (overlapXY(cz, cuz) || overlapXY(cuz, cz)))
                            supported = true;
                    if (!supported)
                        break;
                }
            if (supported) {
                //result++;
            }
        }
        for (Cube c : cubes) {
            ArrayList<Cube> newCubes = new ArrayList<>();
            zMap = new HashMap<>();
            Cube newCube = null;
            for (Cube cu : cubes) {
                if (cu == c) {
                    newCube = new Cube(cu.x1, cu.y1, cu.z1, cu.x2, cu.y2, cu.z2);
                    newCubes.add(newCube);
                } else {
                    newCubes.add(new Cube(cu.x1, cu.y1, cu.z1, cu.x2, cu.y2, cu.z2));
                }
            }
            result += fall(newCubes, newCube);
        }
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day22.txt"));
        ArrayList<Cube> cubes = new ArrayList<>();
        while (scan.hasNextLine()) {
            String[] parseData = scan.nextLine().split("~");
            int[] data = {Integer.parseInt(parseData[0].split(",")[0]), Integer.parseInt(parseData[0].split(",")[1]), Integer.parseInt(parseData[0].split(",")[2]), Integer.parseInt(parseData[1].split(",")[0]), Integer.parseInt(parseData[1].split(",")[1]), Integer.parseInt(parseData[1].split(",")[2])};
            cubes.add(new Cube(data[0], data[1], data[2], data[3], data[4], data[5]));
        }
        Collections.sort(cubes, (c1, c2) -> c1.z1 - c2.z1);
        fall(cubes, null);
        System.out.println(getSupport(cubes));
    }
}
