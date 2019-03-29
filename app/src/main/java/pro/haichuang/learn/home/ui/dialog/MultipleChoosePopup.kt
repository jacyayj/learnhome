package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.popup_multiple_choose.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.utils.DataUtils

class MultipleChoosePopup(view: View, result: (ids: String) -> Unit = {}) : PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT) {
    private val data by lazy { DataUtils.piCiData }

    init {
        val layoutInflater = LayoutInflater.from(view.context)
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_multiple_choose, null)
        contentView.listView.adapter = CommonAdapter(layoutInflater, R.layout.item_popup_multiple_choose, data)
        contentView.confirm.setOnClickListener {
            var ids = ""
            for (i in 0..data.lastIndex) {
                if (contentView.listView.isItemChecked(i))
                    ids += "${data[i].id},"
            }
            result(ids.removeSuffix(","))
            dismiss()
        }
    }

}