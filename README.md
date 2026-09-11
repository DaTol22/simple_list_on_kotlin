# SimpleList
#### David Santiago Rassa - 202510483
#### Alejandro Quintero Ruíz - 202510184
Implementación propia de una lista enlazada simple en Kotlin, que cumple el contrato completo de la interfaz `MutableList<E>` (equivalente a `List`/`Collection` de Java).

## Descripción

`SimpleList<E>` es una lista genérica basada en nodos simplemente enlazados (`Node<E>`, cada uno con una referencia `next` al siguiente). A diferencia de una implementación parcial, esta clase declara `MutableList<E>` como supertipo, por lo que el compilador exige y verifica que todos los métodos del contrato estén correctamente implementados.

Internamente no se mantiene un puntero al último nodo ni al tamaño; ambos se calculan recorriendo la lista cuando se necesitan.

## Requisitos y ejecución

- Kotlin 2.3.20
- JDK 17

Para compilar y ejecutar desde línea de comandos:

```bash
kotlinc SimpleList.kt -include-runtime -d simplelist.jar
java -jar simplelist.jar
```

Diferencias frente a la versión en Java

La implementación final viene de un código base con varios errores que se identificaron y corrigieron durante el desarrollo para corregir bugs y aprovechar al máximo las diferencias entre kotlin y java

- **bug en `get`, `set`, `add(index)` y `remove(index)`**: la versión original arrancaba el contador de índice en `0` pero el recorrido de nodos en `head.next`, lo que desfasaba todas las operaciones con índice distinto de `0` (por ejemplo, `get(1)` devolvía el tercer elemento en vez del segundo).
- retainAll() que saltaba nodos: la lógica de referencia (adaptada de la simpleList de java) avanzaba el puntero `current` dos veces por iteración, saltándose nodos y dejando elementos que debían eliminarse.
- **Centralización del recorrido**: en vez de repetir un `while (nodo != null) { ...; nodo = nodo.next }` en cada método, se definió un helper `nodeSequence()` (usando `generateSequence`) del que se apoyan `contains`, `indexOf`, `lastIndexOf`, `toString`, etc. Esto reduce la duplicación y evita que un mismo bug de recorrido tenga que corregirse en varios lugares.
- 
## Limitaciones conocidas

- Al ser una lista **simplemente** enlazada (sin puntero al nodo anterior ni al último), operaciones como `get(index)`, `set(index, ...)`, `add(index, ...)` y `previous()` en el iterador son **O(n)**, no O(1) como en una implementación con lista doblemente enlazada (`java.util.LinkedList`).
- `size` recorre toda la lista en cada llamada; si se usa muy frecuentemente en un bucle, conviene guardar el valor en una variable local en vez de invocarlo repetidamente.
