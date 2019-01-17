package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.gone
import kotlinx.android.synthetic.main.popup_double_choose.view.*
import pro.haichuang.learn.home.R

class DoubleChoosePopup(private val view: View) : PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT) {
    private val layoutInflater = LayoutInflater.from(view.context)

    init {

        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_double_choose, null)
        contentView.listView.adapter = CommonAdapter(layoutInflater, R.layout.item_popup_multiple_choose, arrayListOf("提前批录取院校"))
        contentView.listView2.adapter = CommonAdapter(layoutInflater, R.layout.item_popup_multiple_choose, arrayListOf("只搜新招", "全部搜索"))
    }

    fun show(type: Int = 0) {
        showAsDropDown(view)
        when (type) {
            0 -> {
                contentView.listView.adapter = CommonAdapter(layoutInflater, R.layout.item_popup_multiple_choose, arrayListOf("提前批录取院校"))
                contentView.listView2.adapter = CommonAdapter(layoutInflater, R.layout.item_popup_multiple_choose, arrayListOf("只搜新招", "全部搜索"))
            }
            1 -> {
                contentView.listView.adapter = CommonAdapter(layoutInflater, R.layout.item_popup_multiple_choose, arrayListOf("专业大类排位查询","专业排位查询"))
                contentView.choose_label1.text = "选择查询类型"
                contentView.choose_view2.gone()
            }
        }
    }

}