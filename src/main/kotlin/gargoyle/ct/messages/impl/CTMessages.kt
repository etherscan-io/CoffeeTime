package gargoyle.ct.messages.impl

import gargoyle.ct.log.Log
import gargoyle.ct.messages.LocaleProvider
import gargoyle.ct.messages.MessageProvider
import gargoyle.ct.messages.MessageProviderEx
import gargoyle.ct.pref.CTPreferences.SupportedLocales
import gargoyle.ct.prop.CTObservableProperty
import gargoyle.ct.util.get
import java.text.MessageFormat
import java.util.*

class CTMessages(private val localeProvider: LocaleProvider, private val parent: MessageProvider?, baseName: String) :
    MessageProviderEx {
    private lateinit var messages: ResourceBundle
    private lateinit var baseName: String

    constructor(baseName: String) : this(CTFixedLocaleProvider(), null, baseName)
    constructor(parent: MessageProvider?, baseName: String) : this(CTFixedLocaleProvider(), parent, baseName)
    constructor(localeProvider: LocaleProvider, baseName: String) : this(localeProvider, null, baseName)

    init {
        load(baseName)
        localeProvider.locale().addPropertyChangeListener { reload() }
    }

    private fun load(baseName: String) {
        this.baseName = baseName
        try {
            messages = ResourceBundle.getBundle(baseName.replace('/', '.'), localeProvider.getLocale())
        } catch (ex: MissingResourceException) {
            if (parent == null) {
                throw MissingResourceException("Can''t find bundle $baseName", baseName, ex.key)
            } else {
                Log.warn("Can''t find bundle $baseName")
            }
        }
    }

    private fun reload() = load(baseName)

    override fun getLocale(): Locale = localeProvider.getLocale()

    override fun setLocale(locale: Locale): Unit = localeProvider.setLocale(locale)

    override fun locale(): CTObservableProperty<SupportedLocales> = localeProvider.locale()

    override fun getMessage(message: String, vararg args: Any): String {
        if (messages.locale != localeProvider.getLocale()) {
            reload()
        }
        return try {
            val pattern = messages[message]!!
            try {
                MessageFormat.format(pattern, *args)
            } catch (ex: IllegalArgumentException) {
                throw IllegalArgumentException("can''t parse message:$message->$pattern(${args.contentToString()})", ex)
            }
        } catch (ex: MissingResourceException) {
            if (parent == null) {
                val bundle = messages.baseBundleName
                throw MissingResourceException("Can''t find resource for bundle $bundle, key $message", bundle, message)
            } else {
                parent.getMessage(message, *args)
            }
        }
    }

}
