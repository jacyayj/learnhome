package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.jacy.kit.utils.DialogUtils
import com.vondear.rxtool.RxEncryptTool
import com.vondear.rxtool.RxKeyboardTool
import kotlinx.android.synthetic.main.dialog_password.*
import pro.haichuang.learn.home.R

class PasswordDialog(context: Context, private val result: (pwd: String) -> Unit) : Dialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_password)
        DialogUtils.setBottom(window, 1f)
        setCanceledOnTouchOutside(false)
        close.setOnClickListener {
            dismiss()
        }
        pwd.setOnTextChangeListener { s, b ->
            if (b) {
                result(RxEncryptTool.encryptMD5ToString(s))
                dismiss()
            }
        }

    }

    override fun show() {
        super.show()
        pwd.post {
            RxKeyboardTool.showSoftInput(context, pwd)
        }
    }
}