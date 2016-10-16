/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avl;

/**
 *
 * @author tomas
 */
public interface IData {
    
    public int compare(Object paKey);
    public Object getKey();
    public String toString();
}
