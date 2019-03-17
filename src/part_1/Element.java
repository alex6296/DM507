package part_1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * segil17 - Sebastian Gildenpfennig
 * rofra17 - Robert Francisti
 * askot17 - Alex Skotner
 * jejoh16 - Jean Johnsen
 * 
 */
public class Element {
    private int key;
    private Object data;

    public Element(int i, Object o){
	this.key = i;
	this.data = o;
    }
    public int getKey(){
	return this.key;
    }
    public Object getData(){
	return this.data;
    }  
}
