package pro.haichuang.learn.home.config

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import com.zhouyou.http.EasyHttp
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.MyCallBack
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.login.LoginActivity
import pro.haichuang.learn.home.utils.SPUtils
import java.lang.reflect.ParameterizedType

open class DataBindingActivity<T : BaseModel> : BaseActivity() {

    lateinit var model: T
    private val binding by lazy { DataBindingUtil.setContentView<ViewDataBinding>(this, getLayoutId()) }

    override fun initDatabinding() {
        model = getClassInstance().newInstance()
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

    fun notifyModel(model: T) {
        this.model = model
        binding.setVariable(BR.model, model)
        binding.executePendingBindings()
    }

    fun autoPost(url: String, showLoading: Boolean = true, needSession: Boolean = false) {
        if (SPUtils.isTourist)
            if (url == Url.Teacher.Collect || url == Url.Content.Collect || url == Url.Lecture.Collect || url == Url.College.Collect || url == Url.Order.Collect
                    || url == Url.Comment.Save || url == Url.Comment.Up || url == Url.Comment.Up || url == Url.Content.Up || url == Url.Teacher.Fee || url == Url.Lecture.Apply
                    || url == Url.Consult.Save || url == Url.Friend.Attention|| url == Url.Account.Activate
            ) {
                mStartActivity(LoginActivity::class.java, Pair("re_login", true))
                toast("请先登录后再操作")
                return
            }
        if (model.checkSuccess(url)) {
            val params = model.getParams(url)
            if (needSession)
                SPUtils.session?.let {
                    params.put("sessionKey", it)
                }
            val request = EasyHttp.post(url)
                    .params(sign(params))
            //发送验证码成功时，拦截到请求头取到JSESSIONID
            if (url == Url.Sms.Send) {
                request.addInterceptor {
                    val response = it.proceed(it.request())
                    val headers = response.headers()
                    if (headers.size() > 0) {
                        val cookie = headers.value(0)
                        if (cookie.contains("JSESSIONID"))
                            model.JSESSIONID = cookie.split(";")[0].split("=")[1]
                    }
                    response
                }
            }
            //需要附带验证码的接口，将JSESSIONID附加在cookie上传给服务器
            if (url == Url.User.Register|| url == Url.User.ForgetPassword)
                request.addCookie("JSESSIONID", model.JSESSIONID)
            request.execute(MyCallBack(url, this, showLoading))
        }
    }

}