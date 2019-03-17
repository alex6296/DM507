package part_1;

import static java.lang.Math.floor;
/**
 *
 * segil17 - Sebastian Gildenpfennig
 * rofra17 - Robert Francisti
 * askot17 - Alex Skotner
 * jejoh16 - Jean Johnsen
 * 
 */
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

    /**
     * removes the smallest element in the heap and returns it
     *
     * @return Element
     */
    @Override
    public Element extractMin() {
        //TODO
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
            System.out.println("rootindex:" + rootIndex);
            System.out.println(this.toString());
            int rootKey = rootElement.getKey();
            return compareFullSubTree(rootIndex, rootKey, leftChildIndex, leftChildKey, rightChildIndex, rightChildKey);
        } //no nodes
        else {
            return rootIndex;
        }
    }
/**
 * compares a tree with only 1 child.
 * @param rootIndex
 * @param rootKey
 * @param leftChieldIndex
 * @param leftChildKey
 * @return  the index of the node with the smallest key
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
     * @param RootIndex the index of the root of the sub-tree to start the heap
     * sort from.
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
