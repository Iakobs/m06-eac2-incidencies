/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbImplJPA.empresaImpl;

import empresa.*;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author professor
 */
@Entity
@DiscriminatorValue(value = "U")
public class IncidenciaUrgentImpl extends IncidenciaImpl implements IncidenciaUrgent {

    private String telefon;

    @Override
    public String getTelefon() {
        return telefon;
    }

    @Override
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
