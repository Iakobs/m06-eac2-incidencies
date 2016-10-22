/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package empresa;

/**
 *
 * @author professor
 */
public interface Incidencia {

    public String getReferencia();

    public void setReferencia(String referencia);

    public String getDescripcio();

    public void setDescripcio(String descripcio);

    public float getCost();

    public void setCost(float cost);
    
    public Empresa getEmpresa();

    public void setEmpresa(Empresa empresa);
    

}
