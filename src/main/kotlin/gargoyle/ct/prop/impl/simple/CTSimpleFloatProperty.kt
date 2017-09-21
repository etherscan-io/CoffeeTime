package gargoyle.ct.prop.impl.simple

import gargoyle.ct.prop.CTNumberProperty

class CTSimpleFloatProperty constructor(name: String, def: Float = 0f) :
    CTSimpleProperty<Float>(name, def), CTNumberProperty<Float>
