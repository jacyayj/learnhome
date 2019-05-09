package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.jacy.kit.adapter.CommonAdapter
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.dialog_zhiyuan_result.view.*
import pro.haichuang.learn.home.R

class ChooseZhiYuanPopup(private val view: View, data: ArrayList<String>, private val result: (res: Int) -> Unit) : PopupWindow(DensityUtil.dp2px(95f), WindowManager.LayoutParams.WRAP_CONTENT) {


    private val layoutInflater by lazy { LayoutInflater.from(view.context) }

    init {
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_choose_zhiyuan, null)
        contentView.listView.adapter = CommonAdapter(LayoutInflater.from(view.context), R.layout.item_zhiyuan_result_popup, data)
        contentView.listView.setOnItemClickListener { _, _, position, _ ->
            result(position+1)
            dismiss()
        }
    }

    fun show() {
        showAsDropDown(view)
    }
}