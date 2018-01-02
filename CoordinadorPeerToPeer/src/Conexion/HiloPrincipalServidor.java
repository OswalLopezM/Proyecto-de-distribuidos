/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Registro.Registro;
import java.io.IOException;
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
            System.out.println("SE INICIA HILO DE ESCUCHA");
            int PUERTO = Registro.PUERTO_CONEXION_COORDINADOR; //Puerto para la conexión
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
}
