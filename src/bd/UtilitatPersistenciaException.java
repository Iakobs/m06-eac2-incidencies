/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bd;

/**
 * Excepcio que indica un problema en accedir a la base de dades
 * @author professor
 */
public class UtilitatPersistenciaException extends Exception {
    public UtilitatPersistenciaException (String missatge){
        super(missatge);
    }
}
