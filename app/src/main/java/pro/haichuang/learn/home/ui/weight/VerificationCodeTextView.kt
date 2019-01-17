package pro.haichuang.learn.home.ui.weight

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.widget.TextView

class VerificationCodeTextView : TextView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var time = 60
    private var hand: Handler? = null
    var requestCode: () -> Unit = {}
    var notifyCount: () -> Unit = {
        if (hand == null)
            hand = @SuppressLint("HandlerLeak")
            object : Handler() {
                override fun handleMessage(msg: Message?) {
                    msg?.let {
                        when (msg.what) {
                            0x01 -> {
                                if (time > 0) {
                                    text = "${time}s后重新获取"
                                    isEnabled = false
                                    time--
                                    sendEmptyMessageDelayed(0x01, 1000)
                                } else {
                                    sendEmptyMessage(0x02)
                                }
                            }
                            else -> {
                                isEnabled = true
                                text = "重新获取"
                                time = 5
                            }
                        }
                    }
                }
            }
        hand?.sendEmptyMessage(0x01)
    }

    init {
        text = "获取验证码"
        setOnClickListener {
            requestCode()
        }
    }
}