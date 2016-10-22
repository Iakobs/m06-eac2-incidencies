/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbImplDB4O.empresaImpl;

import com.db4o.ObjectContainer;
import com.db4o.ext.ExtObjectContainer;
import empresa.Empresa;
import empresa.Incidencia;
import java.util.List;

/**
 *
 * @author Sergio
 */
public class EmpresaImpl implements Empresa{
    
    private String codi;
    private String nomEmpresa;
    private String seuEmpresa;
    private transient ExtObjectContainer db;

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
            Incidencia in = new IncidenciaImpl();
            Empresa em = new EmpresaImpl();
            em.setCodi(getCodi());
            in.setEmpresa(em);
            return db.queryByExample(in);
    }
    
    public void objectOnActivate(ObjectContainer db){
        
        this.db=db.ext();

    }
    
    public void objectOnNew(ObjectContainer db){
        
        this.db=db.ext();

    }
    
}

