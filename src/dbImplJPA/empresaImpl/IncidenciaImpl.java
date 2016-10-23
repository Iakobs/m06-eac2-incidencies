/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbImplJPA.empresaImpl;

import empresa.*;
import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author professor
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipus", length = 1, discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
    @NamedQuery(name = "IncidenciaImpl.obtenirIncidenciesOrdenadesPerReferencia",
            query = "SELECT i FROM IncidenciaImpl i ORDER BY i.referencia")
    ,
    @NamedQuery(name = "IncidenciaImpl.obtenirIncidenciesPerEmpresa",
            query = "SELECT i FROM IncidenciaImpl i WHERE i.empresa.nomEmpresa LIKE :nomEmpresa")
})
public class IncidenciaImpl implements Incidencia, Serializable {

    @Id
    private String referencia;  // abreviatura que serivira de clau
    private String descripcio;
    private float cost;
    @ManyToOne(fetch = FetchType.LAZY)
    private EmpresaImpl empresa;

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
        this.empresa = (EmpresaImpl) empresa;
    }
}
