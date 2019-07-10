package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.dialog_zhiyuan_result.*
import kotlinx.android.synthetic.main.item_dialog_zhiyuan.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.utils.DialogUtils

class ZhiYuanResultDialog(context: Context, private val result: () -> Unit, private val remove: (id: Int) -> Unit) : Dialog(context, R.style.Dialog) {

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

    fun show(arr: ArrayList<CollegeModel>) {
        super.show()
        listView.adapter = CommonAdapter(LayoutInflater.from(context), R.layout.item_dialog_zhiyuan, arr) { view, t, position ->
            view.delete.setOnClickListener {
                (listView.adapter as CommonAdapter<*>).remove(position)
                remove(t.id)
            }
        }
    }

}