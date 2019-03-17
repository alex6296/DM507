package part_1;

import java.util.Scanner;
//Yoyotest
public class Heapsort { 
    public static void main(String[] args) {

	PQ pq = new PQHeap(20);

	int n = 0;
	Scanner sc = new Scanner(System.in);
        
	while (sc.hasNextInt()) {
	    pq.insert(new Element(sc.nextInt(),null));
	    n++;
       }
        System.out.println(pq.toString());
        
	while (n > 0){
	    System.out.println(pq.extractMin().getKey());
	    n--;
	}
   }
}
