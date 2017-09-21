package gargoyle.ct.prop.impl.simple

import gargoyle.ct.prop.CTNumberProperty

class CTSimpleLongProperty constructor(name: String, def: Long = 0L) :
    CTSimpleProperty<Long>(name, def), CTNumberProperty<Long>
