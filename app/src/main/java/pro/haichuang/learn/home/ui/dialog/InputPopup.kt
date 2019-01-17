package pro.haichuang.learn.home.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import pro.haichuang.learn.home.R

class InputPopup(private val view: View) : PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT) {

    private val layoutInflater by lazy { LayoutInflater.from(view.context) }

    init {
        isOutsideTouchable = true
        contentView = layoutInflater.inflate(R.layout.popup_input, null)
    }

}