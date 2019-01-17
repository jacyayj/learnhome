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

class ChoosePiCiPopup(private val view: View, private val result: (res: String) -> Unit = {}) : PopupWindow(DensityUtil.dp2px(95f), WindowManager.LayoutParams.WRAP_CONTENT), View.OnClickListener {
    override fun onClick(v: View?) {
        result((v as TextView).text.toString())
        dismiss()
    }

    private val layoutInflater by lazy { LayoutInflater.from(view.context) }

    init {
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_choose_pici, null)
        contentView.pici_1.setOnClickListener(this)
        contentView.pici_2.setOnClickListener(this)
        contentView.pici_3.setOnClickListener (this)
    }

    fun show(){
        showAsDropDown(view,-80,0)
    }
}