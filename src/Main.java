import java.util.Scanner;

/**
 * Tecnológico Nacional de México Campus León
 * Ingeniería en Sistemas Computacionales
 * Lenguajes y Autómatas I
 * Programa que convierte una Expresión Regular a su Autómata Finito No Determinista equivalente
 * @author Aldana Pérez José Samuel <20240446@leon.tecnm.mx>
 * Lunes 17 de abril 2023
 */
public class Main {
  /**
   * Método inicial del programa.
   * Se lee la expresión regular del usuario, realiza la conversión e imprime los resultados.
   */
  public static void main(String[] args) {
    try {
      Scanner teclado = new Scanner(System.in);
      System.out.println("Convertidor de Expresiones Regulares a Autómatas Finitos No Determinitas");
      System.out.print("Ingrese la expresión regular: ");
      String entrada = teclado.nextLine();

      Convertidor app = new Convertidor(entrada);
      app.convertir();
      app.imprimirAFN();
    } catch(IllegalArgumentException e) {
      System.err.println(e);
    }
  }
}