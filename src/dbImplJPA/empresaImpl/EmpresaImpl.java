/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbImplJPA.empresaImpl;

import empresa.Empresa;
import empresa.Incidencia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author professor
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "EmpresaImpl.obtenirEmpresaPerCodi",
            query = "SELECT e FROM EmpresaImpl e WHERE e.codi = :codi")
    ,
    @NamedQuery(name = "EmpresaImpl.obtenirEmpresesOrdenadesPerNomEmpresa",
            query = "SELECT e FROM EmpresaImpl e ORDER BY e.nomEmpresa")
})
public class EmpresaImpl implements Empresa, Serializable {

    @Id
    private String codi;
    private String nomEmpresa;
    private String seuEmpresa;
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "empresa",
            targetEntity = IncidenciaImpl.class)
    private final List llistaIncidencies = new ArrayList();

    /**
     *
     * @return
     */
    @Override
    public String getCodi() {
        return codi;
    }

    /**
     *
     * @param codi
     */
    @Override
    public void setCodi(String codi) {
        this.codi = codi;
    }

    /**
     *
     * @return
     */
    @Override
    public String getNomEmpresa() {
        return nomEmpresa;
    }

    /**
     *
     * @param nomEmpresa
     */
    @Override
    public void setNomEmpresa(String nomEmpresa) {
        this.nomEmpresa = nomEmpresa;
    }

    /**
     *
     * @return
     */
    @Override
    public String getSeuEmpresa() {
        return seuEmpresa;
    }

    /**
     *
     * @param seuEmpresa
     */
    @Override
    public void setSeuEmpresa(String seuEmpresa) {
        this.seuEmpresa = seuEmpresa;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Incidencia> getLlistaIncidencies() {
        return (List<Incidencia>) llistaIncidencies;
    }

    public void setLlistaIncidencies(List l) {
        llistaIncidencies.clear();
        llistaIncidencies.addAll(l);
    }
}
