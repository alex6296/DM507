package part_1;

import java.util.Scanner;

public class Heapsort {

    public static void main(String[] args) {
        //initializes the heap

        PQ pq = new PQHeap(20);

        int n = 0;
        //setting up scanner to make inputs available in the console
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()) {
            pq.insert(new Element(sc.nextInt(), null));
            n++;
        }
        System.out.println(pq.toString());

        //for some reason this isnt activating so remember to write "quit" in the console
        while (n > 0) {
            System.out.println(pq.extractMin().getKey());
            n--;
        }
    }
}
