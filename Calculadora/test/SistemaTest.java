/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.mockito.InOrder;
import org.junit.Ignore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

/**
 *
 * @author Fran
 */
public class SistemaTest {    
    static EscritorArchivos escritorMock;
    public SistemaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("Before Class");
        System.out.println("Abriendo Conexión con la Base de Datos");   
        
        /*creacion del mock de EscritorArchivo*/
        escritorMock = mock(EscritorArchivos.class);
        
        /*definicion de stub. Metodo númeroLineas*/
        when(escritorMock.numeroLineas()).thenReturn(1);
        
        /*Uso de thenReturn() consecutivo*/
        when(escritorMock.getSiguienteLinea()).thenReturn("linea 1").thenReturn("linea 2").thenReturn("linea 3");
        
        /*definición de excepcion lanzada por un metodo void*/
        doThrow(new RuntimeException()).when(escritorMock).borrarArchivo();        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("\nAfter Class");
        System.out.println("Cerrando Conexión con la Base de Datos");
    }
    
    @Before
    public void setUp() {
        System.out.println("\nBefore Test");
    }
    
    @After
    public void tearDown() {
        System.out.println("After Test");
    }   

    /**
     * Test of getNumeroA method, of class Sistema.
     */
    @Test
    public void testGetNumeroA() {
        System.out.println("getNumeroA");
        Sistema instance = new SistemaImpl();
        int expResult = 0;
        int result = instance.getNumeroA();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    /**
     * Test of multiplicacion method, of class Sistema.
     * 
     * El siguiente metodo no se ejecuta, ya que tiene la anotacion @Ignore
     */
    @Ignore
    @Test    
    public void testMultiplicacion() {
        System.out.println("multiplicacion");
        Sistema instance = new SistemaImpl();
        instance.multiplicacion();
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of division method, of class Sistema.
     * El test espera una excepcion por la division entre 0
     */
    @Test(expected=ArithmeticException.class)
    public void testDivision() {
        System.out.println("division");
        Sistema instance = new SistemaImpl();
        instance.setNumeroA(1);
        instance.setNumeroB(0);
        instance.division();
    }   
    
    /*
     * El siguiente test falla en caso de que tarde mas de 1000 milisegundos
     */    
    @Test(timeout=1000)
    public void testMetodoCritico() {
        System.out.println("metodocritico");
        Sistema instance = new SistemaImpl();
        instance.metodoCritico();
    }
    
    @Test
    public void testGuardarResultado(){
        System.out.println("GuardarResultado Test");
        Sistema instance = new SistemaImpl();
                
        instance.guardarResultado(escritorMock);                               
        /*
         * Uso de verify() para probar que ambos métodos del EscritorArchivos
         * sean llamados
         */
        verify(escritorMock).guardarMsj();
        verify(escritorMock).setMensaje(anyString());        
    }
    
    @Test
    public void testGuardarResultado2(){
        System.out.println("GuardarResultado Test");
        Sistema instance = new SistemaImpl();
        
        InOrder inOrder = inOrder(escritorMock);
        instance.guardarResultado(escritorMock);                               
        /*
         * en este orden es en el que se deberia llamar a los metodos del la
         * clase EscritorArchivos, pero falla porque se llaman al reves
         */        
        inOrder.verify(escritorMock).setMensaje(anyString());        
        inOrder.verify(escritorMock).guardarMsj();
    }
    
    @Test
    public void testConAtMost(){        
        System.out.println("At Most Test");
        Sistema instance = new SistemaImpl();
        
        instance.escribirResultadoEnArchivoExterno(escritorMock);
        
        /*
         * Mockito permite verificar que un metodo sea llamado el numero de veces
         * requerido, Usando AtLeast(), AtMost(), AtLeastOnce(), times() y never()
         */
        verify(escritorMock, atLeast(1)).guardarMensajeArchivoExterno(anyString());
        verify(escritorMock, atLeastOnce()).guardarMensajeArchivoExterno(anyString());                            
        verify(escritorMock, atMost(3)).guardarMensajeArchivoExterno(anyString());
        verify(escritorMock, never()).setMensaje("mensaje");
        /*
         * otra caracteristica de mockito es que a pesar de que no se hizo el stub
         * respectivo del metodo guardarMensaje... siempre retorno algo, en este
         * caso false
         */
    }
    
    @Test
    public void testConThenReturn(){        
        System.out.println("Then Return Test");
        Sistema instance = new SistemaImpl();
        
        instance.escribirResultadoEnArchivoExterno(escritorMock);
        
        /*
         * Mockito permite personalizar que valores retorna un mock mediante la 
         * instruccion thenReturn()
         */
        System.out.println("Numero de lineas: "+escritorMock.numeroLineas());
        System.out.println("Numero de lineas: "+escritorMock.numeroLineas());
        System.out.println(escritorMock.getSiguienteLinea());
        System.out.println(escritorMock.getSiguienteLinea());
        System.out.println(escritorMock.getSiguienteLinea());
        System.out.println(escritorMock.getSiguienteLinea());
        System.out.println(escritorMock.getSiguienteLinea());
    }
    
    @Test
    public void testIguales(){        
        System.out.println("Iguales Test");
        Sistema instance = new SistemaImpl();
        boolean result = instance.iguales(10, 10);
        assertTrue(result);
    }
    
    @Test
    public void testDiferentes(){        
        System.out.println("Not Iguales Test");
        Sistema instance = new SistemaImpl();
        boolean result = instance.iguales(15, 10);
        assertFalse(result);
    }
    
    
    /**
     * Test of setNumeroA method, of class Sistema.
     */
    @Test
    public void testSetNumeroA() {
        System.out.println("setNumeroA");
        int n = 0;
        Sistema instance = new SistemaImpl();
        instance.setNumeroA(n);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
        
    /**
     * Test of setNumeroB method, of class Sistema.
     */
    @Test
    public void testSetNumeroB() {
        System.out.println("setNumeroB");
        int n = 0;
        Sistema instance = new SistemaImpl();
        instance.setNumeroB(n);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    /**
     * Test of setResultado method, of class Sistema.
     */
    @Test
    public void testSetResultado() {
        System.out.println("setResultado");
        int n = 0;
        Sistema instance = new SistemaImpl();
        instance.setResultado(n);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of setOperacion method, of class Sistema.
     */
    @Test
    public void testSetOperacion() {
        System.out.println("setOperacion");
        char o = ' ';
        Sistema instance = new SistemaImpl();
        instance.setOperacion(o);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    /**
     * Test of getNumeroB method, of class Sistema.
     */
    @Test
    public void testGetNumeroB() {
        System.out.println("getNumeroB");
        Sistema instance = new SistemaImpl();
        int expResult = 0;
        int result = instance.getNumeroB();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
  //      fail("The test case is a prototype.");
    }

    /**
     * Test of getResultado method, of class Sistema.
     */
    @Test
    public void testGetResultado() {
        System.out.println("getResultado");
        Sistema instance = new SistemaImpl();
        int expResult = 0;
        int result = instance.getResultado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getOperacion method, of class Sistema.
     */
    @Test
    public void testGetOperacion() {
        System.out.println("getOperacion");
        Sistema instance = new SistemaImpl();
        char expResult = ' ';
        char result = instance.getOperacion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of suma method, of class Sistema.
     */
    @Test
    public void testSuma() {
        System.out.println("suma");
        Sistema instance = new SistemaImpl();
        instance.suma();
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of resta method, of class Sistema.
     */
    @Test
    public void testResta() {
        System.out.println("resta");
        Sistema instance = new SistemaImpl();
        instance.resta();
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    /**
     * Test of establecerNumeroA method, of class Sistema.
     */    
    @Test    
    public void testEstablecerNumeroA() {
        System.out.println("establecerNumeroA");
        String a = "4";
        Sistema instance = new SistemaImpl();
        instance.establecerNumeroA(a);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of establecerNumeroB method, of class Sistema.
     */
    @Test
    public void testEstablecerNumeroB() {
        System.out.println("establecerNumeroB");
        String b = "8";
        Sistema instance = new SistemaImpl();
        instance.establecerNumeroB(b);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of retornarNumeroA method, of class Sistema.
     */
    @Test
    public void testRetornarNumeroA() {
        System.out.println("retornarNumeroA");
        Sistema instance = new SistemaImpl();
        String expResult = "0";
        String result = instance.retornarNumeroA();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of retornarNumeroB method, of class Sistema.
     */
    @Test
    public void testRetornarNumeroB() {
        System.out.println("retornarNumeroB");
        Sistema instance = new SistemaImpl();
        String expResult = "0";
        String result = instance.retornarNumeroB();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of retornarResultado method, of class Sistema.
     */
    @Test
    public void testRetornarResultado() {
        System.out.println("retornarResultado");
        Sistema instance = new SistemaImpl();
        String expResult = "0";
        String result = instance.retornarResultado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    public class SistemaImpl extends Sistema {
    }
}
