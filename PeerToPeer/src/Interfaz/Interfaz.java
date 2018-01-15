/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;
/**
 * esta clase tendra metdodos estaticos para mostrar la interfaz por consola
 * @author oswal
 */
public class Interfaz {
    /**
     * muestra la interfaz del menu principal
     */
    public static void menuPrincipal(){
        System.out.println("______            _____    ______");
        System.out.println("| ___ \\          |_   _|   | ___ \\            ");
        System.out.println("| |_/ /__  ___ _ __| | ___ | |_/ /__  ___ _ __ ");
        System.out.println("|  __/ _ \\/ _ \\ '__| |/ _ \\|  __/ _ \\/ _ \\ '__|");
        System.out.println("| | |  __/  __/ |  | | (_) | | |  __/  __/ | ");
        System.out.println("\\_|  \\___|\\___|_|  \\_/\\___/\\_|  \\___|\\___|_| ");
            System.out.println("¿QUE DESEA HACER?");
            System.out.println("5: Registrar usuario");
            System.out.println("6: Salir PeerToPeer");
            System.out.println("7: Salir PeerToPeer");
            System.out.println("0: Salir");

                                               
    }
    /**
     * muestra la interfaz cuando se quiere visualizar como cliente las descargas actuales.
     */
    public static void mostrarDescargasActivasCliente(){
        
    }
    /**
     * muestra la interfaz cuando se quiere visualizar como servidor las descargas actuales.
     */
    public static void mostrarDescargasActivasServidor(){
        
    }
    
    public static void buscar(){
        
        System.out.println("------------------------------------");
        System.out.println("------------------------------------");
        System.out.println("-     1: Buscar                    -");
        System.out.println("-     2: Reportes                  -");
        System.out.println("-  3: Mostrar Recursos conocidos   -");
        System.out.println("-  4: Mostrar Recursos propios     -");
        System.out.println("------------------------------------");
        System.out.println("------------------------------------");
        
    }
}
