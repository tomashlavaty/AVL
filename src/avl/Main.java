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
       //int[] test= new int[]{81,25,1,59,56,98,97,123};
        Random rd=new Random();
        
        // int[] test= new int[]{80,13,53,61,73,65,48,56,72,91,89,55,14,81,54};
        // int[] test= new int[]{16,4,85,3,30,86,96,71,79,90};//10,9,12,14,13
         int[] test= new int[]{65,33,71};
       /* for (int i = 0; i < test.length; i++) {
            strom.add(new Node(new Auto(test[i])));
       
        }*/
        for (int i = 0; i < test.length; i++) {
            strom.add(new Node(new Auto(test[i])));
        //strom.search(8).s}
        }
       
        System.out.println(strom.preOrderNodePrint());
         strom.delete(33);
         //strom.delete(72);
         
       
        //System.out.println(strom.search(48).resetBalanFakt());
        System.out.println(strom.preOrderNodePrint());
        System.out.println(strom.levelOrder());
//        System.out.println(strom.search(48).resetBalanFakt());
        //  System.out.println(strom.search(9).getLeft());
       
       
        
       //strom.search(5).setRight(strom.lavaRotacia(strom.search(8)));
       
       // System.out.println(strom.search(7));
        
        System.out.println();
       
        
        
    }
    
}
