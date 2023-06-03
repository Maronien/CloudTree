package org.example;


import java.util.*;

public class Solution {

    static int n;
    static int[] values;
    static int[] colors;
    static List<List<Integer>> matrix;

    public static Tree solve() {
        //read the tree from STDIN and return its root as a return value of this function
        Scanner in = new Scanner(System.in);

        n = in.nextInt();
        values = new int[n];
        colors = new int[n];
        matrix = new ArrayList<>();
        for(int i=0;i<n;i++){
            matrix.add(new ArrayList<Integer>());
        }
        in.nextLine();
        for(int i=0; i<n; i++) {
            values[i] = in.nextInt();
        }
        in.nextLine();
        for(int i=0; i<n; i++) {
            colors[i] = in.nextInt();
        }
        in.nextLine();
        for (int i = 0; i < n - 1; i++) {
            int node1 = in.nextInt() - 1;
            int node2 = in.nextInt() - 1;
            matrix.get(node1).add(node2);
            matrix.get(node2).add(node1);
            in.nextLine();
        }
        in.close();
        TreeNode root = new TreeNode(values[0], colors[0] == 1 ? Color.GREEN : Color.RED, 0);
        build(root, 0, 0);
        return root;

    }

    public static Tree build(Tree node, int nodeNum, int parentNum) {
        boolean isLeaf = true;
        for (int i: matrix.get(nodeNum)) {
            if (i != parentNum) {
                Tree newNode = new TreeNode(values[i], colors[i] == 1 ? Color.GREEN : Color.RED, node.getDepth() + 1);
                ((TreeNode) node).addChild(build(newNode, i, nodeNum));
                isLeaf = false;
            }
        }
        if(isLeaf){
            node = new TreeLeaf(values[nodeNum], colors[nodeNum] == 1 ? Color.GREEN : Color.RED, node.getDepth());
        }
        return node;
    }


    public static void main(String[] args) {

        Tree root = solve();
        SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
        ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
        FancyVisitor vis3 = new FancyVisitor();
        root.accept(vis1);
        root.accept(vis2);
        root.accept(vis3);

        int res1 = vis1.getResult();
        int res2 = vis2.getResult();
        int res3 = vis3.getResult();
        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
}
