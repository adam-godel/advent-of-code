import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayDeque;
import java.math.BigInteger;
public class Day22 {
    private static BigInteger numCards = new BigInteger("119315717514047");
    private static class LinearEq {
        BigInteger m, b; // y = mx+b
        LinearEq(BigInteger m, BigInteger b) {
            this.m = m; this.b = b;
        }
        void compose(BigInteger m, BigInteger b) {
            this.m = this.m.multiply(m).mod(numCards);
            this.b = this.b.multiply(m).add(b).mod(numCards);
        }
        BigInteger solve(BigInteger x, BigInteger rep) {
            BigInteger repM = m.modPow(rep, numCards);
            BigInteger repB = b.multiply(repM.subtract(BigInteger.ONE)).multiply(m.subtract(BigInteger.ONE).modInverse(numCards)).mod(numCards);
            return x.subtract(repB).multiply(repM.modInverse(numCards)).mod(numCards);
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        // part 1
        Scanner scan = new Scanner(new File("day22.txt"));
        ArrayDeque<Integer> deck = new ArrayDeque<>();
        for (int i = 0; i < 10007; i++)
            deck.addLast(i);
        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            if (data.contains("deal into new stack")) {
                ArrayDeque<Integer> helper = new ArrayDeque<>();
                while (!deck.isEmpty())
                    helper.addFirst(deck.removeFirst());
                while (!helper.isEmpty())
                    deck.addFirst(helper.removeLast());
            } else if (data.contains("deal with increment")) {
                int increment = Integer.parseInt(data.replace("deal with increment ", ""));
                int[] helper = new int[deck.size()]; int i = 0;
                while (!deck.isEmpty()) {
                    helper[i] = deck.removeFirst();
                    i = (i+increment) % helper.length;
                }
                for (int card : helper)
                    deck.addLast(card);
            } else if (data.contains("cut -")) {
                for (int i = 0; i < Integer.parseInt(data.replace("cut -", "")); i++)
                    deck.addFirst(deck.removeLast());
            } else if (data.contains("cut")) {
                for (int i = 0; i < Integer.parseInt(data.replace("cut ", "")); i++)
                    deck.addLast(deck.removeFirst());
            }
        }
        int i = 0;
        while (deck.peekFirst() != 2019) {
            deck.removeFirst(); i++;
        }
        System.out.println(i);

        // part 2
        scan = new Scanner(new File("day22.txt"));
        BigInteger x = new BigInteger("2020");
        LinearEq shuffle = new LinearEq(BigInteger.ONE, BigInteger.ZERO);
        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            if (data.contains("deal into new stack"))
                shuffle.compose(BigInteger.ONE.negate(), BigInteger.ONE.negate());
            else if (data.contains("deal with increment"))
                shuffle.compose(new BigInteger(data.replace("deal with increment ", "")), BigInteger.ZERO);
            else if (data.contains("cut"))
                shuffle.compose(BigInteger.ONE, (new BigInteger(data.replace("cut ", ""))).negate());
        }
        BigInteger numShuffles = new BigInteger("101741582076661");
        System.out.println(shuffle.solve(x, numShuffles));
    }
}
