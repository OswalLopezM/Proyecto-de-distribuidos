package Dominio;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *clase que controlara el status de las desargas
 * 
 * @author oswal
 */
public class Status {
    
    protected String nombre;
    protected int statusActual;
    protected String tipo;

    public Status(String nombre,String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
        
    }
    /**
     *  metodo que se encarga de eliminar el archivo el cual almacerana el status 
     * de la descarga actual
     */
    public void eliminarArchivo(){
        File f=null;
        String sfichero= "status//"+this.tipo+"//status"+this.statusActual+".txt";
        f= new File(sfichero);
        f.delete();
    }
    /**
     *  metodo que se encarga de actualizar el archivo el cual almacerana el status 
     * de la descarga actual
     * @param acumulado 
     */
    public void actualizarArchivo(Integer acumulado){
        try {
            File f=null;
            String sfichero= "status//"+this.tipo+"//status"+this.statusActual+".txt";
            f= new File(sfichero);
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(String.valueOf(this.nombre+" - "+acumulado ));
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Status.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
    /**
     * metodo que se encarga de crear el archivo el cual almacerana el status 
     * de la descarga actual
     */
    public void crearArchvio(){
        try {
            int i=0;
            File f=null;
            String sfichero="";
            while(i <=10000){
                sfichero ="status//"+this.tipo+"//status"+i+".txt";
                f= new File(sfichero);
                if(!f.exists()){
                   this.statusActual = i;
                   BufferedWriter bw = new BufferedWriter(new FileWriter(f));

                   bw.close();
                   i=100000;
               }else{
                     i++;
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Status.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * metodo que se encarga de devolver los status de todos los archivos descargandose actual mente
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public String devolverStatus(){
        
            int i=0;
        File f=null;
        String sfichero ="";
        String texto= "";
        BufferedReader br= null;
        try {
            while (i<=1000){
                sfichero ="status//"+this.tipo+"//status"+i+".txt";
                f= new File(sfichero);
                if(f.exists()){

                        br= new BufferedReader(new FileReader(sfichero));

                    texto=texto+br.readLine()+"\n";
                    br.close();
                }
                i++;
            }
        } catch (FileNotFoundException ex) {
                    Logger.getLogger(Status.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
            Logger.getLogger(Status.class.getName()).log(Level.SEVERE, null, ex);
        }
        return texto;
    }
}
