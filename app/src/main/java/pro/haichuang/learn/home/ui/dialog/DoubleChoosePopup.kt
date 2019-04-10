package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.AbsListView
import android.widget.PopupWindow
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.gone
import kotlinx.android.synthetic.main.popup_double_choose.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.NameId

class DoubleChoosePopup(private val view: View, result: (isNew: Boolean) -> Unit = {}) : PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT) {
    private val layoutInflater = LayoutInflater.from(view.context)
    private var type = -1

    init {

        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_double_choose, null)
        contentView.listView.adapter = CommonAdapter(layoutInflater, R.layout.item_popup_multiple_choose, arrayListOf(NameId("提前批录取院校", -1)))
        contentView.listView2.adapter = CommonAdapter(layoutInflater, R.layout.item_popup_multiple_choose, arrayListOf(NameId("只搜新招", -1), NameId("全部搜索", "")))
        contentView.confirm.setOnClickListener {
            when (type) {
                0 -> {
                    result(contentView.listView2.isItemChecked(0))
                    dismiss()
                }
            }
        }
    }

    fun show(type: Int = 0) {
        showAsDropDown(view)
        if (this.type == -1)
            when (type) {
                0 -> {
                    contentView.choose_label1.gone()
                    contentView.listView.gone()
                    contentView.choose_label2.text = "是否招新"
                    contentView.listView2.choiceMode = AbsListView.CHOICE_MODE_SINGLE
                    contentView.listView2.adapter = CommonAdapter(layoutInflater, R.layout.item_popup_multiple_choose, arrayListOf(NameId("只搜新招", -1), NameId("全部搜索", -1)))
                }
                1 -> {
                    contentView.listView.adapter = CommonAdapter(layoutInflater, R.layout.item_popup_multiple_choose, arrayListOf(NameId("专业大类排位查询", -1), NameId("专业排位查询", -1)))
                    contentView.choose_label1.text = "选择查询类型"
                    contentView.choose_view2.gone()
                }
            }
        this.type = type
    }

}