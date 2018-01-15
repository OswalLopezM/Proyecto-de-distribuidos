/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import ConexionArchivos.HiloRecepcionArchivo;
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
        //System.out.println("EnvioNodo.enviarListaRecursos: empeiza el metodo");
        ArrayList<Recurso> recursosPropios = new DAORecurso().todosLosRecursosPropios();
        ArrayList<OtrosUsuarios> otrosUsuarios = new DAOOtrosUsuarios().todosLosOtrosUsuarios();
            Usuario usuario = new DAOUsuario().devolverUsuarioActivo();
        
        Integer p = 3;
        
        for(Recurso recurso : recursosPropios){
            boolean consiguio = false,soyYo = false;
            OtrosUsuarios otro = null, primerOtroUsuario = null;
            Integer mayorCercano = 0;
            //System.out.println("EnvioNodo.enviarListaRecursos: el recurso tiene hash "+recurso.getHashRecurso()+" "+recurso.getNombreRecurso()+" "+recurso.getIpRecurso()+" "+recurso.getRutaRecurso()+" "+recurso.getRecursoPropio());
            for(OtrosUsuarios otroUsuario : otrosUsuarios){
                if(otro == null){
                    otro  = otroUsuario;
                    
                    primerOtroUsuario = otroUsuario;
                }
                //if(usuario.getHashIp().compareTo(otroUsuario.getHash_ip())  != 0){//si el usuario a quien le voy a enviar es distinto a mi (usuaruio que envia)
              //      System.out.println("EnvioNodo.enviarListaRecursos: Estoy en un usuario con ip "+ otroUsuario.getIp() + " y el hash es "+otroUsuario.getHash_ip());
                //    System.out.println("EnvioNodo.enviarListaRecursos: el hash del recuro es: " + recurso.getHashRecurso());
                    if(recurso.getHashRecurso() <= otroUsuario.getHash_ip()  && //si el hash del recurso es menor que el hash del usuario y el hash del usuario es menor al que ya habia seleccionado anterior mente
                        mayorCercano < otroUsuario.getHash_ip()){
                        //hash recurso: 1650000000 hash mari: 
                        mayorCercano = otroUsuario.getHash_ip();
                  //      System.out.println("EnvioNodo.enviarListaRecursos: consegui al usuario que voy a enviar el recurso");
                        otro = otroUsuario;
                        consiguio = true;
                        if(usuario.getHashIp().compareTo(otroUsuario.getHash_ip())  == 0){
                    //        System.out.println("EnvioNodo.enviarListaRecursos: me toca ami el recurso");
                            soyYo = true;
                        }else{
                      //      System.out.println("EnvioNodo.enviarListaRecursos: no me toca ami el recurso");
                            soyYo = false;
                        }
                        break;
                    }
                //}
            }
            //System.out.println("EnvioNodo.enviarListaRecursos consiguio: "+consiguio+ "\n"+ "y la comparacion de usuario.getHashIp().compareTo(primerOtroUsuario.getHash_ip()) "+usuario.getHashIp().compareTo(primerOtroUsuario.getHash_ip())+"\n"+ "usuario.getHashIp(): "+usuario.getHashIp()+ " primerOtroUsuario.getHash_ip() "+ primerOtroUsuario.getHash_ip());
            //System.out.println("");
            if(!consiguio){
              //  System.out.println("EnvioNodo.enviarListaRecursos el recurso se le va a enviar al primer usuario de la lista");
                otro = primerOtroUsuario;
                consiguio = true;
                if(usuario.getHashIp().compareTo(primerOtroUsuario.getHash_ip())  == 0){
                    soyYo = true;
                }
            }
            //System.out.println("EnvioNodo.enviarListaRecursos: el usuario tiene ip: "+otro.getIp());
            if(!soyYo){
              //  System.out.println("EnvioNodo.enviarListaRecursos voy a enviar el recurso");
                enviar(otro.getIp(),otro.getPuertoTexto(),recurso);
            }
            
        }
        //System.out.println("EnvioNodo.enviarListaRecursos termina el metodo");
    }
    
    
    public void enviar(String ip, Integer puerto,Recurso recurso){
        try {
            Socket clientSocket;
            System.out.println("EnvioNodo.enviar: Voy a enviar el recurso con nombre " + recurso.getNombreRecurso() + " y hash: "+recurso.getHashIpRecurso()+" a ip: "+ip + " puerto: "+ puerto);
            clientSocket = new Socket(ip,puerto);
            ObjectOutputStream envio = new ObjectOutputStream(clientSocket.getOutputStream()); // Envio el dato
            envio.writeObject(recurso);
            envio.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(PeticionCoordinador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscarEnOtroNodo(Integer hashrecurso, Integer miPuertoTexto, Integer miPuertoArchivo,
            String miIp, String tuIp, Integer tuPuertoTexto, Integer tuPuertoArchivo) throws IOException{
        Socket clientSocket;
        System.out.println("Esta es la ip que llega "+tuIp +" Con este puerto: "+tuPuertoTexto);
            clientSocket = new Socket(tuIp,tuPuertoTexto);
        try {
            
            
            String mensaje = "BUSCAR;"+hashrecurso.toString()+";"+miIp+";"+miPuertoTexto+";"+miPuertoArchivo+
                    ";"+tuIp+";"+tuPuertoTexto.toString()+";"+tuPuertoArchivo.toString();
            System.out.println("El mensaje es: "+mensaje);
            ObjectOutputStream envio = new ObjectOutputStream(clientSocket.getOutputStream()); // Envio el dato
            envio.writeObject(mensaje.toString());
           
            envio.close();
            clientSocket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(PeticionCoordinador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void encontreRecurso(String miIp, Integer miPuertoTexto, Integer miPuertoArchivo,
             String ipOrigen, Integer puertoTextoOrigen, Integer puertoArchivoOrigen,String hashDelRecurso){
       try {
            Socket clientSocket;
            clientSocket = new Socket(ipOrigen,puertoTextoOrigen);
            
            String mensaje = "RECURSO;"+miIp+";"+miPuertoTexto+";"+miPuertoArchivo+";"+hashDelRecurso;

            ObjectOutputStream envio = new ObjectOutputStream(clientSocket.getOutputStream()); // Envio el dato
            envio.writeObject(mensaje);
            envio.close();
            clientSocket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(PeticionCoordinador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public void encontreRecurso(String ipOrigen, Integer puertoTextoOrigen, Integer puertoArchivoOrigen,String hashDelRecurso){
       try {
           Usuario u = new DAOUsuario().devolverUsuarioActivo();
            Socket clientSocket;
            clientSocket = new Socket(ipOrigen,puertoTextoOrigen);
            
            String mensaje = "RECURSO;"+u.getIp()+";"+u.getPuertoTexto()+";"+u.getPuertoArchivo()+";"+hashDelRecurso;

            ObjectOutputStream envio = new ObjectOutputStream(clientSocket.getOutputStream()); // Envio el dato
            envio.writeObject(mensaje);
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
   int strHashCode = Math.abs(str.hashCode() % 100);
   return strHashCode;
}
  
  public static void solicitarRecurso(String ipDueno, Integer PuertoTextoDueno, Integer hashRecurso){
      try {
            Socket clientSocket;
            System.out.println("la ip del dueno es "+ipDueno+" Puerto de texto: "+ PuertoTextoDueno+ " y el hash del recurs: "+hashRecurso);
            clientSocket = new Socket(ipDueno,PuertoTextoDueno);
            Usuario u = new DAOUsuario().devolverUsuarioActivo();
            String ip = u.getIp();
            Integer puertoArchivo = u.getPuertoArchivo();
            String mensaje = "DESCARGA;"+ip + ";" + puertoArchivo+";"+hashRecurso;
            System.out.println("voy a enviar "+mensaje);
            ObjectOutputStream envio = new ObjectOutputStream(clientSocket.getOutputStream()); // Envio el dato
            envio.writeObject(mensaje);
            envio.close();
            clientSocket.close();
            new HiloRecepcionArchivo().start();
        } catch (IOException ex) {
            Logger.getLogger(PeticionCoordinador.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
    
  public void enviarMensaje(String ip, Integer puerto,String mensaje){
        try {
            Socket clientSocket;
            //System.out.println("EnvioNodo.enviar: Voy a enviar el recurso con nombre " + recurso.getNombreRecurso() + " y hash: "+recurso.getHashIpRecurso()+" a ip: "+ip + " puerto: "+ puerto);
            clientSocket = new Socket(ip,puerto);
            ObjectOutputStream envio = new ObjectOutputStream(clientSocket.getOutputStream()); // Envio el dato
            envio.writeObject(mensaje);
            envio.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(PeticionCoordinador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
