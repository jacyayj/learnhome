package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.jacy.kit.config.toast
import kotlinx.android.synthetic.main.dialog_input.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils

class InputDialog(context: Context, private val result: (str: String) -> Unit) : Dialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_input)
        DialogUtils.initDialogWidth(window, 0.8f)
        confirm.setOnClickListener {
            if (remark.text.toString().isEmpty()) {
                toast(context.getString(R.string.input_remark))
                return@setOnClickListener
            }
            result(remark.text.toString())
            dismiss()
        }
    }

}