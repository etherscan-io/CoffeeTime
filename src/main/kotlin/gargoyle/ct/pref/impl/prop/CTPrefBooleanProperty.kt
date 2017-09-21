package gargoyle.ct.pref.impl.prop

import gargoyle.ct.pref.CTPreferencesProvider

class CTPrefBooleanProperty constructor(
    provider: CTPreferencesProvider,
    name: String,
    def: Boolean = false
) : CTPrefProperty<Boolean>(
    Boolean::class, provider, name, def
)