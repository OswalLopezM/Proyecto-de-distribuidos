/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import DAO.DAOOtrosUsuarios;
import DAO.DAORecurso;
import DAO.DAOUsuario;
import Dominio.OtrosUsuarios;
import Dominio.Recurso;
import Dominio.Usuario;
import Registro.Registro;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oswal
 */
public class EnvioNodo {
    
    public void enviarListaRecursos() {
        System.out.println("EnvioNodo.enviarListaRecursos: empeiza el metodo");
        ArrayList<Recurso> recursosPropios = new DAORecurso().todosLosRecursosPropios();
        ArrayList<OtrosUsuarios> otrosUsuarios = new DAOOtrosUsuarios().todosLosOtrosUsuarios();
            Usuario usuario = new DAOUsuario().devolverUsuarioActivo();
        Integer mayorCercano = 0;
        Integer p = 3;
        
        for(Recurso recurso : recursosPropios){
            boolean consiguio = false,soyYo = false;
            OtrosUsuarios otro = null, primerOtroUsuario = null;
            System.out.println("EnvioNodo.enviarListaRecursos: el recurso tiene hash "+recurso.getHashRecurso()+" "+recurso.getNombreRecurso()+" "+recurso.getIpRecurso()+" "+recurso.getRutaRecurso()+" "+recurso.getRecursoPropio());
            for(OtrosUsuarios otroUsuario : otrosUsuarios){
                if(otro == null){
                    otro  = otroUsuario;
                    
                    primerOtroUsuario = otroUsuario;
                }
                //if(usuario.getHashIp().compareTo(otroUsuario.getHash_ip())  != 0){//si el usuario a quien le voy a enviar es distinto a mi (usuaruio que envia)
                    System.out.println("EnvioNodo.enviarListaRecursos: Estoy en un usuario con ip "+ otroUsuario.getIp() + " y el hash es "+otroUsuario.getHash_ip());
                    if(recurso.getHashRecurso() < otroUsuario.getHash_ip()  && //si el hash del recurso es menor que el hash del usuario y el hash del usuario es menor al que ya habia seleccionado anterior mente
                        mayorCercano < otroUsuario.getHash_ip()){
                        //hash recurso: 1650000000 hash mari: 
                        mayorCercano = otroUsuario.getHash_ip();
                        System.out.println("EnvioNodo.enviarListaRecursos: consegui al usuario que voy a enviar el recurso");
                        otro = otroUsuario;
                        consiguio = true;
                        if(usuario.getHashIp().compareTo(otroUsuario.getHash_ip())  == 0){
                            System.out.println("EnvioNodo.enviarListaRecursos: me toca ami el recurso");
                            soyYo = true;
                        }else{
                            System.out.println("EnvioNodo.enviarListaRecursos: no me toca ami el recurso");
                            soyYo = false;
                        }
                        break;
                    }
                //}
            }
            System.out.println("EnvioNodo.enviarListaRecursos consiguio: "+consiguio+ "\n"+ "y la comparacion de usuario.getHashIp().compareTo(primerOtroUsuario.getHash_ip()) "+usuario.getHashIp().compareTo(primerOtroUsuario.getHash_ip())+"\n"+ "usuario.getHashIp(): "+usuario.getHashIp()+ " primerOtroUsuario.getHash_ip() "+ primerOtroUsuario.getHash_ip());
            System.out.println("");
            if(!consiguio){
                System.out.println("EnvioNodo.enviarListaRecursos el recurso se le va a enviar al primer usuario de la lista");
                otro = primerOtroUsuario;
                consiguio = true;
                if(usuario.getHashIp().compareTo(primerOtroUsuario.getHash_ip())  == 0){
                    soyYo = true;
                }
            }
            System.out.println("EnvioNodo.enviarListaRecursos: el usuario tiene ip: "+otro.getIp());
            if(!soyYo){
                System.out.println("EnvioNodo.enviarListaRecursos voy a enviar el recurso");
                enviar(otro.getIp(),otro.getPuertoTexto(),recurso);
            }
            
        }
        System.out.println("EnvioNodo.enviarListaRecursos termina el metodo");
    }
    
    
    public void enviar(String ip, Integer puerto,Recurso recurso){
        try {
            Socket clientSocket;
            clientSocket = new Socket(ip,puerto);
            System.out.println("Voy a enviar a ip: "+ip + " puerto: "+ puerto);
            ObjectOutputStream envio = new ObjectOutputStream(clientSocket.getOutputStream()); // Envio el dato
            envio.writeObject(recurso);
            envio.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(PeticionCoordinador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     /**
     * metodo que se encarga de convertir a hash la contrasena
     * @param clave clave a convertir
     * @return la clave convertida
     */
 static Integer toHash(String str){
   int hash = 2;
   int strHashCode = Math.abs(29*hash + str.hashCode() % 100);
   return strHashCode;
}
    
    
}
