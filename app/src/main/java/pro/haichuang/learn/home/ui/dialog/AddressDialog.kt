package pro.haichuang.learn.home.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.dialog_choose_address.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.AreaBean
import pro.haichuang.learn.home.utils.DialogUtils

class AddressDialog(context: Context, private val result: (qx: String) -> Unit) : Dialog(context, R.style.Dialog) {

    private val adapter by lazy { CommonAdapter<AreaBean>(layoutInflater, R.layout.item_address) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_choose_address)
        setCanceledOnTouchOutside(true)
        DialogUtils.setBottom(window)
        close.setOnClickListener { dismiss() }
        grid.adapter = adapter
        grid.setOnItemClickListener { _, _, position, _ ->
            result(adapter.getItem(position).city_name)
            dismiss()
        }
    }

    fun show(data: ArrayList<AreaBean>?) {
        super.show()
        data?.let { adapter.refresh(it) }
    }
}