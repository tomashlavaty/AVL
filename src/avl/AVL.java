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
        while (true) {  //proces prejdenia stromu az k listu a pridanie prvku

            if (actual.getData().compare(paNode.getData().getKey()) == -1) { //ak vkladany prvok je mensi ako aktualny
                if (actual.getLeft() == null) {     //ak sa nachadza v liste prvok sa prida
                    cesta.push(actual);
                    actual.setLeft(paNode);
                    break;
                } else {                            //ak sa nenachadyame v liste pokracuje dalej
                    cesta.push(actual);
                    actual = actual.getLeft();
                }

            } else if (actual.getData().compare(paNode.getData().getKey()) == 1) {      //ak vkladany prvok je vacsi ako aktualny
                if (actual.getRight() == null) {        //ak sa nachadza v liste prvok sa prida
                    cesta.push(actual);
                    actual.setRight(paNode);
                    break;
                } else {                                //ak sa este nenachadza v liste, nastavi sa novy aktualny vrchol a pokracuje sa dalej          
                    cesta.push(actual);
                    actual = actual.getRight();
                }
            } else {

                break;
            }
        }
        System.out.println(paNode.getData().toString() + " cesta:" + cesta.toString());
        while (!cesta.empty()) { //pokial je stack nieje prazdny
            Node prejdeny = cesta.pop();
            Node rodicNevyvazeneho;

            prejdeny.setBalanFakt(prejdeny.vypocBalanFakt());
            if (prejdeny.getBalanFakt() == 2 && (prejdeny.getRight().getBalanFakt() < 0)) { //zigzag >
                System.out.println(paNode.getData() + " Tu bude treba vykonat dvojitu prava lava rotaciu" + prejdeny.getData() + " " + prejdeny.getBalanFakt());
                // prejdeny.setRight(this.pravaRotacia(prejdeny.getRight()));
                if (prejdeny == root) {
                    //this.setRoot(this.lavaRotacia(prejdeny));
                    this.setRoot(this.pravaLavaRotacia(prejdeny));
                    // break;
                } else {
                    //cesta.pop().setRight(this.lavaRotacia(prejdeny));
                    //break;
                    rodicNevyvazeneho = cesta.pop();

                    if (rodicNevyvazeneho.getRight() == prejdeny) {
                        // rodicNevyvazeneho.setRight(this.lavaRotacia(prejdeny));
                        rodicNevyvazeneho.setRight(this.pravaLavaRotacia(prejdeny));
                    } else {
                        //rodicNevyvazeneho.setLeft(this.lavaRotacia(prejdeny));
                        rodicNevyvazeneho.setLeft(this.pravaLavaRotacia(prejdeny));
                    }
                }
                break;
                //  break;//pozor stopne reset BF (nemalo by treba postupovat dalej)
            } else if ((prejdeny.getBalanFakt() == -2) && (prejdeny.getLeft().getBalanFakt() > 0)) {  //zagzig <
                System.out.println(paNode.getData() + " Tu bude treba vykonat dvojitu lava-prava rotaciu" + prejdeny.getLeft().getData() + " " + prejdeny.getBalanFakt());
                //prejdeny.setLeft(this.lavaRotacia(prejdeny.getLeft()));

                if (prejdeny == root) {
                    //this.setRoot(this.pravaRotacia(prejdeny));
                    this.setRoot(this.lavaPravaRotacia(prejdeny));
                } else {
                    rodicNevyvazeneho = cesta.pop();
                    if (rodicNevyvazeneho.getLeft() == prejdeny) {
                        //rodicNevyvazeneho.setLeft(this.pravaRotacia(prejdeny));
                        rodicNevyvazeneho.setLeft(this.lavaPravaRotacia(prejdeny));
                    } else {
                        //rodicNevyvazeneho.setRight(this.pravaRotacia(prejdeny));
                        rodicNevyvazeneho.setRight(this.lavaPravaRotacia(prejdeny));
                    }
                }
                break;
            } else if (prejdeny.getBalanFakt() == 2) {
                System.out.println(paNode.getData() + " Tu sa vykonava lava rotacia" + prejdeny.getData() + " " + prejdeny.getBalanFakt() + " rodic");
                if (prejdeny == root) {
                    this.setRoot(this.lavaRotacia(prejdeny));

                } else {
                    rodicNevyvazeneho = cesta.pop();
                    if (rodicNevyvazeneho.getRight() == prejdeny) {
                        rodicNevyvazeneho.setRight(this.lavaRotacia(prejdeny));
                    } else {
                        rodicNevyvazeneho.setLeft(this.lavaRotacia(prejdeny));
                    }

                }
                //cesta.pop().setLeft(this.lavaRotacia(pomNode));
                break;
            } else if (prejdeny.getBalanFakt() == -2) {
                System.out.println(paNode.getData() + " Tu sa vykonava  prava rotaciaa " + prejdeny.getData() + " " + prejdeny.getBalanFakt() + "rodic");
                if (prejdeny == root) {
                    this.setRoot(this.pravaRotacia(prejdeny));

                } else {
                    rodicNevyvazeneho = cesta.pop();
                    if (rodicNevyvazeneho.getLeft() == prejdeny) {
                        rodicNevyvazeneho.setLeft(this.pravaRotacia(prejdeny));
                    } else {
                        rodicNevyvazeneho.setRight(this.pravaRotacia(prejdeny));
                    }

                }
                // cesta.pop().setRight(this.pravaRotacia(pomNode));
                break;
            }

        }

    }

    public Node lavaPravaRotacia(Node paNode) {

        Node pomLavaRot = paNode;
        Node pomPravaRot;

        pomLavaRot.setLeft(lavaRotacia(paNode.getLeft()));
        pomPravaRot = pravaRotacia(pomLavaRot);

        return pomPravaRot;
    }

    public Node pravaLavaRotacia(Node paNode) {
        Node pomLavaRot;
        Node pomPravaRot = paNode;

        pomPravaRot.setRight(pravaRotacia(paNode.getRight()));
        pomLavaRot = lavaRotacia(pomPravaRot);

        return pomLavaRot;
    }

    public boolean delete(Object paKey) {
        Node actual = root;
        Stack<Node> cesta = new Stack<>();
        Stack<Node> hladMin;

        while (true) {
            if (actual.getData().compare(paKey) == 0) {
                if (actual == root) {
                    cesta.push(actual);
                }
                
                break;
            } else if (actual.getData().compare(paKey) == -1) {
                cesta.push(actual);
                actual = actual.getLeft();
            } else if (actual.getData().compare(paKey) == 1) {
                cesta.push(actual);
                actual = actual.getRight();
            }
            if (actual == null) {
                cesta.clear();
                break;
            }
        }
        System.out.println();

        Node rodic = null;
        Node rodicNevyvazeneho;
        if (!cesta.isEmpty() && (actual != root)) {
            System.out.println("NRMAZEM ROOT");
            rodic = cesta.peek();

            //cesta.push(rodic);
            if (actual.getPocetDeti() == 0) {
                if (rodic.getLeft() == actual) {
                    rodic.setLeft(null);
                } else {
                    rodic.setRight(null);
                }
                //ak jedno dieta  
            } else if (actual.getPocetDeti() == 1) {
                //cesta.pop();
                if (rodic.getLeft() == actual) { //ak mazany vrchol je lavym synom, rodicovi sa prenastavy lavy syn inak pravy
                    if (actual.getLeft() == null) {     //zistovanie ktory syn mazaneho vrhola sa priradi rodicu
                        rodic.setLeft(actual.getRight()); //rodicovi sa priradi lavy syn (pravy syn mazaneho vrchola)
                        cesta.push(actual.getRight());
                    } else {
                        rodic.setLeft(actual.getLeft());   // rodicovi sa priradi lavy syn (lavy syn mazaneho vrchola)
                        cesta.push(actual.getLeft());
                    }
                } else if (actual.getLeft() == null) {  //pripad ked mazany brchol je pravym synom rodica
                    rodic.setRight(actual.getRight());  //rodicovu sa priradi pravy syn (pravy syn mazaneho vrchola)
                    cesta.push(actual.getRight());
                } else {
                    rodic.setRight(actual.getLeft());   //rodiovi sa priradi pravy syn (lavy syn mazaneho vrchola)
                    cesta.push(actual.getLeft());
                }
                //ak 2 deti
            } else {
                hladMin = new Stack<>();
                Node min = actual.getRight();
                Node rodicMin = actual;  //pomocna  premena na ulozenie vrcholu ktory je rodicom minima                                                                                                                                                                                                                   
                //Najdenie minima v Pravom podstrome
                while (min.getLeft() != null) {
                    hladMin.push(min);
                    rodicMin = min;
                    min = min.getLeft();
                }
                //mazanie VRCHOLA KTORY NIEJE KOREN A MA 2 DETI
                if (rodicMin.getLeft() == min) { //odstranenie odkazu na vrchol s min. hodnotou kluca (ktory pojde na miesto mazaneho)
                    if (min.getRight() == null) {
                        if (min.getRight() == null) {
                            rodicMin.setLeft(null);
                        }
                    } else {
                        rodicMin.setLeft(min.getRight());
                    }
                } else {
                    rodicMin.setRight(null);//
                }

                min.setLeft(actual.getLeft());
                min.setRight(actual.getRight());

                if (rodic.getLeft() == actual) {
                    cesta.push(min);
                    rodic.setLeft(min);
                } else {
                    cesta.push(min);
                    rodic.setRight(min);
                }
                for (Node n : hladMin) {
                    cesta.push(n);
                }

            }
        } else if (!cesta.isEmpty() && (actual == root)) {
            System.out.println("MAZEM ROOT");
            if (actual.getPocetDeti() == 1) {

            } else if (actual.getPocetDeti() == 2) {
                System.out.println("2dei");
                cesta = new Stack<>();
                Node min = actual.getRight();
                Node rodicMin = actual;  //pomocna  premena na ulozenie vrcholu ktory je rodicom minima                                                                                                                                                                                                                   
                //Najdenie minima v Pravom podstrome               
                while (min.getLeft() != null) {
                    cesta.push(min);
                    rodicMin = min;
                    min = min.getLeft();
                }

                if (rodicMin.getLeft() == min) { //odstranenie odkazu na vrchol s min. hodnotou kluca (ktory pojde na miesto mazaneho)
                    if (min.getRight() == null) {
                        if (min.getRight() == null) {
                            rodicMin.setLeft(null);
                        }
                    } else {
                        rodicMin.setLeft(min.getRight());
                    }
                } else {
                    rodicMin.setRight(null);//
                }

                min.setLeft(root.getLeft());
                min.setRight(root.getRight());
                this.setRoot(min);

                cesta.add(0, min);

                System.out.println("LOL" + cesta);
            } else {
                root = null;
            }

        } else {
            System.err.println("cesta is empty ");

        }

//        System.out.println("\nBALANCE FAKTORY" + rodic.getBalanFakt());
        System.out.println("BUDE sa mazat" + actual + cesta.toString());
        Node v;
        while (!cesta.empty()) {

            v = cesta.pop();  //v cykle sa vyberaju vrcholi zo stacku
            System.out.println(v.getData() + ">>" + v.getBalanFakt() + "       " + v.vypocBalanFakt());

            if (v.vypocBalanFakt() != 0) {

                if (v.vypocBalanFakt() == 2 && v.getRight().getBalanFakt() < 0) {
                    System.out.println("prava lava");
                    if (v == root) {
                        //this.setRoot(this.pravaRotacia(prejdeny));
                        this.setRoot(this.pravaLavaRotacia(v));
                    } else {
                        rodicNevyvazeneho = cesta.peek();
                        if (rodicNevyvazeneho.getLeft() == v) {
                            //rodicNevyvazeneho.setLeft(this.pravaRotacia(prejdeny));
                            rodicNevyvazeneho.setLeft(this.pravaLavaRotacia(v));
                        } else {
                            //rodicNevyvazeneho.setRight(this.pravaRotacia(prejdeny));
                            rodicNevyvazeneho.setRight(this.pravaLavaRotacia(v));
                        }
                    }

                } else if (v.vypocBalanFakt() == 2) {
                    System.out.println("LAVA");
                    if (v == root) {
                        this.setRoot(this.lavaRotacia(v));

                    } else {
                        rodicNevyvazeneho = cesta.peek();
                        if (rodicNevyvazeneho.getRight() == v) {
                            rodicNevyvazeneho.setRight(this.lavaRotacia(v));
                        } else {
                            rodicNevyvazeneho.setLeft(this.lavaRotacia(v));
                        }

                    }
                    
                    //lava
                } //((prejdeny.getBalanFakt() == -2) && (prejdeny.getLeft().getBalanFakt() > 0)) {
                else if (v.vypocBalanFakt() == -2 && v.getLeft().getBalanFakt() > 0) {

                    System.out.println("LAVA PRAVA");
                    if (v == root) {
                        //this.setRoot(this.pravaRotacia(prejdeny));
                        this.setRoot(this.lavaPravaRotacia(v));
                    } else {
                        rodicNevyvazeneho = cesta.peek();
                        if (rodicNevyvazeneho.getLeft() == v) {
                            //rodicNevyvazeneho.setLeft(this.pravaRotacia(prejdeny));
                            rodicNevyvazeneho.setLeft(this.lavaPravaRotacia(v));
                        } else {
                            //rodicNevyvazeneho.setRight(this.pravaRotacia(prejdeny));
                            rodicNevyvazeneho.setRight(this.lavaPravaRotacia(v));
                        }
                    }

                    //cesta.pop().setLeft(this.lavaRotacia(pomNode));
                } else if (v.vypocBalanFakt() == -2) {
                    System.out.println("PRAVA");
                    if (v == root) {
                        this.setRoot(this.pravaRotacia(v));

                    } else {
                        rodicNevyvazeneho = cesta.peek();
                        if (rodicNevyvazeneho.getLeft() == v) {
                            rodicNevyvazeneho.setLeft(this.pravaRotacia(v));
                        } else {
                            rodicNevyvazeneho.setRight(this.pravaRotacia(v));
                        }

                    }
                    // cesta.pop().setRight(this.pravaRotacia(pomNode));

                } else {
                    System.err.println("-TOTO BY SA NEMALO VYPISAT-TEST" + v.getBalanFakt() + v.toString());
                }
            } else {
                System.out.println("KONIEC -- strom je po delete znova vyvazeny");
                // break;
            }
        }

        return true;
    }

    public Node pravaRotacia(Node paNode) {

        Node pomNode;
        pomNode = paNode.getLeft();
        paNode.setLeft(paNode.getLeft().getRight());
        pomNode.setRight(paNode);
        paNode.setBalanFakt(paNode.getBalanFakt() + 1);
        return pomNode;
    }

    public Node lavaRotacia(Node paNode) {

        Node pomNode;
        pomNode = paNode.getRight();
        paNode.setRight(paNode.getRight().getLeft());
        paNode.setBalanFakt(paNode.getBalanFakt() - 1);
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
                pomNode = pomNode.getLeft(); //vlozi sa do stacku lavy syn
            } else {                        //ak neexistuje lavy syn resp. null vypis vrchol a chod vpravo
                pomNode = naVypis.pop();
                retaz += pomNode.getData().toString() + "\n";
                pomNode = pomNode.getRight();
            }
        }

        return retaz;
    }

    public String levelOrder() {
        String vypis = "";
        Queue<Node> naVypis = new LinkedList<Node>();
        Node pomNode;
        if (root != null) {
            naVypis.offer(root);
        }
        int pocet;
        while (!naVypis.isEmpty()) {
            pocet = naVypis.size();
            while (pocet > 0) {
                pomNode = naVypis.poll();
                vypis += pomNode.getData().getKey()+"  ";
                if (pomNode.getLeft() != null) {
                    naVypis.offer(pomNode.getLeft());
                }
                if (pomNode.getRight() != null) {
                    naVypis.offer(pomNode.getRight());
                }
                pocet--;
            }
            vypis+="\n";

        }
        return vypis;
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
