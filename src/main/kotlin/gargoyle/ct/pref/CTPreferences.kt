package gargoyle.ct.pref

import gargoyle.ct.config.CTConfig
import gargoyle.ct.messages.Described
import gargoyle.ct.pref.impl.prop.CTPrefProperty
import java.util.*

interface CTPreferences : CTPreferencesManager {
    fun block(): CTPrefProperty<Boolean>
    fun config(): CTPrefProperty<CTConfig>
    fun iconStyle(): CTPrefProperty<IconStyle>
    fun sound(): CTPrefProperty<Boolean>
    fun supportedLocales(): CTPrefProperty<SupportedLocales>
    fun transparency(): CTPrefProperty<Boolean>
    fun transparencyLevel(): CTPrefProperty<Int>
    enum class IconStyle(val path: String, override val description: String) : Described {
        BW("bw", "icon-style.bw"), WB("wb", "icon-style.wb");

        override fun toString(): String {
            return description
        }
    }

    enum class SupportedLocales(  //NON-NLS
        val locale: Locale
    ) : Described {
        EN(Locale.ENGLISH), RU(Locale("ru", "RU"));

        override val description: String
            get() = locale.displayName

        companion object {
            fun findSimilar(locale: Locale = Locale.getDefault(), def: SupportedLocales = EN): SupportedLocales =
                values().firstOrNull { value: SupportedLocales -> isSimilar(value.locale, locale) } ?: def

            private fun isSimilar(locale1: Locale, locale2: Locale): Boolean = locale1 == locale2

            fun forLocale(locale: Locale): SupportedLocales? = values().firstOrNull { isSimilar(it.locale, locale) }
        }
    }

    companion object {
        const val PREF_BLOCK: String = "block"
        const val PREF_CONFIG: String = "config"
        const val PREF_ICON_STYLE: String = "icon-style"
        const val OPACITY_PERCENT: Int = 100
        const val PREF_SOUND: String = "sound"
        const val PREF_TRANSPARENCY: String = "transparency"
        const val PREF_TRANSPARENCY_LEVEL: String = "transparency-level"
    }
}
