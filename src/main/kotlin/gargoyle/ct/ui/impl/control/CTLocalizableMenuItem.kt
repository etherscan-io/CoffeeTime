package gargoyle.ct.ui.impl.control

import gargoyle.ct.messages.LocaleProvider
import javax.swing.JMenuItem

class CTLocalizableMenuItem(provider: LocaleProvider, action: CTLocalizableAction) : JMenuItem(action) {
    init {
        provider.locale()
            .addPropertyChangeListener { update(action) }
        action.init(this)
    }

    private fun update(action: CTLocalizableAction) {
        text = action.localizedText
        toolTipText = action.toolTipLocalizedText
    }
}
