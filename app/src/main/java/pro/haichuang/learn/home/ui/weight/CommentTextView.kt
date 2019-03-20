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

    var name: String? = ""
        set(value) {
            field = value
            initText()
        }

    var repeatName: String? = ""
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
        if (name.isNullOrEmpty() || repeatName.isNullOrEmpty())
            return
        val textContent = "${name}回复$repeatName：$content"
        val nameIndex = name?.length ?: 0
        val repeatNameIndex = name?.length ?: 0 + 2 + (repeatName?.length ?: 0)
        val spannedString = SpannableString(textContent)
        spannedString.setSpan(ForegroundColorSpan(nameColor), 0, nameIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        spannedString.setSpan(ForegroundColorSpan(nameColor), repeatNameIndex - (repeatName?.length
                ?: 0), repeatNameIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        text = spannedString
    }
}