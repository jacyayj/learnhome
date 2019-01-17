package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_title_notice.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils

class TitleNoticeDialog(context: Context) : Dialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_title_notice)
        DialogUtils.initDialogWidth(window, 1f * 5 / 6)
        confirm.setOnClickListener { dismiss() }
    }
}