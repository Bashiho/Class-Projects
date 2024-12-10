public class RedBlackTree {
    private Node root = null;

    //constructor method
    public RedBlackTree(){
    }

    //method for left rotations
    private void leftRotate(Node x){
        Node newParent = x.right;

        //updates root
        if(x == root)
            root = newParent;
        
        //moves x down and newParent up
        x.swapDown(newParent);

        //connects x and newParent
        x.right = newParent.left;

        //If not null, connects newParent to x
        if(newParent.left != null)
            newParent.left.parent = x;
        
        //connects newParent and x
        newParent.left = x;
    }

    //method for right rotations
    private void rightRotate(Node x){
        Node newParent = x.left;

        //updates root
        if(x == root)
            root = newParent;
        
        //moves x down and newParent up
        x.swapDown(newParent);

        //connect x w/ newParents new right element
        x.left = newParent.right;

        //if not null, connects new right node w/ its parent
        if(newParent.right != null)
            newParent.right.parent = x;
        
        //connects newParent w/ x
        newParent.right = x;
    }

    //method to search for value, returns node if found, returns null otherwise
    public Node search(int val){
        //creates temp node to use for searching
        Node temp = root;
        //navigates through tree until val is found or reaches end of tree
        while (temp != null) {
            //moves temp to temp.left if val is in left subtree
            if (val < temp.val){
                if(temp.left == null)
                    return temp;
                else
                    temp = temp.left;
            }
            else if(val == temp.val)
                return temp;
            //moves temp to right if val in right subtree
            else{
                if(temp.right == null)
                    return temp;
                else
                    temp = temp.right;
            }
        }
        //returns node w/ val if found, else returns parent of newNode
        return temp;
    }

    //method to insert node w/ given value
    public void insert(int val){
        //creates node w/ val
        Node newNode = new Node(val);
        

        //if null, insert as root
        if(root == null){
            newNode.color = Color.BLACK;
            root = newNode;
        }
        else{
            //finds if node is in tree, and if not, where to insert node
            Node temp = search(val);
            //if val in tree, returns, else returns leaf node
            if(temp.val == val)
                return;
            newNode.parent = temp;
            if(val < temp.val)
                temp.left = newNode;
            else
                temp.right = newNode;
            fixRedRed(newNode);
        }
    }

    private void swapColors(Node x1, Node x2) {
        Color temp = x1.color;
        x1.color = x2.color;
        x2.color = temp;
    }

    //method to fix problems caused by insertion of node
    private void fixRedRed(Node node){
        //if reaches root, changes color to black and returns
        if(node == root){
            node.color = Color.BLACK;
            return;
        }

        //initializes temp nodes
        Node parent = node.parent, grandparent = parent.parent, uncle = null;
        if(parent == null || grandparent == null)
            uncle = null;
        if(grandparent != null && parent != null && parent == grandparent.left)
            uncle = parent.parent.right;
        else if(grandparent != null && parent != null && parent == grandparent.right)
            uncle = parent.parent.left;
        
        if(parent.color != Color.BLACK){
            //if uncle red, changes colors and recursively calls method
            if(uncle != null && uncle.color == Color.RED){
                parent.color = Color.BLACK;
                uncle.color = Color.BLACK;
                grandparent.color = Color.RED;
                fixRedRed(grandparent);
            }
            else{
                //if parent and node are left nodes,
                if(parent == grandparent.left){
                    if(node == parent.left)
                        swapColors(parent, grandparent);
                    //if parent is left and node is right
                    else{
                        leftRotate(parent);
                        swapColors(node, grandparent);
                    }
                    rightRotate(grandparent);
                }
                //if parent is right node
                else{
                    if(node == parent.left){
                        rightRotate(parent);
                        swapColors(node, grandparent);
                    }
                    //if both right nodes
                    else
                        swapColors(parent, grandparent);

                    leftRotate(grandparent);
                }
            }
        }
    }

    //method to delete node w/ given val, searches then calls deleteNode
    public void delete(int val){
        deleteNode(search(val));
    }

    //method to find replacement node for deleteNode method
    public Node findReplace(Node x){
        //if has both children, returns successor of x
        if(x.left != null && x.right != null){
            Node temp = x.right;
            while(temp.left != null)
                temp = temp.left;
            return temp;
        }
        //if leaf, returns null
        if(x.left == null && x.right == null)
            return null;
        
        //else returns child
        if(x.left != null)
            return x.left;
        else
            return x.right;
    }

    //method to delete node passed through from delete method
    public void deleteNode(Node x){
        //Finds node to replace deleted node
        Node temp = findReplace(x);
        Node parent = x.parent;
        //If x is leaf
        if(temp == null){
            //if removing root, then root = null
            if(x == root)
                root = null;
            //if x != root
            else{
                if(x.parent.color == Color.BLACK && x.color == Color.BLACK)
                    fixDoubleBlack(x);
                //if x has a sibling, makes its color red
                else if(x.sibling() != null)
                    x.sibling().color = Color.RED;
                //removes pointers to x
                if(x == x.parent.left)
                    parent.left = null;
                else
                    parent.right = null;
            }
            return;
        }
        //If x has 1 child
        if(x.left == null || x.right == null){
            //if root is to be removed
            if(x == root){
                //places temp's value into root then removes temp
                x.val = temp.val;
                x.left = x.right = null;
            }
            //if x isn't root
            else{
                //replaces x w/ temp
                if(x.parent.left == x)
                    parent.left = temp;
                else
                    parent.right = temp;

                temp.parent = parent;
                //checks for and fixes double black
                if(x.color == Color.BLACK && temp.color == Color.BLACK)
                    fixDoubleBlack(x);
                //if not double black, makes child black
                else
                    temp.color = Color.BLACK;
            }
            return;
        }
        //if x has 2 children
        //swaps val w/ successor and recursively remove successor
        int tempVal = x.val;
        x.val = temp.val;
        temp.val = tempVal;
        //removes temp which now contains value to be deleted
        deleteNode(temp);
    }
    
    //method to fix double black nodes during deletion process
    public void fixDoubleBlack(Node x){
        //returns when reached root
        if(x == root)
            return;

        Node sibling = x.sibling(), parent = x.parent;

        if(sibling == null)
            fixDoubleBlack(parent);
        else{
            //if sibling is red
            if(sibling.color == Color.RED){
                //makes parent red and sibling black
                parent.color = Color.RED;
                sibling.color = Color.BLACK;

                //if sibling is left child, rotates right from parent
                if(sibling == parent.left)
                    rightRotate(parent);
                //if sibling is right child, rotates left from parent
                else
                    leftRotate(parent);
                fixDoubleBlack(x);
            }
            //if sibling is black
            else{
                //if sibling has a red child
                if((sibling.left != null && sibling.left.color == Color.RED) || 
                   (sibling.right != null && sibling.right.color == Color.RED)){
                    //if sibling's left child is red
                    if(sibling.left != null && sibling.left.color == Color.RED){
                        //if sibling is left child, change colors then rotate right from parent
                        if(sibling == sibling.parent.left){
                            sibling.left.color = sibling.color;
                            sibling.color = parent.color;
                            rightRotate(parent);
                        }
                        //if sibling is right child, change sibling.left.color 
                        //then rotate right from sibling, then rotate left from parent
                        else{
                            sibling.left.color = parent.color;
                            rightRotate(sibling);
                            leftRotate(parent);
                        }
                    }
                    //if sibling's right child is red
                    else{
                        //if sibling is left child, change sibling.right.color
                        //then rotate left from sibling, then rotate right from parent
                        if(sibling == parent.left){
                            sibling.right.color = parent.color;
                            leftRotate(sibling);
                            rightRotate(parent);
                        }
                        //if sibling is right child, change colors then rotate left from parent
                        else{
                            sibling.right.color = sibling.color;
                            sibling.color = parent.color;
                            leftRotate(parent);
                        }
                    }
                    //set parents color to black
                    parent.color = Color.BLACK;
                }
                //2 black children
                else{
                    sibling.color = Color.RED;
                    if(parent.color == Color.BLACK)
                        fixDoubleBlack(parent);
                    else
                        parent.color = Color.BLACK;
                }
            }
        }
    }

    //main method for inOrder traversal, passes root to helper method
    public void inOrder(){
        if (root == null)
            System.out.println("Tree is empty");
        else
            inOrder(root);
        System.out.println();
    }
    
    //helper method for inOrder traversal, handles printing
    public void inOrder(Node node){
        if(node == null)
            return;
        inOrder(node.left);
        System.out.print(node.val + " ");
        inOrder(node.right);
    }
}
