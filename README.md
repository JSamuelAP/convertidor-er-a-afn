# Convertidor de Expresiones Regulares a Autómatas Finitos No Deterministas
Aplicación de consola que convierte expresiones regulares a autómatas finitos no deterministas **paso por paso**, y muesta el **AFN resultante**.

# Uso 🦮
Primero hay que compilar los archivos de Java:
```bash
cd src
javac *.java
```

Después se ejecuta la clase Main:
```bash
java Main
```

Ahora se podrá ingresar una expresión regular para ser convertida, los metacarácteres que acepta son:
- Unión: `+`, `,`, y `|`
- Agrupación: `(`, `)`, `[` y `]`
- Cerradura de Kleene: `*`

# Ejemplo 🌟
```
Convertidor de Expresiones Regulares a Autómatas Finitos No Determinitas
Ingrese la expresión regular: (a+b)*c

Paso 1. Agregar estado inicial y final
>(q0) --- (a+b)*c --> ((q1))

Paso 2. Separar concatenacion en (a+b)*c: 
>(q0) --- (a+b)* --> (q2)
(q2) --- c --> ((q1))

Paso 3. Separar cerradura de Kleene en (a+b)*: 
>(q0) --- _ --> (q3)
(q3) --- a+b --> (q3)
(q3) --- _ --> (q2)

Paso 4. Separar uniones en a+b: 
(q3) --- a --> (q3)
(q3) --- b --> (q3)

AFN resultante: 
K = {q0, q1, q2, q3}
E = {a, b, c}
F = {q1}
S = q0
D =
q	o	D(q,o)
q0	_	q3
q2	c	q1
q3	_	q2
q3	a	q3
q3	b	q3
```
Donde:
- `_` son palabras vacías (`ε`)
- *`K`* es el conjunto de estados
- `E` es el conjunto de símbolos del alfabeto (`Σ`)
- *`F`* es el conjunto de estados finales
- *`S`* es el estado inicial
- `D` Tabla de relaciones o Relación de transición (`Δ`)
- `o` es el símbolo del alfabeto (`σ`)

# Recomendaciones 📌
Para obtener los resultados esperados, no utilizar agrupaciones inecesarias, como: `((a+b))`, pues solo se quitará los parentesís externos y el resultado `(a+b)` ya no convertirá lo que hay en el interior.
