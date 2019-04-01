package pro.haichuang.learn.home.ui.weight

import android.content.Context
import android.util.AttributeSet
import com.yhy.widget.core.web.HybridWebView

class MyWebView : HybridWebView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var guide: String? = null
        set(value) {
            field = value
            value?.let {
                loadData(value)
            }
        }
}