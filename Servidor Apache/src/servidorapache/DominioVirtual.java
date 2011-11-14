/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidorapache;

/**
 *
 * @author Ricardo Alvarado
 */
public class DominioVirtual {

    String ruta;
    String pagina;
    String host;
    String path_del_browser;

    public DominioVirtual(String path_b, String ruta_servidor, String host_definido, String pagina_default){
        path_del_browser = path_b;
        ruta = ruta_servidor;
        pagina = pagina_default;
        host = host_definido;
    }

    public String getRuta(){
        return ruta;
    }

    public void setRuta(String ruta_servidor){
        ruta = ruta_servidor;
    }

    public String getPagina(){
        return pagina;
    }

    public void setPagina(String pagina_default){
        pagina = pagina_default;
    }

    public String getHost(){
        return host;
    }

    public void setHost(String host_entrada){
        host = host_entrada;
    }

    public String getPathBrowser(){
        return path_del_browser;
    }

    public void setPathBrowser(String path_browser){
        path_del_browser = path_browser;
    }
}
