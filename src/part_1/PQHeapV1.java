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
        System.out.println("size: "+size);
        minHeapify(getParent(size));
        size++;
    }

    private int getParent(int currentNodeIndex) {
        int value = (int) floor(currentNodeIndex / 2);
        System.out.println("");
        System.out.print("get parent of " + currentNodeIndex + " -> " + value);
        if (value < 1) {
            System.out.println("   (first index = 1 so param was returned do to no parent exisitng)");
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
            System.out.println("used partial compare on root key: " + rootKey +" index: "+rootIndex);
            return comparePartialTree(rootIndex,rootKey,leftChildIndex, leftChildKey);

        } // if there is afull sub tree
        else if (leftChildElement != null && rightChildElement != null) {
            int leftChildKey = leftChildElement.getKey();
            int rightChildKey = rightChildElement.getKey();
            int rootKey = rootElement.getKey();
            System.out.println("used full compare on root key: " + rootKey+" index: "+rootIndex);
            return compareFullSubTree(rootIndex,rootKey,leftChildIndex, leftChildKey,rightChildIndex, rightChildKey);
        } //no nodes
        else {
            System.out.println("root is smallest so returned root");
            return rootIndex;
        }
    }

    private int comparePartialTree(int rootIndex,int rootKey, int leftChieldIndex, int leftChildKey) {
        System.out.println("partial input key: "+rootKey+" index: "+rootIndex+" root");
                System.out.println("partial input key: "+leftChildKey+" index: "+leftChieldIndex+" leftchield");
        if (leftChildKey < rootKey) {
            System.out.println("-comparePartialTree-  smallest key is leftchild key: " + leftChildKey+" index: "+leftChieldIndex);
            return leftChieldIndex;
        } else {
            System.out.println("-comparePartialTree-  smallest key is root key: " + rootKey+" index: "+rootIndex);
            return rootIndex;
        }
    }

    private int compareFullSubTree(int rootIndex,int rootKey, int leftChieldIndex, int leftChildKey,int rightChildIndex, int rightChildKey) {
        //TODO COMPARE CHIELDS
        int smallest;
        //finding the smallest chield
        if (leftChildKey < rightChildKey) {
            smallest = leftChieldIndex;
            System.out.println("-compareFullSubTree-  smallest key is leftchild key:" + leftChildKey+" index: "+leftChieldIndex);
        } else {
            smallest = rightChildIndex;
            System.out.println("-compareFullSubTree-  smallest key is rightchild key: " + rightChildKey+" index: "+rightChildIndex);
        }
        //compare the smallest chield to the root
        if (rootKey <= rightChildKey && rootKey <= leftChildKey) {
            smallest = rootIndex;
            System.out.println("-compareFullSubTree-  @override smallest key is root key " + rootKey);
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
        System.out.println("subTree-root = " + RootIndex);

        try {
            leftChildIndex = getLeftChild(RootIndex);
            System.out.println("leftChildindex = " + leftChildIndex);
        } catch (Exception e) {
            leftChildIndex = null;
            System.out.println("leftChildindex = null ");
        }

        try {
            rightChildIndex = getRightChild(RootIndex);
            System.out.println("RightChildindex = " + rightChildIndex);
        } catch (Exception e) {
            rightChildIndex = null;
            System.out.println("RightChildindex = null");
        }

        smallestKeyIndex = findSmallestElement(RootIndex, leftChildIndex, rightChildIndex);


        //if node was smaller then root 
        if (smallestKeyIndex != RootIndex) {
            System.out.println("in heapify loop:");
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
        stack.insert(new Element(9, null));
        System.out.println(stack.toString());
        System.out.println("element 1 was succesfully added");
        stack.insert(new Element(2, null));
        System.out.println(stack.toString());
        System.out.println("element 2 was succesfully added");

        stack.insert(new Element(1, null));
        System.out.println(stack.toString());
        System.out.println("element 3 was succesfully added");
        stack.insert(new Element(5, null));
        System.out.println(stack.toString());
        System.out.println("element 4 was succesfully added");
        stack.insert(new Element(4, null));
        System.out.println(stack.toString());
        System.out.println("element 5 was succesfully added");
        stack.insert(new Element(8, null));
        System.out.println(stack.toString());
        System.out.println("element 6 was succesfully added");
        stack.insert(new Element(10, null));
        System.out.println(stack.toString());
        System.out.println("element 7 was succesfully added");
        stack.insert(new Element(2, null));
        System.out.println(stack.toString());
        System.out.println("element 8 was succesfully added");

    }
}
