/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import DAO.DAORecurso;
import Dominio.Recurso;
import java.util.ArrayList;

/**
 *
 * @author oswal
 */
public class EnvioNodo {
    
    public void enviarListaRecursos(ArrayList<String> nodos){
        ArrayList<Recurso> recursosPropios = new DAORecurso().todosLosRecursos();
        Integer mayorCercano = 0;
        String ipCercano = "";
        Integer puertoCercano =0;
        for(Recurso recurso : recursosPropios){
            for(String nodo : nodos){
                if(recurso.getHashRecurso() < Integer.parseInt(nodo.split(";")[0])   && 
                        mayorCercano > Integer.parseInt(nodo.split(";")[0])   ){
                    mayorCercano = Integer.parseInt(nodo.split(";")[0]);
                    ipCercano = nodo.split(";")[0];
                    puertoCercano = Integer.parseInt(nodo.split(";")[0]);

                }
            }
        }
    }
}
