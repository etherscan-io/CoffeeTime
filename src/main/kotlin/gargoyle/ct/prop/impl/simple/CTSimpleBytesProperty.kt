package gargoyle.ct.prop.impl.simple

class CTSimpleBytesProperty constructor(name: String, def: ByteArray = ByteArray(0)) :
    CTSimpleProperty<ByteArray>(name, def)