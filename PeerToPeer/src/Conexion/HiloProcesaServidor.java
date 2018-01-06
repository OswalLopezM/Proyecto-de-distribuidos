/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
            //String recibo = (String) ois.readObject();
            //System.out.println("llego: "+recibo);
            Object recibo = ois.readObject();
            if(recibo instanceof String){
                //logica para cualquier otra cosa.
                System.out.println("llego un string");
            }else if(recibo instanceof ArrayList){
                //logica para actualizar tabla de finger
                System.out.println("llego un array");
            }
            //ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream()); 
            //servidor responde
            //oos.writeObject("");
            clientSocket.close();
            //oos.close();
            ois.close();
      } catch (IOException ex) {
          Logger.getLogger(HiloProcesaServidor.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ClassNotFoundException ex) {    
            Logger.getLogger(HiloProcesaServidor.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
