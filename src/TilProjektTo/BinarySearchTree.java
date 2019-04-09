/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarysearchtree;

/**
 *
 * @author robertfrancisti
 */
public class BinarySearchTree implements Dict {
    
    
    
    Node x;
    Node y;
    Node root;
    
    public BinarySearchTree(){
        
        BinarySearchTree newEmpty = new BinarySearchTree();
       
    }
    public void insert(int k) {
        
        y = null;
        x = root;
        
            if(x == null){
                x = new Node(0 );
            }
        while(x != null){
            x = y;
            if(x.key < k.key){
                x = x.leftChild;
               
            }else{
                x = x.rightChild;                  
                // z.p = y                        
            }if(y == null){
                // Tree is empty 
            }else if(k.key < y.key){
                y.leftChild = k;
            }else{
                y.rightChild = k;
            }
        }
        
        
        
    }

    @Override
    public int[] orderedTraversal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean search(int k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

class Node {
    
    int key; 
    String name;
    Node leftChild;
    Node rightChild;
    
    
    Node(int key){
        this.key = key;
      
    }
    
    
    public String toString(){
        
        return "I have the key " + key + " and the name " + name; 
    }
    
    
    
}

