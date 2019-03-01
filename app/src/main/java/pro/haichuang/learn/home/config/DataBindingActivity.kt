package pro.haichuang.learn.home.config

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import com.android.databinding.library.baseAdapters.BR
import com.google.gson.Gson
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.model.HttpParams
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.MyCallBack
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.utils.mlog
import java.lang.reflect.ParameterizedType

open class DataBindingActivity<T : BaseModel> : BaseActivity() {

    open val model: T by lazy { getClassInstance().newInstance() }
    private val binding by lazy { DataBindingUtil.setContentView<ViewDataBinding>(this, getLayoutId()) }

    override fun initDatabinding() {
        binding.setVariable(BR.model, model)
    }

    /**
     * 获取泛型class对象
     * @param index 多个泛型时泛型索引，取第几个泛型
     */
    open fun getClassInstance(index: Int = 0): Class<T> {
        val type = javaClass.genericSuperclass
        // 判断 是否泛型
        return if (type is ParameterizedType) {
            // 返回表示此类型实际类型参数的Type对象的数组.
            // 当有多个泛型类时，数组的长度就不是1了
            val ptype = type.actualTypeArguments
            ptype[index] as Class<T>  //将第一个泛型T对应的类返回
        } else {
            Any::class.java as Class<T>//若没有给定泛型，则返回Object类
        }
    }


    fun <T> autoPost(url: String, showLoading: Boolean = true) {
        if (model.checkSuccess(url)) {
            val request = EasyHttp.post(url)
                    .params(model.getParams(url))
            //发送验证码成功是，拦截到请求头取到JSESSIONID
            if (url == Url.Sms.Send) {
                request.addInterceptor {
                    val response = it.proceed(it.request())
                    val headers = response.headers()
                    model.JSESSIONID = headers.value(0).split(";")[0].split("=")[1]
                    response
                }
            }
            //需要附带验证码的接口，将JSESSIONID附加在cookie上传给服务器
            if (url == Url.User.Register)
                request.addCookie("JSESSIONID",model.JSESSIONID)
            request.execute(MyCallBack<T>(url, this, showLoading))
        }
    }

}