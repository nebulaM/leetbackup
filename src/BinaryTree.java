

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTree {

    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) { this.val = val; }
    }

    /**
     * 199. Binary Tree Right Side View [recursion]
     *
     * Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.
     *
     * Example:
     *
     * Input: [1,2,3,null,4,null,null]
     * Output: [1, 3, 4]
     * Explanation:
     *
     *    1            <---
     *  /   \
     * 2     3         <---
     *  \
     *   4             <---
     *
     * @param root - Root of the given tree.
     * @return soln - List of integer contains binary tree view from left.
     */
    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> soln = new ArrayList<>();
        _rightSideView(root, soln, 0);
        return soln;
    }

    private static void _rightSideView(TreeNode root, List<Integer> soln, int level) {
        if (root == null)
            return;
        if (soln.size() <= level)
            soln.add(root.val);
        _rightSideView(root.right, soln, level + 1);
        _rightSideView(root.left, soln, level + 1);
    }

    /**
     * 230. Kth Smallest Element in a BST
     *
     * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
     *
     * Example:
     *
     * Input: root = [3,1,4,null,2], k = 1
     * Output: 1
     * Input: root = [5,3,6,2,4,null,null,1], k = 3
     * Output: 3
     *
     * @param root - Root of the given tree.
     * @param k - A number.
     * @return kthSmallest - kth smallest element in the tree.
     */
    private static int kthSmallestRet;
    private static int kthSmallestCount;
    public static int kthSmallest(TreeNode root, int k) {
        kthSmallestRet = 0;
        kthSmallestCount = k;
        _kthSmallest(root);
        return kthSmallestRet;
    }

    private static void _kthSmallest(TreeNode root)
    {
        if (null == root)
            return;
        _kthSmallest(root.left);

        kthSmallestCount -= 1;
        if (0 == kthSmallestCount)
        {
            kthSmallestRet = root.val;
            return;
        }

        _kthSmallest(root.right);
    }

    public static TreeNode stringToTreeNode(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return null;
        }

        String[] parts = input.split(",");
        String item = parts[0];
        TreeNode root = new TreeNode(Integer.parseInt(item));
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        int index = 1;
        while(!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int leftNumber = Integer.parseInt(item);
                node.left = new TreeNode(leftNumber);
                nodeQueue.add(node.left);
            }

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int rightNumber = Integer.parseInt(item);
                node.right = new TreeNode(rightNumber);
                nodeQueue.add(node.right);
            }
        }
        return root;
    }

    public static String integerArrayListToString(List<Integer> nums, int length) {
        if (length == 0) {
            return "[]";
        }

        String result = "";
        for(int index = 0; index < length; index++) {
            Integer number = nums.get(index);
            result += Integer.toString(number) + ", ";
        }
        return "[" + result.substring(0, result.length() - 2) + "]";
    }

    public static String integerArrayListToString(List<Integer> nums) {
        return integerArrayListToString(nums, nums.size());
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            String[] lines = line.split(" ",2);
            int testCode = Integer.parseInt(lines[0]);

            String out;
            switch (testCode)
            {
                case 199: { // Input: "199 [array of tree]"
                    TreeNode root = stringToTreeNode(lines[1]);
                    List<Integer> ret = rightSideView(root);
                    out = integerArrayListToString(ret);
                    break;
                }
                case 230: { // Input: "230 [array of tree] k"
                    String[] inputs = lines[1].split(" ", 2);
                    TreeNode root = stringToTreeNode(inputs[0]);
                    int ret = kthSmallest(root, Integer.parseInt(inputs[1]));
                    out = Integer.toString(ret);
                    break;
                }
                default: {
                    out = "Invalid input";
                    break;
                }
            }
            System.out.println(out);
        }
    }
}
