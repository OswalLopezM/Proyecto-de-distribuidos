package Conexion;

import Dominio.Usuario;
import Registro.Registro;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oswal
 */
public class PeticionCoordinador {
    
    
    public Usuario AgregarPeerToPeer(String dato)  //Método para iniciar el cliente
    {
        try {
            Socket clientSocket;
            clientSocket = new Socket(Registro.IP_CONEXION,Registro.PUERTO_CONEXION_COORDINADOR);
            ObjectOutputStream envio = new ObjectOutputStream(clientSocket.getOutputStream()); // Envio el dato
            envio.writeObject(dato);


            ObjectInputStream recibo = new ObjectInputStream(clientSocket.getInputStream());//Recibo el dato
            String texto = (String)  recibo.readObject();
            System.out.println("llego al cliente: "+texto);

            envio.close();
            recibo.close();
            clientSocket.close();
            Usuario usuario = new Usuario(dato.split(";")[1],Integer.parseInt(texto.split(";")[0]),Integer.parseInt(texto.split(";")[1]));
            return usuario;

        } catch (IOException ex) {
            //Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("El servidor esta desconectado");
        }catch(Exception e){
            System.out.println("El servidor esta desconectado");
        }
        return null;
    }
    
    public void salirPeerToPeer(String dato){
        
        try {
            Socket clientSocket;
            clientSocket = new Socket("localhost",Registro.PUERTO_CONEXION_COORDINADOR);
            ObjectOutputStream envio = new ObjectOutputStream(clientSocket.getOutputStream()); // Envio el dato
            envio.writeObject(dato);
            envio.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(PeticionCoordinador.class.getName()).log(Level.SEVERE, null, ex);
        }
            

    }
}
