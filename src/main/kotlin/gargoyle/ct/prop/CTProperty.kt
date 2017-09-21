package gargoyle.ct.prop

interface CTProperty<T : Any> : CTReadOnlyProperty<T> {
    fun set(value: T)
}