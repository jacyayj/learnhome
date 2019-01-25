package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.scwang.smartrefresh.layout.util.DensityUtil
import pro.haichuang.learn.home.R

class IndexOperationPopup(private val view: View, private val result: (res: String) -> Unit = {}) : PopupWindow(DensityUtil.dp2px(95f), DensityUtil.dp2px(50f)), View.OnClickListener {
    override fun onClick(v: View?) {
        result((v as TextView).text.toString())
        dismiss()
    }

    private val layoutInflater by lazy { LayoutInflater.from(view.context) }

    init {
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_index_operation, null)
    }

    fun show() {
        showAsDropDown(view)
    }
}