package gargoyle.ct.ui.impl

import gargoyle.ct.config.CTConfig
import gargoyle.ct.config.convert.CTUnitConverter
import gargoyle.ct.config.convert.impl.CTConfigConverter
import gargoyle.ct.log.Log
import gargoyle.ct.messages.impl.CTMessages
import gargoyle.ct.messages.impl.CTPreferencesLocaleProvider
import gargoyle.ct.pref.CTPreferences
import gargoyle.ct.ui.CTDialog
import gargoyle.ct.ui.CTIconProvider
import gargoyle.ct.util.getResourceName
import java.awt.Component
import java.text.ParseException
import javax.swing.Icon
import javax.swing.ImageIcon
import javax.swing.JFormattedTextField
import javax.swing.JOptionPane
import javax.swing.text.MaskFormatter

class CTNewConfigDialog(private val owner: Component, preferences: CTPreferences, iconProvider: CTIconProvider) :
    CTDialog<CTConfig?> {
    private val configConverter: CTUnitConverter<CTConfig> = CTConfigConverter()
    private val messages: CTMessages
    private var icon: Icon? = null

    init {
        messages = CTMessages(
            CTPreferencesLocaleProvider(preferences),
            CTNewConfigDialog::class.getResourceName(LOC_NEW_CONFIG)
        )
        updateIcon(iconProvider)
        preferences.iconStyle()
            .addPropertyChangeListener { updateIcon(iconProvider) }
    }

    fun updateIcon(iconProvider: CTIconProvider) {
        icon = ImageIcon(iconProvider.mediumIcon)
    }

    override fun showMe(): CTConfig? {
        while (true) {
            val field: JFormattedTextField = try {
                JFormattedTextField(MaskFormatter(STR_CONFIG_PATTERN))
            } catch (e: ParseException) {
                throw IllegalArgumentException(STR_CONFIG_PATTERN, e)
            }
            field.toolTipText = messages.getMessage(STR_NEW_CONFIG_TOOLTIP)
            val result = JOptionPane.showOptionDialog(
                owner,
                field,
                messages.getMessage(STR_TITLE),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon, arrayOf<Any>(
                    messages.getMessage(STR_OK),
                    messages.getMessage(STR_CANCEL)
                ),
                null
            )
            if (result == JOptionPane.OK_OPTION) {
                try {
                    return configConverter.parse(field.value.toString())
                } catch (ex: IllegalArgumentException) {
                    Log.debug(ex, ex.message ?: "")
                }
            }
            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.NO_OPTION) {
                return null
            }
        }
    }

    companion object {
        private const val LOC_NEW_CONFIG = "messages/new_config"
        private const val STR_CANCEL = "cancel"
        private const val STR_CONFIG_PATTERN = "##U/##U/##U"
        private const val STR_NEW_CONFIG_TOOLTIP = "new-config-field.tooltip"
        private const val STR_OK = "ok"
        private const val STR_TITLE = "new-config-title"
    }
}
