/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bd;

import java.util.List;
import empresa.Empresa;
import empresa.Incidencia;



/**
 * Interface que permet la persistencia dels objectes de manera independent de 
 * la resta de l'aplicacio.
 * 
 * @author professor
 */

public interface GestorPersistencia {
    /**
    * Assigna els valors de configuracio de la connexio a partir
    * d'un sistema d'inicialitzacio concretat a cada instancia
    * d'aquesta interficie.
     * @throws UtilitatPersistenciaException
    */
    void iniciar () throws UtilitatPersistenciaException;

    /**
     * Estableix la connexio amb l'SGBD
     * @throws UtilitatPersistenciaException si es produexi ha algun error en 
     * establir la connexio.
     */
    
    void obrir() throws UtilitatPersistenciaException;
    
    /**
     * Tanca la connexio amb l'SGBD
     */    
    
    void tancar(); 

    /**
     * Insereix una incidencia a la base de dades amb una referencia que no te cap altre 
     * incidencia a la base de dades.
     * @param incidencia incidencia que s'ha d'inserir
     * @throws UtilitatPersistenciaException si ja hi ha un altre incidencia a la 
     * base de dades amb la mateixa referencia o si es produeix un error a la base
     * de dades.
     */

    void inserir(Incidencia incidencia) throws UtilitatPersistenciaException;
    
   
    /**
     * Crea un objecte de tipus IncidenciaEstandar amb una referencia determinada, pero no l'insereix
     * a la base de dades.  La resta de dades prenen la seva inicialitzacio per 
     * defecte.
     * @param referencia referencia que s'assigna la la incidencia creada
     * @return referencia al incidencia creat
     */    
    
    Incidencia novaIncidenciaEstandarTemporal(String referencia) throws UtilitatPersistenciaException;


    /**
     * Crea un objecte de tipus IncidenciaUrgent amb una referencia determinada, pero no l'insereix
     * a la base de dades.  La resta de dades prenen la seva inicialitzacio per 
     * defecte.
     * @param referencia referencia que s'assigna l' incidencia creada
     * @return referencia al incidencia creat
     */    
    
    Incidencia novaIncidenciaUrgentTemporal(String referencia) throws UtilitatPersistenciaException;
  
    

    /**
     * Modifica la incidencia referenciada pel parametre. Ha de pertanyer al context
     * persistencia.
     *
     * @param incidencia incidencia a modificar
     * @throws UtilitatPersistenciaException si no existeix l'objecte 
     * al context de persistencia o si es produeix un error a
     * l'SGBD.
     */
    void modificar(Incidencia incidencia) throws UtilitatPersistenciaException;

    /**
     * Eliminar la incidencia referenciada pel parametre.
     * @param incidencia incidencia a eliminar de la base de dades
     * @throws UtilitatPersistenciaException si no existeix l'objecte 
     * al context de persistencia o si es produeix un error a
     * l'SGBD.
     */
    
    void eliminar(Incidencia incidencia) throws UtilitatPersistenciaException;


   /**
     * Obte una instancia persistent (emmagatzemada a la base de dades)  
     * de la incidencia, identificada per la referencia que es passa per parametre.
     * @param referencia que identifica la incidencia que es desitja recuperar
     * @return Instancia de l'entitat recuperada amb les dades emmagatzemades.
     * @throws UtilitatPersistenciaException si no existeix cap instancia amb 
     * la referencia assenyalada pel parametre o si es produeix un error al SGBD.
     */
        
    Incidencia obtenirIncidencia (String referencia) throws UtilitatPersistenciaException;

    
    /**
     * Obte la llista de totes les incidencies de la base de dades ordenades per la seva referencia
     * @return llista de totes les incidencies de la base de dades ordenades per la seva referencia
     * @throws UtilitatPersistenciaException si es produeix un error a l'SGBD.
     */    
    List <Incidencia> obtenirIncidenciesOrdenadesPerReferencia() throws UtilitatPersistenciaException;

    
    
    /**
     * Obte la llista de totes les incidencies d'una empresa
     * @param nomEmpresa nom de l'empresa on s'han produit les incidencies seleccionada per la llista desplegable.
     * @return llista de incidencies seleccionades
     * @throws UtilitatPersistenciaException si es produeix un error a l'SGBD.
     */    
    List <Incidencia> obtenirIncidenciesPerEmpresa(String nomEmpresa) throws UtilitatPersistenciaException;

    /**
     * Modifica el cost de les incidencies de l'empresa indicada pel primer parametre segons la formula
     * cost = cost * (1+percentatge/100).
     * @param nomEmpresa nomEmpresa que identifica l'empresa on s'han produit les incidencies
     * @param percentatge tant per cent que cal modificar el cost
     * @trhows UtilitatPersistenciaException si es produeix un error a l'SGBD
     *
     */    
    void modificarCostResolucioIncidencia (String nomEmpresa, float percentatge) throws UtilitatPersistenciaException;

 
    
    /**
     * Insereix una empresa a la base de dades amb un codi que no te cap altre 
     * empresa a la base de dades.
     * @param empresa empresa
     * @throws UtilitatPersistenciaException si ja hi ha una altra empresa a la 
     * base de dades amb el mateix nom o si es produeix un error a la base
     * de dades.
     */

    void inserir(Empresa empresa) throws UtilitatPersistenciaException;
    
   
    /**
     * Crea un objecte de tipus Empresa amb un codi determinat, pero no l'insereix
     * a la base de dades.  La resta de dades prenen la seva inicialitzacio per 
     * defecte.
     * @param codi codi que s'assigna a l' empresa creada
     * @return referencia a l' empresa creada
     * @throws bd.UtilitatPersistenciaException
     */    
    
    Empresa nouEmpresaTemporal(String codi) throws UtilitatPersistenciaException;

    
    /**
     * Modifica l' empresa referenciada pel parametre. Ha de pertanyer al context
     * persistencia.
     *
     * @param empresa empresa a modificar
     * @throws UtilitatPersistenciaException si no existeix l'objecte 
     * al context de persistencia o si es produeix un error a
     * l'SGBD.
     */
    void modificar(Empresa empresa) throws UtilitatPersistenciaException;

    

     /**
     * Elimina de la base de dades l' empresa que es passa com a parametre.
     * @param empresa empresa a eliminar 
     * @throws UtilitatPersistenciaException si no existeix l'objecte 
     * al context de persistencia o si es produeix un error a
     * l'SGBD. Tambe llenca una excepcio si l' empresa te alguna incidencia
     */
    
    void eliminar(Empresa empresa) throws UtilitatPersistenciaException;


   /**
     * Obte una instancia persistent (emmagatzemada a la base de dades)  
     * de l' empresa, identificada amb el codi que es passa per parametre.
     * 
     * @param codi codi que identifica l' empresa que es desitja recuperar
     * @return Instancia de l'entitat recuperada amb les dades emmagatzemades.
     * @throws UtilitatPersistenciaException si no existeix cap instancia amb 
     * el codi assenyalat pel parametre o si es produeix un error al SGBD.
     */
        
    Empresa obtenirEmpresa (String codi) throws UtilitatPersistenciaException;

   /**
     * Obte la llista de totes les empreses de la base de dades ordenades pel seu codi 
     * @return llista de totes les empreses de la base de dades ordenades pel seu codi
     * @throws UtilitatPersistenciaException si es produeix un error a l'SGBD.
     */    
    List <Empresa> obtenirEmpresasOrdenatsPerNomEmpresa() throws UtilitatPersistenciaException;
    
    /**
     * Grava a la base de dades els canvis produits als objectes del context de persistencia
     * i que son pendents de gravar.
     * @throws UtilitatPersistenciaException per indicar un error al SGBD
     */
    
    void gravaCanvis() throws UtilitatPersistenciaException;

    /**
     * Fa que no es gravin a la base de dades els canvis produits als objectes del context de
     * persistencia que estaven pendents de gravar. Despres d'executar-se no es considera que 
     * hi hagi cap canvi pendent de gravar.
     * @throws UtilitatPersistenciaException per indicar un error al SGBD
     */
    void anullaCanvis() throws UtilitatPersistenciaException;
    
    
}
