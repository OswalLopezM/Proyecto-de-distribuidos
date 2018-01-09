package Dominio;

/**
 *
 * @author oswal
 */
public class Usuario {
    protected String ip;
    protected Integer hashIp;
    protected Integer puertoArchivo;
    protected Integer puertoTexto;

    public Usuario(String ip, Integer puertoArchivo, Integer puertoTexto) {
        this.ip = ip;
        this.hashIp = toHash(ip);
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

    public Integer getHashIp() {
        return hashIp;
    }

    public void setHashIp(Integer hashIp) {
        this.hashIp = hashIp;
    }
    
/**
     * metodo que se encarga de convertir a hash la contrasena
     * @param clave clave a convertir
     * @return la clave convertida
     */
    private Integer toHash(String ip){
        Integer hash = 512;
        hash =  37*hash + ip.hashCode();
        return hash;
    }
}
