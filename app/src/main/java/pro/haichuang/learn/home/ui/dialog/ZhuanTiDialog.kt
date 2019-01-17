package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.dialog_choose_address.*
import kotlinx.android.synthetic.main.dialog_zhuanti.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils

class ZhuanTiDialog(context: Context) : Dialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_zhuanti)
        setCanceledOnTouchOutside(true)
        DialogUtils.initDialogWidth(window, 0.7f)
        confirm.setOnClickListener { dismiss() }
    }
}