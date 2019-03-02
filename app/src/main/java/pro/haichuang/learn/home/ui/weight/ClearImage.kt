package pro.haichuang.learn.home.ui.weight

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import pro.haichuang.learn.home.R

class ClearImage : ImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setImageResource(R.drawable.icon_login_clear)
    }

    fun setEdit(edit: EditText) {
        setOnClickListener { edit.setText("") }
    }

}