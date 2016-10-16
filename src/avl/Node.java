/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avl;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author tomas
 */
public class Node {

    private IData data;
    private Node Left;
    private Node Right;
    private int BalanFakt;

    public Node(IData data) {

        this.data = data;
    }

    public int getPocetDeti() {
        if (this.Left != null && this.Right != null) {
            return 2;
        } else if (this.Left != null || this.Right != null) {
            return 1;
        } else {
            return 0;
        }

    }

    public IData getData() {
        return data;
    }

    public void setData(IData data) {
        this.data = data;
    }

    public Node getLeft() {
        return Left;
    }

    public void setLeft(Node Left) {
        this.Left = Left;
    }

    public Node getRight() {
        return Right;
    }

    public void setRight(Node Right) {
        this.Right = Right;
    }

    public int vyskaVrch() {
        Queue<Node> front = new LinkedList<>();
        int vyska = 0;
        Node pomNode;

        if (this == null) {
            return 0;
        }
        front.offer(this);
        front.offer(null);

        while (!front.isEmpty()) {
            pomNode = front.poll();
            if (pomNode == null) {
                vyska++;
                if (front.isEmpty()) {
                    break;
                }
                front.offer(null);

            } else {
                if (pomNode.getLeft() != null) {
                    front.offer(pomNode.getLeft());
                }
                if (pomNode.getRight() != null) {
                    front.offer(pomNode.getRight());
                }

            }

        }

        return vyska;
    }

    public int resetBalanFakt() {
        //vrchol ma oboch potomkov
        if (this.getLeft() != null && this.getRight() != null) {
            //this.BalanFakt=((-this.getLeft().vyskaVrch())+ (this.getRight().vyskaVrch()));
            return (-this.getLeft().vyskaVrch()+ this.getRight().vyskaVrch());
        //vrchol ma prave jedneho potomka (praveho)    
        } else if (this.getLeft() == null && this.getRight() !=null) {
            //this.BalanFakt=this.getRight().vyskaVrch();
            return this.getRight().vyskaVrch();            
        } 
        //vrchol ma prave jedneho potomka (laveho)
        else if(this.getRight()==null && this.getLeft()!= null){
            //this.BalanFakt=-this.getLeft().vyskaVrch();
            return (-this.getLeft().vyskaVrch());
        }
        //vrchol nema potomka
        else {
            //this.BalanFakt=0;
            return 0;
        }

    }

    @Override
    public String toString() {
        return data.toString();
    }

    public int getBalanFakt() {
        return BalanFakt;
    }

    public void setBalanFakt(int BalanFakt) {
        this.BalanFakt = BalanFakt;
    }
    

  
    
}
