/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.math.BigInteger;

/**
 *
 * @author Fran
 */
public class Calculadora {
    
    public int suma(int x, int y){
        return x+y;       
    }
    
    public static int division(int x, int y){
        return x/y;
    }       
    
    /*
     * Operaciones con vectores
     */
    public static boolean iguales(int[] a, int[] b) {        
        if ((a == null) || (b == null)) {
            throw new IllegalArgumentException("null argument");
        }
        
        boolean iguales = false;
        if (a.length != b.length) {
            iguales = false;
        }
        iguales = true;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                iguales = false;
            }
        }

        return iguales;
    }

    /**
     * Scalar multiplication of given vectors.
     */
    public static int productoPunto(int[] a, int[] b) {
        if ((a == null) || (b == null)) {
            throw new IllegalArgumentException("Argumento invalido");
        }

        if (a.length != b.length) {
            throw new IllegalArgumentException(
                    "los vectores tienen tamaños diferentes ("
                    + a.length + ", " + b.length + ')');
        }
        
        int suma = 0;
        for (int i = 0; i < a.length; i++) {
            suma += a[i] * b[i];
        }
        return suma;
    }
    
    public static int metodoCritico(){
        boolean condicion = true;
        int resultado = 1;
        while(condicion){
            //se deberia modificar la condicion del while
        }
        return resultado;
    }
    
    public static String factorial(int number) 
                                               throws IllegalArgumentException {
        if (number < 1) {
            throw new IllegalArgumentException("zero or negative parameter (" + number + ')');
        }

        BigInteger factorial = new BigInteger("1");
        for (int i = 2; i <= number; i++) {
            factorial = factorial.multiply(new BigInteger(String.valueOf(i)));
        }
        return factorial.toString();
    }
    
}
