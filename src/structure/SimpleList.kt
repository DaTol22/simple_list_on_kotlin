package structure

class SimpleList<E>(var head : Node<E>? = null, override val size: Int = 0) : Collection<E> {

    fun size(): Int{
        var current = head
        var count = 0
        while (current != null) {
            count++
            current = current.next
        }
        return count
    }


    override fun isEmpty() : Boolean{
        return head == null
    }

    override fun contains(e: E) : Boolean{
        var aux = head
        while(aux != null){
            if (aux.value == e){
                return true
            }
            aux = aux.next
        }
        return false
    }

    override fun containsAll (c : Collection<E>) : Boolean {
        val aux = c.toSet()
        aux.forEach{
            if(!contains(it)){
                return false            
            }    
        }
        return true // se puede refactorizar con un c.all {contains(it)}
    }

    fun addAll (c : Collection<E>){
        c.forEach{
            add(it) 
        }
    }

    fun addAll(index : Int, c : Collection<E>){
        if(index == size()){
            addAll(c)
            return
        }
        val aux : SimpleList<E> = SimpleList()
        var counter = 0
        while(counter < size()){
            if(counter == index){
                aux.addAll(c)
            }
            aux.add(get(counter) as E)
            counter++
        }
        clear()
        addAll(aux)
    }

    override fun iterator() : Iterator<E>{
        return object : Iterator<E> {
            var actual = head
            override fun hasNext(): Boolean {
                return actual != null
            }
            override fun next(): E {
                val curr = actual ?: throw NoSuchElementException()
                actual = curr.next
                return curr.value
            }
        }
    }

    fun toArray(): Array<Any?> {
        var aux = head
        return Array(size()) {
            val value = aux?.value
            aux = aux?.next
            value
        }
    }

    inline fun <reified E> toArray(a: Array<E>): Array<E> {
        val currentSize = size
        val result = if (a.size < currentSize) arrayOfNulls<E>(currentSize) as Array<E> else a
        var aux = head
        var i = 0
        while (aux != null && i < currentSize) {
            result[i++] = aux.value as E
            aux = aux.next
        }
        if (result.size > currentSize) {
            (result as Array<E?>)[currentSize] = null
        }
        return result
    }

    fun add(e : E) : Boolean {
        val newNode : Node<E> = Node(e)
        if (head == null) {
            head = newNode
        }
        else{
            var aux = head
            while(aux?.next != null){
                aux = aux.next
            }
            aux!!.next = newNode
        }
        return true;
    }

    fun remove(e : E) : Boolean{
        if (head != null) {        
            if(head!!.value == e){
                head = head!!.next
                return true
            }
            var previousNode = head
            var currentNode = head!!.next
            while(currentNode != null){
                if(currentNode.value == e){
                    if(currentNode.next != null){
                        previousNode!!.next = currentNode.next
                    }else{
                        previousNode!!.next = null
                    }
                    return true
                }
                previousNode = currentNode
                currentNode = currentNode.next
            }
        }
        return false
    }

    fun remove(index : Int) : Boolean{
        if (head != null) {        
            if(index == 0){
                head = head!!.next
                return true
            }
            var counter = 0
            var previousNode = head
            var currentNode = head!!.next
            while(currentNode != null){
                if(counter == index){
                    if(currentNode.next != null){
                        previousNode!!.next = currentNode.next
                    }else{
                        previousNode!!.next = null
                    }
                    return true
                }
                previousNode = currentNode
                currentNode = currentNode.next
                counter++
            }
        }
        return false
    }

    fun clear(){
        head = null
    }

    fun get(index : Int) : E? {
        if (head != null) {        
            if(index == 0){
                return head!!.value
            }
            var currentNode = head!!.next
            var counter = 0
            while(currentNode != null){
                if(counter == index){
                    return currentNode!!.value
                }
                currentNode = currentNode.next
                counter++
            }
        }
        return null
    }

    fun set(index : Int, element : E) {
        if (head != null) {  
            var infoToSave : E
            if(index == 0){
                infoToSave = head!!.value
                head!!.value = element
            }
            var currentNode = head!!.next
            var counter = 0
            while(currentNode != null){
                if(counter == index){
                    infoToSave = currentNode.value
                    currentNode.value = element
                }
                currentNode = currentNode.next
                counter++
            }
        }
    }

    fun add (index : Int, element : E) : Boolean{
        if(head != null){
            val newNode : Node<E> = Node(element)
            if(index == 0){
                newNode.next = head
            }
            var previousNode = head
            var currentNode = head!!.next
            var counter = 0
            while(currentNode != null){
                if(counter == index){
                    previousNode!!.next = newNode
                    newNode.next = currentNode
                }
                previousNode = currentNode
                currentNode = currentNode.next
                counter++
            }
        }
        return false
    }

    fun indexOf(e: E): Int {
        var aux = head
        var index = 0
        while (aux != null) {
            if (aux.value == e) {
                return index
            }
            aux = aux.next
            index++
        }
        return -1
    }

    fun retainAll(c: Collection<E>): Boolean {
        var result = false
        var previous: Node<E>? = null
        var current = head
        while (current != null) {
            if (!c.contains(current.value)) {
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

    fun listIterator(): MutableListIterator<E> {
        return listIterator(0)
    }

    fun listIterator(index: Int): MutableListIterator<E> {
        if (index < 0 || index > size()) {
            throw IndexOutOfBoundsException()
        }
        return object : MutableListIterator<E> {
            var currentIndex = index
            var lastReturnedIndex = -1

            override fun hasNext(): Boolean = currentIndex < size()

            override fun next(): E {
                if (!hasNext()) throw NoSuchElementException()
                val value = get(currentIndex)!!
                lastReturnedIndex = currentIndex
                currentIndex++
                return value
            }

            override fun hasPrevious(): Boolean = currentIndex > 0

            override fun previous(): E {
                if (!hasPrevious()) throw NoSuchElementException()
                currentIndex--
                lastReturnedIndex = currentIndex
                return get(currentIndex)!!
            }

            override fun nextIndex(): Int = currentIndex

            override fun previousIndex(): Int = currentIndex - 1

            override fun remove() {
                if (lastReturnedIndex == -1) throw IllegalStateException()
                this@SimpleList.remove(lastReturnedIndex)
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




}