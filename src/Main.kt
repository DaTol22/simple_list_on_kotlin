import structure.SimpleList

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val list : SimpleList<Int> = SimpleList()
    list.add(1)
    list.add(2)
    list.add(3)

    println(list.size)
    list.clear()
    list.add(1)
    println(list.contains(1))
    println(list.contains(2))
    println(list.get(0))
    println(list.isEmpty())




}