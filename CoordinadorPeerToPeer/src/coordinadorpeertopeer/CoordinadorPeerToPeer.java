package coordinadorpeertopeer;

import Conexion.Envio;
import Conexion.HiloPrincipalServidor;
import DAO.DAOUsuario;
import Dominio.Usuario;
import java.util.Scanner;

public class CoordinadorPeerToPeer {

    public static void main(String[] args) {
        new DAOUsuario().eliminarUsuarios();
        //new DAOUsuario().ordenarLista();
        new HiloPrincipalServidor().start();
        String entradaTeclado="";
        while(!entradaTeclado.equals("0")){
            System.out.println("¿QUE DESEA HACER?");
            System.out.println("1: Mostrar anillo");
            System.err.println("0: Salir");
            Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
            entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
            if(entradaTeclado.equals("1")){
                System.out.println("Los datos de los usuarios del anillo son:");
                for(Usuario u : new DAOUsuario().todosLosUsuarios()){
                    System.out.println("Ip: "+ u.getIp());
                    System.out.println("Hash IP:"+u.getIp());
                    System.out.println("Puerto de texto: "+u.getPuertoTexto());
                    System.out.println("Puerto de archivo: "+u.getPuertoArchivo());
                }
            }
        }

        //Envio.enviarListaATodos();
    }
    
}