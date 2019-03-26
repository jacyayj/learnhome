package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.jacy.kit.config.show
import kotlinx.android.synthetic.main.dialog_notice.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils

class NoticeDialog(context: Context, private val result: () -> Unit = {}) : Dialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_notice)
        DialogUtils.initDialogWidth(window, 0.66f)
        cancel.setOnClickListener { dismiss() }
        confirm.setOnClickListener {
            result()
            dismiss()
        }
    }

    fun show(title: String, content: String = "") {
        super.show()
        msg_title.text = title
        if (content.isNotEmpty()) {
            msg_content.show()
            msg_content.text = content
        }
    }

}