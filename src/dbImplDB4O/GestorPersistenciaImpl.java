/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbImplDB4O;

import bd.GestorPersistencia;
import bd.UtilitatPersistenciaException;
import com.db4o.Db4oEmbedded;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.ExtObjectContainer;
import com.db4o.query.Predicate;

import empresa.Empresa;
import empresa.Incidencia;
import dbImplDB4O.empresaImpl.IncidenciaImpl;
import dbImplDB4O.empresaImpl.IncidenciaEstandarImpl;
import dbImplDB4O.empresaImpl.IncidenciaUrgentImpl;
import dbImplDB4O.empresaImpl.EmpresaImpl;
import java.util.Comparator;
import java.util.List;


/**
 *
 * @author professor
 */
public class GestorPersistenciaImpl implements GestorPersistencia {

    private final String DB4OFILE="incidencies.db4o";
    private ExtObjectContainer db;
    private final String CLAU_DUPLICADA="Clau duplicada";
    private final String OBJECTE_NO_PERSISTENT="Objecte no persistent";
    private final String ERROR_INESPERAT="Error inesperat";

    
    
    @Override
    public void iniciar() throws UtilitatPersistenciaException {

    }

    @Override
    public void obrir() throws UtilitatPersistenciaException {

        try{
            EmbeddedConfiguration configuracio = Db4oEmbedded.newConfiguration();
            configuracio.common().updateDepth(2);

            db = Db4oEmbedded.openFile(configuracio,DB4OFILE ).ext(); 
        }catch(RuntimeException e){
            propagaExcepcio(e);
        }

    }

    @Override
    public void tancar() {
        try{
            db.close();
        }catch(Exception e){
            // no fem res
        }
    }

    

    @Override
    public void gravaCanvis() throws UtilitatPersistenciaException {
        try{
            db.commit();
            db.purge();
        }catch(RuntimeException e){
            propagaExcepcio(e);
        }
    }

    @Override
    public void anullaCanvis() throws UtilitatPersistenciaException {
        try{
            db.rollback();
            db.purge();
        }catch(RuntimeException e){
            propagaExcepcio(e);
        }

    }

    private void propagaExcepcio(Exception e) throws UtilitatPersistenciaException {
        throw new UtilitatPersistenciaException(ERROR_INESPERAT + ": "+ e.getMessage());
    }

    @Override
    public void inserir(Incidencia incidencia) throws UtilitatPersistenciaException {
        try{
            try{
                this.obtenirIncidencia(incidencia.getReferencia());
            }catch(UtilitatPersistenciaException e){  // si l'incidencia no es troba a la BD cal donar-la d'alta
                if(e.getMessage().equals(OBJECTE_NO_PERSISTENT)){
                    db.store(incidencia);
                    return;
                }
            }
            throw new UtilitatPersistenciaException(CLAU_DUPLICADA);
            
        }catch(RuntimeException e){
            propagaExcepcio(e);
        }
    }

    @Override
    public Incidencia novaIncidenciaEstandarTemporal(String referencia) throws UtilitatPersistenciaException {
        Incidencia incidencia = new IncidenciaEstandarImpl();
        incidencia.setReferencia(referencia);
        return incidencia;
    }

    @Override
    public Incidencia novaIncidenciaUrgentTemporal(String referencia) throws UtilitatPersistenciaException {
        Incidencia incidencia = new IncidenciaUrgentImpl();
        incidencia.setReferencia(referencia);
        return incidencia;
    }

    @Override
    public void modificar(Incidencia incidencia) throws UtilitatPersistenciaException {
        try{
            if(!db.isStored(incidencia)){
                throw new UtilitatPersistenciaException(OBJECTE_NO_PERSISTENT);
            }
            db.store(incidencia);
        }catch(RuntimeException e){
            propagaExcepcio(e);
        }      
    }

    @Override
    public void eliminar(Incidencia incidencia) throws UtilitatPersistenciaException {
        try{
            if(!db.isStored(incidencia)){
                 throw new UtilitatPersistenciaException(OBJECTE_NO_PERSISTENT);
            }
            db.delete(incidencia);
        }catch(RuntimeException e){
            propagaExcepcio(e);
        }         
    }

    @Override
    public Incidencia obtenirIncidencia(String referencia) throws UtilitatPersistenciaException {
        try{
            Incidencia incidencia = new IncidenciaImpl();
            incidencia.setReferencia(referencia);
            
            List<Incidencia> in1;

            in1 = db.queryByExample(incidencia);

            if(in1.isEmpty()){
                 throw new UtilitatPersistenciaException(OBJECTE_NO_PERSISTENT);
            }
            else if (in1.size()>1){
                 throw new UtilitatPersistenciaException(ERROR_INESPERAT);
            }
            else{
                Incidencia aux = in1.get(0);

                return aux;
            }
        }catch(RuntimeException e){
            propagaExcepcio(e);
            return null; // no s'executara mai; nomes es perque compili
        }            
    }

    @Override
    public List<Incidencia> obtenirIncidenciesOrdenadesPerReferencia() throws UtilitatPersistenciaException {
        
        
        try{
            return 
                db.query(
                    new Predicate<Incidencia>(){

                        @Override
                        public boolean match(Incidencia m) {
                            return true;
                        }
                    },
                    new Comparator<Incidencia>(){

                        @Override
                        public int compare(Incidencia m1, Incidencia m2) {
                            return m1.getReferencia().compareTo(m2.getReferencia());
                        }
                    }
            );
            
        }catch(RuntimeException e){
            propagaExcepcio(e);
            return null; // inaccessible; es per poder compilar
        }
    }

    @Override
    public List<Incidencia> obtenirIncidenciesPerEmpresa(String nomEmpresa) throws UtilitatPersistenciaException {
        try{
            
            Incidencia pattern=new IncidenciaImpl();
            Empresa auxEmpresa = new EmpresaImpl();

            auxEmpresa.setNomEmpresa(nomEmpresa);

            pattern.setEmpresa(auxEmpresa);

            return (db.queryByExample(pattern));
            
        }catch(RuntimeException e){
            propagaExcepcio(e);
            return null; // inaccessible; nomes per compilar
        }
    }

    @Override
    public void modificarCostResolucioIncidencia(String nomEmpresa, float percentatge) throws UtilitatPersistenciaException {
        try{
            Empresa auxEmpresa=obtenirEmpresa(nomEmpresa);

            for(Incidencia m:auxEmpresa.getLlistaIncidencies()){
                float auxCost = m.getCost();

                m.setCost(auxCost*(1+percentatge/100));

                modificar(m);
            }
        }catch(RuntimeException e){
            propagaExcepcio(e);
        }
    }

    @Override
    public void inserir(Empresa empresa) throws UtilitatPersistenciaException {
        try{
            try{
                this.obtenirEmpresa(empresa.getCodi());
            }catch(UtilitatPersistenciaException e){  // si l'empresa no es troba a la BD cal donar-la d'alta
                if(e.getMessage().equals(OBJECTE_NO_PERSISTENT)){
                    db.store(empresa);
                    return;
                }
            }
            throw new UtilitatPersistenciaException(CLAU_DUPLICADA);
            
        }catch(RuntimeException e){
            propagaExcepcio(e);
        }
    }

    @Override
    public Empresa nouEmpresaTemporal(String codi) throws UtilitatPersistenciaException {
        Empresa empresa = new EmpresaImpl();
        empresa.setCodi(codi);
        return empresa;
    }

    @Override
    public void modificar(Empresa empresa) throws UtilitatPersistenciaException {
         try{
            if(!db.isStored(empresa)){
                throw new UtilitatPersistenciaException(OBJECTE_NO_PERSISTENT);
            }
            db.store(empresa);
        }catch(RuntimeException e){
            propagaExcepcio(e);
        }      
    }

    @Override
    public void eliminar(Empresa empresa) throws UtilitatPersistenciaException {
        try{
            if(!db.isStored(empresa)){
                 throw new UtilitatPersistenciaException(OBJECTE_NO_PERSISTENT);
            }
            db.delete(empresa);
        }catch(RuntimeException e){
            propagaExcepcio(e);
        }       
    }

    @Override
    public Empresa obtenirEmpresa(String codi) throws UtilitatPersistenciaException {
        try{
            Empresa empresa = new EmpresaImpl();
            empresa.setCodi(codi);
            
            List<Empresa> emp1;

            emp1 = db.queryByExample(empresa);

            if(emp1.isEmpty()){
                 throw new UtilitatPersistenciaException(OBJECTE_NO_PERSISTENT);
            }
            else if (emp1.size()>1){
                 throw new UtilitatPersistenciaException(ERROR_INESPERAT);
            }
            else{
                Empresa aux = emp1.get(0);

                return aux;
            }
        }catch(RuntimeException e){
            propagaExcepcio(e);
            return null; // no s'executara mai; nomes es perque compili
        }      
    }

    @Override
    public List<Empresa> obtenirEmpresasOrdenatsPerNomEmpresa() throws UtilitatPersistenciaException {
        
        
        try{
            return 
                db.query(
                    new Predicate<Empresa>(){

                        @Override
                        public boolean match(Empresa m) {
                            return true;
                        }
                    }
                    ,
                    new Comparator<Empresa>(){

                        @Override
                        public int compare(Empresa m1, Empresa m2) {
                            return m1.getNomEmpresa().compareTo(m2.getNomEmpresa());
                        }
                    }
            );
        }catch(RuntimeException e){
            propagaExcepcio(e);
            return null; // inaccessible; nomes per compilar
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
