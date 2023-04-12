/**
 * Clase para representar una transición.
 * Implementa la interfaz Comparable para que sea comparable por el nombre del estado de origen.
 * @author Aldana Pérez José Samuel <20240446@leon.tecnm.mx>
 */
public class Transicion implements Comparable<Transicion> {
  /** Estado de origen */
  private final Estado estadoOrigen;
  /** Estado de destino */
  private final Estado estadoDestino;
  /** Expresión que transiciona */
  private final Expresion expresion;

  /**
   * Instanciar una transición
   * @param estadoOrigen Estado de donde parte la transición
   * @param expresion Expresión que transiciona
   * @param estadoDestino Estado a dond ellega la transición
   */
  public Transicion(Estado estadoOrigen, Expresion expresion, Estado estadoDestino) {
    this.estadoOrigen = estadoOrigen;
    this.expresion = expresion;
    this.estadoDestino = estadoDestino;
  }

  /**
   * Obtener una representación de la transición.
   * Ejemplo: (q1) --- a --> (q2)
   * @return La transición en un String
   */
  @Override
  public String toString() {
    return estadoOrigen.toString() + " --- " + expresion.getExpresionRegular() + " --> " + estadoDestino.toString();
  }

  /**
   * Obtener el estado de origen
   * @return Estado de origen
   */
  public Estado getEstadoOrigen() { return estadoOrigen; }

  /**
   * Obtener el estado de destino
   * @return Estado de destino
   */
  public Estado getEstadoDestino() { return estadoDestino; }

  /**
   * Obtener la expresión de transición
   * @return String de la expresión
   */
  public Expresion getExpresion() { return expresion; }

  /**
   * Para que las transiciones sean ordenables en base al estado de origen.
   * Si los estados tienen el mismo estado de origen, ahora se ordena en base a la expresión.
   * @param o el estado con el que será comparado
   * @return Entero resultado de la comparación
   */
  @Override
  public int compareTo(Transicion o) {
    int comparacion = this.estadoOrigen.compareTo(o.estadoOrigen);
    if (comparacion == 0)
      return this.expresion.getExpresionRegular().compareTo(o.expresion.getExpresionRegular());
    return comparacion;
  }
}
