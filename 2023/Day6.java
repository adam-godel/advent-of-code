public class Day6 {
    public static void main(String[] args) {
        int time = 48989083; //int[] time = {48, 98, 90, 83};
        long recordDistance = 390110311121360l; //int[] recordDistance = {390, 1103, 1112, 1360};
        int count = 0;
        for (long j = 0; j < time; j++) {
            long distance = (time-j)*j;
            if (distance > recordDistance)
                count++;
        }
        System.out.println(count);
    }
}
