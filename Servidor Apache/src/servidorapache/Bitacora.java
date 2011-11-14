/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorapache;

import java.io.*;
import java.util.Calendar;

/**
 *
 * @author Ricardo Alvarado
 */
public class Bitacora {

    String path;
    String nombre;
    int tamano_maximo;
    File archivo;
    PantallaPrincipal interfaz;
    FileWriter archivo_a_escribir;
    int tamano_actual;

    public Bitacora(String path_bitacora, int tam_max, PantallaPrincipal dom) {
        path = path_bitacora;
        tamano_maximo = tam_max;
        interfaz = dom;
        archivo = new File(path);
        if (archivo.exists()) {
            System.out.println("El fichero existe.");
            tamano_actual = 0;
            try {
                archivo_a_escribir = new FileWriter(archivo, true);
            } catch (IOException e) {
                interfaz.desplegar("No se pudo abir la bitacora!");

            }

        } else {
            System.out.println("El fichero no existe.");
            try {
                if (archivo.createNewFile()) {
                    try {
                        archivo_a_escribir = new FileWriter(archivo);
                    } catch (IOException e) {
                        interfaz.desplegar("No se pudo abir la bitacora!");
                    }
                    try {
                        archivo_a_escribir.write("Timestamp,Metodo,URL,Datos,CodigoRetorno\n");
                        tamano_actual = 0;
                    } catch (IOException e) {
                        interfaz.desplegar("No se pudo escribir el encabezado en la bitacora!");
                    }
                    interfaz.desplegar("El fichero se ha creado correctamente");
                } else {
                    System.out.println("No ha podido ser creada la bitacora");
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        try {
            archivo_a_escribir.close();
        } catch (IOException e) {
            interfaz.desplegar("No se pudo cerrar la bitacora!");

        }
    }

    public void escribir_linea(String metodo, String url, String datos, String codigo_retorno) {
        if (tamano_actual < tamano_maximo) {
            try {
                archivo_a_escribir = new FileWriter(archivo, true);
            } catch (IOException e) {
                interfaz.desplegar("No se pudo abir la bitacora!");

            }
            Calendar calendar = Calendar.getInstance();
            java.util.Date now = calendar.getTime();
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
            System.out.println(currentTimestamp.toString());

            try {
                archivo_a_escribir.write(currentTimestamp.toString()+";"+metodo+";"+url+";"+datos+";"+codigo_retorno+"\n");
                ++tamano_actual;
            } catch (IOException e) {
                interfaz.desplegar("No se pudo escribir en la bitacora!");

            }

            try {
                archivo_a_escribir.close();
            } catch (IOException e) {
                interfaz.desplegar("No se pudo cerrar la bitacora!");

            }
        } else {
            interfaz.desplegar("No se escribio en la bitacora porque se excede el numero maximo de lineas permitido.\n");
        }

    }
}
