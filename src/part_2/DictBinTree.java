/*
 * segil17@student.sdu.dk - Sebastian Gildenpfennig
 * rofra17@student.sdu.dk - Robert Francisti
 * askot17@student.sdu.dk - Alex Skotner
 */
package part_2;

public class DictBinTree implements Dict {

    private Node root; //header object
    private int length = 0; //number of nodes in tree

    int[] result; //return value for orderedTraversal
    private int count; //amoubnt of nodes travered in orderedTraversal

    /**
     * non-args constructor
     */
    public DictBinTree() {
    }

    /**
     * inserts an element in the binary tree
     *
     * @param k the key that is used to sort the element
     */
    @Override
    public void insert(int k) {

        length++;
        Node z = new Node(k); //transforms the given parameter to a node object

        Node x = root; //pointer
        Node y = null; //trailing pointer 

        //recursive loop looking for an null chield node
        while (x != null) {
            y = x;
            if (z.key < x.key) {
                x = x.getLeftChield();
            } else {
                x = x.getRightChield();
            }
        }
        //z.p = y; //sets the parrent of the new value to y

        //inserts new node as chield to the leaf node found by the recursive loop
        if (y == null) {
            root = z; //treee was empty   
        } else if (z.getKey() < y.getKey()) {
            y.setLeftChield(z);
        } else {
            y.setRightChield(z);
        }

    }

    /**
     * Gives an full list of itemes contained wrapper method for InorderTreeWalk
     *
     * @return int[] of sorted list
     */
    @Override
    public int[] orderedTraversal() {
        result = new int[length];
        count = 0;
        inorderTreeWalk(root);
        return result;
    }

    /**
     * treverses a sub-tree and makes recursive call to the chieldren of the
     * subtree
     *
     * @param m sub-tree root
     */
    private void inorderTreeWalk(Node n) {
        if (n != null) {
            inorderTreeWalk(n.getLeftChield());

            result[count] = n.getKey();
            count++;

            inorderTreeWalk(n.getRightChield());
        }
    }

    /**
     *look if the tree contaions a given value
     * @param k key value
     * @return boolean 
     */
    @Override
    public boolean search(int k) {
        Node result = treeSearch(k, root);
        if (result == null) {
            return false;
        } else {
            return true;
        }
    }
/**
 * Looks for a given key in all subtrees of a Node
 * @param key that need to be fould
 * @param parent node that will be seached below
 * @return Node 
 */
    private Node treeSearch(int key, Node parent) {
        if (parent == null || parent.getKey() == key) {
            return parent;
        }
        if (key < parent.key) {
            return treeSearch(key, parent.leftChield);
        } else {
            return treeSearch(key, parent.rightChield);
        }
    }
/**
 * a node in the BinaryTree
 */
    public class Node {

        private int key;
        private Node leftChield = null;
        private Node rightChield = null;

        public Node(int key) {
            this.key = key;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public boolean hasLeftChield() {
            if (leftChield == null) {
                return false;
            }
            return true;
        }

        public Node getLeftChield() {
            return leftChield;
        }

        public void setLeftChield(Node leftChield) {
            this.leftChield = leftChield;
        }

        public boolean hasRightChield() {
            if (rightChield == null) {
                return false;
            }
            return true;
        }

        public Node getRightChield() {
            return rightChield;
        }

        public void setRightChield(Node rightChield) {
            this.rightChield = rightChield;
        }

    }
}
