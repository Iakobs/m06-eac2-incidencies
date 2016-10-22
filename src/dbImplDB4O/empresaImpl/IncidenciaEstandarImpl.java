/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbImplDB4O.empresaImpl;

import empresa.Empresa;
import empresa.IncidenciaEstandar;

/**
 *
 * @author Sergio
 */
public class IncidenciaEstandarImpl extends IncidenciaImpl implements IncidenciaEstandar {

    private String email;

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
