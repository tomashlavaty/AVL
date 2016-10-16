/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avl;

import java.util.Random;
import java.util.Stack;

/**
 *
 * @author tomas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        AVL strom =new AVL();
        
        String[] knihy= new String[]{"e","b","a","c","d","g","f","h","x"};
       // int[] test= new int[]{81,25,1,59,56,98,97,123};
        Random rd=new Random();
        
         int[] test= new int[]{80,13,54,61,73,65,48,56,72,91,89,55,14};
         //int[] test= new int[]{8,9,20,11,10};
       /* for (int i = 0; i < test.length; i++) {
            strom.add(new Node(new Auto(test[i])));
       
        }*/
        for (int i = 0; i < 10000; i++) {
            strom.add(new Node(new Auto(rd.nextInt(100000))));
        //strom.search(8).s}
        }
       
        System.out.println(strom.preOrderNodePrint());
        //  System.out.println(strom.search(9).getLeft());
       
       
        
       //strom.search(5).setRight(strom.lavaRotacia(strom.search(8)));
       
       // System.out.println(strom.search(7));
        
        System.out.println();
       
        
        
    }
    
}
