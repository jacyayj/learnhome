package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.popup_single_choose.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.NameId

class SingleChoosePopup(view: View, data: ArrayList<NameId>, result: (id: String) -> Unit) : PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT) {

    init {
        val layoutInflater = LayoutInflater.from(view.context)
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_single_choose, null)
        contentView.listView.adapter = CommonAdapter(layoutInflater, R.layout.item_popup_single_choose, data)
        contentView.listView.setOnItemClickListener { _, _, position, _ ->
            result(data[position].id)
            dismiss()
        }
    }

}