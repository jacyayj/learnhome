package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_invate.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils

class InvateDialog(context: Context) : Dialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_invate)
        DialogUtils.initDialogWidth(window, 0.8f)
        close.setOnClickListener { dismiss() }
    }

}