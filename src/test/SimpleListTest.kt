package structure

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName

class SimpleListTest {

    private lateinit var list: SimpleList<Int>

    @BeforeEach
    fun setUp() {
        list = SimpleList()
    }

    @Nested
    @DisplayName("size / isEmpty")
    inner class SizeAndEmpty {

        @Test
        fun `lista nueva esta vacia y size es 0`() {
            assertTrue(list.isEmpty())
            assertEquals(0, list.size)
        }

        @Test
        fun `size refleja la cantidad de elementos agregados`() {
            list.add(1)
            list.add(2)
            list.add(3)
            assertEquals(3, list.size)
            assertFalse(list.isEmpty())
        }
    }

    @Nested
    @DisplayName("contains / containsAll")
    inner class Contains {

        @Test
        fun `contains devuelve true si el elemento esta`() {
            list.addAll(listOf(1, 2, 3))
            assertTrue(list.contains(2))
        }

        @Test
        fun `contains devuelve false si el elemento no esta`() {
            list.addAll(listOf(1, 2, 3))
            assertFalse(list.contains(99))
        }

        @Test
        fun `contains en lista vacia es false`() {
            assertFalse(list.contains(1))
        }

        @Test
        fun `containsAll true cuando todos los elementos estan`() {
            list.addAll(listOf(1, 2, 3, 4))
            assertTrue(list.containsAll(listOf(2, 4)))
        }

        @Test
        fun `containsAll false cuando falta al menos uno`() {
            list.addAll(listOf(1, 2, 3))
            assertFalse(list.containsAll(listOf(2, 99)))
        }
    }

    @Nested
    @DisplayName("add")
    inner class Add {

        @Test
        fun `add agrega al final de la lista`() {
            list.add(1)
            list.add(2)
            assertEquals(listOf(1, 2), list.toList())
        }

        @Test
        fun `add con index 0 en lista vacia`() {
            list.add(0, 10)
            assertEquals(listOf(10), list.toList())
        }

        @Test
        fun `add con index 0 inserta al inicio`() {
            list.addAll(listOf(2, 3))
            list.add(0, 1)
            assertEquals(listOf(1, 2, 3), list.toList())
        }

        @Test
        fun `add con index intermedio inserta en la posicion correcta`() {
            list.addAll(listOf(1, 2, 4))
            list.add(2, 3)
            assertEquals(listOf(1, 2, 3, 4), list.toList())
        }

        @Test
        fun `add con index igual al size agrega al final`() {
            list.addAll(listOf(1, 2))
            list.add(2, 3)
            assertEquals(listOf(1, 2, 3), list.toList())
        }

        @Test
        fun `add con index invalido lanza excepcion`() {
            list.addAll(listOf(1, 2))
            assertThrows(IndexOutOfBoundsException::class.java) {
                list.add(5, 99)
            }
            assertThrows(IndexOutOfBoundsException::class.java) {
                list.add(-1, 99)
            }
        }
    }

    @Nested
    @DisplayName("get / set")
    inner class GetSet {

        @Test
        fun `get devuelve el valor en la posicion correcta`() {
            list.addAll(listOf(10, 20, 30))
            assertEquals(10, list[0])
            assertEquals(20, list[1])
            assertEquals(30, list[2])
        }

        @Test
        fun `get con index invalido lanza excepcion`() {
            list.addAll(listOf(1, 2))
            assertThrows(IndexOutOfBoundsException::class.java) { list[5] }
            assertThrows(IndexOutOfBoundsException::class.java) { list[-1] }
        }

        @Test
        fun `set reemplaza el valor y devuelve el anterior`() {
            list.addAll(listOf(1, 2, 3))
            val old = list.set(1, 99)
            assertEquals(2, old)
            assertEquals(listOf(1, 99, 3), list.toList())
        }

        @Test
        fun `set con index invalido lanza excepcion`() {
            list.addAll(listOf(1, 2))
            assertThrows(IndexOutOfBoundsException::class.java) { list.set(5, 99) }
        }
    }

    @Nested
    @DisplayName("remove / removeAt")
    inner class Remove {

        @Test
        fun `remove por valor elimina la primera coincidencia`() {
            list.addAll(listOf(1, 2, 3, 2))
            assertTrue(list.remove(2))
            assertEquals(listOf(1, 3, 2), list.toList())
        }

        @Test
        fun `remove por valor devuelve false si no existe`() {
            list.addAll(listOf(1, 2, 3))
            assertFalse(list.remove(99))
        }

        @Test
        fun `remove del primer elemento actualiza head`() {
            list.addAll(listOf(1, 2, 3))
            assertTrue(list.remove(1))
            assertEquals(listOf(2, 3), list.toList())
        }

        @Test
        fun `removeAt elimina y devuelve el elemento en esa posicion`() {
            list.addAll(listOf(1, 2, 3))
            val removed = list.removeAt(1)
            assertEquals(2, removed)
            assertEquals(listOf(1, 3), list.toList())
        }

        @Test
        fun `removeAt indice 0 actualiza head correctamente`() {
            list.addAll(listOf(1, 2, 3))
            val removed = list.removeAt(0)
            assertEquals(1, removed)
            assertEquals(listOf(2, 3), list.toList())
        }

        @Test
        fun `removeAt con index invalido lanza excepcion`() {
            list.addAll(listOf(1, 2))
            assertThrows(IndexOutOfBoundsException::class.java) { list.removeAt(5) }
            assertThrows(IndexOutOfBoundsException::class.java) { SimpleList<Int>().removeAt(0) }
        }
    }

    @Nested
    @DisplayName("addAll")
    inner class AddAll {

        @Test
        fun `addAll agrega todos los elementos al final`() {
            list.add(1)
            list.addAll(listOf(2, 3, 4))
            assertEquals(listOf(1, 2, 3, 4), list.toList())
        }

        @Test
        fun `addAll con coleccion vacia devuelve false y no cambia la lista`() {
            list.add(1)
            val changed = list.addAll(emptyList())
            assertFalse(changed)
            assertEquals(listOf(1), list.toList())
        }

        @Test
        fun `addAll con index inserta en medio de la lista`() {
            list.addAll(listOf(1, 4, 5))
            list.addAll(1, listOf(2, 3))
            assertEquals(listOf(1, 2, 3, 4, 5), list.toList())
        }

        @Test
        fun `addAll con index 0 inserta al inicio`() {
            list.addAll(listOf(3, 4))
            list.addAll(0, listOf(1, 2))
            assertEquals(listOf(1, 2, 3, 4), list.toList())
        }

        @Test
        fun `addAll con index igual al size equivale a agregar al final`() {
            list.addAll(listOf(1, 2))
            list.addAll(2, listOf(3, 4))
            assertEquals(listOf(1, 2, 3, 4), list.toList())
        }

        @Test
        fun `addAll con index invalido devuelve false`() {
            list.addAll(listOf(1, 2))
            assertFalse(list.addAll(5, listOf(99)))
        }
    }

    @Nested
    @DisplayName("indexOf / lastIndexOf")
    inner class IndexOf {

        @Test
        fun `indexOf devuelve la primera posicion`() {
            list.addAll(listOf(1, 2, 3, 2))
            assertEquals(1, list.indexOf(2))
        }

        @Test
        fun `indexOf devuelve -1 si no existe`() {
            list.addAll(listOf(1, 2, 3))
            assertEquals(-1, list.indexOf(99))
        }

        @Test
        fun `lastIndexOf devuelve la ultima posicion`() {
            list.addAll(listOf(1, 2, 3, 2))
            assertEquals(3, list.lastIndexOf(2))
        }

        @Test
        fun `lastIndexOf devuelve -1 si no existe`() {
            list.addAll(listOf(1, 2, 3))
            assertEquals(-1, list.lastIndexOf(99))
        }
    }

    @Nested
    @DisplayName("retainAll / removeAll")
    inner class RetainRemoveAll {

        @Test
        fun `retainAll deja solo los elementos presentes en la coleccion`() {
            list.addAll(listOf(1, 2, 3, 4, 5))
            val changed = list.retainAll(listOf(2, 4))
            assertTrue(changed)
            assertEquals(listOf(2, 4), list.toList())
        }

        @Test
        fun `retainAll devuelve false si no hay cambios`() {
            list.addAll(listOf(1, 2))
            val changed = list.retainAll(listOf(1, 2, 3))
            assertFalse(changed)
            assertEquals(listOf(1, 2), list.toList())
        }

        @Test
        fun `retainAll con coleccion vacia deja la lista vacia`() {
            list.addAll(listOf(1, 2, 3))
            val changed = list.retainAll(emptyList())
            assertTrue(changed)
            assertTrue(list.isEmpty())
        }

        @Test
        fun `removeAll elimina los elementos presentes en la coleccion`() {
            list.addAll(listOf(1, 2, 3, 4, 5))
            val changed = list.removeAll(listOf(2, 4))
            assertTrue(changed)
            assertEquals(listOf(1, 3, 5), list.toList())
        }

        @Test
        fun `removeAll devuelve false si ningun elemento coincide`() {
            list.addAll(listOf(1, 2, 3))
            val changed = list.removeAll(listOf(99, 100))
            assertFalse(changed)
            assertEquals(listOf(1, 2, 3), list.toList())
        }
    }

    @Nested
    @DisplayName("clear")
    inner class Clear {

        @Test
        fun `clear deja la lista vacia`() {
            list.addAll(listOf(1, 2, 3))
            list.clear()
            assertTrue(list.isEmpty())
            assertEquals(0, list.size)
        }
    }

    @Nested
    @DisplayName("iterator / listIterator")
    inner class Iteration {

        @Test
        fun `iterator recorre todos los elementos en orden`() {
            list.addAll(listOf(1, 2, 3))
            val result = mutableListOf<Int>()
            for (e in list) result.add(e)
            assertEquals(listOf(1, 2, 3), result)
        }

        @Test
        fun `listIterator next y hasNext funcionan correctamente`() {
            list.addAll(listOf(1, 2, 3))
            val it = list.listIterator()
            assertTrue(it.hasNext())
            assertEquals(1, it.next())
            assertEquals(2, it.next())
            assertEquals(3, it.next())
            assertFalse(it.hasNext())
        }

        @Test
        fun `listIterator previous y hasPrevious funcionan correctamente`() {
            list.addAll(listOf(1, 2, 3))
            val it = list.listIterator(list.size)
            assertTrue(it.hasPrevious())
            assertEquals(3, it.previous())
            assertEquals(2, it.previous())
            assertEquals(1, it.previous())
            assertFalse(it.hasPrevious())
        }

        @Test
        fun `listIterator remove elimina el ultimo elemento devuelto por next`() {
            list.addAll(listOf(1, 2, 3))
            val it = list.listIterator()
            it.next() // 1
            it.next() // 2
            it.remove()
            assertEquals(listOf(1, 3), list.toList())
        }

        @Test
        fun `listIterator set reemplaza el ultimo elemento devuelto`() {
            list.addAll(listOf(1, 2, 3))
            val it = list.listIterator()
            it.next()
            it.set(99)
            assertEquals(listOf(99, 2, 3), list.toList())
        }

        @Test
        fun `listIterator add inserta en la posicion actual`() {
            list.addAll(listOf(1, 3))
            val it = list.listIterator()
            it.next() // 1
            it.add(2)
            assertEquals(listOf(1, 2, 3), list.toList())
        }

        @Test
        fun `listIterator remove sin next previo lanza excepcion`() {
            list.addAll(listOf(1, 2))
            val it = list.listIterator()
            assertThrows(IllegalStateException::class.java) { it.remove() }
        }

        @Test
        fun `listIterator con index invalido lanza excepcion`() {
            list.addAll(listOf(1, 2))
            assertThrows(IndexOutOfBoundsException::class.java) { list.listIterator(5) }
        }
    }

    @Nested
    @DisplayName("subList")
    inner class SubList {

        @Test
        fun `subList devuelve el rango fromIndex hasta toIndex exclusivo`() {
            list.addAll(listOf(1, 2, 3, 4, 5))
            val sub = list.subList(1, 4)
            assertEquals(listOf(2, 3, 4), sub.toList())
        }

        @Test
        fun `subList con rango vacio devuelve lista vacia`() {
            list.addAll(listOf(1, 2, 3))
            val sub = list.subList(1, 1)
            assertTrue(sub.isEmpty())
        }

        @Test
        fun `subList con rango invalido lanza excepcion`() {
            list.addAll(listOf(1, 2, 3))
            assertThrows(IndexOutOfBoundsException::class.java) { list.subList(2, 1) }
            assertThrows(IndexOutOfBoundsException::class.java) { list.subList(0, 10) }
            assertThrows(IndexOutOfBoundsException::class.java) { list.subList(-1, 2) }
        }
    }

    @Nested
    @DisplayName("toString / equals / hashCode")
    inner class ObjectMethods {

        @Test
        fun `toString muestra los elementos entre corchetes`() {
            list.addAll(listOf(1, 2, 3))
            assertEquals("[1, 2, 3]", list.toString())
        }

        @Test
        fun `toString de lista vacia`() {
            assertEquals("[]", list.toString())
        }
    }
}