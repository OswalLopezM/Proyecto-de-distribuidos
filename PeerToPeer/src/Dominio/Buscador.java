/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import Conexion.EnvioNodo;
import DAO.DAOFinger;
import DAO.DAORecurso;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class Buscador {
    public Integer _hashRecurso; 
    public Buscador(){}
    
    public Buscador(Integer hash_recurso){
        this._hashRecurso = hash_recurso;
    }
    
   /**
    * Este metodo se encarga de revisar si el recurso es de la persona quien busca
    * @return 
    */
    public Boolean miRecurso(){
        Boolean _esMio = false;
        DAORecurso _dao = new DAORecurso();
        for(Recurso r : _dao.todosLosRecursosPropios()){
            if(r.getHashRecurso().equals(_hashRecurso)){
                _esMio = true;
                return _esMio;
            }
        }
     return _esMio;
    }
    
    /**
     * Se encarga de revisar si la persona quien busca conoce quien tiene el recurso
     * @return 
     */
     public String conozcoDireccion(){
        String _conozco = "No";
        for(Recurso r : Recurso.ObtenerTodosLosRecursosConocidos()){
            if((r.getHashRecurso().equals(_hashRecurso)) && (r.getRecursoPropio() == false)){
                _conozco = r.getIpRecurso();
                return _conozco;
            }
        }
     return _conozco;
    }
     
     /**
      * Se encarga de buscar en la tabla de finger el recurso
     * @param miIp
     * @param miPuertoTexto
     * @param miPuertoArchivo
      * @return 
      */
      public String tablaFingerSinSalto(String miIp, Integer miPuertoTexto, Integer miPuertoArchivo){
        String _loTiene = "No";
        DAOFinger _dao = new DAOFinger();
        for(Finger f : _dao.todosLosFinger()){
            if(_hashRecurso<=f.hash_ip){
                _loTiene = f.getIp();
                return _loTiene;
            }
        }     
     return _loTiene;
    }
  
      
    public void tablaFingerConSalto(String miIp, Integer miPuertoTexto, Integer miPuertoArchivo){
        DAOFinger _dao = new DAOFinger();
        for(Finger f : _dao.todosLosFinger()){
          if((f.getPosicion() == 5) && (_hashRecurso>f.hash_ip)){
                EnvioNodo envio = new EnvioNodo();
              try {
                  envio.buscarEnOtroNodo(_hashRecurso, miPuertoTexto, miPuertoArchivo, miIp,f.getIp(),f.getPuertoTexto(),f.getPuertoArchivo());
              } catch (IOException ex) {
                  Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
        }
    }
}
