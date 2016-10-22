/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package empresa;

import java.util.List;

/**
 *
 * @author professor
 */
public interface  Empresa {

   /**
     *
     * @return
     */
    public String getCodi();

    /**
     *
     * @param codi
     */
    public void setCodi(String codi);

    /**
     *
     * @return
     */
    public String getNomEmpresa();

    /**
     *
     * @param nomEmpresa
     */
    public void setNomEmpresa(String nomEmpresa);

    /**
     *
     * @return
     */
    public String getSeuEmpresa();

    /**
     *
     * @param seuEmpresa
     */
    public void setSeuEmpresa(String seuEmpresa);
    
    /**
     *
     * @return
     */
    public List<Incidencia> getLlistaIncidencies();
    
}
