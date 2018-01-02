package coordinadorpeertopeer;

import Conexion.HiloPrincipalServidor;

public class CoordinadorPeerToPeer {

    public static void main(String[] args) {
        new HiloPrincipalServidor().start();
    }
    
    //acomodar 
    public void ordenar(int LIMITE){
        Integer[] vector =new Integer [3] ;
        int temp =0;
        for (int i=1; i<LIMITE; i++)
            for(int j=0 ; j<LIMITE - 1; j++){
                if (vector[j] > vector[j+1])
                    temp = vector[j];
                vector[j] = vector[j+1];
                vector[j+1] = temp;
            }
    }
}