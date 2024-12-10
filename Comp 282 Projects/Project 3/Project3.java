public class Project3 {
    public static void main(String args[]){
        RedBlackTree tree = new RedBlackTree();
        RedBlackTree tree2 = new RedBlackTree();

        //inserts nodes into tree, deletes nodes from tree
        //then prints tree through inOrder traversal
        tree.insert(49);
        tree.insert(85);
        tree.insert(51);
        tree.insert(90);
        tree.insert(28);
        tree.insert(37);
        tree.insert(61);
        tree.insert(10);
        tree.insert(32);
        tree.insert(40);
        tree.insert(59);
        tree.insert(19);
        tree.insert(63);
        tree.insert(26);
        tree.insert(55);
        tree.delete(49);
        tree.delete(59);
        tree.delete(51);
        tree.delete(19);
        tree.delete(26);
        tree.delete(10);
        System.out.print("Tree 1 inOrder traversal: ");
        tree.inOrder();
        
        //inserts nodes into tree2, deletes nodes from tree2
        //then prints tree2 through inOrder traversal
        tree2.insert(14);
        tree2.insert(95);
        tree2.insert(71);
        tree2.insert(36);
        tree2.insert(25);
        tree2.insert(88);
        tree2.insert(27);
        tree2.insert(33);
        tree2.insert(79);
        tree2.insert(81);
        tree2.insert(63);
        tree2.insert(26);
        tree2.insert(30);
        tree2.insert(44);
        tree2.insert(62);
        tree2.delete(88);
        tree2.delete(30);
        tree2.delete(27);
        tree2.delete(79);
        tree2.delete(62);
        tree2.delete(44);
        System.out.print("Tree 2 inOrder traversal: ");
        tree2.inOrder();
    }
}
