/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import DAO.DAOUsuario;
import Dominio.Usuario;
import Registro.Registro;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oswal
 */
public class HiloPrincipalServidor extends Thread{
    
     public void run (){
            
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            Usuario usuario = new DAOUsuario().buscarUsuario(toHash(ip).toString());
            
            int PUERTO = usuario.getPuertoTexto(); //Puerto para la conexión
            System.out.println("SE INICIA HILO DE ESCUCHA DE TEXTO CON PUERTO: "+PUERTO);
            ServerSocket serverSocket = new ServerSocket(PUERTO); //Socket del servidor
            Socket clientSocket; //Socket del cliente
            for (;;)
            {
                clientSocket = serverSocket.accept();
                new HiloProcesaServidor(clientSocket).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(HiloPrincipalServidor.class.getName()).log(Level.SEVERE, null, ex);
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
}
