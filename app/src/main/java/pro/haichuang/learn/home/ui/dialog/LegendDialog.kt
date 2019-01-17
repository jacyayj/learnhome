package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.jacy.kit.config.show
import kotlinx.android.synthetic.main.dialog_legend.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils

class LegendDialog(context: Context) : Dialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_legend)
        DialogUtils.initDialogWidth(window, 0.66f)
        close.setOnClickListener { dismiss() }
    }

    fun show(type: Int) {
        super.show()
        when (type) {
            0 -> legend_1.show()
            1 -> legend_2.show()
            3 -> {
                title.text = "专业概率"
                legend_3.show()
            }
        }
    }

}