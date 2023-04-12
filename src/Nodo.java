/**
 * Clase que representa un nodo de la Pila
 * @author Aldana Pérez José Samuel <20240446@leon.tecnm.mx>
 * @see Pila
 */
public class Nodo {
  /** Contenido del nodo */
  private Object dato;
  /** Nodo siguiente */
  private Nodo siguiente;

  /**
   * Instanciar un nodo
   * @param dato Contenido del nodo
   * @param siguiente Siguiente nodo
   */
  public Nodo(Object dato, Nodo siguiente) {
    this.dato = dato;
    this.siguiente = siguiente;
  }

  /**
   * Obtener el contenido del nodo
   * @return Objeto que contiene el nodo
   */
  public Object getDato() { return this.dato; }

  /**
   * Establecer el contenido del nodo
   * @param dato Nuevo dato a almacenar
   */
  public void setDato(Object dato) { this.dato = dato; }

  /**
   * Obtener el siguiente nodo
   * @return Siguiente nodo en la pila
   */
  public Nodo getSiguiente() { return this.siguiente; }

  /**
   * Establecer el siguiente nodo
   * @param siguiente Nodo que le seguira al nodo actual
   */
  public void setSiguiente(Nodo siguiente) { this.siguiente = siguiente; }
}