package part_1;

import java.util.Scanner;

/**
 *
 * segil17 - Sebastian Gildenpfennig
 * rofra17 - Robert Francisti
 * askot17 - Alex Skotner
 * jejoh16 - Jean Johnsen
 * 
 */

public class Heapsort { 
    public static void main(String[] args) {

	PQ pq = new PQHeap(10);

	int n = 0;
	Scanner sc = new Scanner(System.in);
	while (sc.hasNextInt()) {
	    pq.insert(new Element(sc.nextInt(),null));
	    n++;
       }
	while (n > 0){
	    System.out.println(pq.extractMin().getKey());
	    n--;
	}
   }
}
