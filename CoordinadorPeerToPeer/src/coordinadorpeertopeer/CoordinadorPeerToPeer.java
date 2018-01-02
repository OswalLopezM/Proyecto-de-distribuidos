package coordinadorpeertopeer;

import Conexion.HiloPrincipalServidor;
import DAO.DAOUsuario;

public class CoordinadorPeerToPeer {

    public static void main(String[] args) {
        new DAOUsuario().ordenarLista();
        //new HiloPrincipalServidor().start();
    }
    
}