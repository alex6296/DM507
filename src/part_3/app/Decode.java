/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part_3.app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Math.floor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * fefgege
 *
 * @author Lenovo
 */
public class Decode {

    //TEST
    private static final File FILEINPUT = new File("input.txt"); //The input file we are using
    private static final File FILEOUTPUT = new File("output.txt"); //The output file we are using
    private final int SIZE = 256; //Final size of the bytes
    private StringBuilder sb = new StringBuilder(256); //Stringbuilder which contains total of 256 bytes
    private String[] encodeList = new String[256]; //Encodelistarray which contains the translations

    private static final boolean TESTMODE = true; //change to false for less sout information

    public static void main(String[] args) {
        Decode e = new Decode();

        try {
            e.run(args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run(String[] args) throws Exception {
        int[] inputArray = getInput(args/*args[0]*/); // Retrieves the inputfile and creates an array
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
       System.out.println("--- huffmanify ---");
       
        PQ Q = new PQHeap(SIZE);   //transforms array to PQHeap

        //insert all bytes in PQ
        for (int charValue = 0; charValue < inputArray.length; charValue++) {
            Knot k = new Knot(charValue, inputArray[charValue]);
            Element e = new Element(k.freq, k);
            Q.insert(e);
        }

        Knot root = huffmanify(Q); //Generates the huffman tree
        System.out.println("root.value = " + root.value);
        System.out.println("root.freq = " + root.freq);

        System.out.println("--- treeSearch ---");
        System.out.println("value : code");
        treeSearch(root);
        System.out.println("--- compress ---");
        deCode(args/*args[0]*/); // 
    }

    private int[] getInput(String[] args) throws Exception {
        int[] result = new int[SIZE]; //Creates a new array which contains the final int size which is 256

        // Open input and output byte streams to/from files.
        FileInputStream inFile = new FileInputStream(FILEOUTPUT/*args[0]*/); //Retrieves the input  file 
        BitInputStream reader = new BitInputStream(inFile); //BitinputStream object is instantiated and takes our file as constructor

        //find frequencies [index = charNumber, value = freq.]
        for (int i = 0; i < SIZE; i++) { 
            result[i] = reader.readInt(); //Finds frequencies and inserts the into our result array
        }
        return result;
    }

    private Knot huffmanify(PQ C) {
        int n = C.getSize(); //Retrieves the size of the PQ object C
        PQ Q = C;

        
        for (int i = 0; i < n - 1; i++) { //Runs through C

            //new node
            int placeHolder = 0;
            Knot z = new Knot(placeHolder, placeHolder); //Instantiates a new knot which holds the value and frequency 

            //tries to add children
            Knot y, x;
            try {
                x = Q.extractMin().getData();
                z.setLeftChield(x);  //try to add leftChield
                z.freq += x.freq;
                if (x.freq > 0) {
                    z.value += x.value;

                    if (TESTMODE) {
                        if (x.freq > 0) {
                            System.out.println("x.value = " + x.value);
                        }
                    }

                }
            } catch (NullPointerException e) {
            }

            try {
                y = Q.extractMin().getData(); //gets the root data 
                z.setRightChield(y);  //try to add leftChield
                z.freq += y.freq; //adds the frequency to its parent
                if (y.freq > 0) {
                    z.value += y.value; //adds the frequency to its parent 
                }

                if (TESTMODE) {
                    if (y.freq > 0) {
                        System.out.println("y.value = " + y.value);
                    }
                }

            } catch (NullPointerException e) { //Catches possible nullpointerexceptions
            }

            
            Element e = new Element(z.freq, z); 
            Q.insert(e); //inserts the  combined knot to Q

        }

        Knot r = Q.extractMin().data; //Extracts the data of the root 

        if (TESTMODE) {
            System.out.println("at end heap.size() = " + Q.getSize());

        }

        return r;
    }

    public void treeSearch(Knot r) {

        if (r == null) { // if r is null then we got the root if thats the case return nothing 
            return;
        }

        sb.append('0'); //appends 0 to the stringbuilder if we pick a child located at left-most side. 
        treeSearch(r.leftChield); // recursive calling to check on leftchild 
        sb.deleteCharAt(sb.length() - 1); //clearsstrinbuilder

        //checks if r has no leftchild and has no rightchild and that its frequency is above 0
        if (!r.hasLeftChield() && !r.hasRightChield() && r.freq > 0) {
            String finalCode = sb.toString(); //get converted code
            encodeList[r.value] = finalCode; // save to code list
            System.out.println("   " + r.value + " : " + finalCode);
        }

        sb.append('1'); //appends 1 to the stringbuilder if we pick a child located at right-most side. 
        treeSearch(r.rightChield); //recursive calling to check rightchild
        sb.deleteCharAt(sb.length() - 1); //clears stringbuilder

    }

    private void deCode(String[] args) throws FileNotFoundException, IOException {
        //TODO IF THIS IS SOLVED IN THIS FILE PORT TO THE OTHER
    }

// DICT 
    public class Knot {

        public int value, freq;
        private Knot leftChield = null;
        private Knot rightChield = null;

        public Knot(int value, int freq) {
            this.value = value;
            this.freq = freq;
        }

        public boolean hasLeftChield() {
            return leftChield != null;
        }

        public Knot getLeftChield() {
            return leftChield;
        }

        public void setLeftChield(Knot leftChield) {
            this.leftChield = leftChield;
        }

        public boolean hasRightChield() {
            return rightChield != null;
        }

        public Knot getRightChield() {
            return rightChield;
        }

        public void setRightChield(Knot rightChield) {
            this.rightChield = rightChield;
        }

    }

//PQ HEAP
    public interface PQ {

        public Element extractMin();

        public void insert(Element e);

        public int getSize();
    }

    public class PQHeap implements PQ {

        public final int heapSize; // the current amount of knots
        public int length = 0; //the max number of knots
        public final Element[] heap; //the array

        /**
         * Constructor
         *
         * @param maxElms maximum number of element there can be in the heap.
         */
        public PQHeap(int maxElms) {
            heapSize = maxElms+1; //caps the size of the heap
            heap = new Element[maxElms+1]; //initializes the heap
        }

        /**
         * removes the smallest element in the heap and returns it
         *
         * @return Element
         */
        @Override
        public Element extractMin() {

            if (length <= 0) {
                return null;
            }

            Element min = heap[1]; //set min equal to the smallest element in the heap
            heap[1] = heap[length]; //takes the biggest element and set it as the root
            length = length - 1; //reduces the length by 1, due to the removal of the smallest element
            minHeapify(1); //validates the structure of the heap
            return min;
        }

        /**
         * Returns the index of the parent to given parameter
         *
         * @param currentNodeIndex
         * @return int index of the parent node
         */
        private int Parent(int currentNodeIndex) {
            int value = (int) floor(currentNodeIndex / 2);
            return value;
        }

        /**
         * Acquires left child to given parameter
         *
         * @param currentNodeIndex
         * @return int index of the left child
         */
        private int LeftChild(int currentNodeIndex) {
            return (currentNodeIndex * 2);
        }

        /**
         * Acquires the right child to given parameter
         *
         * @param currentNodeIndex
         * @return int index of the right child
         */
        private int RightChild(int currentNodeIndex) {
            return currentNodeIndex * 2 + 1;
        }

        /**
         * insets an element to the heap and sorts it.
         *
         * @param Element that need to be inserted
         */
        @Override
        public void insert(Element e) {
            length++;
            heap[length] = e; //inserts the element
            heapIncreaseKey(length, e); // moves the knot closer to the leaf nodes
        }

        /**
         * compares root element with children
         *
         * @param i root-index
         * @param e specified knot
         */
        private void heapIncreaseKey(int i, Element e) {
            if (e.getKey() > heap[i].getKey()) { // checks if key is bigger then current key
                return;
            }
            //compares keys
            heap[i] = e;
            while (i > 1 && heap[Parent(i)].getKey() > heap[i].getKey()) {
                exchange(i, Parent(i)); // if key is bigger exchange
                i = Parent(i); // set new parrent knot
            }
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
         * compares sub-trees and returns the smallest element to a recursive
         * loop until the array is sorted
         *
         * @param i root-index
         */
        public void minHeapify(int i) {
            int smallest;
            int l = LeftChild(i);
            int r = RightChild(i);
            //compares root and leftchild elements
            if (l <= length && heap[l].getKey() < heap[i].getKey()) {
                smallest = l;
            } else {
                smallest = i;
            }
            //compares smallest element with right child
            if (r <= length && heap[r].getKey() < heap[smallest].getKey()) {
                smallest = r;
            }

            // if root is not the smallest element exchange root with the smallest and continues the recursive loop
            if (smallest != i) {
                exchange(i, smallest);
                minHeapify(smallest);
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

        @Override
        public int getSize() {
            return length;
        }

    }

    public class Element {

        private final int key;
        private Knot data;

        public Element(int i, Knot o) {
            this.key = i;
            this.data = o;
        }

        public int getKey() {
            return this.key;
        }

        public Knot getData() {
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
        @Override
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
        @Override
        public void close() throws IOException {
            while (numBitsInCurrentByte != 0) {
                writeBit(0);
            }
            output.close();
        }

    }

}
