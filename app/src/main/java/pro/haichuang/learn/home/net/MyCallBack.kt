package pro.haichuang.learn.home.net

import com.jacy.kit.net.CommonCallBack
import com.jacy.kit.net.HttpCallBack
import pro.haichuang.learn.home.bean.Response

class MyCallBack<T>(callBack: HttpCallBack, private val showProgress: Boolean = false) : CommonCallBack<Response<T>, T>(callBack) {
    override fun onStart() {
        if (showProgress)
            super.onStart()
    }
}