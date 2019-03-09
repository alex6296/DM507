/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part_1;

import static java.lang.Math.floor;

/**
 *
 * @author Administrator
 */
public class PQHeapV1 implements PQ {

    private final int maxSize;
    private int size = 1;
    private final Element[] heap;

    public PQHeapV1(int maxElms) {
        maxSize = maxElms;
        heap = new Element[maxElms];
    }

    @Override
    public Element extractMin() {
        return heap[1];

    }

    @Override
    public void insert(Element e) {
        //checks if size limit is reached
        if (size + 1 >= maxSize) {
            System.out.println("no more space so element " + e.getKey() + " not added");
            return;
        }
        //insert new element
        heap[size] = e;

        //sort
        minHeapify(getParent(size));
        size++;
    }

    private int getParent(int currentNodeIndex) {
        int value = (int) floor(currentNodeIndex / 2);
        System.out.print("get parent of " + currentNodeIndex + " -> " + value);
        if (value < 1) {
            System.out.println("   (first index=1 so param was returned do to no parent exisitng)");
            return currentNodeIndex;
        }
        System.out.println("");
        return value;
    }

    private int getLeftChild(int currentNodeIndex) {
        return (currentNodeIndex * 2);
    }

    private int getRightChild(int currentNodeIndex) {
        return currentNodeIndex * 2 + 1;
    }

    private void exchange(int rootIndex, int smallestIndex) {
        //saves root outside array
        System.out.println("exchange " + rootIndex + " with " + smallestIndex);
        Element tempRoot = heap[rootIndex];

        //set root to the smallest
        heap[rootIndex] = heap[smallestIndex];
        //changes the original smallest to the root
        heap[smallestIndex] = tempRoot;
    }

    public int findSmallestElement(int rootIndex, int leftChildIndex, int rightChildIndex) {

        System.out.println("---heap atm---");
        System.out.println(this.toString());
        System.out.println("-------------- ");

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

        //if only there is a left node 
        if (leftChildElement != null && rightChildElement == null) {
            int leftChildKey = leftChildElement.getKey();
            int rootKey = rootElement.getKey();
            System.out.println("used partial compareon root " + rootKey);
            return comparePartialTree(rootKey, leftChildKey);

        } // if there is afull sub tree
        else if (leftChildElement != null && rightChildElement != null) {
            int leftChildKey = leftChildElement.getKey();
            int rightChildKey = rightChildElement.getKey();
            int rootKey = rootElement.getKey();
            System.out.println("used full compare on root " + rootKey);
            return compareFullSubTree(rootKey, leftChildKey, rightChildKey);
        } //no nodes
        else {
            System.out.println("root is smallest so returned root");
            return rootIndex;
        }
    }

    private int comparePartialTree(int rootKey, int leftChildKey) {
        if (leftChildKey < rootKey) {
            return this.keyToIndex(leftChildKey);
        } else {
            return this.keyToIndex(rootKey);
        }
    }

    private int compareFullSubTree(int rootKey, int leftChildKey, int rightChildKey) {
        //TODO COMPARE CHIELDS
        int smallest=rootKey;
        //finding the smallest node in the sub-tree
        if ( leftChildKey < rootKey) {
            smallest = this.keyToIndex(leftChildKey);
        }  else if(rightChildKey < rootKey) {
            smallest = this.keyToIndex(rightChildKey);
        }


        //check if right child is smaller then left
        
        return smallest;
    }

    private void minHeapify(int RootIndex) {
        //vars
        Integer leftChildIndex;
        Integer rightChildIndex;
        int smallestKeyIndex;

        System.out.println("- - - - - - - - - -");
        System.out.println("heapify:");
        System.out.println("sub-root = " + RootIndex);

        try {
            leftChildIndex = getLeftChild(RootIndex);
            System.out.println("leftChild = " + leftChildIndex);
        } catch (Exception e) {
            leftChildIndex = null;
            System.out.println("leftChild = null ");
        }

        try {
            rightChildIndex = getRightChild(RootIndex);
            System.out.println("RightChild = " + rightChildIndex);
        } catch (Exception e) {
            rightChildIndex = null;
            System.out.println("RightChild = null");
        }

        smallestKeyIndex = findSmallestElement(RootIndex, leftChildIndex, rightChildIndex);
        System.out.println("rootIndex = " + RootIndex);
        System.out.println("smallestKeyIndex = " + smallestKeyIndex);

        //if node was smaller then root 
        if (smallestKeyIndex != RootIndex) {
            System.out.println("in root compare loop");
            System.out.println("rootIndex = " + RootIndex);
            System.out.println("smallestKeyIndex = " + smallestKeyIndex);
            System.out.println("doing an exchange");
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

    public static void main(String args[]) {
        PQHeapV1 stack = new PQHeapV1(10);
        stack.insert(new Element(3, 1));
        System.out.println(stack.toString());
        System.out.println("element 1 was succesfully added");
        stack.insert(new Element(2, 2));
        System.out.println(stack.toString());
        System.out.println("element 2 was succesfully added");
        
          stack.insert(new Element(1, 3)); System.out.println(stack.toString());
        /* *
         * stack.insert(new Element(5, 4)); System.out.println(stack.print());
         * stack.insert(new Element(3, 5)); System.out.println(stack.print());
         */

    }

    private int keyToIndex(int smallestKey) {
        for (int i = 1; i < this.maxSize; i++) {
            if (heap[i].getKey() == smallestKey) {
                return i;
            }
        }
        return -1;
    }
}
