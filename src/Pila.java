/**
 * Clase para representar la estructura de datos Pila
 * @author Aldana Pérez José Samuel <20240446@leon.tecnm.mx>
 */
public class Pila {
  /** Ultimo nodo insertado en la pila */
  private Nodo ultimo;
  /** Número de elementos de la pila */
  private byte longitud;

  /**
   * Instanciar una pila vacía
   */
  public Pila() {}

  /**
   * Instanciar una pila con un elemento
   * @param dato Elemento del primer nodo de la pila
   */
  public Pila(Object dato) {
    ultimo = new Nodo(dato, null);
    longitud = 1;
  }

  /**
   * Insertar un nuevo elemento en la cima de la pila
   * @param dato Elemento a insertar
   */
  public void insertar(Object dato) {
    ultimo = new Nodo(dato, ultimo);
    longitud++;
  }

  /**
   * Obtener y eliminar el elemento de la cima de la pila
   * @return Elemento del ultimo nodo
   */
  public Nodo removerUltimo() {
    if (estaVacia()) return null;

    Nodo nodoEliminado = ultimo;
    ultimo = ultimo.getSiguiente();
    nodoEliminado.setSiguiente(null);

    longitud--;
    return nodoEliminado;
  }

  /**
   * Solo obtener el elemento de la cima de la pila
   * @return Elemento del ultimo nodo
   */
  public Nodo obtenerUltimo() { return ultimo; }

  /**
   * Verificar si la pila no tiene elementos
   * @return True si el número de elementos es 0
   */
  public boolean estaVacia() { return longitud == 0; }

  /**
   * Obtener la representación del contenido de la pila
   * @return String de todos los elementos de la pila
   */
  public String toString() {
    String longitudPila = "Longitud: " + longitud + " elementos\n";
    String texto = "";
    Nodo nodoActual = ultimo;

    while (nodoActual != null) {
      texto = (nodoActual.getSiguiente() == null ? "" : " <- ") + nodoActual.getDato() + texto;
      nodoActual = nodoActual.getSiguiente();
    }

    return longitudPila + texto;
  }

  /**
   * Obtener el número de elementos de la pila
   * @return La longitud de la pila
   */
  public byte getLongitud() { return longitud; }
}