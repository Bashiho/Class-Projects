public class AVLTree {
    //root node of tree
    public Node root;

    //returns height of node, used in balance method
    public int height(Node curr){
        if(curr == null) //if Node isn't in tree return 0
            return 0;
        //else return Node's height
        else return curr.height;
    }

    //Computes balance of node
    public int calcBal(Node curr){
        if(curr == null) //if node doesn't exist return 0
            return 0;
        else //else return difference of height of right and left nodes
            return(height(curr.right) - height(curr.left));
    }

    //Updates height of the node
    public void updateHeight(Node curr){
        //saves heights of left and right nodes into l and r respectively
        int l = height(curr.left); 
        int r = height(curr.right);
        //sets curr height to max of l & r + 1
        curr.height = Math.max(l, r) + 1;
    }

    //Performs left rotation
    public Node rotateLeft(Node x){
        Node y = x.right;
        Node z = y.left;
        //moves nodes around
        y.left = x;
        x.right = z;
        //updates height of nodes
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    //Performs right rotation
    public Node rotateRight(Node y){
        Node x = y.left;
        Node z = x.right;
        //moves nodes around
        x.right = y;
        y.left = z;
        //updates height of nodes
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    //Balances tree using rotations after insertions and deletions
    public Node balanceTree(Node root){
        updateHeight(root);
        int balance = calcBal(root);

        //if right node is higher than left, checks balance of root.right
        if(balance > 1){
            //if root.right.left is lower than root.right.right, rotates root.right to right
            //then rotates root to left and returns root
            if(calcBal(root.right) < 0){
                root.right = rotateRight(root.right);
                return rotateLeft(root);
            }
            //else rotates root to left and returns root
            else //else rotates left and returns new position of root
                return rotateLeft(root);
        }
        //if left node is higher than right, checks balance of root.left
        if (balance < -1){
            //if root.left.left is higher than root.left.right, rotates root.left to the left
            //then rotates root to right and returns root
            if(calcBal(root.left) > 0){
                root.left = rotateLeft(root.left);
                return rotateRight(root);
            }
            //else rotates root to right and returns root
            else
                return rotateRight(root);
        }
        //if rotation not needed, returns root w/o rotating
        return root;
    }

    //Recursively finds successor of root
    public Node successor(Node root){
        //if root.left exists, calls successor using left subtree of root
        if(root.left != null)
            return successor(root.left);

        //returns root of subtree when root.left is null
        else 
        return root;
    }

    //Method used in main to call insert helper w/ simpler parameters
    public void insert(int key){
        //if key not in tree, calls helper
        if (find(root, key) == null)
            root = insert(root , key);
    }

    //Inserts node with given key into tree
    //Doesn't need to check for key, only called when key isn't in tree
    public Node insert(Node root, int key){
        //if root is null, returns node as root
        if(root == null)
            return new Node(key);
        //if key<root, inserts to left of root recursively
        else if(key < root.value)
            root.left = insert(root.left, key);
        //if key>root, inserts to right of root recursively
        else
            root.right = insert(root.right, key);
        //balances tree then returns
        return balanceTree(root);
    }

    //Method used in main to call delete helper w/ simpler parameters
    public void delete(int key){
        if(find(root, key) != null)
            root = delete(root, key);
    }

    //Removes node with given key from tree
    public Node delete(Node root, int key){
        //null case covered by find() in prev method
        //If key<root, recursively searches left subtree to find key
        if (key<root.value)
            root.left = delete(root.left, key);

        //If key>root, recursively searches right subtree to find key
        else if(key>root.value)
            root.right = delete(root.right, key);
            
        //if key is in root
        else{
            //if root.right is null then replaces root with root.left
            //if both null, replaces w/ null to remove node
            if(root.right == null)
                root = root.left;
            
            //if root.left is null then replaces root with root.right
            else if (root.left == null)
                root = root.right;
            
            //if both nodes exist, replaces w/ successor of root.right then recursively removes successor
            else{
            Node temp = successor(root.right);
            root.value =temp.value;
            root.right = delete(root.right, root.value);
            }
        }
        //if tree is empty, returns null
        if(root == null)
            return root;
        //else balances tree then returns
        else
            return balanceTree(root);
    }

    //Finds node with given key
    public Node find(Node root, int key){
        if(root == null || key == root.value)
            return root;
        //if key is less than root, recursively searches left subtree for key
        if (key<root.value)
            return find(root.left, key);
        //else recursively searches right subtree for key
        else return find(root.right, key);
    }

    //Recursively prints inOrder traversal of tree
    public void inOrder(Node root){
        if(root == null){
            System.out.println("\nEmpty Tree");
            return;
        }

        if(root.left != null)
            inOrder(root.left);
        System.out.print(root.value + " ");
        if(root.right != null)
            inOrder(root.right);
    }

}


