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
public class PQHeap implements PQ {

    private final int maxSize;
    private int size = 0;
    private Element[] heap;
    private final int noChild = -2147483647;

    public PQHeap(int maxElms) {
        maxSize = maxElms;
        heap = new Element[maxElms];
    }

    @Override
    public Element extractMin() {
        return heap[1];
    }

    @Override
    public void insert(Element e) {
        heap[size + 1] = e;
        minHeapify(heap, heap[size + 1].getKey());
        size++;
    }

    private int getParent(int currentNodeIndex) {
        int value = (int) floor(currentNodeIndex / 2);
        return heap[value].getKey();
    }

    private int getLeftChildIndex(int currentNodeIndex) {
        try {
            return heap[(currentNodeIndex * 2)].getKey();
        } catch (Exception e) {
            return noChild;
        }

    }

    private int getRightChildIndex(int currentNodeIndex) {
        try {
            return heap[currentNodeIndex * 2 + 1].getKey();
        } catch (Exception e) {
            return noChild;
        }

    }

    private void minHeapify(Element heap[], int rootIndex) {
        int leftChild = getLeftChildIndex(rootIndex);
        int rightChild = getRightChildIndex(rootIndex);
        int smallest;

        if (leftChild <= this.size) {
            if (heap[leftChild].getKey() > heap[rootIndex].getKey()) {
                smallest = leftChild;
            } else {
                smallest = rootIndex;
            }
        } else {
            smallest = rootIndex;
        }

        if (rightChild <= this.size) {
            if (heap[rightChild].getKey() > heap[smallest].getKey()) {
                smallest = rightChild;
            }

        }

        if (smallest != rootIndex) {
            exchange(heap[rootIndex].getKey(), heap[smallest].getKey());
            minHeapify(heap, smallest);
        }

    }

    private void exchange(int target, int source) {
        //saves root outside array
        Element savedTarget = heap[target];

        //set root to the smallest
        heap[target] = heap[source];
        //changes the original smallest to the root
        heap[source] = savedTarget;
    }

    private String print() {
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
        PQHeap stack = new PQHeap(10);
        stack.insert(new Element(1, 1));
        System.out.println(stack.print());
        stack.insert(new Element(0, 2));
        System.out.println(stack.print());
        /**
         * stack.insert(new Element(0, 3)); System.out.println(stack.print());
         *
         * stack.insert(new Element(5, 4)); System.out.println(stack.print());
         * stack.insert(new Element(3, 5)); System.out.println(stack.print());
         */

    }

}
