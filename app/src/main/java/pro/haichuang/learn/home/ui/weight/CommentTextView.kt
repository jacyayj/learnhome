package pro.haichuang.learn.home.ui.weight

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.widget.TextView

class CommentTextView : TextView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val nameColor by lazy { Color.parseColor("#295DA4") }

    var name = ""
        set(value) {
            field = value
            initText()
        }

    var repeatName = ""
        set(value) {
            field = value
            initText()
        }

    var content = ""
        set(value) {
            field = value
            initText()
        }

    private fun initText() {
        val textContent = "${name}回复$repeatName：$content"
        val nameIndex = name.length
        val repeatNameIndex = name.length + 2 + repeatName.length
        val spannedString = SpannableString(textContent)
        spannedString.setSpan(ForegroundColorSpan(nameColor), 0, nameIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        spannedString.setSpan(ForegroundColorSpan(nameColor), repeatNameIndex - repeatName.length, repeatNameIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        text = spannedString
    }
}