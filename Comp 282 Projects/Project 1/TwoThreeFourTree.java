public class TwoThreeFourTree {
    Node root;

    public TwoThreeFourTree() {
        root = null;
    }
    
    public void insert(int key) {
        if (root == null) {
            root = new Node();
            root.keys[0] = key;                
            root.numKeys = 1;
        } 
        else {
            if (root.numKeys == 3) {
                Node newRoot = new Node();
                newRoot.isLeaf = false;
                newRoot.children[0] = root;
                splitChild(newRoot, 0);
                root = newRoot;
            }
            insertNonFull(root, key);
        }
    }
    
    private void splitChild(Node parent, int index) {
        Node fullChild = parent.children[index];
        Node newChild = new Node();
        newChild.isLeaf = fullChild.isLeaf;
        newChild.numKeys = 1;
        newChild.keys[0] = fullChild.keys[2];
        parent.children[index + 1] = newChild;
        parent.keys[index] = fullChild.keys[1];
        parent.numKeys++;
        for (int i = parent.numKeys - 1; i > index; i--) {
            parent.children[i + 1] = parent.children[i];
            parent.keys[i] = parent.keys[i - 1];
        }
        for (int i = 0; i < 1; i++) {
            newChild.keys[i] = fullChild.keys[i + 2];
            fullChild.keys[i + 2] = 0;
        }
        if (!fullChild.isLeaf) {
            for (int i = 0; i < 2; i++) {
                newChild.children[i] = fullChild.children[i + 2];
                fullChild.children[i + 2] = null;
            }
        }
        fullChild.numKeys = 1;
        parent.numKeys++;
    }
    
    private void insertNonFull(Node node, int key) {
        int i = node.numKeys - 1;

        if (node.isLeaf) {
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.numKeys++;
        } else {
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
             i++;

            if (node.children[i].numKeys == 3) {
                splitChild(node, i);
                if (key > node.keys[i]) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }
    
    }
}
