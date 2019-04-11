/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part_2;

import java.util.Scanner;

/**
 *
 * @author robert
 */
public class TreeSort  {

    
    static DictBinTree db;

    public TreeSort() {
           db = new DictBinTree();
    }
      

    
    
    public static void main(String[] args) {
        
        
	int n = 0;
	Scanner sc = new Scanner(System.in);
        
	while (sc.hasNextInt()) {
            db.insert(sc.nextInt());
	    n++;
       }
        for (int v : db.orderedTraversal()) {            
            System.out.println(v);
        }
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void sortingAlgorithm(){
        
        int counter = 0;
  
       while(counter < 30){
            db.insert(counter);
            System.out.println(counter + " has been inserted into the dictionary" + db);
            counter++;
            System.out.println("counter is now " + counter );
        }
        for (int b : db.orderedTraversal()) {
            
            System.out.println(b);
        }
        
      
    }
    
   
    private class DictBinTree  {

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
     
}

