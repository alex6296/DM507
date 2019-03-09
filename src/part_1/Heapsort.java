package part_1;

import java.util.Scanner;

public class Heapsort { 
    public static void main(String[] args) {

	PQ pq = new PQHeapV1(10);

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
