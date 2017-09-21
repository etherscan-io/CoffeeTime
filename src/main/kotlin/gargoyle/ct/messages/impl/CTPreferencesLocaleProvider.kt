package gargoyle.ct.messages.impl

import gargoyle.ct.messages.LocaleProvider
import gargoyle.ct.pref.CTPreferences
import gargoyle.ct.pref.CTPreferences.SupportedLocales
import gargoyle.ct.prop.CTObservableProperty
import java.util.*

class CTPreferencesLocaleProvider private constructor(
    private val locale: CTObservableProperty<SupportedLocales>
) : LocaleProvider {
    constructor(preferences: CTPreferences) : this(preferences.supportedLocales())

    override fun getLocale(): Locale = locale.get().locale

    override fun setLocale(locale: Locale) {
        SupportedLocales.forLocale(locale)?.let { this.locale.set(it) }
    }

    override fun locale(): CTObservableProperty<SupportedLocales> = locale
}
