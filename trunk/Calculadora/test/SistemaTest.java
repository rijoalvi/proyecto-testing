/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
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
        escritorMock = mock(EscritorArchivos.class);
        when(escritorMock.numeroLineas()).thenReturn(1);
        doThrow(new FileNotFoundException()).when(escritorMock).borrarArchivo();        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("Ater Class");
        System.out.println("Cerrando Conexión con la Base de Datos");
    }
    
    @Before
    public void setUp() {
        System.out.println("Before Test");
    }
    
    @After
    public void tearDown() {
        System.out.println("After Test");
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
        fail("The test case is a prototype.");
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
        fail("The test case is a prototype.");
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
        fail("The test case is a prototype.");
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
        fail("The test case is a prototype.");
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
        fail("The test case is a prototype.");
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
        fail("The test case is a prototype.");
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
        fail("The test case is a prototype.");
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
        fail("The test case is a prototype.");
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
        fail("The test case is a prototype.");
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
        fail("The test case is a prototype.");
    }

    /**
     * Test of multiplicacion method, of class Sistema.
     */
    @Ignore
    @Test    
    public void testMultiplicacion() {
        System.out.println("multiplicacion");
        Sistema instance = new SistemaImpl();
        instance.multiplicacion();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of division method, of class Sistema.
     */
    @Test(expected=ArithmeticException.class)
    public void testDivision() {
        System.out.println("division");
        Sistema instance = new SistemaImpl();
        instance.setNumeroA(1);
        instance.setNumeroB(0);
        instance.division();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of establecerNumeroA method, of class Sistema.
     */
    
    @Test    
    public void testEstablecerNumeroA() {
        System.out.println("establecerNumeroA");
        String a = "";
        Sistema instance = new SistemaImpl();
        instance.establecerNumeroA(a);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of establecerNumeroB method, of class Sistema.
     */
    @Test
    public void testEstablecerNumeroB() {
        System.out.println("establecerNumeroB");
        String b = "";
        Sistema instance = new SistemaImpl();
        instance.establecerNumeroB(b);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retornarNumeroA method, of class Sistema.
     */
    @Test
    public void testRetornarNumeroA() {
        System.out.println("retornarNumeroA");
        Sistema instance = new SistemaImpl();
        String expResult = "";
        String result = instance.retornarNumeroA();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retornarNumeroB method, of class Sistema.
     */
    @Test
    public void testRetornarNumeroB() {
        System.out.println("retornarNumeroB");
        Sistema instance = new SistemaImpl();
        String expResult = "";
        String result = instance.retornarNumeroB();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retornarResultado method, of class Sistema.
     */
    @Test
    public void testRetornarResultado() {
        System.out.println("retornarResultado");
        Sistema instance = new SistemaImpl();
        String expResult = "";
        String result = instance.retornarResultado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test(timeout=1000)
    public void testMetodoCritico() {
        System.out.println("metodocritico");
        Sistema instance = new SistemaImpl();
        instance.metodoCritico();
        //(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testGuardarResultado(){
        System.out.println("GuardarResultado Test");
        Sistema instance = new SistemaImpl();
        
        InOrder inOrder = inOrder(escritorMock);
        instance.guardarResultado(escritorMock);                               
        /*
         * en este orden es en el que se deberia llamar a los metodos del la
         * clase EscritorArchivos, pero falla porque se llaman al reves
         */
        inOrder.verify(escritorMock).guardarMsj();
        inOrder.verify(escritorMock).setMensaje(anyString());        
    }
/* 
    public static void crearArchivo(){
        File archivo;
        FileWriter archivo_a_escribir;
        archivo = new File("log.txt");
        if (archivo.exists()) {
            System.out.println("El fichero existe.");            
            try {
                archivo_a_escribir = new FileWriter(archivo, true);
            } catch (IOException e) {
                System.out.println("No se pudo abir la bitacora!");
            }

        } else {
            System.out.println("El fichero no existe.");
            try {
                if (archivo.createNewFile()) {
                    try {
                        archivo_a_escribir = new FileWriter(archivo);
                        try {
                        archivo_a_escribir.write("Timestamp,Metodo,URL,Datos,CodigoRetorno\n");                        
                        } catch (IOException e) {
                            System.out.println("No se pudo escribir el encabezado en la bitacora!");
                        }
                        System.out.println("El fichero se ha creado correctamente");
                        try {
                            archivo_a_escribir.close();
                        } catch (IOException e) {
                            System.out.println("No se pudo cerrar la bitacora!");
                        }
                    } catch (IOException e) {
                        System.out.println("No se pudo abir la bitacora!");
                    }
                    
                } else {
                    System.out.println("No ha podido ser creada la bitacora");
                }
            } catch (IOException ioe) {
                System.out.println(ioe.toString());
            }
        }                
    }
*/
    public class SistemaImpl extends Sistema {
    }
}
