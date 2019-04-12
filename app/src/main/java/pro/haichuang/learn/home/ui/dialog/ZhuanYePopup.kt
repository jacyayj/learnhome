package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.jacy.kit.adapter.CommonAdapter
import com.vondear.rxtool.RxDeviceTool
import kotlinx.android.synthetic.main.popup_grid_multiple.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.NameId

class ZhuanYePopup(private val view: View, private val result: (code: String, position: Int, name: String) -> Unit) : PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT) {

    private val layoutInflater by lazy { LayoutInflater.from(view.context) }
    private val adapter by lazy { CommonAdapter<NameId>(layoutInflater, R.layout.item_grid_multiple) }

    private var lastCheckedPosition = -1
    private var checkedPosition = -1

    private var isInit = false

    init {
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_grid_multiple, null)
        contentView.grid.adapter = adapter

        contentView.grid.setOnItemClickListener { _, _, position, _ ->
            contentView.all.isChecked = false
            checkedPosition = position
        }
        contentView.all.setOnClickListener {
            if (contentView.all.isChecked.not()) {
                contentView.all.isChecked = true
                contentView.grid.setItemChecked(checkedPosition, false)
                checkedPosition = -1
            }
        }
        contentView.confirm.setOnClickListener {
            if (lastCheckedPosition != checkedPosition) {
                lastCheckedPosition = checkedPosition
                if (checkedPosition != -1) {
                    val item = adapter.getItem(checkedPosition)
                    result(item.code, checkedPosition, item.name)
                } else result("", -1, "全部")
            }
            dismiss()
        }
        contentView.post {
            val sh = RxDeviceTool.getScreenHeight(view.context)
            if (contentView.height >= sh*0.8) {
                val params = contentView.layoutParams
                params.height = (sh * 0.6).toInt()
                contentView.layoutParams = params
            }
        }
    }

    fun show(data: ArrayList<NameId>, isRefresh: Boolean = false) {
        showAsDropDown(view)
        if (isRefresh || !isInit) {
            adapter.refresh(data)
            isInit = true
        }
    }

    fun show() {
        if (adapter.isEmpty)
            result("", -2, "")
        else
            showAsDropDown(view)
    }
}