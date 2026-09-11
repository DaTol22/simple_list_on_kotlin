package structure

class SimpleList<E>(var head : Node<E>? = null) : Collection<E> {

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

    fun contains(o: Any) : Boolean{
        var aux = head
        while(aux != null){
            if (aux.value == o){
                return true
            }
            aux = aux.next
        }
        return false
    }

    fun containsAll (c : Collection<*>) : Boolean {
        val aux = c.toSet()
        aux.forEach{
            if(!contains(it)){
                return false            
            }    
        }return true // se puede refactorizar con un c.all {contains(it)}
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
        var aux : SimpleList<E> = SimpleList()
        var counter : Int = 0
        while(counter < size()){
            if(counter == index){
                aux.addAll(c)
            }
            aux.add(this[counter])
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

    fun remove(e : E) Boolean{
        if (head != null) {        
            if(head.value == e){
                head = head.next
                return true
            }
            var previousNode = head
            var currentNode = head.next
            while(currentNode != null){
                if(currentNode.value == e){
                    if(currentNode.next != null){
                        previousNode.next = currentNode.next
                    }else{
                        previousNode.next = null
                    }
                    return true
                }
                previousNode = currentNode
                currentNode = currentNode.next
            }
        }return false
    }

    fun remove(index : Int) Boolean{
        if (head != null) {        
            if(index == 0){
                head = head.next
                return true
            }
            Int counter = 0
            var previousNode = head
            var currentNode = head.next
            while(currentNode != null){
                if(counter == index){
                    if(currentNode.next != null){
                        previousNode.next = currentNode.next
                    }else{
                        previousNode.next = null
                    }
                    return true
                }
                previousNode = currentNode
                currentNode = currentNode.next
                counter++
            }
        }return false
    }

    fun clear(){
        head = null
    }

    fun get(index : Int) Boolean{
        if (head != null) {        
            if(index == 0){
                return head
            }
            var currentNode = head.next
            Int counter = 0
            while(currentNode != null){
                if(counter == index){
                    return currentNode
                }
                currentNode = currentNode.next
                counter++
            }
        }return null
    }

    fun set(index : Int, element : E) Node<E>{
        if (head != null) {  
            var infoToSave      
            if(index == 0){
                infoToSave = head.value
                head.value = element
                return infoToSave
            }
            var currentNode = head.next
            Int counter = 0
            while(currentNode != null){
                if(counter == index){
                    infoToSave = currentNode.value
                    currentNode.value = element
                    return infoToSave
                }
                currentNode = currentNode.next
                counter++
            }
        }return null
    }

    fun add (index : Int, element : E) Boolean{
        if(head != null){
            val newNode : Node<E> = Node(e)
            if(index == 0){
                newNode.next = head
            }
            var previousNode = head
            var currentNode = head.next
            while(currentNode != null){
                if(counter == index){
                    previousNode.next = newNode
                    newNode.next = currentNode
                }
                previousNode = currentNode
                currentNode = currentNode.next
                counter++
            }
        }return false
    }

}