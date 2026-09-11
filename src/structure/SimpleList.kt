package structure

class SimpleList<E>(var head: Node<E>? = null) : MutableList<E> {

    override val size: Int
        get() = nodeSequence().count()

    override fun isEmpty(): Boolean {
        return head == null
    }

    override fun contains(element: E): Boolean {
        return nodeSequence().any { it.value == element }
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        return elements.all { contains(it) }
    }

    // TODO COMENTARIO PARA ENTENDER LOS HELPERS (nos ahorraron el uso de whiles en varois métodos)
    // Helpers privados: centralizan el recorrido de la lista enlazada
    // para no repetir el mismo while en cada método.

    private fun nodeSequence(): Sequence<Node<E>> = generateSequence(head) { it.next }

    private fun nodeAt(index: Int): Node<E>? = nodeSequence().elementAtOrNull(index)


    override fun iterator(): MutableIterator<E> = listIterator()

    override fun add(element: E): Boolean {
        val newNode = Node(element)
        val last = nodeSequence().lastOrNull()
        if (last == null) {
            head = newNode
        } else {
            last.next = newNode
        }
        return true
    }

    override fun remove(element: E): Boolean {
        if (head == null) return false
        if (head!!.value == element) {
            head = head!!.next
            return true
        }
        var previous = head
        var current = head!!.next
        while (current != null) {
            if (current.value == element) {
                previous!!.next = current.next
                return true
            }
            previous = current
            current = current.next
        }
        return false
    }

    override fun addAll(elements: Collection<E>): Boolean {
        var changed = false
        elements.forEach {
            if (add(it)) changed = true
        }
        return changed
    }

    override fun addAll(index: Int, elements: Collection<E>): Boolean {
        if (index < 0 || index > size) return false
        if (elements.isEmpty()) return false

        var predecessor: Node<E>? = null
        var successor = head
        var counter = 0
        while (counter < index) {
            predecessor = successor
            successor = successor?.next
            counter++
        }

        var current = predecessor
        for (element in elements) {
            val newNode = Node(element)
            if (current == null) {
                head = newNode
            } else {
                current.next = newNode
            }
            current = newNode
        }
        current?.next = successor
        return true
    }

    override fun clear() {
        head = null
    }

    override fun get(index: Int): E {
        return nodeAt(index)?.value
            ?: throw IndexOutOfBoundsException("Index: $index, Size: $size")
    }

    override fun set(index: Int, element: E): E {
        val node = nodeAt(index)
            ?: throw IndexOutOfBoundsException("Index: $index, Size: $size")
        val old = node.value
        node.value = element
        return old
    }

    override fun add(index: Int, element: E) {
        if (index < 0 || index > size) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
        val newNode = Node(element)
        if (index == 0) {
            newNode.next = head
            head = newNode
            return
        }
        val previous = nodeAt(index - 1)!!
        newNode.next = previous.next
        previous.next = newNode
    }

    override fun removeAt(index: Int): E {
        if (head == null || index < 0 || index >= size) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
        if (index == 0) {
            val old = head!!.value
            head = head!!.next
            return old
        }
        val previous = nodeAt(index - 1)!!
        val target = previous.next!!
        previous.next = target.next
        return target.value
    }

    override fun indexOf(element: E): Int {
        return nodeSequence().indexOfFirst { it.value == element }
    }

    override fun lastIndexOf(element: E): Int {
        var last = -1
        nodeSequence().forEachIndexed { i, node ->
            if (node.value == element) last = i
        }
        return last
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        var result = false
        var previous: Node<E>? = null
        var current = head
        while (current != null) {
            if (!elements.contains(current.value)) {
                result = true
                if (previous == null) {
                    head = current.next
                } else {
                    previous.next = current.next
                }
            } else {
                previous = current
            }
            current = current.next
        }
        return result
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        var result = false
        var previous: Node<E>? = null
        var current = head
        while (current != null) {
            if (elements.contains(current.value)) {
                result = true
                if (previous == null) {
                    head = current.next
                } else {
                    previous.next = current.next
                }
            } else {
                previous = current
            }
            current = current.next
        }
        return result
    }

    override fun listIterator(): MutableListIterator<E> = listIterator(0)

    override fun listIterator(index: Int): MutableListIterator<E> {
        if (index < 0 || index > size) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
        return object : MutableListIterator<E> {
            var currentIndex = index
            var lastReturnedIndex = -1

            override fun hasNext(): Boolean = currentIndex < size

            override fun next(): E {
                if (!hasNext()) throw NoSuchElementException()
                val value = get(currentIndex)
                lastReturnedIndex = currentIndex
                currentIndex++
                return value
            }

            override fun hasPrevious(): Boolean = currentIndex > 0

            override fun previous(): E {
                if (!hasPrevious()) throw NoSuchElementException()
                currentIndex--
                lastReturnedIndex = currentIndex
                return get(currentIndex)
            }

            override fun nextIndex(): Int = currentIndex

            override fun previousIndex(): Int = currentIndex - 1

            override fun remove() {
                if (lastReturnedIndex == -1) throw IllegalStateException()
                this@SimpleList.removeAt(lastReturnedIndex)
                if (lastReturnedIndex < currentIndex) currentIndex--
                lastReturnedIndex = -1
            }

            override fun set(element: E) {
                if (lastReturnedIndex == -1) throw IllegalStateException()
                this@SimpleList.set(lastReturnedIndex, element)
            }

            override fun add(element: E) {
                this@SimpleList.add(currentIndex, element)
                currentIndex++
                lastReturnedIndex = -1
            }
        }
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw IndexOutOfBoundsException("fromIndex: $fromIndex, toIndex: $toIndex, Size: $size")
        }
        val result = SimpleList<E>()
        for (i in fromIndex until toIndex) {
            result.add(get(i))
        }
        return result
    }

    override fun toString(): String {
        return nodeSequence().joinToString(prefix = "[", postfix = "]") { it.value.toString() }
    }
}