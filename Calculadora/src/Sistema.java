abstract public class Sistema {
  protected int numeroA;
  protected int numeroB;
  protected int resultado;
  protected char operacion;
  protected int base;

  public Sistema() {
      this.numeroA = 0;
      this.numeroB = 0;
      this.resultado = 0;
      this.operacion = ' ';
      this.base = 10;
  }

  public void setNumeroA(int n){
      this.numeroA = n;
  }

  public void setNumeroB(int n){
      this.numeroB = n;
  }

  public void setResultado(int n){
      this.resultado = n;
  }

  public void setOperacion(char o){
      this.operacion = o;
  }

  public int getNumeroA(){
      return this.numeroA;
  }

  public int getNumeroB(){
      return this.numeroB;
  }

  public int getResultado(){
      return this.resultado;
  }

  public char getOperacion(){
      return this.operacion;
  }

  public void suma(){
      this.resultado = this.numeroA + this.numeroB;
  }
  public void resta(){
      this.resultado = this.numeroA - this.numeroB;
  }
  public void multiplicacion(){
      this.resultado = this.numeroA * this.numeroB;
  }
  public void division(){
      this.resultado = this.numeroA / this.numeroB;
  }

  public void establecerNumeroA(String a){
      int n = Integer.parseInt(a,base);
      this.setNumeroA(n);
  }
  public void establecerNumeroB(String b){
      int n = Integer.parseInt(b,base);
      this.setNumeroB(n);
  }
  public String retornarNumeroA(){
      String cad="";
      switch(base){
      case 2:
          cad = Integer.toBinaryString(numeroA);
          break;
      case 8:
          cad = Integer.toOctalString(numeroA);
          break;
      case 10:
          cad = String.valueOf(numeroA);
          break;
      case 16:
          cad = Integer.toHexString(numeroA);
          break;
      }
      return cad;
    
  }
  public String retornarNumeroB(){
      String cad="";
      switch(base){
      case 2:
          cad = Integer.toBinaryString(numeroB);
          break;
      case 8:
          cad = Integer.toOctalString(numeroB);
          break;
      case 10:
          cad = String.valueOf(numeroB);
          break;
      case 16:
          cad = Integer.toHexString(numeroB);
          break;
      }
      return cad;
  }
  public String retornarResultado(){
      String cad="";
      switch(base){
      case 2:
          cad = Integer.toBinaryString(resultado);
          break;
      case 8:
          cad = Integer.toOctalString(resultado);
          break;
      case 10:
          cad = String.valueOf(resultado);
          break;
      case 16:
          cad = Integer.toHexString(resultado);
          break;
      }
      return cad;
  }
  /*
   * Metodo que se deberia ejecutar de forma eficiente, 
   * por ejemplo una transaccion bancaria
   */
  public void metodoCritico(){
      while(true){
          
      }
  }
  /*
   * El metodo esta mal programado, orden de invocacion de metodo erroneo
   */
  public void guardarResultado(EscritorArchivos escritor){      
      escritor.guardarMsj();
      escritor.setMensaje(String.valueOf(resultado));      
  }
  
  /*
   * Intenta por lo menos 3 veces llamar al metodo guardar...
   */
  public void escribirResultadoEnArchivoExterno(EscritorArchivos escritor){
      int intentos = 3;
      boolean conexionExitosa = false;
      while (intentos>0 && !conexionExitosa){
          conexionExitosa = escritor.guardarMensajeArchivoExterno(String.valueOf(resultado));
          intentos--;
      }
      
  }
  
  public boolean iguales (int a, int b){
      boolean respuesta = false;
      if (a == b){
          respuesta = true;
      }
      else{
          respuesta = false;
      }
      return respuesta;
  }
  
  
    
}