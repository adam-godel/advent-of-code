import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
public class Day24 {
    private static class Point {
        long x, y, z; double vx, vy, vz;
        Point(long x, long y, long z, double vx, double vy, double vz) {
            this.x = x; this.y = y; this.z = z;
            this.vx = vx; this.vy = vy; this.vz = vz;
        }
    }
    private static boolean intersectXY(Point a, Point b) {
        if (a.vx/b.vx == a.vy/b.vy)
            return false;
        long c1 = a.x-b.x, c2 = a.y-b.y; 
        double a1 = -1*a.vx, b1 = b.vx, a2 = -1*a.vy, b2 = b.vy;
        double t = (c1*b2-b1*c2)/(a1*b2-b1*a2);
        double s = (a1*c2-c1*a2)/(a1*b2-b1*a2);
        if (t < 0 || s < 0)
            return false;
        double interX = a.x+a.vx*t; double interY = b.y+b.vy*s;
        long min = 200000000000000l, max = 400000000000000l;
        if (interX < min || interX > max || interY < min || interY > max)
            return false;
        return true;
    }
    private static long intersectAll(ArrayList<Point> points) {
        Point a = points.get(0), b = points.get(1), c = points.get(2);

        double abvx = a.vx-b.vx; double abvy = a.vy-b.vy; double abvz = a.vz-b.vz;
        double acvx = a.vx-c.vx; double acvy = a.vy-c.vy; double acvz = a.vz-c.vz;
        double abx = a.x-b.x; double aby = a.y-b.y; double abz = a.z-b.z;
        double acx = a.x-c.x; double acy = a.y-c.y; double acz = a.z-c.z;
        double c1 = (b.y*b.vx-b.x*b.vy)-(a.y*a.vx-a.x*a.vy);
        double c2 = (c.y*c.vx-c.x*c.vy)-(a.y*a.vx-a.x*a.vy);
        double c3 = (b.x*b.vz-b.z*b.vx)-(a.x*a.vz-a.z*a.vx);
        double c4 = (c.x*c.vz-c.z*c.vx)-(a.x*a.vz-a.z*a.vx);
        double c5 = (b.z*b.vy-b.y*b.vz)-(a.z*a.vy-a.y*a.vz);
        double c6 = (c.z*c.vy-c.y*c.vz)-(a.z*a.vy-a.y*a.vz);

        double Pxz = acvx*abz-abvx*acz; double Pyx = acvy*abx-abvy*acx; double Pzy = acvz*aby-abvz*acy;
        double Pxx = abvx*acx-acvx*abx; double Pzz = abvz*acz-acvz*abz; double Pyy = abvy*acy-acvy*aby;
        double Pxc = abvx*c4-acvx*c3; double Pyc = abvy*c2-acvy*c1; double Pzc = abvz*c6-acvz*c5;
        double Pxd = acvx*abvz-abvx*acvz; double Pyd = acvy*abvx-abvy*acvx; double Pzd = acvz*abvy-abvz*acvy;
        
        double Qz0 = (abvy/Pxd)*Pxx; double Qx0 = (abvy/Pxd)*Pxz-(abvx/Pyd)*Pyy-aby; double Qy0 = abx-(abvx/Pyd)*Pyx;
        double r0 = c1-(abvy/Pxd)*Pxc+(abvx/Pyd)*Pyc;
        double Qy1 = (abvx/Pzd)*Pzz; double Qz1 = (abvx/Pzd)*Pzy-(abvz/Pxd)*Pxx-abx; double Qx1 = abz-(abvz/Pxd)*Pxz;
        double r1 = c3-(abvx/Pzd)*Pzc+(abvz/Pxd)*Pxc;
        double Qx2 = (abvz/Pyd)*Pyy; double Qy2 = (abvz/Pyd)*Pyx-(abvy/Pzd)*Pzz-abz; double Qz2 = aby-(abvy/Pzd)*Pzy;
        double r2 = c5-(abvz/Pyd)*Pyc+(abvy/Pzd)*Pzc;
        
        double Pvz = ((Qx1*Qy0-Qx0*Qy1)*(Qx2*r0-Qx0*r2)-(Qx2*Qy0-Qx0*Qy2)*(Qx1*r0-Qx0*r1))/((Qx2*Qy0-Qx0*Qy2)*(Qx0*Qz1-Qx1*Qz0)-(Qx1*Qy0-Qx0*Qy1)*(Qx0*Qz2-Qx2*Qz0));
        double Pvy = ((Qx0*Qz1-Qx1*Qz0)*Pvz+(Qx1*r0-Qx0*r1))/(Qx1*Qy0-Qx0*Qy1);
        double Pvx = (r0-Qy0*Pvy-Qz0*Pvz)/Qx0;
        
        double Pxf = (Pxx*Pvz+Pxz*Pvx+Pxc)/Pxd;
        double Pyf = (Pyy*Pvx+Pyx*Pvy+Pyc)/Pyd;
        double Pzf = (Pzz*Pvy+Pzy*Pvz+Pzc)/Pzd;

        long Px = Math.round(Pxf), Py = Math.round(Pyf), Pz = Math.round(Pzf);
        return Px+Py+Pz;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day24.txt"));
        ArrayList<Point> points = new ArrayList<>();
        while (scan.hasNextLine()) {
            String[] data = scan.nextLine().split(" @ ");
            points.add(new Point(Long.parseLong(data[0].split(", ")[0]), Long.parseLong(data[0].split(", ")[1]), Long.parseLong(data[0].split(", ")[2]), 
                    Double.parseDouble(data[1].split(", ")[0]), Double.parseDouble(data[1].split(", ")[1]), Double.parseDouble(data[1].split(", ")[2])));
        }
        int result = 0;
        for (int i = 0; i < points.size()-1; i++)
            for (int j = i+1; j < points.size(); j++)
                if (intersectXY(points.get(i), points.get(j)))
                    result++;
        System.out.println(result);
        System.out.println(intersectAll(points));
    }
}
