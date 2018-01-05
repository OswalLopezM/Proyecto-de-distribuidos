package Conexion;

import Registro.Registro;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author oswal
 */
public class PeticionCoordinador {
    
    
    public Object startClient(String dato)  //Método para iniciar el cliente
    {
    try {
        
        ObjectOutputStream envio;
        Socket cs;
        cs = new Socket("localhost",Registro.PUERTO_CONEXION_COORDINADOR);
        envio = new ObjectOutputStream(cs.getOutputStream()); // Envio el dato
        envio.writeObject(dato);
        
        
        //ObjectInputStream ois = new ObjectInputStream(cs.getInputStream());//Recibo el dato
        //Object recibo = ois.readObject();
       // String recibotext = (String) recibo;
      //  System.out.println("llego al cliente: "+recibotext);
        
        cs.close();
        
        return null;
        
    } catch (IOException ex) {
        //Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("El servidor esta desconectado");
    }catch(Exception e){
        System.out.println("El servidor esta desconectado");
    }
        return "error";


    }
}
