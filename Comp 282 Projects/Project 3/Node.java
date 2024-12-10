enum Color{
    RED, BLACK
}

public class Node {
    int val;
    Color color;
    Node left;
    Node right;
    Node parent;
    
    //constructor method for new nodes
    Node(int val){
        this.val = val;
        this.color = Color.RED;
        parent = left = right = null;
    }

    //returns pointer to uncle node
    Node uncle(){
        if(parent == null || parent.parent == null)
            return null;
        if(parent == parent.parent.left)
            return parent.parent.right;
        else
            return parent.parent.left;
    }

    //returns pointer to sibling node
    Node sibling(){
        if(parent == null)
            return null;
        else if(parent.left == this)
            return parent.right;
        else
            return parent.left;
    }

    void swapDown(Node newParent){
        if(parent != null){
            if(this == parent.left)
                parent.left = newParent;
            else 
                parent.right = newParent;
        }
        newParent.parent = parent;
        parent = newParent;
    }
}
