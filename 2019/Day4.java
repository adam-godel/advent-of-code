public class Day4 {
    public static void main(String[] args) {
        int count = 0;
        for (int i = 158126; i <= 624574; i++) {
            String s = ""+i;
            boolean twoAdj = false, adjSolid = false; int adjCount = 0;
            for (int j = 0; j < s.length()-1; j++)
                if (s.charAt(j) == s.charAt(j+1)) {
                    if (++adjCount < 2)
                        twoAdj = true;
                    else
                        twoAdj = false;
                } else {
                    if (twoAdj)
                        adjSolid = true;
                    adjCount = 0;
                }
            if (twoAdj)
                adjSolid = true;
            boolean ascending = true;
            for (int j = 0; j < s.length()-1; j++)
                if (s.charAt(j+1) < s.charAt(j))
                    ascending = false;
            if (adjSolid && ascending)
                count++;
        }
        System.out.println(count);
    }
}
