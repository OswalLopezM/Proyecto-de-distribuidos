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
        ArrayList<Recurso> recursosPropios = new DAORecurso().todosLosRecursosPropios();
        ArrayList<OtrosUsuarios> otrosUsuarios = new DAOOtrosUsuarios().todosLosOtrosUsuarios();
            Usuario usuario = new DAOUsuario().devolverUsuarioActivo();
        Integer mayorCercano = 0;
        OtrosUsuarios otro = null, primerOtroUsuario = null;
        boolean consiguio = false;
        for(Recurso recurso : recursosPropios){
            for(OtrosUsuarios otroUsuario : otrosUsuarios){
                if(otro == null){
                    otro  = otroUsuario;
                    primerOtroUsuario = otroUsuario;
                }
                System.out.println("EnvioNodo.enviarListaRecursos: la comparacion de que si son diferentes es: "+ (usuario.getHashIp() == otroUsuario.getHash_ip()));
                System.out.println("EnvioNodo.enviarListaRecursos: Porque usuario.getHashIp() = " + usuario.getHashIp() + " y otroUsuario.getHash_ip() = "+ otroUsuario.getHash_ip());
                if(usuario.getHashIp().equals(otroUsuario.getHash_ip()) ){//si el usuario a quien le voy a enviar es distinto a mi (usuaruio que envia)
                    System.out.println("entro en el if");
                    if(recurso.getHashRecurso() < otroUsuario.getHash_ip()  && //si el hash del recurso es menor que el hash del usuario y el hash del usuario es menor al que ya habia seleccionado anterior mente
                        mayorCercano > otroUsuario.getHash_ip()){
                        mayorCercano = otroUsuario.getHash_ip();
                        otro = otroUsuario;
                        consiguio = true;
                    }
                }
            }
            if(!consiguio){
                otro = primerOtroUsuario;
            }
            enviar(otro.getIp(),otro.getPuertoTexto(),recurso);
        }
    }
    
    
    public void enviar(String ip, Integer puerto,Recurso recurso){
        try {
            Socket clientSocket;
            clientSocket = new Socket(ip,puerto);
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
    private Integer toHash(String ip){
        Integer hash = 512;
        hash =  37*hash + ip.hashCode();
        return hash;
    }
    
    
}
