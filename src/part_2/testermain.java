/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part_2;

/**
 *
 * @author Lenovo
 */
public class testermain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DictBinTree t = new DictBinTree();
        t.insert(1);
        t.insert(3);
        t.insert(7);
        t.insert(-1);
        t.insert(9);
        t.insert(9);
        t.insert(0);
        t.insert(2);
        for (int v : t.orderedTraversal()) {
            System.out.println(v);
        }
        ;
    }

}
