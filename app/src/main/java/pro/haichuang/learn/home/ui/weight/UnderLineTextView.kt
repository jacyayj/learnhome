package pro.haichuang.learn.home.ui.weight

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView

class UnderLineTextView : TextView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }
}