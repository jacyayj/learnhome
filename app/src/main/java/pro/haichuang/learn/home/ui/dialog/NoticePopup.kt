package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.popup_choose_pici.view.*
import org.w3c.dom.Text
import pro.haichuang.learn.home.R

class NoticePopup(private val view: View) : PopupWindow(DensityUtil.dp2px(140f), WindowManager.LayoutParams.WRAP_CONTENT) {

    private val layoutInflater by lazy { LayoutInflater.from(view.context) }

    init {
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_notice, null)
    }

    fun show() {
        showAsDropDown(view,350,0)
    }
}