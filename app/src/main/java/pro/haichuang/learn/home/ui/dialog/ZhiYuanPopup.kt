package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.jacy.kit.adapter.CommonAdapter
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.popup_zhiyuan.view.*
import pro.haichuang.learn.home.R

class ZhiYuanPopup(private val layoutInflater: LayoutInflater, result: (res: String) -> Unit = {}) : PopupWindow(DensityUtil.dp2px(75f), WindowManager.LayoutParams.WRAP_CONTENT) {

    private val data by lazy { arrayListOf("A", "B", "C", "D", "E", "F") }

    init {
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_zhiyuan, null)
        contentView.listView.adapter = CommonAdapter(layoutInflater, R.layout.item_zhiyuan_popup, data)
        contentView.listView.setOnItemClickListener { _, _, position, _ ->
            result(data[position] + "志愿")
            dismiss()
        }
    }

    fun show(view: View, name: String) {
        showAsDropDown(view)
        val index = data.indexOf(name.substring(0, 1))
        if (index != -1)
            contentView.listView.setItemChecked(index, true)
        else
            contentView.listView.clearChoices()
    }

}