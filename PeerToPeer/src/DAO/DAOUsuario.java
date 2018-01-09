package DAO;



import Dominio.Usuario;

import java.io.FileOutputStream;

import java.io.IOException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class DAOUsuario {

    private Element root;

    private String fileLocation = "src//XML//Usuario.xml";

    

    public DAOUsuario() {

        try {
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = null;
            doc = builder.build(fileLocation);
            root = doc.getRootElement();
        } catch (JDOMException ex) {
            System.out.println("No se pudo iniciar la operacion por: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("No se pudo iniciar la operacion por: " + ex.getMessage());
        }
    }

    

    private Element UsuariotoXmlElement(Usuario nUsuario ) {

        Element Usuariotrans = new Element("Usuario");
        Element hashIp = new Element("hashIp");
        hashIp.setText(nUsuario.getHashIp().toString());
        Element ip = new Element("ip");
        ip.setText((nUsuario.getIp()));
        Element puertoTexto = new Element("puertoTexto");
        puertoTexto.setText((nUsuario.getPuertoTexto()).toString());
        Element puertoArchivo = new Element("puertoArchivo");
        puertoArchivo.setText((nUsuario.getPuertoArchivo()).toString());
        
        
        Usuariotrans.addContent(hashIp);
        Usuariotrans.addContent(ip);
        Usuariotrans.addContent(puertoTexto);
        Usuariotrans.addContent(puertoArchivo);
        return Usuariotrans;

    }

    

    private Usuario UsuarioToObject(Element element) throws ParseException {
        Usuario nUsuario = new Usuario (element.getChildText("ip"),
                Integer.parseInt(element.getChildText("puertoArchivo")),
                Integer.parseInt(element.getChildText("puertoTexto")));
        return nUsuario;

    }

    

    public boolean agregarUsuario(Usuario nUsuario) {
        boolean resultado = false;
        root.addContent(UsuariotoXmlElement((Usuario) nUsuario));
        resultado = updateDocument();
        return resultado;

    }
    
    public Usuario registrarUsuario(String ip) {
        Usuario usuario = new Usuario(ip,obtenerPuertoArchvioDisponible(),
                obtenerPuertoTextoDisponible());
        root.addContent(UsuariotoXmlElement((Usuario) usuario));
        updateDocument();
        ordenarLista();
        return usuario;
    }

    

    private boolean updateDocument() {

        try {
            XMLOutputter out = new XMLOutputter(org.jdom.output.Format.getPrettyFormat());
            FileOutputStream file = new FileOutputStream(fileLocation);
            out.output(root, file);
            file.flush();
            file.close();
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }

    }

    

    public static Element buscar(List raiz, String dato) {
        Iterator i = raiz.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            if (dato.equals(e.getChild("hashIp").getText())) {
                return e;
            }
        }
        return null;

    }

 

    

    public boolean actualizarUsuario(Usuario nUsuario,String hashIp) {
        boolean resultado = false;
        Element aux = new Element("Usuario");
        List Usuario = this.root.getChildren("Usuario");
        while (aux != null) {
            aux = DAOUsuario.buscar(Usuario,hashIp);
            if (aux != null) {
                Usuario.remove(aux);
                resultado = updateDocument();
            }
        }
        agregarUsuario(nUsuario);
        return resultado;
    } 

    

    public Usuario buscarUsuario(String usuario) {

        Element aux = new Element("Usuario");

        List Usuario= this.root.getChildren("Usuario");

        while (aux != null) {

            aux = DAOUsuario.buscar(Usuario,usuario);

            if (aux != null) {

                try {

                    return UsuarioToObject(aux);

                } catch (ParseException ex) {

                    System.out.println(ex.getMessage());

                }

            }

        }

        return null;

    }
    
    

   public Usuario devolverUsuarioActivo() {
        Usuario resultado = new Usuario();
        for (Object it : root.getChildren()) {
            Element xmlElem = (Element) it;
            try {
                resultado = (UsuarioToObject(xmlElem));
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;

    } 

    

    

    public ArrayList<Usuario> todosLosUsuarios() {
        ArrayList<Usuario> resultado = new ArrayList<Usuario>();
        for (Object it : root.getChildren()) {
            Element xmlElem = (Element) it;
            try {
                resultado.add(UsuarioToObject(xmlElem));
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;

    } 

    

    

    public void ordenarLista(){
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        lista = todosLosUsuarios();
        Usuario arreglo[] = new Usuario[lista.size()];
        arreglo = lista.toArray(arreglo);
        System.out.println("El arreglo tiene longitud: "+ arreglo.length);
        eliminarUsuarios();
        for(int i = 0; i < arreglo.length - 1; i++){
            for(int j = 0; j < arreglo.length - 1; j++){
                if (arreglo[j].getHashIp() < (arreglo[j + 1].getHashIp()))
                {
                    System.out.println("enre en el if con arreglo[j]"+arreglo[j].getHashIp()+" arreglo[j + 1]"+arreglo[j + 1].getHashIp());
                    Usuario tmp = arreglo[j+1];
                    arreglo[j+1] = arreglo[j];
                    arreglo[j] = tmp;
                }
            }
        }
        for(int i = arreglo.length-1;i >= 0; i--){
            System.out.println(i+" "+arreglo[i].getHashIp());
            agregarUsuario(arreglo[i]);
        }

    }

    

    public boolean borrarUsuario(String hashIp) {
        boolean resultado = false;
        Element aux = new Element("Usuario");
        List Usuario = this.root.getChildren("Usuario");
        while (aux != null) {
            aux = DAOUsuario.buscar(Usuario,hashIp);
            if (aux != null) {
                Usuario.remove(aux);
                resultado = updateDocument();
            }
        }
        return resultado;

    }

    

    public void eliminarUsuarios(){
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        lista = todosLosUsuarios();
        for (Usuario s:lista){
            borrarUsuario(s.getHashIp().toString());
        }

    }

    public Integer obtenerPuertoTextoDisponible(){
        Integer puerto =5001;
        ArrayList<Usuario> lista = todosLosUsuarios();
        for (Usuario usuario : lista){
            if(usuario.getPuertoTexto() > puerto){
                puerto = usuario.getPuertoTexto();
            }
        }
        puerto++;
        return puerto;
    }
    
    public Integer obtenerPuertoArchvioDisponible(){
        Integer puerto =6001;
        ArrayList<Usuario> lista = todosLosUsuarios();
        for (Usuario usuario : lista){
            if(usuario.getPuertoArchivo() > puerto){
                puerto = usuario.getPuertoArchivo();
            }
        }
        puerto++;
        return puerto;
    }
}

