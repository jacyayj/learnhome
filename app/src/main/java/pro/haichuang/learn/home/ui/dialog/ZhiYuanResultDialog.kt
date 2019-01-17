package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.jacy.kit.config.show
import kotlinx.android.synthetic.main.dialog_zhiyuan_result.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils

class ZhiYuanResultDialog(context: Context, private val result: () -> Unit) : Dialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_zhiyuan_result)
        DialogUtils.initDialogWidth(window, 1f)
        close.setOnClickListener { dismiss() }
        confirm.setOnClickListener {
            result()
            dismiss()
        }
    }

    fun show(type: Int) {
        super.show()
    }

}