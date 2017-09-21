package gargoyle.ct.prop.impl.ro

import gargoyle.ct.util.simpleClassName
import kotlin.reflect.KClass

class CTReadOnlyEnumProperty<E : Enum<E>> : CTBaseReadOnlyProperty<E> {
    constructor(type: KClass<E>, value: E) : super(type.simpleClassName, value)
    constructor(name: String, value: E) : super(name, value)
    constructor(value: E) : super(value::class.simpleClassName, value)
}
