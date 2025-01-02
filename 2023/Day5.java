import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Day5 {
    private static long search(List<TreeMap<Long, Long>> list, long key) {
        Iterator<TreeMap<Long, Long>> iter = list.iterator();
        while (iter.hasNext()) {
            TreeMap<Long, Long> map = iter.next();
            long ceilRange = map.ceilingKey(key) != null ? map.get(map.ceilingKey(key))-map.ceilingKey(key) : 0;
            long floorRange = map.floorKey(key) != null ? map.get(map.floorKey(key))-map.floorKey(key) : 0;
            if (ceilRange == floorRange && floorRange != 0)
                key = map.get(map.floorKey(key)) + (key-map.floorKey(key));
        }
        return key;
    }
    private static void updateMap(Scanner scan, TreeMap<Long, Long> result) {
        while (scan.hasNextLine()) {
            String[] parseData = scan.nextLine().split(" ");
            long[] data = new long[parseData.length];
            for (int i = 0; i < data.length; i++)
                data[i] = Long.parseLong(parseData[i]);

            result.put(data[1], data[0]);
            result.put(data[1]+data[2]-1, data[0]+data[2]-1);
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        //long[] initialSeeds = {2906422699l, 6916147, 3075226163l, 146720986, 689152391, 244427042, 279234546, 382175449, 1105311711, 2036236, 3650753915l, 127044950, 3994686181l, 93904335, 1450749684, 123906789, 2044765513, 620379445, 1609835129, 60050954};
        long[] initialSeeds = {2906422699l, 3075226163l, 689152391, 279234546, 470322270, 1105311711, 3650753915l, 3994686181l, 1450749684, 2044765513, 2251558661l, 2458351809l, 1609835129};
        int[] initialRanges = {6916147, 146720986, 244427042, 191087724, 191087725, 2036236, 127044950, 93904335, 123906789, 206793148, 206793148, 206793149, 60050954};
        List<TreeMap<Long, Long>> listResult = new ArrayList<TreeMap<Long, Long>>();

        Scanner scan = new Scanner(new File("day5/seed-to-soil.txt"));
        listResult.add(new TreeMap<Long, Long>());
        updateMap(scan, listResult.get(0));

        scan = new Scanner(new File("day5/soil-to-fertilizer.txt"));
        listResult.add(new TreeMap<Long, Long>());
        updateMap(scan, listResult.get(1));

        scan = new Scanner(new File("day5/fertilizer-to-water.txt"));
        listResult.add(new TreeMap<Long, Long>());
        updateMap(scan, listResult.get(2));

        scan = new Scanner(new File("day5/water-to-light.txt"));
        listResult.add(new TreeMap<Long, Long>());
        updateMap(scan, listResult.get(3));

        scan = new Scanner(new File("day5/light-to-temperature.txt"));
        listResult.add(new TreeMap<Long, Long>());
        updateMap(scan, listResult.get(4));

        scan = new Scanner(new File("day5/temperature-to-humidity.txt"));
        listResult.add(new TreeMap<Long, Long>());
        updateMap(scan, listResult.get(5));

        scan = new Scanner(new File("day5/humidity-to-location.txt"));
        listResult.add(new TreeMap<Long, Long>());
        updateMap(scan, listResult.get(6));

        long[] result = new long[initialSeeds.length];
        for (int i = 0; i < initialSeeds.length; i++) {
            long[] seedRange = new long[initialRanges[i]];
            for (int j = 0; j < seedRange.length; j++)
                seedRange[j] = search(listResult, initialSeeds[i]++);
            long min = Long.MAX_VALUE;
            for (long l : seedRange)
                if (l < min)
                    min = l;
            result[i] = min;
            System.out.println(result[i]);
        }
        long min = Long.MAX_VALUE;
        for (long l : result)
            if (l < min)
                min = l;
        System.out.println("////////\n" + min);

        scan.close();
    }
}
