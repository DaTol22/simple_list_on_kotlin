package structure

class SimpleList<E>(var head : Node<E>) : List<E>, Collection<E> {

    override val size: Int
}