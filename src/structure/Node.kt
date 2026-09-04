package structure

data class Node<E>(var value: E, var next: Node<E>? = null ) {

    override fun toString(): String {
        return "Node [value=$value, next=$next]"
    }

}