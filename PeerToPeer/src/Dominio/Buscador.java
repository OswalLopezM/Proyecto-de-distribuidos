/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import Conexion.EnvioNodo;
import DAO.DAOFinger;
import DAO.DAORecurso;
import java.util.ArrayList;

/**
 *
 * @author maria
 */
public class Buscador {
    Integer _hashRecurso; 
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
        DAORecurso _dao = new DAORecurso();
        for(Recurso r : _dao.todosLosRecursos()){
            if((r.getHashRecurso().equals(_hashRecurso)) && (r.getRecursoPropio() == false)){
                _conozco = r.getIpRecurso();
                return _conozco;
            }
        }
     return _conozco;
    }
     
     /**
      * Se encarga de buscar en la tabla de finger el recurso
      * @return 
      */
      public String tablaFinger(){
        String _loTiene = "No";
        Usuario user = new Usuario();
        DAOFinger _dao = new DAOFinger();
        for(Finger f : _dao.todosLosFinger()){
            if((_hashRecurso<f.hash_ip) || (_hashRecurso == f.hash_ip)){
                _loTiene = f.getIp();
                return _loTiene;
            }else if((f.getPosicion() == 5) && (_hashRecurso>f.hash_ip)){
                EnvioNodo envio = new EnvioNodo();
                envio.buscarEnOtroNodo(_hashRecurso, user.getPuertoTexto(), user.getPuertoArchivo(), user.getIp(),f.getIp());
            }
        }
     return _loTiene;
    }
}
