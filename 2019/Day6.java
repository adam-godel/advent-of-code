import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
public class Day6 {
    private static class TreeNode {
        String value;
        TreeNode parent;
        LinkedList<TreeNode> childNodes;
        TreeNode(String value) {
            this.value = value;
            this.childNodes = new LinkedList<>();
        }
        void addChild(TreeNode child) {
            childNodes.add(child);
        }
        void setParent(TreeNode parent) {
            this.parent = parent;
        }
        int depth() {
            int result = 0;
            TreeNode trav = this;
            while (trav.parent != null) {
                result++;
                trav = trav.parent;
            }
            return result;
        }
        public String toString() {
            return value;
        }
    }
    private static int sumDepths(TreeNode root) {
        if (root == null)
            return 0;
        int sum = root.depth();
        for (TreeNode tn : root.childNodes)
            sum += sumDepths(tn);
        return sum;
    }
    private static TreeNode get(LinkedList<TreeNode> list, String value) {
        for (TreeNode tn : list)
            if (tn.value.equals(value))
                return tn;
        return null;
    }
    private static boolean contains(TreeNode root, TreeNode node) {
        TreeNode trav = node;
        while (trav != null && !trav.value.equals(root.value))
            trav = trav.parent;
        if (trav == null)
            return false;
        return true;
    }
    private static TreeNode lowestCommonParent(TreeNode node1, TreeNode node2) {
        TreeNode trav = node1;
        while (trav != null && !contains(trav, node2))
            trav = trav.parent;
        return trav;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("day6.txt"));
        TreeNode root = null;
        LinkedList<TreeNode> nodes = new LinkedList<>();
        while (scan.hasNextLine()) {
            String[] data = scan.nextLine().split("\\)");
            TreeNode node = get(nodes, data[0]);
            if (node == null) {
                node = new TreeNode(data[0]);
                if (node.value.equals("COM"))
                    root = node;
                nodes.add(node);
            }
            TreeNode child = get(nodes, data[1]);
            if (child == null) {
                child = new TreeNode(data[1]);
                nodes.add(child);
            }
            node.addChild(child);
            child.setParent(node);
        }
        System.out.println(sumDepths(root));
        TreeNode YOU = get(nodes, "YOU");
        TreeNode SAN = get(nodes, "SAN");
        TreeNode LCP = lowestCommonParent(YOU, SAN);
        int result = YOU.depth() + SAN.depth() - 2*LCP.depth() - 2;
        System.out.println(result);
    }
}
