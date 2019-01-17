package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_zhiyuan_school.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils

class ZhiYuanSchoolDialog(context: Context) : Dialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_zhiyuan_school)
        DialogUtils.initDialogWidth(window, 1f)
        close.setOnClickListener { dismiss() }
    }

    fun show(type: Int) {
        super.show()
    }

}