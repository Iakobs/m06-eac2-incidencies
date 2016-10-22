/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbImplDB4O.empresaImpl;

import empresa.Empresa;
import empresa.Incidencia;

/**
 *
 * @author Sergio
 */
public class IncidenciaImpl implements Incidencia {
    private String referencia;  // abreviatura que serivira de clau
    private String descripcio;
    private float cost;
    private Empresa empresa;


    @Override
    public String getReferencia() {
        return referencia;
    }

    @Override
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @Override
    public String getDescripcio() {
        return descripcio;
    }

    @Override
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public Empresa getEmpresa() {
        return empresa;
    }

    @Override
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }   
    
}
