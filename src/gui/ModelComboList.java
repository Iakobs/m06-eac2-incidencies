/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.lang.reflect.Method;
import java.util.List;
import javax.swing.DefaultComboBoxModel;


/**
 * Crea un combo amb les diferents ciutats de la base de dades i el valor null;
 * per defecte, es selecciona el valor null
 * @author professor
 */
public class ModelComboList <T> extends DefaultComboBoxModel{

    private List <T> llista=null;
    private int elementSeleccionat=0;
    
    
    
    public ModelComboList(List<T> llista ) {
        

        this.llista=llista;
        elementSeleccionat=llista.size(); //null
//        llista.add(null);
//        setSelectedItem(null);
        
    }
    
    
    @Override
    public void removeAllElements() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeElement(Object anObject) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeElementAt(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertElementAt(Object anObject, int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addElement(Object anObject) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getIndexOf(Object anObject) {
        return llista.indexOf(anObject);
    }

    @Override
    public Object getElementAt(int index) {
        return llista.get(index);
    }

    @Override
    public int getSize() {
        return llista.size();
    }

    @Override
    public Object getSelectedItem() {
        if(elementSeleccionat>=llista.size() || elementSeleccionat<0){ // no utilitzem IndexOutOfBoundException per compatibilitat amb db4o
            return null;
        }
        else{
            return llista.get(elementSeleccionat);
        }
    }

    @Override
    public void setSelectedItem(Object anObject) {

        if(anObject==null){
            elementSeleccionat=llista.size();
        }
        else{
            int resultat=-1;            
            for(int i=0;llista.get(i)!=null && i<llista.size();i++){
                if(anObject.equals(llista.get(i))){
                    resultat=i;
                    break;
                }
            }
            elementSeleccionat=resultat;
        }

        //elementSeleccionat=llista.indexOf(anObject);
    }
    

}