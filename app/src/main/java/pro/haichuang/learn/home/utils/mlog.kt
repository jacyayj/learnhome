package pro.haichuang.learn.home.utils

import com.vondear.rxtool.RxLogTool

object mlog {

    private const val TAG = "learn_home"
    //规定每段显示的长度
    private val LOG_MAXLENGTH = 3000

    fun v(msg: String?) {
        msg?.let {
            val strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                //剩下的文本还是大于规定长度则继续重复截取并输出
                if (strLength > end) {
                    RxLogTool.v(TAG, msg.substring(start, end))
                    start = end
                    end += LOG_MAXLENGTH
                } else {
                    RxLogTool.v(TAG, msg.substring(start, strLength))
                    break
                }
            }
        }
    }

}