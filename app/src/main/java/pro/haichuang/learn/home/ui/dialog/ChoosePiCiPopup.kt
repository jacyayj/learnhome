package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.jacy.kit.utils.toRoundInt
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.popup_choose_pici.view.*
import pro.haichuang.learn.home.R

class ChoosePiCiPopup(private val view: View, private val result: (name: String, batch: Int) -> Unit = {_,_->}) : PopupWindow(DensityUtil.dp2px(95f), WindowManager.LayoutParams.WRAP_CONTENT), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v is TextView)
            result(v.text.toString(), v.tag.toRoundInt())
        dismiss()
    }

    private val layoutInflater by lazy { LayoutInflater.from(view.context) }

    init {
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_choose_pici, null)
        contentView.pici_1.setOnClickListener(this)
        contentView.pici_2.setOnClickListener(this)
        contentView.pici_3.setOnClickListener(this)
    }

    fun show() {
        showAsDropDown(view, -80, 0)
    }
}