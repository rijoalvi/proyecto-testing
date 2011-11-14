/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorapache;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.util.Date;
import java.text.*;
import java.util.*;

/**
 *
 * @author Ricardo Alvarado
 */
public class Servidor extends Thread {

    ///Variables Globales:
    private int puerto; // el puerto por el que va a estar trabajando el servidor
    private PantallaPrincipal interfaz; // La interfaz desde la que se llamo al metodo
    private boolean activo; // es una senal de si el servidor esta activado o si esta desactivado, para terminar el proceso del hilo.
    private DominioVirtual[] dominios_virtuales; // un arreglo que contiene todos los dominios virtuales del servidor;
    int cantidad_dominios; // La cantidad de dominios virtuales que tiene el servidor
    private String path_log; //la direccion donde se ubica el log
    private int tam_max_log; //el tamano maximo del log
    private String path_configuracion; //el lugar donde se encuentra ubicado el archivo XML de configuracion
    Bitacora bitacora; //la bitacora para los datos

    public Servidor(PantallaPrincipal p, String path_archivo_xml) {
        activo = true;
        interfaz = p;
        path_configuracion = path_archivo_xml;
        this.cargar_configuracion_XML();
        bitacora = new Bitacora(path_log, tam_max_log, interfaz);
        this.start(); // La idea es que el servidor corra en un hilo aparte, para que no se pegue la interfaz.
    }

    //Aqui es donde va a correr el servidor. Se usa el metodo run() dado que es un hilo aparte.
    public void run() {
        ServerSocket socketServidor = null;
        interfaz.desplegar("Se intentara conectar al puerto " + puerto);
        try {
            socketServidor = new ServerSocket(puerto);
            interfaz.desplegar("\nListo");
        } //En caso de error:
        catch (Exception e) {
            interfaz.desplegar("\nError al conectar al puerto " + puerto + ". Descripcion:\n" + e.getMessage() + "\n");
            return;
        }

        //Cuando se llega aqui, estamos seguros de que el socket esta conectado.
        while (activo) {
            try {

                //Se acepta la conexion entrante
                Socket conexion = socketServidor.accept();

                //Averiguamos la Direccion IP del Cliente para mostrarla en la interfaz:
                InetAddress direccionIP = conexion.getInetAddress();
                interfaz.desplegar("\nSe conecto el cliente: " + direccionIP.getHostName() + "\n");

                //Se lee la peticion del cliente a un buffer.
                BufferedReader peticion = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

                //Se debe prepara un flujo de salida para enviar la respuesta al cliente.
                DataOutputStream respuesta = new DataOutputStream(conexion.getOutputStream());

                //Este es el metodo que realiza el trabajo "pesado" del servidor. Es el que analiza las peticiones y da respuesta a cada una
                manejar_peticion(peticion, respuesta);
            } //En caso de error:
            catch (Exception e) {
                interfaz.desplegar("\nError al manejar la peticion: " + e.getMessage() + "\n");
            }
        }

        //Cuando se desactiva el servidor se debe cerrar el socket:
        try {
            socketServidor.close();
        } //En caso de error:
        catch (Exception e) {
            return;
        }
    }

    ///Metodo que realiza el manejo de las peticiones HTTP:
    private void manejar_peticion(BufferedReader entrada, DataOutputStream salida) {
        int metodo = 0; //Aqui sabremos si la peticion es un get, head o post o si es invalida
        String valor_cabecera = ""; //La cabecera del archivo (la primera linea del archivo)
        String nombre_archivo_solicitado = ""; //El nombre del archivo que el cliente pide
        long tamano_archivo_solicitado = 0; //El tamano del archivo solicitado por el cliente
        DominioVirtual dominio = null;

        try {

            //Leemos la primera linea de la entrada:
            valor_cabecera = entrada.readLine();

            nombre_archivo_solicitado = new String(valor_cabecera);

            //Para no tener problemas de mayusculas y minusculas
            valor_cabecera.toUpperCase();

            //En caso de que sea un GET
            if (valor_cabecera.startsWith("GET")) {
                metodo = 1;
            }

            //En caso de que sea un HEAD
            if (valor_cabecera.startsWith("HEAD")) {
                metodo = 2;
            }

            //En caso de que sea un POST
            if (valor_cabecera.startsWith("POST")) {
                metodo = 3;
            }

            //Si la peticion no es ninguna de las anteriores, entonces no es soportada:
            if (metodo == 0) {
                try {
                    salida.writeBytes(construir_cabecera_http(501, 0, 0, null));
                    interfaz.desplegar("\nError 501: La peticion no es soportada\n");
                    this.escribir_a_bitacora(metodo, "", "", "501");


                    //Hecho esto, se cierra el flujo:
                    salida.close();
                    return;
                } //En caso de error:
                catch (Exception e3) {
                    interfaz.desplegar("\nError al notificar al cliente el error: " + e3.getMessage() + "\n");
                }
            }

            //Obtenemos un string que contenga el archivo que solicita el cliente.
            //Dado que la primera linea es por ejemplo GET /index.html HTTP/1.1, lo que hacemos es obtener un
            //substring que contenga solo la direccion.
            int inicio_archivo = 0;
            int fin_archivo = 0;
            inicio_archivo = (valor_cabecera.indexOf("/"));
            nombre_archivo_solicitado = valor_cabecera.substring(inicio_archivo);
            fin_archivo = nombre_archivo_solicitado.indexOf(' ');

            //se guarda el nombre completo del archivo solicitado:
            nombre_archivo_solicitado = valor_cabecera.substring(inicio_archivo, (fin_archivo + inicio_archivo));

            //Obtenemos la instancia del dominio que estamos utilizando:
            dominio = this.obtener_dominio(nombre_archivo_solicitado);
            if (dominio == null) {
                try {
                    salida.writeBytes(construir_cabecera_http(403, 0, 0, null));
                    interfaz.desplegar("\nError 403: Se trato de acceder un espacio prohibido\n");
                    this.escribir_a_bitacora(metodo, nombre_archivo_solicitado, "", "403");

                    //Hecho esto, se cierra el flujo:
                    salida.close();
                    return;
                } //En caso de error:
                catch (Exception e3) {
                    interfaz.desplegar("\nError al notificar al cliente el error 403: " + e3.getMessage() + "\n");
                    return;
                }
            }
        } //En caso de error:
        catch (Exception e) {
            interfaz.desplegar("error al obtener el archivo solicitado " + e.getMessage() + "\n");
        }

        //En caso de que se este pidiendo la pagina default del servidor:
        if (nombre_archivo_solicitado.endsWith("/")) {
            nombre_archivo_solicitado = nombre_archivo_solicitado + dominio.getPagina();
        }
        nombre_archivo_solicitado = nombre_archivo_solicitado.substring(1);

        int aux = nombre_archivo_solicitado.indexOf("/");
        if (aux != -1) {
            String tmp = nombre_archivo_solicitado.substring(0, aux);
            boolean bandera = false;
            while ((!bandera) && (dominio.getPathBrowser().contains(tmp))) {
                nombre_archivo_solicitado = nombre_archivo_solicitado.substring(aux + 1);
                if (nombre_archivo_solicitado.contains("/")) {
                    aux = nombre_archivo_solicitado.indexOf("/");
                    tmp = nombre_archivo_solicitado.substring(0, aux);
                } else {
                    bandera = true;
                }


            }
        }

        //Se notifica a la interfaz
        interfaz.desplegar("\nSe pidio el archivo:  " + nombre_archivo_solicitado + " del servidor\n");


        //Se inicializa el tipo de archivo solicitado asumiendo que si no es ninguno de los siguiente, entonces es html
        int tipo_archivo_solicitado = 11;

        //Se busca que tipo de archivo es el solicitado:
        if (nombre_archivo_solicitado.endsWith(".html")) {
            tipo_archivo_solicitado = 0;
        }
        if (nombre_archivo_solicitado.endsWith(".xml")) {
            tipo_archivo_solicitado = 1;
        }
        if (nombre_archivo_solicitado.endsWith(".doc")) {
            tipo_archivo_solicitado = 2;
        }
        if (nombre_archivo_solicitado.endsWith(".xls")) {
            tipo_archivo_solicitado = 3;
        }
        if (nombre_archivo_solicitado.endsWith(".ppt")) {
            tipo_archivo_solicitado = 4;
        }
        if (nombre_archivo_solicitado.endsWith(".jpg") || nombre_archivo_solicitado.endsWith(".jpeg")) {
            tipo_archivo_solicitado = 5;
        }
        if (nombre_archivo_solicitado.endsWith(".gif")) {
            tipo_archivo_solicitado = 6;
        }
        if (nombre_archivo_solicitado.endsWith(".png")) {
            tipo_archivo_solicitado = 7;
        }
        if (nombre_archivo_solicitado.endsWith(".bmp")) {
            tipo_archivo_solicitado = 8;
        }
        if (nombre_archivo_solicitado.endsWith(".css")) {
            tipo_archivo_solicitado = 9;
        }
        if (nombre_archivo_solicitado.endsWith(".js")) {
            tipo_archivo_solicitado = 10;
        }

        if (tipo_archivo_solicitado > 10) {
            try {

                //Si tuve un error debido a que el tipo de archivo no es soportado, entonces le notifico al cliente mediante un error 406
                //(Archivo no valido)
                salida.writeBytes(construir_cabecera_http(406, 0, 0, null));
                interfaz.desplegar("Error 406: Tipo de Archivo no valido\n");
                this.escribir_a_bitacora(metodo, nombre_archivo_solicitado, "", "406");


                //Hecho esto, se cierra el flujo:
                salida.close();
                return;
            } //En caso de error:
            catch (Exception e) {
                interfaz.desplegar("No se pudo notificar el Error 406 al cliente: " + e.getMessage() + "\n");
            }
            return;
        }


        FileInputStream archivo_solicitado = null;

        try {
            //Se abre el archivo que solicito el cliente:

            archivo_solicitado = new FileInputStream(dominio.getRuta() + nombre_archivo_solicitado);

            //se obtiene el tamano del archivo solicitado por el cliente:
            File archivo = new File(dominio.getRuta() + nombre_archivo_solicitado);
            tamano_archivo_solicitado = archivo.length();

        } //En caso de error:
        catch (Exception e) {

            try {

                //Si tuve un error debido a que no pude abrir el archivo, entonces le notifico al cliente mediante un error 404
                //(Archivo no encontrado)
                salida.writeBytes(construir_cabecera_http(404, 0, 0, null));

                //Hecho esto, se cierra el flujo:
                salida.close();
            } //En caso de error:
            catch (Exception e2) {
                interfaz.desplegar("No se pudo notificar el Error 404 al cliente: " + e.getMessage() + "\n");
            }
            //En caso de error:
            interfaz.desplegar("Error 404: No se pudo encontrar el archivo " + e.getMessage() + "\n");
            this.escribir_a_bitacora(metodo, nombre_archivo_solicitado, "", "404");
        }


        /*******************************************Seccion de manejo de tipos de archivos***************************************/
        try {
            //Si se llega aqui es porque la peticion fue exitosa por lo que se escribe 200 en la cabecera
            salida.writeBytes(construir_cabecera_http(200, tipo_archivo_solicitado, tamano_archivo_solicitado, dominio));
            if (metodo == 2) {
                this.escribir_a_bitacora(metodo, nombre_archivo_solicitado, "", "200");

            }

            //En caso de POST:
            if (metodo == 3) {
                    boolean bandera_datos_post = false;
                    String datos_post = "";
                    String lin;
                    try {
                        while (entrada.ready()) {
                            lin = entrada.readLine();
                            if(lin.contains("Content-Length")){
                                int l =1;
                            }
                            if (lin.equalsIgnoreCase("\r\n")) {
                                bandera_datos_post = true;
                            }
                            if (bandera_datos_post) {
                                datos_post = datos_post + lin + " & ";
                            }
                        }

                    } catch (Exception e) {
                    }
                    this.escribir_a_bitacora(metodo, nombre_archivo_solicitado, datos_post, "200");
                }

            //En caso de que sea un GET, se le agrega el archivo solicitado
            if ((metodo == 1) || (metodo == 3)) {
                int b = archivo_solicitado.read();
                while (b != -1) {
                    //Aqui se agrega el archivo al Buffer de salida (respuesta)
                    salida.write(b);
                    b = archivo_solicitado.read();
                }
                if (metodo == 1) {
                    this.escribir_a_bitacora(metodo, nombre_archivo_solicitado, "", "200");
                }
                
            }
            salida.close();
            archivo_solicitado.close();
        } //En caso de error:
        catch (Exception e) {
            interfaz.desplegar("Error al enviar los datos al cliente: " + e.getMessage() + "\n");
        }
    }

    //Aqui es donde se contruye la cabecera para las peticiones http:
    private String construir_cabecera_http(int codigo_cabecera, int tipo_archivo, long tamano_archivo, DominioVirtual dominio_usado) {

        //La cabecera del archivo:
        String cabecera = "HTTP/1.1 ";

        switch (codigo_cabecera) {
            case 200:
                cabecera = cabecera + "200 OK";
                break;
            case 400:
                cabecera = cabecera + "400 Bad Request";
                break;
            case 403:
                cabecera = cabecera + "403 Forbidden";
                break;
            case 404:
                cabecera = cabecera + "404 Not Found";
                break;
            case 406:
                cabecera = cabecera + "406 Not Acceptable";
                break;
            case 501:
                cabecera = cabecera + "501 Not Implemented";
                break;
        }

        cabecera = cabecera + "\r\n"; //Aqui agregamos un poco de informacion a la cabecera
        //cabecera = cabecera + "Date: close\r\n"; //Se cierra la conexion

        /*NOTA: ESTO ES COPIADO DE LA W3.ORG:
         *The order in which header fields with differing field names are received is not significant.
         *However, it is "good practice" to send general-header fields first, followed by request-header or response- header fields,
         *and ending with the entity-header fields.
         *
         * ESTO QUIERE DECIR QUE NO HAY UN ORDEN ESPECIFICO PARA LOS ENCABEZADOS, AUNQUE SI UNO RECOMENDADO, QUE ES EL QUE USAREMOS EN ESTE SERVIDOR.
         * 1. CABECERAS GENERALES
         * 2. CABECERAS DE RESPUESTA
         * 3. CABECERAS DE ENTIDADES
         */


        //Aqui obtenemos la fecha del servidor
        Date fecha = new Date();
        DateFormat formato = DateFormat.getInstance();
        formato.setTimeZone(TimeZone.getTimeZone("GMT"));
        String fecha2 = formato.format(fecha);
        Date gmt = null;
        try {
            gmt = formato.parse(fecha2);
        } catch (Exception e) {
        }

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss.SSS");
        cabecera = cabecera + "Date: " + sdf.format(gmt) + "\r\n"; //La fecha del servidor
        cabecera = cabecera + "Accept: text/xml,application/msword,application/vnd.ms-excel,application/vnd.ms-powerpoint,image/jpeg,image/gif,image/png,image/bmp,text/css,application/javascript,text/html\r\n"; //Se cierra la conexion
        cabecera = cabecera + "Content-Length: " + tamano_archivo + "\r\n"; //El Tamano del archivo en el servidor

        //Aqui es donde se coloca el tipo de archivo MIME:
        switch (tipo_archivo) {
            case 0:
                cabecera = cabecera + "Content-Type: text/html\r\n";
                break;
            case 1:
                cabecera = cabecera + "Content-Type: text/xml\r\n";
                break;
            case 2:
                cabecera = cabecera + "Content-Type: application/msword\r\n";
                break;
            case 3:
                cabecera = cabecera + "Content-Type: application/vnd.ms-excel\r\n";
                break;
            case 4:
                cabecera = cabecera + "Content-Type: application/vnd.ms-powerpoint\r\n";
                break;
            case 5:
                cabecera = cabecera + "Content-Type: image/jpeg\r\n";
                break;
            case 6:
                cabecera = cabecera + "Content-Type: image/gif\r\n";
                break;
            case 7:
                cabecera = cabecera + "Content-Type: image/png\r\n";
                break;
            case 8:
                cabecera = cabecera + "Content-Type: image/bmp\r\n";
                break;
            case 9:
                cabecera = cabecera + "Content-Type: text/css\r\n";
                break;
            case 10:
                cabecera = cabecera + "Content-Type: application/javascript\r\n";
                break;
            default:
                cabecera = cabecera + "Content-Type: text/html\r\n";
                break;
        }

        cabecera = cabecera + "Server: servidor simple por Ricardo Alvarado v0\r\n"; //El nombre del servidor
        if (dominio_usado != null) {
            cabecera = cabecera + dominio_usado.getHost() + "\r\n";
        }
        //Aqui finalizamos el encabezado:
        cabecera = cabecera + "\r\n";

        return cabecera;
    }

    private void cargar_configuracion_XML() {
        int contador_dominios = 0;

        try {
            FileInputStream lector = new FileInputStream(path_configuracion);
            DataInputStream flujo_entrada = new DataInputStream(lector);
            BufferedReader entrada;
            entrada = new BufferedReader(new InputStreamReader(flujo_entrada));
            String linea;
            while (entrada.ready()) {
                linea = entrada.readLine();
                if (linea.contains("<VirtualHost")) {
                    ++contador_dominios;
                }

            }
            entrada.close();
            cantidad_dominios = contador_dominios;
            dominios_virtuales = new DominioVirtual[cantidad_dominios];


            FileInputStream lector2 = new FileInputStream(path_configuracion);
            DataInputStream flujo_entrada2 = new DataInputStream(lector2);
            BufferedReader entrada2;
            entrada2 = new BufferedReader(new InputStreamReader(flujo_entrada2));
            linea = "";
            int inicio;
            int fin;
            String path_browser;
            String path;
            String host;
            String default_pag;
            int contador = 0;
            while (entrada2.ready()) {
                linea = entrada2.readLine();
                if (linea.contains("<Port>")) {
                    linea = entrada2.readLine();
                    String num_puerto = linea.substring(linea.indexOf(" ") + 1);
                    try {
                        puerto = Integer.parseInt(num_puerto);
                    } catch (Exception e) {
                        interfaz.desplegar("Error en el archivo de configuracion del puerto");
                        return;
                    }
                }


                if (linea.contains("<VirtualHost")) {
                    inicio = (linea.indexOf(" ") + 1);
                    fin = (linea.lastIndexOf("/") + 1);
                    path_browser = linea.substring(inicio, fin);
                    linea = entrada2.readLine();
                    inicio = (linea.indexOf(" ") + 1);
                    path = linea.substring(inicio);
                    linea = entrada2.readLine();
                    inicio = (linea.indexOf(" ") + 1);
                    host = linea.substring(inicio);
                    linea = entrada2.readLine();
                    inicio = (linea.indexOf(" ") + 1);
                    default_pag = linea.substring(inicio);
                    dominios_virtuales[contador] = new DominioVirtual(path_browser, path, host, default_pag);
                    ++contador;
                }

                if (linea.contains("<Log>")) {
                    linea = entrada2.readLine();
                    String pathLog = linea.substring(linea.indexOf(" ") + 1);
                    path_log = pathLog;
                    linea = entrada2.readLine();
                    String maxLog = linea.substring(linea.indexOf(" ") + 1);
                    try {
                        tam_max_log = Integer.parseInt(maxLog);
                    } catch (Exception e) {
                        interfaz.desplegar("Error en el archivo de configuracion de la bitacora");
                        return;
                    }
                }
            }
        } //En caso de error:
        catch (Exception e) {
        }
    }

    private DominioVirtual obtener_dominio(String peticion) {
        DominioVirtual respuesta = null;
        String dominio_peticion;
        int inicio = peticion.indexOf("/");
        int fin = (peticion.lastIndexOf("/") + 1);
        dominio_peticion = peticion.substring(inicio, fin);
        int contador = 0;
        boolean se_encontro_dominio = false;
        while ((contador < cantidad_dominios) && (!se_encontro_dominio)) {
            if (dominios_virtuales[contador].getPathBrowser().contains(dominio_peticion)) {
                respuesta = dominios_virtuales[contador];
                se_encontro_dominio = true;
            } else {
                ++contador;
            }
        }
        return respuesta;
    }

    public void desactivarServidor() {
        activo = false;
    }

    private void escribir_a_bitacora(int metodo, String url, String datos, String codigo) {
        String metodo_usado = "";
        switch (metodo) {
            case 0:
                metodo_usado = "NO SOPORTADO";
                break;
            case 1:
                metodo_usado = "GET";
                break;
            case 2:
                metodo_usado = "HEAD";

                break;
            case 3:
                metodo_usado = "POST";
                break;
        }
        bitacora.escribir_linea(metodo_usado, url, datos, codigo);
    }
}//Ultima linea

