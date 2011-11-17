/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

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
public class CalculadoraTest {
    public static Calculadora calc;
    
    public CalculadoraTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        calc = mock(Calculadora.class);
        
        when(calc.suma(2,2)).thenReturn(2);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of suma method, of class Calculadora.
     */
    @Test
    public void testSuma() {
        System.out.println("suma");
        int x = 0;
        int y = 0;
        int expResult = 0;        
        int result = calc.suma(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of division method, of class Calculadora.
     */
    @Test
    public void testDivision() {
        System.out.println("division");
        int x = 0;
        int y = 0;
        int expResult = 0;
        int result = Calculadora.division(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iguales method, of class Calculadora.
     */
    @Test
    public void testIguales() {
        System.out.println("iguales");
        int[] a = new int[]{0,1};
        int[] b = new int[]{1,0};
        boolean expResult = true;
        boolean result = Calculadora.iguales(a, b);
        
        assertTrue(Calculadora.iguales(new int[]{0, 1, 0}, new int[]{0, 1, 0}));
        assertFalse(Calculadora.iguales(new int[]{0, 0, 0}, new int[]{1, 1, 1}));
        assertEquals(expResult, result);                
    }

    /**
     * Test of productoPunto method, of class Calculadora.
     */
    @Test
    public void testProductoPunto() {
        System.out.println("productoPunto");
        int[] a = null;
        int[] b = null;
        int expResult = 0;
        int result = Calculadora.productoPunto(a, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testSuma2() {
        System.out.println("suma");
        int x = 0;
        int y = 0;
        int expResult = 4;
        
        int result = calc.suma(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testSuma3() {
        System.out.println("in order verify");
        int x = 0;
        int y = 0;
        int expResult = 4;
        
        int result = calc.suma(x, y);
        
        int num = calc.suma(5, 4);
        int num1 = calc.suma(5, 4);
        int num2 = calc.suma(3, 2);
        int num3 = calc.suma(5, 4);
        //hacer una clase que sea guardador de archivos, con un metodo que guarde algo en un archivo, 
        //la clase ademas tiene una variable string msj, q es el mensaje q se desea guardar.
        // la idea es que calculadora.class lo llame asi guardador.guardar() y
        //despues guardador setMensjae().. lo q esta mal, ya que debería primero modificar el msj, y luego hacer guardar
        int ver = verify(calc, atLeast(2)).suma(5, 4);
        //inOrder.verify(calc, atLeast(1)).add("B");
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test(timeout=1)
    public void testFactorial() {
        System.out.println("factorial");

        String expResult = "479001600";
        String result = Calculadora.factorial(12);
                
        assertEquals(expResult, result);                
    }
    
}
