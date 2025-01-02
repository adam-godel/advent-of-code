import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
public class Day12 {
    private static class Moon {
        int x, y, z, vx, vy, vz;
        Moon(int x, int y, int z) {
            this.x = x; this.y = y; this.z = z;
            vx = 0; vy = 0; vz= 0;
        }
        void applyVel() {
            x += vx; y += vy; z += vz;
        }
    }
    private static long gcd(long n1, long n2) {
        if (n2 == 0)
            return n1;
        return gcd(n2, n1 % n2);
    }
    private static long lcm(long a, long b) {
        return a*b / gcd(a, b);
    }
    private static long lcm(int[] input) {
        long result = input[0];
        for (int i : input) 
            result = lcm(result, i);
        return result;
    }
    private static void gravity(Moon m1, Moon m2) {
        if (m1.x < m2.x) {
            m1.vx++; m2.vx--;
        } else if (m1.x > m2.x) {
            m1.vx--; m2.vx++;
        }
        if (m1.y < m2.y) {
            m1.vy++; m2.vy--;
        } else if (m1.y > m2.y) {
            m1.vy--; m2.vy++;
        }
        if (m1.z < m2.z) {
            m1.vz++; m2.vz--;
        } else if (m1.z > m2.z) {
            m1.vz--; m2.vz++;
        }
    }
    private static long untilRepeat(ArrayList<Moon> moons) {
        int x = 0; String init = "";
        for (Moon m : moons)
            init += m.x + " " + m.vx + " ";
        while (true) {
            String str = "";
            for (Moon m : moons)
                str += m.x + " " + m.vx + " ";
            if (str.equals(init) && x > 0)
                break;
            for (int i = 0; i < moons.size()-1; i++)
                for (int j = i+1; j < moons.size(); j++)
                    gravity(moons.get(i), moons.get(j));
            for (Moon m : moons)
                m.applyVel();
            x++;
        }
        int y = 0; init = "";
        for (Moon m : moons)
            init += m.y + " " + m.vy + " ";
        while (true) {
            String str = "";
            for (Moon m : moons)
                str += m.y + " " + m.vy + " ";
            if (str.equals(init) && y > 0)
                break;
            for (int i = 0; i < moons.size()-1; i++)
                for (int j = i+1; j < moons.size(); j++)
                    gravity(moons.get(i), moons.get(j));
            for (Moon m : moons)
                m.applyVel();
            y++;
        }
        int z = 0; init = "";
        for (Moon m : moons)
            init += m.z + " " + m.vz + " ";
        while (true) {
            String str = "";
            for (Moon m : moons)
                str += m.z + " " + m.vz + " ";
            if (init.equals(str) && z > 0)
                break;
            for (int i = 0; i < moons.size()-1; i++)
                for (int j = i+1; j < moons.size(); j++)
                    gravity(moons.get(i), moons.get(j));
            for (Moon m : moons)
                m.applyVel();
            z++;
        }
        return lcm(new int[]{x, y, z});
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day12.txt"));
        ArrayList<Moon> moons = new ArrayList<>();
        while (scan.hasNextLine()) {
            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(scan.nextLine());
            int[] pos = new int[3]; int i = 0;
            while (m.find())
                pos[i++] = Integer.parseInt(m.group());
            moons.add(new Moon(pos[0], pos[1], pos[2]));
        }
        for (int step = 0; step < 1000; step++) {
            for (int i = 0; i < moons.size()-1; i++)
                for (int j = i+1; j < moons.size(); j++)
                    gravity(moons.get(i), moons.get(j));
            for (Moon m : moons)
                m.applyVel();
        }
        int result = 0;
        for (Moon m : moons)
            result += (Math.abs(m.x)+Math.abs(m.y)+Math.abs(m.z))*(Math.abs(m.vx)+Math.abs(m.vy)+Math.abs(m.vz));
        System.out.println(result);
        scan = new Scanner(new File("day12.txt"));
        moons = new ArrayList<>();
        while (scan.hasNextLine()) {
            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(scan.nextLine());
            int[] pos = new int[3]; int i = 0;
            while (m.find())
                pos[i++] = Integer.parseInt(m.group());
            moons.add(new Moon(pos[0], pos[1], pos[2]));
        }
        System.out.println(untilRepeat(moons));
    }
}