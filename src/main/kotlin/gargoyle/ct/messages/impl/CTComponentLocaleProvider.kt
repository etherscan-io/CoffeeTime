package gargoyle.ct.messages.impl

import gargoyle.ct.messages.LocaleProvider
import gargoyle.ct.pref.CTPreferences.SupportedLocales
import gargoyle.ct.prop.CTObservableProperty
import gargoyle.ct.ui.prop.CTComponentLocaleProperty
import java.awt.Component
import java.util.*

class CTComponentLocaleProvider private constructor(private val locale: CTObservableProperty<SupportedLocales>) :
    LocaleProvider {
    constructor(component: Component) : this(CTComponentLocaleProperty(component))

    override fun getLocale(): Locale = locale.get().locale

    override fun setLocale(locale: Locale) {
        SupportedLocales.forLocale(locale)?.let { this.locale.set(it) }
    }

    override fun locale(): CTObservableProperty<SupportedLocales> = locale
}
