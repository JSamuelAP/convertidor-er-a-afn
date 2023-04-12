/**
 * Clase para representar un estado del AFN
 * Implementa la interfaz Comparable para poder ordenar estados en base a su nombre
 * @author Aldana Pérez José Samuel <20240446@leon.tecnm.mx>
 */
public class Estado implements Comparable<Estado> {
  /** Nombre o etiqueta del estado */
  private final String nombre;
  /** Tipo de estado (inicial, no final o final) */
  private final TiposEstados tipoEstado;
  /** Para asignar e identificar el tipo de estado */
  enum TiposEstados { INICIAL, NOFINAL, FINAL }

  /**
   * Instanciar un estado
   * @param nombre Nombre o etiqueta del estado (qn)
   * @param tipoEstado Tipo de estado (inicial, no final, final)
   */
  public Estado(String nombre, TiposEstados tipoEstado) {
    this.nombre = nombre;
    this.tipoEstado = tipoEstado;
  }

  /**
   * Obtener el nombre del estado
   * @return El nombre del estado (qn)
   */
  public String getNombre() { return this.nombre; }

  /**
   * Obtener una representación del estado dentro de un circulo.
   * @return >(qn) si es estado inicial, (qn) si es estado no final y ((qn)) si es estado final
   */
  @Override
  public String toString() {
    return switch (tipoEstado) {
      case INICIAL -> ">(" + nombre + ")";
      case NOFINAL -> "(" + nombre + ")";
      case FINAL -> "((" + nombre + "))";
    };
  }

  /**
   * Para ordenar los estados en base a los números de su nombre
   * @param o El estado con el que será comparado
   * @return Entero resultado de la comparación
   */
  @Override
  public int compareTo(Estado o) {
    int n1 = Integer.parseInt(this.nombre.substring(1));
    int n2 = Integer.parseInt(o.nombre.substring(1));
    return Integer.compare(n1, n2);
  }
}
