package Dominio;

/**
 *
 * @author oswal
 */
public class Usuario {
    protected String ip;
    protected String hashIp;
    protected Integer puertoArchivo;
    protected Integer puertoTexto;


    public Usuario(String ip, Integer puertoArchivo, Integer puertoTexto) {
        this.ip = ip;
        this.hashIp = toHash(ip).toString();
        this.puertoArchivo = puertoArchivo;
        this.puertoTexto = puertoTexto;
    }

    public Integer getPuertoArchivo() {
        return puertoArchivo;
    }

    public void setPuertoArchivo(Integer puertoArchivo) {
        this.puertoArchivo = puertoArchivo;
    }

    public Integer getPuertoTexto() {
        return puertoTexto;
    }

    public void setPuertoTexto(Integer puertoTexto) {
        this.puertoTexto = puertoTexto;
    }
    
    

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHashIp() {
        return hashIp;
    }

    public void setHashIp(String hashIp) {
        this.hashIp = hashIp;
    }
    
/**
     * metodo que se encarga de convertir a hash la contrasena
     * @param clave clave a convertir
     * @return la clave convertida
     */
 static Integer toHash(String str){
   int hash = 2;
   int strHashCode = Math.abs(29*hash + str.hashCode() % 100);
   return strHashCode;
}
}
