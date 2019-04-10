package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.popup_input.view.*
import pro.haichuang.learn.home.R

class InputPopup(private val view: View, result: (str: String) -> Unit) : PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT) {

    private val layoutInflater by lazy { LayoutInflater.from(view.context) }

    init {
        isOutsideTouchable = true
        isFocusable = true
        contentView = layoutInflater.inflate(R.layout.popup_input, null)
        contentView.confirm.setOnClickListener {
            result(contentView.search_input.text.toString())
            dismiss()
        }
    }

    fun clear() {
        contentView.search_input.setText("")
    }

    fun show() {
        showAsDropDown(view)
    }
}