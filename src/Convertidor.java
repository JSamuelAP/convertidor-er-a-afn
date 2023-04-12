import java.util.ArrayList;

/**
 * Clase que realiza la conversión de Expresión Regular a Autómata Finito No Determinista.
 * Contiene los métodos para tratar uniones, concatenaciones y cerraduras de Kleene.
 * @author Aldana Pérez José Samuel <20240446@leon.tecnm.mx>
 */
public class Convertidor {
  /** Estado inicial del AFN */
  private final Estado q0;
  /** Estado final del AFN */
  private final Estado q1;
  /** Expresión Regular a convertir */
  private final Expresion er;
  /** Objeto Autómata Finito No Determinista */
  private final AFN automataFinito;
  /** Para indicar el número de paso en los pasos de la conversión */
  private int numeroPaso;

  /**
   * Instanciar un convertidor de ER a AFN
   * @param expresionRegular Expresión Regular a convertir
   * @throws IllegalArgumentException Si la expresión regular no es válida
   */
  public Convertidor(String expresionRegular) {
    this.q0 = new Estado("q0", Estado.TiposEstados.INICIAL);
    this.q1 = new Estado("q1", Estado.TiposEstados.FINAL);
    this.automataFinito = new AFN(q0, q1);
    this.automataFinito.calcularAlfabeto(expresionRegular);
    this.er = new Expresion(expresionRegular);
    if (!this.er.esValida()) throw new IllegalArgumentException("\nExpresión regular inválida");
    // 2 porque 1 ya se imprime directo en el primer paso
    this.numeroPaso = 2;
  }

  /**
   * Iniciar la conversión de ER a AFN
   */
  public void convertir() { transformarEstadosInicalFinal(q0, er, q1); }

  /**
   * Crear la transición del estado inicial a final con la ER inicial
   * @param i Estado inicial del AFN
   * @param expresionRegular Expresión Regular a convertir
   * @param f Estado final del AFN
   */
  private void transformarEstadosInicalFinal(Estado i, Expresion expresionRegular, Estado f) {
    System.out.println("\nPaso 1. Agregar estado inicial y final");
    Transicion t1 = automataFinito.crearTransicion(i, expresionRegular, f);
    System.out.println(t1);

    identificarSiguienteTransformacion(q0, expresionRegular, q1);
  }

  /**
   * Separar las concatenaciones de la expresión y crear las transiciones entre ellas
   * @param estadoOrigen Estado de donde parte la expresión
   * @param expresion Expresión a separar
   * @param estadoDestino Estado a donde llegará la ultima concatenación
   */
  private void transformarConcatenacion(Estado estadoOrigen, Expresion expresion, Estado estadoDestino) {
    System.out.println("\nPaso " + numeroPaso + ". Separar concatenacion en " + expresion.getExpresionRegular() + ": ");
    numeroPaso++;
    ArrayList<Expresion> subExpresiones = Expresion.separarConcatenaciones(expresion);
    ArrayList<Transicion> transicionesGeneradas = new ArrayList<>(); // Para recuperar las transiciones después

    Estado estadoIntermedio = automataFinito.crearEstado(); // Para las transiciones entre los estados de origen y destino

    // Crear la primera transición
    Transicion transicion = automataFinito.crearTransicion(estadoOrigen, subExpresiones.get(0), estadoIntermedio);
    transicionesGeneradas.add(transicion);
    System.out.println(transicion);

    // Crear las transiciones intermedias
    for (int i = 1; i < subExpresiones.size() - 1; i++) {
      Estado estadoIntermedioAux = automataFinito.crearEstado(); // Auxiliar para el estado destino
      transicion = automataFinito.crearTransicion(estadoIntermedio, subExpresiones.get(i), estadoIntermedioAux);
      transicionesGeneradas.add(transicion);
      System.out.println(transicion);
      estadoIntermedio = estadoIntermedioAux; // El estado destino se convierte en el origen de la siguiente transición
    }

    // Crear la ultima transición
    transicion = automataFinito.crearTransicion(
            estadoIntermedio,
            subExpresiones.get(subExpresiones.size() - 1),
            estadoDestino
    );
    transicionesGeneradas.add(transicion);
    System.out.println(transicion);

    // Buscar si las subexpresiones se pueden transformar, esto se hace al final de cada transformación
    for(Transicion t: transicionesGeneradas)
      identificarSiguienteTransformacion(t.getEstadoOrigen(), t.getExpresion(), t.getEstadoDestino());
  }

  /**
   * Separar las uniones de la expresión y crear las transiciones
   * @param estadoOrigen Estado de donde parte la expresión
   * @param expresion Expresión a separr
   * @param estadoDestino Estado a donde llegarán las sub expresiones
   */
  private void transformarUnion(Estado estadoOrigen, Expresion expresion, Estado estadoDestino) {
    System.out.println("\nPaso " + numeroPaso + ". Separar uniones en " + expresion.getExpresionRegular() + ": ");
    numeroPaso++;
    ArrayList<Expresion> subExpresiones = Expresion.separarUniones(expresion);

    // Por cada sub expresión, crear una transición desde el origen hacia el destino
    for (Expresion subExpresion : subExpresiones) {
      Transicion t = automataFinito.crearTransicion(estadoOrigen, subExpresion, estadoDestino);
      System.out.println(t);
    }

    for (Expresion subExpresion : subExpresiones)
      identificarSiguienteTransformacion(estadoOrigen, subExpresion, estadoDestino);
  }

  /**
   * Tratar la expresión con cerradura de Kleene
   * @param estadoOrigen Estado de donde parte la expresión
   * @param expresion Expresión con cerradura de Kleene
   * @param estadoDestino Estado a donde llegará la expresión
   */
  private void transformarCerraduraKleene(Estado estadoOrigen, Expresion expresion, Estado estadoDestino) {
    System.out.println(
            "\nPaso " + numeroPaso + ". Separar cerradura de Kleene en " + expresion.getExpresionRegular() + ": "
    );
    numeroPaso++;
    expresion.quitarCerraduraKleene();

    // Crear la transición de palabra vacía a un estado intermedio
    Estado estadoIntermedio = automataFinito.crearEstado();
    Transicion transicionKleen = automataFinito.crearTransicion(
            estadoOrigen,
            new Expresion("_"),
            estadoIntermedio);
    System.out.println(transicionKleen);

    // Crear la transición del estado intermedio así mismo con la expresión sin cerradura de Kleene
    transicionKleen = automataFinito.crearTransicion(estadoIntermedio, expresion, estadoIntermedio);
    System.out.println(transicionKleen);

    // Crear la transición de palabra vacía al estado destino
    transicionKleen = automataFinito.crearTransicion(
            estadoIntermedio,
            new Expresion("_"),
            estadoDestino
    );
    System.out.println(transicionKleen);

    identificarSiguienteTransformacion(estadoIntermedio, expresion, estadoIntermedio);
  }

  /**
   * Identificar si la expresión se trata de una unión, concatenación o cerradura de Kleene.
   * Transformar la expresión según sea el caso.
   * @param estadoOrigen Estado de donde parte la expresión
   * @param expresion Expresión a tratar
   * @param estadoDestino Estado a donde llegará la expresión
   */
  private void identificarSiguienteTransformacion(Estado estadoOrigen, Expresion expresion, Estado estadoDestino) {
    if (expresion.esExpresionMinima()) return;
    if (expresion.esUnion()) transformarUnion(estadoOrigen, expresion, estadoDestino);
    else if (expresion.esConcatenacion()) transformarConcatenacion(estadoOrigen, expresion, estadoDestino);
    else if (expresion.esCerraduraKleene()) transformarCerraduraKleene(estadoOrigen, expresion, estadoDestino);
  }

  /**
   * Imprimir el Autómata Finito No Determinista resultante
   * @see AFN#toString()
   */
  public void imprimirAFN() {
    System.out.println("\nAFN resultante: ");
    System.out.println(this.automataFinito);
  }
}