package part_1;

import static java.lang.Math.floor;

/**
 *
 * @author Alex Skotner
 */
public class PQHeap implements PQ {

    public final int length; //the max number of knots
    public int heapSize = 0; // the current amount of knots
    public final Element[] heap; //the array

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

        Element min = heap[1];
        heap[1] = heap[heapSize];
        heapSize = heapSize - 1;
        minHeapify(1);
        return min;
    }

    /**
     * Returns the index of the parent to given param.
     *
     * @param currentNodeIndex
     * @return int index of the parent
     */
    private int Parent(int currentNodeIndex) {
        int value = (int) floor(currentNodeIndex / 2);
        /*if (value < 1) {
         return currentNodeIndex;
         }*/
        return value;
    }

    /**
     * Returns the index of the left child to given param.
     *
     * @param currentNodeIndex
     * @return int index of the left child
     */
    private int LeftChild(int currentNodeIndex) {
        return (currentNodeIndex * 2);
    }

    /**
     * Returns the index of the right child to given param.
     *
     * @param currentNodeIndex
     * @return int index of the right child
     */
    private int RightChild(int currentNodeIndex) {
        return currentNodeIndex * 2 + 1;
    }

    private boolean isRoot(Element[] A, int i) {
        if (A[Parent(i)] != null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * insets an element to the heap and sorts it.
     *
     * @param Element
     */
    @Override
    public void insert(Element e) {
        heapSize++;
        heap[heapSize] = e;
        heapIncreaceKey(heapSize, e);
    }

    private void heapIncreaceKey(int i, Element e) {
        if (e.getKey() > heap[i].getKey()) {
            System.out.println("error key is bigger then current key");
            return;
        }
        heap[i] = e;
        while (i > 1 && heap[Parent(i)].getKey() > heap[i].getKey()) {
            exchange(i, Parent(i));
            i = Parent(i);
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

    public void minHeapify(int i) {
        int smallest;
        int l = LeftChild(i);
        int r = RightChild(i);
        if (l > heapSize && heap[l].getKey() < heap[i].getKey()) {
            smallest = l;
        } else {
            smallest = i;
        }
        if (r > heapSize && heap[r].getKey() < heap[smallest].getKey()) {
            smallest = r;
        }
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

}
