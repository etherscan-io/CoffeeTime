package gargoyle.ct.messages.impl

import gargoyle.ct.messages.LocaleProvider
import gargoyle.ct.pref.CTPreferences.SupportedLocales
import gargoyle.ct.pref.CTPreferences.SupportedLocales.Companion.findSimilar
import gargoyle.ct.prop.CTObservableProperty
import gargoyle.ct.prop.impl.simple.CTSimpleEnumProperty
import java.util.*

class CTFixedLocaleProvider constructor(
    private val locale: CTObservableProperty<SupportedLocales> = CTSimpleEnumProperty(findSimilar())
) : LocaleProvider {
    override fun getLocale(): Locale = locale.get().locale

    override fun setLocale(locale: Locale) {
        SupportedLocales.forLocale(locale)?.let { this.locale.set(it) }
    }

    override fun locale(): CTObservableProperty<SupportedLocales> = locale
}
