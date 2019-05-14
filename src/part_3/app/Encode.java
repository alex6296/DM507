package part_3.app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Math.floor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * 1
 *
 * @author Administrator
 */
public class Encode {

    //TEST
    private static File fileInput = new File("input.txt");
    private static File fileOutput = new File("output.txt");
    int SIZE = 255;
    private static boolean TESTMODE = true; //change to false for less sout information

    //VARS
    private Map<Integer, Integer> codeMapping = new HashMap<Integer, Integer>();

    public static void main(String[] args) {
        Encode e = new Encode();

        try {
            e.run(args);
        } catch (Exception ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(String[] args) throws Exception {

        int[] inputArray = getInput(fileInput/*args[0]*/);
        System.out.println("--input ---");
        System.out.println("index : frequency");
        for (int i = 0; i < inputArray.length; i++) {

            if (TESTMODE) {
                System.out.println("   " + i + " : " + inputArray[i]);
            } else {
                if (inputArray[i] != 0) {
                    System.out.println("   " + i + " : " + inputArray[i]);
                }
            }

        }

        System.out.println("--huffmanify ---");
        Knot root = huffmanify(inputArray);
        System.out.println("root = "+root);
        
        tablefy(root);
        System.out.println("---tablefy---");
        for (Integer i : codeMapping.keySet()) {
            System.out.println(i + " : " + codeMapping.get(i));
        }

        System.exit(0);
        //compress();
    }

    private int[] getInput(File input) throws Exception {
        int[] result = new int[SIZE];

        //set all elements freq to 0
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
        }

        // Open input and output byte streams to/from files.
        FileInputStream inFile = new FileInputStream(input);
        BufferedInputStream reader = new BufferedInputStream(inFile);

        //find frequencies [index = charNumber, value = freq.]
        int i = reader.read();
        while (i != -1) {
            result[i]++;
            i = reader.read();
        }

        return result;
    }

    private Knot huffmanify(int[] inputArray) {

        PQ Q = new PQHeap(SIZE + 2);
        int n = Q.getHeapSize();

        //insert all bytes in PQ
        for (int charValue = 0; charValue < inputArray.length; charValue++) {
            Knot k = new Knot(charValue, inputArray[charValue]);
            Element e = new Element(k.freq, k);
            Q.insert(e);
        }

        for (int i = 0; i < n - 1; i++) {

            //new node
            int placeHolder = -1;
            Knot z = new Knot(placeHolder, placeHolder);

            Knot y, x;
            //try to add leftChield
            try {
                x = (Knot) Q.extractMin().data;
                z.setLeftChield(x);
            } catch (NullPointerException e) {
                x = new Knot(0, 0);
            }

            //try to add rightChield
            try {
                y = (Knot) Q.extractMin().data;
                z.setRightChield(y);
            } catch (NullPointerException e) {
                y = new Knot(0, 0);
            }

            //combinde freq
            Knot k = new Knot(x.value + y.value, x.freq + y.freq);
            Element e = new Element(k.freq, k);
            //insert combined knot to Q
            Q.insert(e);
        }
        return (Knot)Q.extractMin().data;
    }

    private void tablefy(Knot hufmanTree) {
        treeWalk(hufmanTree);
    }

    private void treeWalk(Knot n) {
        if (n != null) {
            treeWalk(n.getLeftChield());

            System.out.println(n.value);

            treeWalk(n.getRightChield());
        }
    }

    private void compress() {

    }

    public class Knot {

        public int value, freq;
        private Knot leftChield = null;
        private Knot rightChield = null;

        public Knot(int value, int freq) {
            this.value = value;
            this.freq = freq;
        }

        public boolean hasLeftChield() {
            if (leftChield == null) {
                return false;
            }
            return true;
        }

        public Knot getLeftChield() {
            return leftChield;
        }

        public void setLeftChield(Knot leftChield) {
            this.leftChield = leftChield;
        }

        public boolean hasRightChield() {
            if (rightChield == null) {
                return false;
            }
            return true;
        }

        public Knot getRightChield() {
            return rightChield;
        }

        public void setRightChield(Knot rightChield) {
            this.rightChield = rightChield;
        }

    }

// DICT 
    public interface Dict {

        public void insert(int k);

        public int[] orderedTraversal();

        public boolean search(int k);
    }

    private class DictBinTree implements Dict {

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
         * Gives an full list of itemes contained wrapper method for
         * InorderTreeWalk
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
         * look if the tree contaions a given value
         *
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
         *
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

//PQ HEAP
    public interface PQ {

        public Element extractMin();

        public void insert(Element e);

        public int getHeapSize();
    }

    public class PQHeap implements PQ {

        private final int length; //the max number of knots
        private int heapSize = 1; // the current amount of knots
        private final Element[] heap; //the array

        /**
         * Constructor
         *
         * @param maxElms maximum number of element there can be in the heap.
         */
        public PQHeap(int maxElms) {
            length = maxElms;
            heap = new Element[maxElms];
        }

        public int getHeapSize() {
            return heapSize;
        }

        /**
         * removes the smallest element in the heap and returns it
         *
         * @return Element
         */
        @Override
        public Element extractMin() {

            if (heapSize == 0) {
                return null;
            }

            Element max = heap[1];
            heap[1] = heap[heapSize];
            heapSize = heapSize - 1;
            minHeapify(1);
            return max;

        }

        /**
         * insets an element to the heap and sorts it.
         *
         * @param Element
         */
        @Override
        public void insert(Element e) {
            //checks if size limit is reached
            if (heapSize >= length) {
                return;
            }
            //insert new element
            heap[heapSize] = e;

            //sort
            minHeapify(heapSize);
            heapSize++;
        }

        /**
         * Returns the index of the parent to given param.
         *
         * @param currentNodeIndex
         * @return int index of the parent
         */
        private int getParent(int currentNodeIndex) {
            int value = (int) floor(currentNodeIndex / 2);
            if (value < 1) {
                return currentNodeIndex;
            }
            return value;
        }

        /**
         * Returns the index of the left child to given param.
         *
         * @param currentNodeIndex
         * @return int index of the left child
         */
        private int getLeftChild(int currentNodeIndex) {
            return (currentNodeIndex * 2);
        }

        /**
         * Returns the index of the right child to given param.
         *
         * @param currentNodeIndex
         * @return int index of the right child
         */
        private int getRightChild(int currentNodeIndex) {
            return currentNodeIndex * 2 + 1;
        }

        /**
         * swaps 2 elements.
         *
         * @param rootIndex location of the first element.
         * @param smallestIndex location of the second element.
         */
        private void exchange(int rootIndex, int smallestIndex) {
            //saves root outside array
            Element tempRoot = heap[rootIndex];

            //set root to the smallest
            heap[rootIndex] = heap[smallestIndex];
            //changes the original smallest to the root
            heap[smallestIndex] = tempRoot;
        }

        /**
         * select an methods to compare the sub-tree.
         *
         * @param rootIndex
         * @param leftChildIndex
         * @param rightChildIndex
         * @return int the smallest node in the sub tree.
         */
        public int findSmallestElement(int rootIndex, int leftChildIndex, int rightChildIndex) {

            Element rootElement = heap[rootIndex];
            Element leftChildElement;
            Element rightChildElement;
            try {
                leftChildElement = heap[leftChildIndex];
            } catch (IndexOutOfBoundsException e) {
                leftChildElement = null;
            }
            try {
                rightChildElement = heap[rightChildIndex];
            } catch (Exception e) {
                rightChildElement = null;
            }
            //if only root is null
            if (heap[rootIndex] == null && rightChildElement != null && leftChildElement != null) {
                if (leftChildElement.getKey() <= rightChildElement.getKey()) {
                    return leftChildIndex;
                } else {
                    return rightChildIndex;
                }
            }

            //if only there is a left node 
            if (leftChildElement != null && rightChildElement == null) {
                int leftChildKey = leftChildElement.getKey();
                int rootKey = rootElement.getKey();
                return comparePartialTree(rootIndex, rootKey, leftChildIndex, leftChildKey);

            } // if there is afull sub tree
            else if (leftChildElement != null && rightChildElement != null) {
                int leftChildKey = leftChildElement.getKey();
                int rightChildKey = rightChildElement.getKey();
                int rootKey = rootElement.getKey();
                return compareFullSubTree(rootIndex, rootKey, leftChildIndex, leftChildKey, rightChildIndex, rightChildKey);
            } //no nodes
            else {
                return rootIndex;
            }
        }

        /**
         * compares a tree with only 1 child.
         *
         * @param rootIndex
         * @param rootKey
         * @param leftChieldIndex
         * @param leftChildKey
         * @return the index of the node with the smallest key
         */
        private int comparePartialTree(int rootIndex, int rootKey, int leftChieldIndex, int leftChildKey) {
            if (leftChildKey < rootKey) {
                return leftChieldIndex;
            } else {
                return rootIndex;
            }
        }

        /**
         * compares a full sub-tree
         *
         * @param rootIndex
         * @param rootKey
         * @param leftChieldIndex
         * @param leftChildKey
         * @param rightChildIndex
         * @param rightChildKey
         * @return the index of the node with the smallest key
         */
        private int compareFullSubTree(int rootIndex, int rootKey, int leftChieldIndex, int leftChildKey, int rightChildIndex, int rightChildKey) {
            //TODO COMPARE CHIELDS
            int smallest;
            //finding the smallest chield
            if (leftChildKey < rightChildKey) {
                smallest = leftChieldIndex;
            } else {
                smallest = rightChildIndex;
            }
            //compare the smallest chield to the root
            if (rootKey <= rightChildKey && rootKey <= leftChildKey) {
                smallest = rootIndex;
            }

            //check if right child is smaller then left
            return smallest;
        }

        /**
         * sorts the heap.
         *
         * @param RootIndex the index of the root of the sub-tree to start the
         * heap sort from.
         */
        private void minHeapify(int RootIndex) {
            //vars
            Integer leftChildIndex;
            Integer rightChildIndex;
            int smallestKeyIndex;

            try {
                leftChildIndex = getLeftChild(RootIndex);
            } catch (Exception e) {
                leftChildIndex = null;
            }

            try {
                rightChildIndex = getRightChild(RootIndex);
            } catch (Exception e) {
                rightChildIndex = null;
            }

            smallestKeyIndex = findSmallestElement(RootIndex, leftChildIndex, rightChildIndex);

            //if node was smaller then root 
            if (smallestKeyIndex != RootIndex) {
                //change them around
                exchange(smallestKeyIndex, RootIndex);
                //compare to new root
                minHeapify(getParent(RootIndex));
            }

        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            for (Element e : heap) {
                if (e != null) {

                    sb.append(e.getKey());
                    sb.append(". ");
                    sb.append("[");
                    sb.append(e.getData());
                    sb.append("]");
                    sb.append("\n");
                }

            }

            return sb.toString();
        }

    }

    public class Element {

        private int key;
        private Object data;

        public Element(int i, Knot o) {
            this.key = i;
            this.data = o;
        }

        public int getKey() {
            return this.key;
        }

        public Object getData() {
            return this.data;
        }
    }

// IO HANDLERS
    public class BitInputStream implements AutoCloseable {

        // Underlying byte stream to read from.
        private InputStream input;

        // Buffer of up to 8 bits from the most recently read byte of the
        // underlying byte input stream. Is an int in the range 0 to SIZE
        // if bits are available, or is -1 if the end of stream is
        // reached.
        private int nextBits;

        // Always between 0 and 8, inclusive.
        private int numBitsRemaining;

        private boolean isEndOfStream;

        // Creates a bit input stream based on the given byte input stream.
        public BitInputStream(InputStream in) {
            if (in == null) {
                throw new NullPointerException("No input stream given");
            }
            input = in;
            numBitsRemaining = 0;
            isEndOfStream = false;
        }

        // Reads a bit from the stream. Returns 0 or 1 if a bit is
        // available, or -1 if the end of stream is reached. The end of
        // stream always occurs on a byte boundary.
        public int readBit() throws IOException {
            if (isEndOfStream) {
                return -1;
            }
            if (numBitsRemaining == 0) {
                nextBits = input.read();
                if (nextBits == -1) {
                    isEndOfStream = true;
                    return -1;
                }
                numBitsRemaining = 8;
            }
            numBitsRemaining--;
            return (nextBits >>> numBitsRemaining) & 1;
        }

        // Reads an int from the stream. Throws IOException if 32 bits are
        // not available.
        public int readInt() throws IOException {
            int output = 0;
            int nextBit;
            int bitsAdded = 0;
            while (bitsAdded < 32) {
                nextBit = readBit();
                if (nextBit == -1) {
                    throw new IOException("Not enough bits while trying to read int");
                }
                output = output << 1 | nextBit;
                bitsAdded++;
            }
            return output;
        }

        // Closes this stream and the underlying InputStream.
        public void close() throws IOException {
            input.close();
        }

    }

    public class BitOutputStream implements AutoCloseable {

        // Underlying byte stream to write to.
        private OutputStream output;

        // The accumulated bits for the current byte. Always an int in the
        // range 0 to SIZE.
        private int currentByte;

        // The number of accumulated bits in the current byte. Always
        // between 0 and 8, inclusive.
        private int numBitsInCurrentByte;

        // Creates a bit output stream based on the given byte output
        // stream.
        public BitOutputStream(OutputStream out) {
            if (out == null) {
                throw new NullPointerException("No output stream given");
            }
            output = out;
            currentByte = 0;
            numBitsInCurrentByte = 0;
        }

        // Writes a bit to the stream. The specified bit must be 0 or 1.
        public void writeBit(int b) throws IOException {
            if (!(b == 0 || b == 1)) {
                throw new IllegalArgumentException("Argument must be 0 or 1");
            }
            currentByte = currentByte << 1 | b;
            numBitsInCurrentByte++;
            if (numBitsInCurrentByte == 8) {
                output.write(currentByte);
                numBitsInCurrentByte = 0;
            }
        }

        // Writes an int to the stream.
        public void writeInt(int b) throws IOException {

            int bitsWritten = 0;
            while (bitsWritten < 32) {
                writeBit(b >>> (31 - bitsWritten) & 1);
                bitsWritten++;
            }
        }

        // Closes this stream and the underlying OutputStream. If called
        // when this bit stream is not at a byte boundary, then the
        // minimum number of "0" bits (between 0 and 7 of them) are
        // written as padding to reach the next byte boundary.
        public void close() throws IOException {
            while (numBitsInCurrentByte != 0) {
                writeBit(0);
            }
            output.close();
        }

    }

}
