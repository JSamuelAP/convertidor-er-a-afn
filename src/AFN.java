import java.util.*;

/**
 * Clase para representar un Autómata Finito No Determinista.
 * Contiene todos los elementos de su quíntuplo.
 * @author Aldana Pérez José Samuel <20240446@leon.tecnm.mx>
 */
public class AFN {
  /** Conjunto de simbolos del alfabeto */
  private final HashSet<Character> alfabeto;
  /** Conjunto de todos los estados */
  private final HashSet<Estado> estados;
  /** Estao incial del AFN */
  private final Estado estadoInicial;
  /** Estado final del AFN */
  private final Estado estadoFinal;
  /** Arreglo de transiciones */
  private final ArrayList<Transicion> transiciones;

  /**
   * Instanciar un Autómata Finito Determinista
   * @param i Estado inicial del AFN
   * @param f Estado final del AFN
   */
  public AFN(Estado i, Estado f) {
    this.alfabeto = new HashSet<>();
    this.estadoInicial = i;
    this.estadoFinal = f;
    this.estados = new HashSet<>();
    this.estados.add(this.estadoInicial);
    this.estados.add(this.estadoFinal);
    this.transiciones = new ArrayList<>();
  }

  /**
   * Crear un estado no final y guardarlo en el conjunto de estados
   * @return El estado creado
   */
  public Estado crearEstado() {
    // n es el subindice del nuevo estado q
    int n = this.estados.size();
    Estado q = new Estado("q" + n, Estado.TiposEstados.NOFINAL);
    this.estados.add(q);
    return q;
  }

  /**
   * Crear una transición y guardarla en el arreglo de transiciones
   * @param estadoOrigen Estado de donde parte la transición
   * @param expresionRegular Expresión que transiciona
   * @param estadoDestino Estado a donde llegará la transición
   * @return La transición creada
   */
  public Transicion crearTransicion(Estado estadoOrigen, Expresion expresionRegular, Estado estadoDestino) {
    Transicion t = new Transicion(estadoOrigen, expresionRegular, estadoDestino);
    this.transiciones.add(t);
    return t;
  }

  /**
   * Generar el conjunto de simbolos del alfabeto de la ER.
   * Ignora los metacaracteres de unión, cerradura de Kleene y de agrupación,
   * todo lo demás se considera parte del alfabeto.
   * @param expresionRegular Expresión Regular inicial
   */
  public void calcularAlfabeto(String expresionRegular) {
    // Quitar todos los metacaracteres () [] + | , *
    expresionRegular = expresionRegular.replaceAll("[()\\[\\]+*,|]", "");

    // Recorrer los caracteres y agregarlos al conjunto de simbolos
    // Al ser un HashSet, los simbolos repetidos no se agregaran
    for (int i = 0; i < expresionRegular.length(); i++) {
      char simbolo = expresionRegular.charAt(i);
      this.alfabeto.add(simbolo);
    }
  }

  /**
   * Obtener el quíntuplo del AFN
   * @return El String del quíntuplo
   */
  @Override
  public String toString() {
    return this.estadosToString() + "\n" +
           this.alfabetoToString() + "\n" +
           "F = {" + this.estadoFinal.getNombre() + "}\n" +
           "S = " + this.estadoInicial.getNombre() + "\n" +
           this.transicionesTostring();
  }

  /**
   * Obtener el conjunto de estados ordenados entre {} y separados por comas.
   * Ejemplo: K = { q0, q1, q2 }
   * @return El String del conjunto de estados
   * @see Estado#compareTo(Estado)
   */
  private String estadosToString() {
    // Para ordenar los estados lexicográficamente
    TreeSet<Estado> estadosOrdenados = new TreeSet<>(this.estados);

    String conjuntoK = "K = {";

    // Iterar los elementos
    Iterator<Estado> estados = estadosOrdenados.iterator();
    while (estados.hasNext()) {
      conjuntoK = conjuntoK.concat(estados.next().getNombre());
      // Si no es el ultimo estado, agregar una coma
      if (estados.hasNext()) conjuntoK = conjuntoK.concat(", ");
    }
    conjuntoK = conjuntoK.concat("}");
    return conjuntoK;
  }

  /**
   * Obtener el conjunto de simbolos del alfabeto entre {} y separados por comas.
   * Ejemplo: E = { a, b, c }
   * @return El String del conjunto de simbolos
   */
  private String alfabetoToString() {
    String conjuntoE = "E = {";

    // Iterar los elementos
    Iterator<Character> simbolos = this.alfabeto.iterator();
    while (simbolos.hasNext()) {
      conjuntoE = conjuntoE.concat(simbolos.next().toString());
      // Si no es el ultimo estado, agregar una coma
      if (simbolos.hasNext()) conjuntoE = conjuntoE.concat(", ");
    }
    conjuntoE = conjuntoE.concat("}");
    return conjuntoE;
  }

  /**
   * Obtener la tabla de transiciones ordenada.
   * Solo se incluyen las transiciones donde la expresión solo es un símbolo del alfabeto o la palabra vacía
   * @return El String de la tabla de transiciones
   * @see Transicion#compareTo(Transicion)
   */
  private String transicionesTostring() {
    String relacionTransicion = "D =\nq\to\tD(q,o)\n";
    // Ordenar las transiciones lexicográficamente
    this.transiciones.sort(Comparator.naturalOrder());

    for(Transicion t: this.transiciones) {
      // Mostrar solo las transiciones con expresiones minimas
      if(!t.getExpresion().esExpresionMinima()) continue;

      relacionTransicion = relacionTransicion.concat(
    t.getEstadoOrigen().getNombre() + "\t" +
        t.getExpresion().getExpresionRegular() + "\t" +
        t.getEstadoDestino().getNombre() + "\n"
      );
    }

    return relacionTransicion;
  }
}
