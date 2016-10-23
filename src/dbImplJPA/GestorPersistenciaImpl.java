/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbImplJPA;

import bd.GestorPersistencia;
import bd.UtilitatPersistenciaException;
import dbImplJPA.empresaImpl.*;
import empresa.Empresa;
import empresa.Incidencia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author professor
 */
public class GestorPersistenciaImpl implements GestorPersistencia {

    private final String uPersistencia;
    private final String CLAU_INNEXISTENT = "Clau inexistent";
    private static final String MISSATGE_ERROR = "S'ha produït un error: ";

    EntityManagerFactory emf;
    EntityManager em;

    public GestorPersistenciaImpl(String uPersistencia) {
        this.uPersistencia = uPersistencia;
    }

    /* Mètodes privats per ajudar a la implementació */
    private void iniciaTransaccio() {
        em.getTransaction().begin();
    }

    private void tractaExcepcio(Exception e) throws UtilitatPersistenciaException {
        throw new UtilitatPersistenciaException(MISSATGE_ERROR + e.getMessage());
    }

    private void fesAlta(Object o) throws UtilitatPersistenciaException {
        try {
            em.persist(o);
            em.flush();
        } catch (Exception e) {
            tractaExcepcio(e);
        }
    }

    private void fesModificacio(Object o) throws UtilitatPersistenciaException {
        try {
            em.merge(o);
            em.flush();
        } catch (Exception e) {
            tractaExcepcio(e);
        }
    }

    private void fesEsborrat(Object o) throws UtilitatPersistenciaException {
        try {
            em.remove(o);
            em.flush();
        } catch (Exception e) {
            tractaExcepcio(e);
        }
    }

    private Object fesConsulta(Class classe, Object clau) throws UtilitatPersistenciaException {
        Object o = null;

        try {
            o = em.find(classe, clau);

            if (o == null) {
                throw new UtilitatPersistenciaException(CLAU_INNEXISTENT);
            }
        } catch (Exception e) {
            tractaExcepcio(e);
        }

        return o;
    }

    /* Implementació de la interfície */
    @Override
    public void iniciar() throws UtilitatPersistenciaException {
    }

    @Override
    public void obrir() throws UtilitatPersistenciaException {
        try {
            emf = Persistence.createEntityManagerFactory(uPersistencia);
            em = emf.createEntityManager();
            iniciaTransaccio();
        } catch (Exception ex) {
            tractaExcepcio(ex);
        }
    }

    @Override
    public void tancar() {
        try {
            em.close();
            emf.close();
        } catch (Exception ex) {
        }
    }

    @Override
    public void inserir(Incidencia incidencia) throws UtilitatPersistenciaException {
        fesAlta(incidencia);
    }

    @Override
    public Incidencia novaIncidenciaEstandarTemporal(String referencia) throws UtilitatPersistenciaException {
        IncidenciaEstadarImpl incidencia = new IncidenciaEstadarImpl();
        incidencia.setReferencia(referencia);
        return incidencia;
    }

    @Override
    public Incidencia novaIncidenciaUrgentTemporal(String referencia) throws UtilitatPersistenciaException {
        IncidenciaUrgentImpl incidencia = new IncidenciaUrgentImpl();
        incidencia.setReferencia(referencia);
        return incidencia;
    }

    @Override
    public void modificar(Incidencia incidencia) throws UtilitatPersistenciaException {
        obtenirIncidencia(incidencia.getReferencia());  // verifiquem que existeix
        fesModificacio(incidencia);
    }

    @Override
    public void eliminar(Incidencia incidencia) throws UtilitatPersistenciaException {
        fesEsborrat(obtenirIncidencia(incidencia.getReferencia()));
    }

    @Override
    public Incidencia obtenirIncidencia(String referencia) throws UtilitatPersistenciaException {
        return (IncidenciaImpl) fesConsulta(IncidenciaImpl.class, referencia);
    }

    @Override
    public List<Incidencia> obtenirIncidenciesOrdenadesPerReferencia() throws UtilitatPersistenciaException {
        List<Incidencia> incidencies = null;
        Query query;

        try {
            query = em.createNamedQuery("IncidenciaImpl.obtenirIncidenciesOrdenadesPerReferencia");
            incidencies = query.getResultList();
        } catch (Exception ex) {
            tractaExcepcio(ex);
        }

        return incidencies;
    }

    @Override
    public List<Incidencia> obtenirIncidenciesPerEmpresa(String nomEmpresa) throws UtilitatPersistenciaException {
        List<Incidencia> incidencies = null;
        Query query;

        try {
            query = em.createNamedQuery("IncidenciaImpl.obtenirIncidenciesPerEmpresa");
            query.setParameter("nomEmpresa", nomEmpresa);
            incidencies = query.getResultList();
        } catch (Exception ex) {
            tractaExcepcio(ex);
        }

        return incidencies;
    }

    @Override
    public void modificarCostResolucioIncidencia(String nomEmpresa, float percentatge) throws UtilitatPersistenciaException {
        String actualitzacio;
        Query qry;

        try {

            actualitzacio = "UPDATE IncidenciaImpl i SET i.cost = i.cost * (1 + :perc /100) WHERE i.empresa.nomEmpresa= :nomEmpresa";
            qry = em.createQuery(actualitzacio);

            if (percentatge < -100) {
                percentatge = -100;
            }

            qry.setParameter("perc", percentatge);
            qry.setParameter("nomEmpresa", nomEmpresa);

            qry.executeUpdate();

            em.flush();

        } catch (Exception e) {
            tractaExcepcio(e);
        }
    }

    @Override
    public void inserir(Empresa empresa) throws UtilitatPersistenciaException {
        fesAlta(empresa);
    }

    @Override
    public Empresa nouEmpresaTemporal(String codi) throws UtilitatPersistenciaException {
        EmpresaImpl empresa = new EmpresaImpl();
        empresa.setCodi(codi);
        return empresa;
    }

    @Override
    public void modificar(Empresa empresa) throws UtilitatPersistenciaException {
        obtenirIncidencia(empresa.getCodi());  // verifiquem que existeix
        fesModificacio(empresa);
    }

    @Override
    public void eliminar(Empresa empresa) throws UtilitatPersistenciaException {
        fesEsborrat(obtenirEmpresa(empresa.getCodi()));
    }

    @Override
    public Empresa obtenirEmpresa(String codi) throws UtilitatPersistenciaException {
        return (EmpresaImpl) fesConsulta(EmpresaImpl.class, codi);
    }

    @Override
    public List<Empresa> obtenirEmpresasOrdenatsPerNomEmpresa() throws UtilitatPersistenciaException {
        List<Empresa> empreses = null;
        Query query;

        try {
            query = em.createNamedQuery("EmpresaImpl.obtenirEmpresesOrdenadesPerNomEmpresa");
            empreses = query.getResultList();
        } catch (Exception ex) {
            tractaExcepcio(ex);
        }

        return empreses;
    }

    @Override
    public void gravaCanvis() throws UtilitatPersistenciaException {
        try {
            em.getTransaction().commit();
            iniciaTransaccio();
        } catch (Exception e) {
            tractaExcepcio(e);
        }
    }

    @Override
    public void anullaCanvis() throws UtilitatPersistenciaException {
        try {
            em.getTransaction().rollback();
            iniciaTransaccio();
        } catch (Exception e) {
            tractaExcepcio(e);
        }
    }
}
