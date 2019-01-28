package pro.haichuang.learn.home.utils

import com.vondear.rxtool.RxLogTool

object mlog {

    private const val TAG = "learn_home"

    fun v(msg: String?) {
        msg?.let {
            RxLogTool.v(TAG, it)
        }
    }

}