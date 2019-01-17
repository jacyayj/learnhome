package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.jacy.kit.adapter.CommonAdapter
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.popup_choose_pici.view.*
import kotlinx.android.synthetic.main.popup_zhiyuan.view.*
import org.w3c.dom.Text
import pro.haichuang.learn.home.R

class ZhiYuanPopup(private val view: View, result: (res: String) -> Unit = {}) : PopupWindow(DensityUtil.dp2px(70f), WindowManager.LayoutParams.WRAP_CONTENT) {

    private val layoutInflater by lazy { LayoutInflater.from(view.context) }
    private val data by lazy { arrayListOf("A", "B", "C", "D", "E", "F", "G", "H", "I") }

    init {
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_zhiyuan, null)
        contentView.listView.adapter = CommonAdapter(layoutInflater, R.layout.item_zhiyuan_popup, data)
        contentView.listView.setOnItemClickListener { _, _, position, _ ->
            result(data[position] + "志愿")
            dismiss()
        }

    }

    fun show() {
        showAsDropDown(view)
    }
}