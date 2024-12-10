public class Project2 {
    public static void main(String[] args){
        AVLTree tree = new AVLTree();
        AVLTree tree2 = new AVLTree();
        //inserts nodes into first tree
        tree.insert(93);
        tree.insert(59);
        tree.insert(27);
        tree.insert(12);
        tree.insert(99);
        tree.insert(40);
        tree.insert(68);
        tree.insert(36);
        tree.insert(43);
        tree.insert(76);
        tree.insert(35);
        tree.insert(26);
        tree.insert(50);
        tree.insert(24);
        tree.insert(64);
        //deletes nodes from first tree
        tree.delete(68);
        tree.delete(59);
        tree.delete(26);
        tree.delete(50);
        tree.delete(64);
        tree.delete(93);
        //prints inOrder traversal of first tree
        System.out.print("Tree 1 In Order Traversal: ");
        tree.inOrder(tree.root);
        System.out.println();
        //inserts nodes into second tree
        tree2.insert(58);
        tree2.insert(70);
        tree2.insert(46);
        tree2.insert(48);
        tree2.insert(96);
        tree2.insert(98);
        tree2.insert(22);
        tree2.insert(24);
        tree2.insert(10);
        tree2.insert(63);
        tree2.insert(68);
        tree2.insert(94);
        tree2.insert(44);
        tree2.insert(87);
        tree2.insert(84);
        //deletes nodes from second tree
        tree2.delete(96);
        tree2.delete(68);
        tree2.delete(44);
        tree2.delete(87);
        tree2.delete(84);
        tree2.delete(22);
        //prints inOrder traversal of first tree
        System.out.print("Tree 2 In Order Traversal: ");
        tree2.inOrder(tree2.root);
    }
}
