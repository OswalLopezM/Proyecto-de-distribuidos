package coordinadorpeertopeer;

import Conexion.Envio;
import Conexion.HiloPrincipalServidor;
import DAO.DAOUsuario;
import java.util.Scanner;

public class CoordinadorPeerToPeer {

    public static void main(String[] args) {
        new DAOUsuario().eliminarUsuarios();
        //new DAOUsuario().ordenarLista();
        new HiloPrincipalServidor().start();
        String entradaTeclado="";
        while(!entradaTeclado.equals("0")){
            System.out.println("¿QUE DESEA HACER?");
            System.out.println("5: Enviar lista a usuarios");
            System.out.println("6: Enviar texto simple");
            System.err.println("0: Salir");
            Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
            entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
            if(entradaTeclado.equals("5")){
                Envio.enviarListaATodos();
            }else if(entradaTeclado.equals("6")){
                Envio.enviarTexto();
                
            }
        }

        //Envio.enviarListaATodos();
    }
    
}