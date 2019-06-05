package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_choose_sex.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils

class SexDialog(context: Context, private var result: (c: Int) -> Unit) : Dialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_choose_sex)
        setCanceledOnTouchOutside(true)
        DialogUtils.setBottom(window)
        close.setOnClickListener { dismiss() }
        boy.setOnClickListener {
            result(1)
            dismiss()
        }
        girl.setOnClickListener {
            result(2)
            dismiss()
        }
    }
}