package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.vondear.rxtool.RxEncryptTool
import kotlinx.android.synthetic.main.dialog_password.*
import pro.haichuang.learn.home.R

class PasswordDialog(context: Context, private val result: (pwd: String) -> Unit) : Dialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_password)
        setCanceledOnTouchOutside(false)
        close.setOnClickListener {
            dismiss()
        }
        pwd.requestFocus()
        pwd.setOnTextChangeListener { s, b ->
            if (b) {
                result(RxEncryptTool.encryptMD5ToString(s))
                dismiss()
            }
        }
    }
}