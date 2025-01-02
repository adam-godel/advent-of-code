import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
public class Day24 {
    private static boolean isDuplicate(char[][] data, ArrayList<char[][]> states) {
        for (char[][] state : states) {
            boolean duplicate = true;   
            for (int i = 0; i < data.length; i++)
                for (int j = 0; j < data[i].length; j++)
                    if (data[i][j] != state[i][j]) {
                        duplicate = false;
                        break;
                    }
            if (duplicate)
                return true;
        }
        return false;
    }
    private static int getNumAdj(char[][] data, int i, int j) {
        int count = 0;
        if (i-1 >= 0 && data[i-1][j] == '#') count++;
        if (i+1 < data.length && data[i+1][j] == '#') count++;
        if (j-1 >= 0 && data[i][j-1] == '#') count++;
        if (j+1 < data[i].length && data[i][j+1] == '#') count++;
        return count;
    }
    private static int passMinute(char[][] data, ArrayList<char[][]> states) {
        char[][] helper = new char[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                int numAdj = getNumAdj(data, i, j);
                if (data[i][j] == '#' && numAdj != 1)
                    helper[i][j] = '.';
                else if (data[i][j] == '.' && (numAdj == 1 || numAdj == 2))
                    helper[i][j] = '#';
                else
                    helper[i][j] = data[i][j];
            }
        }
        for (int i = 0; i < helper.length; i++)
            for (int j = 0; j < helper[i].length; j++)
                data[i][j] = helper[i][j];
        if (isDuplicate(data, states)) {
            int result = 0;
            for (int i = 0; i < data.length; i++)
                for (int j = 0; j < data[i].length; j++)
                    if (data[i][j] == '#')
                        result += Math.pow(2, i*data.length+j);
            return result;
        }
        states.add(helper);
        return -1;
    }
    private static int numAdjRecur(HashMap<Integer, char[][]> data, int level, int i, int j) {
        int count = 0; 
        char[][] levelData = data.get(level);
        if (i-1 >= 0 && levelData[i-1][j] == '#') count++;
        if (i+1 < levelData.length && levelData[i+1][j] == '#') count++;
        if (j-1 >= 0 && levelData[i][j-1] == '#') count++;
        if (j+1 < levelData[i].length && levelData[i][j+1] == '#') count++;
        char[][] lowerData = data.get(level+1);
        if (lowerData != null) {
            if (i-1 >= 0 && levelData[i-1][j] == '?') {
                for (int lowerJ = 0; lowerJ < lowerData[i].length; lowerJ++)
                    if (lowerData[lowerData.length-1][lowerJ] == '#') 
                        count++;
            } else if (i+1 < levelData.length && levelData[i+1][j] == '?') {
                for (int upperJ = 0; upperJ < lowerData[i].length; upperJ++)
                    if (lowerData[0][upperJ] == '#') 
                        count++;
            } else if (j-1 >= 0 && levelData[i][j-1] == '?') {
                for (int rightI = 0; rightI < lowerData.length; rightI++)
                    if (lowerData[rightI][lowerData[rightI].length-1] == '#')
                        count++;
            } else if (j+1 < levelData[i].length && levelData[i][j+1] == '?')
                for (int leftI = 0; leftI < lowerData.length; leftI++)
                    if (lowerData[leftI][0] == '#')
                        count++;
        }
        char[][] upperData = data.get(level-1);
        if (upperData != null) {
            if (i == 0 && upperData[1][2] == '#')
                count++;
            if (j == 0 && upperData[2][1] == '#')
                count++;
            if (i == levelData.length-1 && upperData[3][2] == '#')
                count++;
            if (j == levelData[i].length-1 && upperData[2][3] == '#')
                count++;
        }
        return count;
    }
    private static void passMinuteRecur(HashMap<Integer, char[][]> data) {
        HashMap<Integer, char[][]> helper = new HashMap<>();
        char[][] blank1 = new char[data.get(0).length][data.get(0)[0].length];
        for (int i = 0; i < blank1.length; i++)
            for (int j = 0; j < blank1[i].length; j++)
                if (i == 2 && j == 2)
                    blank1[i][j] = '?';
                else
                    blank1[i][j] = '.';
        int min = Collections.min(data.keySet()); data.put(min-1, blank1); 
        char[][] blank2 = new char[data.get(0).length][data.get(0)[0].length];
        for (int i = 0; i < blank2.length; i++)
            for (int j = 0; j < blank2[i].length; j++)
                if (i == 2 && j == 2)
                    blank2[i][j] = '?';
                else
                    blank2[i][j] = '.';
        int max = Collections.max(data.keySet()); data.put(max+1, blank2);
        for (HashMap.Entry<Integer, char[][]> entry : data.entrySet()) {
            int level = entry.getKey(); char[][] levelData = entry.getValue();
            char[][] levelHelper = new char[levelData.length][levelData[0].length];
            for (int i = 0; i < levelData.length; i++) {
                for (int j = 0; j < levelData[i].length; j++) {
                    int numAdj = numAdjRecur(data, level, i, j);
                    if (levelData[i][j] == '#' && numAdj != 1)
                        levelHelper[i][j] = '.';
                    else if (levelData[i][j] == '.' && (numAdj == 1 || numAdj == 2))
                        levelHelper[i][j] = '#';
                    else
                        levelHelper[i][j] = levelData[i][j];
                }
            }
            helper.put(level, levelHelper);
        }
        for (HashMap.Entry<Integer, char[][]> entry : helper.entrySet()) {
            int level = entry.getKey(); char[][] state = entry.getValue();
            if (data.get(level) == null)
                data.put(level, new char[state.length][state[0].length]);
            for (int i = 0; i < state.length; i++)
                for (int j = 0; j < state[i].length; j++)
                    data.get(level)[i][j] = state[i][j];
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        // part 1
        Scanner scan = new Scanner(new File("day24.txt"));
        ArrayList<String> parse = new ArrayList<>();
        while (scan.hasNextLine())
            parse.add(scan.nextLine());
        char[][] oneData = new char[parse.size()][parse.get(0).length()];
        for (int i = 0; i < oneData.length; i++)
            oneData[i] = parse.get(i).toCharArray();
        ArrayList<char[][]> states = new ArrayList<>();
        int dupFound = -1;
        while (dupFound == -1)
            dupFound = passMinute(oneData, states);
        System.out.println(dupFound);

        // part 2
        HashMap<Integer, char[][]> data = new HashMap<>();
        oneData = new char[parse.size()][parse.get(0).length()];
        for (int i = 0; i < oneData.length; i++)
            oneData[i] = parse.get(i).toCharArray();
        oneData[2][2] = '?';
        data.put(0, oneData);
        for (int i = 0; i < 200; i++)
            passMinuteRecur(data);
        int count = 0;
        for (char[][] level : data.values())
            for (int i = 0; i < level.length; i++)
                for (int j = 0; j < level[i].length; j++)
                    if (level[i][j] == '#') 
                        count++;
        System.out.println(count);
    }
}
