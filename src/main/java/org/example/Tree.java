package org.example;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

import java.util.ArrayList;
import java.util.Scanner;

enum Color {
    RED, GREEN
}

abstract class Tree {

    private int value;
    private Color color;
    private int depth;

    public Tree(int value, Color color, int depth) {
        this.value = value;
        this.color = color;
        this.depth = depth;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public int getDepth() {
        return depth;
    }

    public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {

    public ArrayList<Tree> children = new ArrayList<>();

    public TreeNode(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitNode(this);

        for (Tree child : children) {
            child.accept(visitor);
        }
    }

    public void addChild(Tree child) {
        children.add(child);
    }
}

class TreeLeaf extends Tree {

    public TreeLeaf(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitLeaf(this);
    }
}

abstract class TreeVis
{
    public abstract int getResult();
    public abstract void visitNode(TreeNode node);
    public abstract void visitLeaf(TreeLeaf leaf);

}

class SumInLeavesVisitor extends TreeVis {
    int sum = 0;
    public int getResult() {
        int result = sum;
        sum = 0;
        return result;
    }

    public void visitNode(TreeNode node) {
    }

    public void visitLeaf(TreeLeaf leaf) {
        sum += leaf.getValue();
    }
}

class ProductOfRedNodesVisitor extends TreeVis {
    int mult = 1;
    public int getResult() {
        int result = mult;
        mult = 1;
        return result;
    }

    public void visitNode(TreeNode node) {
        if (node.getValue() == 0) {
            mult = 1;
        } else if (node.getColor() == Color.RED) {
            mult = (int)((((long) mult) * node.getValue()) % 1000000007);
        }
    }

    public void visitLeaf(TreeLeaf leaf) {
        if (leaf.getValue() == 0) {
            mult = 1;
        } else if (leaf.getColor() == Color.RED) {
            mult = (int)((((long) mult) * leaf.getValue()) % 1000000007);
        }
    }
}

class FancyVisitor extends TreeVis {
    int sumOfEven = 0;
    int sumOfGreen = 0;
    public int getResult() {
        int result = Math.abs(sumOfEven - sumOfGreen);
        sumOfGreen = 0;
        sumOfEven = 0;
        return result;
    }

    public void visitNode(TreeNode node) {
        if (node.getDepth() % 2 == 0) {
            sumOfEven += node.getValue();
        }
    }

    public void visitLeaf(TreeLeaf leaf) {
        if (leaf.getColor() == Color.GREEN) {
            sumOfGreen += leaf.getValue();
        }
    }
}

