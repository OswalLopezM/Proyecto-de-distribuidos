/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexionArchivos;

import Conexion.HiloProcesaServidor;
import DAO.DAOUsuario;
import Dominio.Usuario;
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
public class HiloPrincipalArchivo extends Thread{
    
     public void run (){
            
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            Usuario usuario = new DAOUsuario().buscarUsuario(toHash(ip).toString());
            
            int PUERTO = usuario.getPuertoArchivo(); //Puerto para la conexión
            System.out.println("SE INICIA HILO DE ESCUCHA DE ARCHIVO CON PUERTO: "+PUERTO);
            ServerSocket serverSocket = new ServerSocket(PUERTO); //Socket del servidor
            Socket clientSocket; //Socket del cliente
            for (;;)
            {
                clientSocket = serverSocket.accept();
                new HiloEnvioArchivo(clientSocket).start();
            } 
        } catch (IOException ex) {
            Logger.getLogger(HiloPrincipalArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
       
     
    
     }
     
     /**
     * metodo que se encarga de convertir a hash la contrasena
     * @param clave clave a convertir
     * @return la clave convertida
     */
  static Integer toHash(String str){
   int strHashCode = str.hashCode() % 100;
   return strHashCode;
}
}

