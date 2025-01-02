import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
public class Day19 {
    private static HashMap<String, String> map = new HashMap<>();
    private static long workflow(int minX, int maxX, int minM, int maxM, int minA, int maxA, int minS, int maxS, String input) {
        if (input.equals("R"))
            return 0;
        if (input.equals("A"))
            return (long)(maxX-minX+1)*(maxM-minM+1)*(maxA-minA+1)*(maxS-minS+1);
        int xn = 0, mn = 1, an = 2, sn = 3;
        String[] output = map.get(input).split(",");
        long result = 0;
        int[] min = {minX, minM, minA, minS}, max = {maxX, maxM, maxA, maxS};
        for (int i = 0; i < output.length-1; i++) {
            String[] comp = output[i].split(":");
            int[] toComp = new int[2]; int valComp = Integer.parseInt(comp[0].substring(2)); int idx = 0;
            int[] x = {min[0], max[0]}, m = {min[1], max[1]}, a = {min[2], max[2]}, s = {min[3], max[3]};
            if (comp[0].charAt(0) == 'x') {
                toComp = x; idx = xn;
            } else if (comp[0].charAt(0) == 'm') {
                toComp = m; idx = mn;
            } else if (comp[0].charAt(0) == 'a') {
                toComp = a; idx = an;
            } else if (comp[0].charAt(0) == 's') {
                toComp = s; idx = sn;
            }
            if (comp[0].charAt(1) == '>' && toComp[1] > valComp) {
                if (toComp[0] <= valComp) {
                    int initMin = min[idx];
                    min[idx] = valComp+1;
                    minX = min[0]; maxX = max[0]; minM = min[1]; maxM = max[1]; minA = min[2]; maxA = max[2]; minS = min[3]; maxS = max[3];
                    result += workflow(minX, maxX, minM, maxM, minA, maxA, minS, maxS, comp[1]);
                    min[idx] = initMin;
                    max[idx] = valComp;
                    minX = min[0]; maxX = max[0]; minM = min[1]; maxM = max[1]; minA = min[2]; maxA = max[2]; minS = min[3]; maxS = max[3];
                } else {
                    return result+workflow(minX, maxX, minM, maxM, minA, maxA, minS, maxS, comp[1]);
                }
            } else if (comp[0].charAt(1) == '<' && toComp[0] < valComp) {
                if (toComp[1] >= valComp) {
                    int initMax = max[idx];
                    max[idx] = valComp-1;
                    minX = min[0]; maxX = max[0]; minM = min[1]; maxM = max[1]; minA = min[2]; maxA = max[2]; minS = min[3]; maxS = max[3];
                    result += workflow(minX, maxX, minM, maxM, minA, maxA, minS, maxS, comp[1]);
                    max[idx] = initMax;
                    min[idx] = valComp;
                    minX = min[0]; maxX = max[0]; minM = min[1]; maxM = max[1]; minA = min[2]; maxA = max[2]; minS = min[3]; maxS = max[3];
                } else {
                    return result+workflow(minX, maxX, minM, maxM, minA, maxA, minS, maxS, comp[1]);
                }
            }
        }
        minX = min[0]; maxX = max[0]; minM = min[1]; maxM = max[1]; minA = min[2]; maxA = max[2]; minS = min[3]; maxS = max[3];
        return result+workflow(minX, maxX, minM, maxM, minA, maxA, minS, maxS, output[output.length-1]);
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day19.txt"));
        while (scan.hasNextLine()) {
            String s = scan.nextLine();
            if (s.length() == 0)
                break;
            String[] data = s.split("\\{");
            map.put(data[0], data[1].substring(0, data[1].length()-1));
        }
        /*long result = 0;
        while (scan.hasNextLine()) {
            String[] data = scan.nextLine().split(",");
            int[] vals = new int[data.length];
            for (int i = 0; i < data.length; i++)
                vals[i] = Integer.parseInt(data[i].replaceAll("\\D+",""));
            result += workflow(vals, vals, "in");
        }
        System.out.println(result);*/
        System.out.println(workflow(1, 4000, 1, 4000, 1, 4000, 1, 4000, "in"));
    }
}
