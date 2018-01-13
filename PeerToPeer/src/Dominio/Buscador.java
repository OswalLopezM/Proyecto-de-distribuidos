/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

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
    
     public String conozcoDireccion(){
        String _conozco = "No conozco IP";
        DAORecurso _dao = new DAORecurso();
        for(Recurso r : _dao.todosLosRecursos()){
            if((r.getHashRecurso().equals(_hashRecurso)) && (r.getRecursoPropio() == false)){
                _conozco = r.getIpRecurso();
                return _conozco;
            }
        }
     return _conozco;
    }
}