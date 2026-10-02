import java.awt.BorderLayout
import java.awt.Component
import java.awt.FlowLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JPasswordField
import javax.swing.JTextField

/**
 * GUI-форма входа (iLO3 remote console): адрес host:port (по умолч. 443), логин, пароль.
 */
class LoginDialog : JDialog() {
    private val serverField = JTextField(22)
    private val userField = JTextField("Administrator", 22)
    private val passField = JPasswordField(22)

    var ok = false
    var server = ""
    var user = ""
    var password = ""

    init {
        title = "iLO3 Remote Console - вход"
        isModal = true
        defaultCloseOperation = DISPOSE_ON_CLOSE

        val form = JPanel(GridBagLayout())
        val c = GridBagConstraints()
        c.insets = Insets(4, 6, 4, 6)
        c.anchor = GridBagConstraints.WEST
        var y = 0
        fun row(label: String, comp: Component) {
            c.gridx = 0; c.gridy = y; form.add(JLabel(label), c)
            c.gridx = 1; c.gridy = y; form.add(comp, c); y++
        }
        row("Адрес сервера (host:port):", serverField)
        row("Логин:", userField)
        row("Пароль:", passField)

        val okBtn = JButton("Подключиться")
        val cancelBtn = JButton("Отмена")
        okBtn.addActionListener { collect(); ok = true; dispose() }
        cancelBtn.addActionListener { dispose() }
        val bp = JPanel(FlowLayout(FlowLayout.RIGHT))
        bp.add(okBtn); bp.add(cancelBtn)

        rootPane.defaultButton = okBtn
        contentPane.add(form, BorderLayout.CENTER)
        contentPane.add(bp, BorderLayout.SOUTH)
        pack()
        setLocationRelativeTo(null)
    }

    private fun collect() {
        server = serverField.text.trim()
        user = userField.text.trim()
        password = String(passField.password)
        if (server.isNotEmpty() && !server.contains(":")) server += ":443"
    }
}
