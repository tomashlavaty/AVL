/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avl;

import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

/**
 *
 * @author tomas
 */
public class AVL {

    private Node root;

    public void add(Node paNode) {
        Stack<Node> cesta = new Stack<Node>();

        if (root == null) {
            root = paNode;
        }

        Node actual = root;
        while (true) {

            if (actual.getData().compare(paNode.getData().getKey()) == -1) {
                if (actual.getLeft() == null) {
                    cesta.push(actual);
                    actual.setLeft(paNode);
                    break;
                } else {
                    cesta.push(actual);
                    actual = actual.getLeft();
                }

            } else if (actual.getData().compare(paNode.getData().getKey()) == 1) {
                if (actual.getRight() == null) {
                    cesta.push(actual);
                    actual.setRight(paNode);
                    break;
                } else {
                    cesta.push(actual);
                    actual = actual.getRight();
                }
            } else {
               
                break;
            }
        }
        System.out.println(paNode.getData().toString() + " cesta:" + cesta.toString());
        while (!cesta.empty()) {
            Node prejdeny = cesta.pop();
            Node pomRodic;

            prejdeny.setBalanFakt(prejdeny.resetBalanFakt());
            if (prejdeny.getBalanFakt() == 2 && (prejdeny.getRight().getBalanFakt() < 0)) { //zigzag >
                System.out.println(paNode.getData() + " Tu bude treba vykonat dvojitu prava lava rotaciu" + prejdeny.getData() + " " + prejdeny.getBalanFakt());
                prejdeny.setRight(this.pravaRotacia(prejdeny.getRight()));
                if (prejdeny == root) {
                    this.setRoot(this.lavaRotacia(prejdeny));
                   // break;
                } else {
                    //cesta.pop().setRight(this.lavaRotacia(prejdeny));
                    //break;
                    pomRodic = cesta.pop();
                    if (pomRodic.getRight() == prejdeny) {
                        pomRodic.setRight(this.lavaRotacia(prejdeny));
                    } else {
                        pomRodic.setLeft(this.lavaRotacia(prejdeny));
                    }                   
                }
                 break;
                //  break;//pozor stopne reset BF (nemalo by treba postupovat dalej)
            } else if ((prejdeny.getBalanFakt() == -2) && (prejdeny.getLeft().getBalanFakt() > 0)) {  //zagzig <
                System.out.println(paNode.getData() + " Tu bude treba vykonat dvojitu lava-prava rotaciu" + prejdeny.getLeft().getData() + " " + prejdeny.getBalanFakt());
                prejdeny.setLeft(this.lavaRotacia(prejdeny.getLeft()));
                if (prejdeny == root) {
                    this.setRoot(this.pravaRotacia(prejdeny));
                   
                } else {
                    //cesta.pop().setLeft(this.pravaRotacia(prejdeny));
                   //break;
                   pomRodic = cesta.pop();
                    if (pomRodic.getLeft() == prejdeny) {
                        pomRodic.setLeft(this.pravaRotacia(prejdeny));
                    } else {
                        pomRodic.setRight(this.pravaRotacia(prejdeny));
                    }
                }
                break;
            } else if (prejdeny.getBalanFakt() == 2) {
                System.out.println(paNode.getData() + " Tu sa vykonava lava rotacia" + prejdeny.getData() + " " + prejdeny.getBalanFakt() + " rodic");
                if (prejdeny == root) {
                    this.setRoot(this.lavaRotacia(prejdeny));
                    
                } else {
                    pomRodic = cesta.pop();
                    if (pomRodic.getRight() == prejdeny) {
                        pomRodic.setRight(this.lavaRotacia(prejdeny));
                    } else {
                        pomRodic.setLeft(this.lavaRotacia(prejdeny));
                    }
                    
                }
                //cesta.pop().setLeft(this.lavaRotacia(pomNode));
                break;
            } else if (prejdeny.getBalanFakt() == -2) {
                System.out.println(paNode.getData() + " Tu sa vykonava  prava rotaciaa " + prejdeny.getData() + " " + prejdeny.getBalanFakt() + "rodic");
                if (prejdeny == root) {
                    this.setRoot(this.pravaRotacia(prejdeny));
                    
                } else {
                    pomRodic = cesta.pop();
                    if (pomRodic.getLeft() == prejdeny) {
                        pomRodic.setLeft(this.pravaRotacia(prejdeny));
                    } else {
                        pomRodic.setRight(this.pravaRotacia(prejdeny));
                    }

                }
                // cesta.pop().setRight(this.pravaRotacia(pomNode));
                break;
            }

        }

    }

    public Node pravaRotacia(Node paNode) {

        Node pomNode;
        pomNode = paNode.getLeft();
        paNode.setLeft(paNode.getLeft().getRight());
        pomNode.setRight(paNode);

        return pomNode;
    }

    public Node lavaRotacia(Node paNode) {

        Node pomNode;
        pomNode = paNode.getRight();
        paNode.setRight(paNode.getRight().getLeft());
        pomNode.setLeft(paNode);

        return pomNode;
    }

    public Node search(Object paKey) {
        Node actual = root;

        while (true) {
            if (actual.getData().compare(paKey) == 0) {
                return (Node) actual;
            }

            if (actual.getData().compare(paKey) == -1) {
                actual = actual.getLeft();
            } else if (actual.getData().compare(paKey) == 1) {
                actual = actual.getRight();
            }

            if (actual == null) {
                System.out.println("Nenasiel sa vrchol s klucom:" + paKey.toString());
                return null;
            }
        }

    }

    public String preOrderPrint() {
        String retaz = "";
        Stack<Node> naVypis = new Stack<Node>();
        Node pomNode;

        if (this.root == null) {
            return "Strom neobsahuje ziadny vrchol";
        } else {
            naVypis.push(root);
            while (!naVypis.empty()) {
                pomNode = naVypis.pop();
                retaz += pomNode.getData().toString() + "\n";
                if (pomNode.getRight() != null) {
                    naVypis.push(pomNode.getRight());
                }
                if (pomNode.getLeft() != null) {
                    naVypis.push(pomNode.getLeft());
                }
            }
        }

        return retaz;
    }

    public String InOrderPrint() {
        String retaz = "";
        Stack<Node> naVypis = new Stack<Node>();
        Node pomNode = root;
        while ((!naVypis.empty()) || (pomNode != null)) {
            if (pomNode != null) {
                naVypis.push(pomNode);
                pomNode = pomNode.getLeft();
            } else {
                pomNode = naVypis.pop();
                retaz += pomNode.getData().toString() + "\n";
                pomNode = pomNode.getRight();
            }
        }

        return retaz;
    }

    public String preOrderNodePrint() {
        String retaz = "";
        Stack<Node> naVypis = new Stack<Node>();
        Node pomNode;

        if (this.root == null) {
            return "Strom neobsahuje ziadny vrchol";
        } else {
            naVypis.push(root);
            while (!naVypis.empty()) {
                pomNode = naVypis.pop();
                retaz += pomNode.getData().toString() + "  " + pomNode.getBalanFakt() + "\n";
                if (pomNode.getRight() != null) {
                    naVypis.push(pomNode.getRight());
                }
                if (pomNode.getLeft() != null) {
                    naVypis.push(pomNode.getLeft());
                }
            }
        }
        return retaz;
    }

    @Override
    public String toString() {
        return "AVL{" + "root=" + root + '}';
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

}
