package School.282.Project 1;

public class Node {
    int numKeys;
    boolean isLeaf;
    
    public void Node() {
        int[] keys = new int[3]; // Max 3 keys
        int[] children = new int[4]; // Max 4 children
        numKeys = 0;
        isLeaf = true;
    }
}
