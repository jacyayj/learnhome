package pro.haichuang.learn.home.ui.weight

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import com.jacy.kit.config.mStartActivity
import com.yhy.widget.core.web.HybridWebView
import com.yhy.widget.core.web.client.WebClient
import pro.haichuang.learn.home.ui.activity.WebActivity

class MyWebView : HybridWebView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        webClient = object : WebClient(this) {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url?.startsWith("http") == true)
                    context.mStartActivity(WebActivity::class.java,Pair("url",url))
                return true
            }
        }
    }

    var guide: String? = null
        set(value) {
            field = value
            value?.let {
                loadData(value)
            }
        }

    var data: String? = null
        set(value) {
            field = value
            value?.let {
                settings.textZoom = 200
                loadData(value)
            }
        }
}