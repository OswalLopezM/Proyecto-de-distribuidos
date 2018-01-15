/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash;

import java.util.Scanner;

/**
 *
 * @author oswalm
 */
public class Hash {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String entradaTeclado = "";
        while(!entradaTeclado.equals("0")){
            System.out.println("Meta nombre archivo con extension");
            Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
            entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
            System.out.println("el hash es:" + Math.abs(entradaTeclado.hashCode()%100));
        }
    }
    
}
