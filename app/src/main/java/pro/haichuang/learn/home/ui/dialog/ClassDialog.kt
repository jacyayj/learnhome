package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.dialog_choose_class.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DialogUtils

class ClassDialog(context: Context) : Dialog(context, R.style.Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_choose_class)
        setCanceledOnTouchOutside(true)
        DialogUtils.setBottom(window)
        close.setOnClickListener { dismiss() }
        grid.adapter = CommonAdapter(layoutInflater, R.layout.item_class, arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))
    }
}