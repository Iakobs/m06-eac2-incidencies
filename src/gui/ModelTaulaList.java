/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * model de taula grafica (Swing) que permet visualitzar els elements de List <T>
 * amb independencia del tipus que sigui T
 *@author professor
 */
public class ModelTaulaList <T> extends DefaultTableModel {

    private List <T> llista=null;
    private Method [][] metodes;
    
    
    /**
     * Constructor
     * @param res llista dels objectes que volem mostrar a la taula
     * @param classe classe dels objectes que formen part de la llista
     * @param capceleres vector amb els textos que encapcalaran cada columna
     * @param camps vector de vectors;  per cada columna porta un vector amb els noms dels
     * metodes que s'hauran d'anar cridant per trobar la dada d'aquella columna (sense el "get" al davant);
     * quan al lloc d'un metode hi va el valor null indica que cal cridar a toString (es un metode comu a tots els objectes)
     * @throws ModelTaulaListException  si es produeix una excepcio consultant les dades, es propaga
     */
    ModelTaulaList (List <T> res, Class classe, String [] capceleres, String[][] camps) throws ModelTaulaListException {
        super(capceleres, 0);
        
        Class auxClasse;
        
        metodes= new Method[camps.length][];  //conte els metodes que caldra cridar consecutivament per visualitzar columna
        

        this.llista=res;
        
        
        for (int i=0; i<camps.length; i++){  //omplim el vector metodes
            try{
                String[] auxCamps=camps[i];
                
                int auxLen=auxCamps.length;
                
                metodes[i]= new Method[auxCamps.length];
                
                auxClasse=classe;
                for(int j=0; j<auxLen;j++){
                    String s=auxCamps[j];
                    if(s==null){
                        metodes[i][j]=auxClasse.getMethod("toString");
                    }else{
                        metodes[i][j]=auxClasse.getMethod("get"+s.substring(0,1).toUpperCase()+s.substring(1));
                    }
                    auxClasse=metodes[i][j].getReturnType();
                }
              
            } catch(NoSuchMethodException e){
                metodes[i]=null;
            }
            catch(SecurityException e){
                metodes[i]=null;
            }
        }
    }
    /**
     * actualitza la llista del model
     * @param llista nova llista del model
     */
    public void setLlista(List<T> llista) {
        this.llista = llista;
    }
    /**
    * Retorna del nombre de files amb dades
    * @return nombre de files amb dades
    */
    @Override
    public int getRowCount() {

        if (llista==null){
            return 0;
        } else {
            return llista.size();
        }
    }
    /**
     * Retorna l'objecte que cal mostrar a la fila i columna indicats pels parametres;  l'origen de dades conte clients
     * @param row     fila de l'objecte a mostrar
     * @param column  columna de l'objecte a mostrar
     * @return  objecte a mostrar
     */
    @Override
    public Object getValueAt(int row, int column) {
        try {
            
            if(row<0 || row>=llista.size()){
                return null;
            }
            Object resultat=llista.get(row);
            
            try{
                for(Method m:metodes[column]){  //per cada columna cal cridar de manera sequencial els metodes que te associats
                    resultat=m.invoke(resultat);
                }
            }catch(NullPointerException e){
                 resultat=null;   
            }
            
            return resultat;
        } catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){ 
           return null; }
    }
    /**
     * indica que la taula no es editable
     * @param row s'ignora
     * @param column s'ignora
     * @return 
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    public void assigna(JTable taula ){
       taula.setModel(this);
    }
    
    public void assigna(List <T> res){
        this.llista=res;
    }
    
}
