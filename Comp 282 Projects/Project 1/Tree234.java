public class Tree234 {
    Node root = new Node();

    public void TwoThreeFourTree() {
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

    public void delete(int key) {
        if (root == null) {
            return; // Tree is empty, nothing to delete
        }
        deleteKey(root, key);
        // If root becomes empty after deletion
        if (root.numKeys == 0) {
            root = root.isLeaf ? null : root.children[0];
        }
    }
    
    private void deleteKey(Node node, int key) {
        int idx = findKeyIndex(node, key);
    
        if (idx < node.numKeys && node.keys[idx] == key) {
            // Key found in this node
            if (node.isLeaf) {
                // Case 1: Leaf Node
                removeFromLeaf(node, idx);
            } else {
                // Case 2: Internal Node
                removeFromInternal(node, idx);
            }
        } else {
            // Key is not found in this node
            if (node.isLeaf) {
                return; // Key is not present
            }
            boolean shouldMerge = (idx == node.numKeys);
            Node child = node.children[idx];
            
            if (child.numKeys < 2) {
                fill(child, idx);
            }
            
            if (shouldMerge && idx > node.numKeys) {
                deleteKey(node.children[idx - 1], key);
            } else {
                deleteKey(child, key);
            }
        }
    }
    
    private int findKeyIndex(Node node, int key) {
        int idx = 0;
        while (idx < node.numKeys && node.keys[idx] < key) {
            idx++;
        }
        return idx;
    }
    
    private void removeFromLeaf(Node node, int idx) {
        for (int i = idx + 1; i < node.numKeys; i++) {
            node.keys[i - 1] = node.keys[i];
        }
        node.numKeys--;
    }
    
    private void removeFromInternal(Node node, int idx) {
        Node leftChild = node.children[idx];
        Node rightChild = node.children[idx + 1];
    
        if (leftChild.numKeys >= 2) {
            // Get predecessor (max from left child)
            int predecessorKey = getPredecessor(leftChild);
            node.keys[idx] = predecessorKey;
            deleteKey(leftChild, predecessorKey);
        } else if (rightChild.numKeys >= 2) {
            // Get successor (min from right child)
            int successorKey = getSuccessor(rightChild);
            node.keys[idx] = successorKey;
            deleteKey(rightChild, successorKey);
        } else {
            // Merge
            merge(node, idx);
            deleteKey(leftChild, node.keys[idx]);
        }
    }
    
    private int getPredecessor(Node node) {
        while (!node.isLeaf) {
            node = node.children[node.numKeys];
        }
        return node.keys[node.numKeys - 1];
    }
    
    private int getSuccessor(Node node) {
        while (!node.isLeaf) {
            node = node.children[0];
        }
        return node.keys[0];
    }
    
    private void merge(Node parent, int idx) {
        Node leftChild = parent.children[idx];
        Node rightChild = parent.children[idx + 1];
    
        leftChild.keys[1] = parent.keys[idx];
        for (int i = 0; i < rightChild.numKeys; i++) {
            leftChild.keys[i + 2] = rightChild.keys[i];
        }
    
        if (!leftChild.isLeaf) {
            for (int i = 0; i <= rightChild.numKeys; i++) {
                leftChild.children[i + 2] = rightChild.children[i];
            }
        }
    
        for (int i = idx + 1; i < parent.numKeys; i++) {
            parent.keys[i - 1] = parent.keys[i];
        }
        for (int i = idx + 2; i <= parent.numKeys; i++) {
            parent.children[i - 1] = parent.children[i];
        }
    
        leftChild.numKeys += rightChild.numKeys + 1;
        parent.numKeys--;
        rightChild = null;
    }
    
    private void fill(Node node, int idx) {
        // Fill the child if it has only one key
        if (idx != 0 && node.children[idx - 1].numKeys >= 2) {
            borrowFromPrev(node, idx);
        } else if (idx != node.numKeys && node.children[idx + 1].numKeys >= 2) {
            borrowFromNext(node, idx);
        } else {
            if (idx != node.numKeys) {
                merge(node, idx);
            } else {
                merge(node, idx - 1);
            }
        }
    }
    
    private void borrowFromPrev(Node node, int idx) {
        Node child = node.children[idx];
        Node sibling = node.children[idx - 1];
    
        for (int i = child.numKeys - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }
        
        if (!child.isLeaf) {
            for (int i = child.numKeys; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
        }
    
        child.keys[0] = node.keys[idx - 1];
        if (!child.isLeaf) {
            child.children[0] = sibling.children[sibling.numKeys];
        }
    
        node.keys[idx - 1] = sibling.keys[sibling.numKeys - 1];
        child.numKeys++;
        sibling.numKeys--;
    }
    
    private void borrowFromNext(Node node, int idx) {
        Node child = node.children[idx];
        Node sibling = node.children[idx + 1];
    
        child.keys[child.numKeys] = node.keys[idx];
        
        if (!child.isLeaf) {
            child.children[child.numKeys + 1] = sibling.children[0];
        }
    
        node.keys[idx] = sibling.keys[0];
    
        for (int i = 1; i < sibling.numKeys; i++) {
            sibling.keys[i - 1] = sibling.keys[i];
        }
    
        if (!sibling.isLeaf) {
            for (int i = 1; i <= sibling.numKeys; i++) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }
    
        child.numKeys++;
        sibling.numKeys--;
    }
}
