package pro.haichuang.learn.home.net

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jacy.kit.config.mStartActivityForResult
import com.jacy.kit.config.toast
import com.jacy.kit.net.HttpCallBack
import com.vondear.rxtool.RxActivityTool
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import pro.haichuang.learn.home.bean.Response
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.ui.activity.login.LoginActivity
import pro.haichuang.learn.home.utils.mlog

class MyCallBack(private val url: String, private val callBack: HttpCallBack, private val showProgress: Boolean = false, private val success: () -> Unit = {}) : SimpleCallBack<String>() {
    override fun onSuccess(t: String?) {
        val response = Gson().fromJson<Response<Any>>(t, object : TypeToken<Response<Any>>() {}.type)
        when {
            response.code == 200 -> {
                callBack.onSuccess(url, response.body)
                success()
            }
            response.code == 302 -> {
                toast("登录已失效，请重新登录")
                RxActivityTool.currentActivity().mStartActivityForResult(LoginActivity::class.java, Constants.RE_LOGIN, Pair("re_login", true))
            }
            else -> toast(response.message + "   " + response.code)
        }
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