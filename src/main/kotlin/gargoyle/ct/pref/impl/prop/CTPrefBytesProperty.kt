package gargoyle.ct.pref.impl.prop

import gargoyle.ct.pref.CTPreferencesProvider

class CTPrefBytesProperty constructor(
    provider: CTPreferencesProvider,
    name: String,
    def: ByteArray = ByteArray(0)
) : CTPrefProperty<ByteArray>(
    ByteArray::class, provider, name, def
)
