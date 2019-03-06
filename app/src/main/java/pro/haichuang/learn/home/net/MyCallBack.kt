package pro.haichuang.learn.home.net

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jacy.kit.config.toast
import com.jacy.kit.net.HttpCallBack
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import pro.haichuang.learn.home.bean.Response
import pro.haichuang.learn.home.utils.mlog

class MyCallBack(private val url:String,private val callBack: HttpCallBack, private val showProgress: Boolean = false) : SimpleCallBack<String>() {
    override fun onSuccess(t: String?) {
        val response = Gson().fromJson<Response<Any>>(t, object : TypeToken<Response<Any>>() {}.type)
        if (response.code == 200)
            callBack.onSuccess(url,response.body)
        else
            toast(response.message + "   " + response.code)
    }

    override fun onError(e: ApiException?) {
        callBack.onError(e?.displayMessage ?: e?.localizedMessage ?: e?.message
        ?: "未知错误 : ${e?.code}")
        callBack.onFinish()
        mlog.v("onError")
    }

    override fun onStart() {
        if (showProgress)
            callBack.onBegin()
    }

    override fun onCompleted() {
        callBack.onFinish()
        mlog.v("onCompleted")
    }
}