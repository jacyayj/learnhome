package pro.haichuang.learn.home.ui.weight

import android.content.Context
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.widget.CheckBox
import android.widget.EditText
import pro.haichuang.learn.home.R

class PasswordToggle : CheckBox {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setButtonDrawable(R.drawable.selector_eye)
    }

    fun setEdit(edit: EditText) {
        setOnCheckedChangeListener { _, isChecked ->
            edit.transformationMethod = if (isChecked) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
            edit.setSelection(edit.text.length)
        }
    }

}