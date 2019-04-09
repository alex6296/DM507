/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part_2;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class DictBinTree implements Dict {

    private Node root; //header object
    private int length = 0; //number of nodes in tree

    int[] result;
    private int count;

    public DictBinTree() {
    }

    @Override
    public void insert(int k) {
        length++;
        Node z = new Node(k);

        Node y = null;
        Node x = root;
        while (x != null) {
            y = x;
            if (z.key < x.key) {
                x = x.getLeftChield();
            } else {
                x = x.getRightChield();
            }
        }
        //z.p = y; //sets the parrent of the new value to y

        if (y == null) {
            root = z; //treee was empty   
        } else if (z.getKey() < y.getKey()) {
            y.setLeftChield(z);
        } else {
            y.setRightChield(z);
        }

    }

    @Override
    public int[] orderedTraversal() {
        result = new int[length];
        count = 0;
        inorderTreeWalk(root);
        return result;
    }

    private void inorderTreeWalk(Node n) {
        if (n != null) {
            inorderTreeWalk(n.getLeftChield());

            result[count] = n.getKey();
            count++;

            inorderTreeWalk(n.getRightChield());
        }
    }

    @Override
    public boolean search(int k) {
        Node result = treeSearch(k, root);
        if (result == null) {
            return false;
        } else {
            return true;
        }
    }

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
