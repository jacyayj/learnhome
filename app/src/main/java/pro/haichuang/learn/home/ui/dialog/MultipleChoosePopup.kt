package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.popup_multiple_choose.view.*
import pro.haichuang.learn.home.R

class MultipleChoosePopup(view: View, val data: ArrayList<String>) : PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT) {

    init {
        val layoutInflater = LayoutInflater.from(view.context)
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_multiple_choose, null)
        contentView.listView.adapter = CommonAdapter(layoutInflater, R.layout.item_popup_multiple_choose, data)
    }

}