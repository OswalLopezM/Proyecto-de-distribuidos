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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oswal
 */
public class HiloProcesaServidor extends Thread {
    
    
    Socket clientSocket;
    
    public HiloProcesaServidor(Socket clientSocket){
      this.clientSocket = clientSocket; 
    }
    
    public void run(){
       try {    
            System.out.println("SE INICIA PROCESO DE RECIBO");
            //servidor recibe
            
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            String recibo = (String) ois.readObject();
            //servidor envia
            ObjectOutputStream envio;
            envio = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.println("llego: "+recibo);
            if(recibo.contains("REGISTRO")){
                Usuario usuario = new DAOUsuario().registrarUsuario(recibo.split(";")[1]);
                envio.writeObject(usuario.getPuertoArchivo()+";"+usuario.getPuertoTexto());
            }
            if(recibo.contains("SALIR")){
                //LOGICA PARA ELIMINAR EL USUARIO
                new DAOUsuario().salirDePeerToPeer(recibo.split(";")[1]);
            }
            
            Envio.enviarListaATodos();
            
            //ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream()); 
            clientSocket.close();
            envio.close();
            ois.close();
      } catch (IOException ex) {
          Logger.getLogger(HiloProcesaServidor.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ClassNotFoundException ex) {    
            Logger.getLogger(HiloProcesaServidor.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
