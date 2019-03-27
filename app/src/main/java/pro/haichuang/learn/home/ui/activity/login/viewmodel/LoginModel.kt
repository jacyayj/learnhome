package pro.haichuang.learn.home.ui.activity.login.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import com.jacy.kit.net.Params
import com.vondear.rxtool.RxRegTool
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.Url

class LoginModel : BaseModel() {

    @Bindable
    var fastLogin = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.fastLogin)
        }

    @Params([Url.User.Login], "username")
    @Bindable
    var user = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.user)
        }
    @Params([Url.User.Login], "password")
    @Bindable
    var pwd = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.pwd)
        }

    @Params([Url.Sms.Send, Url.User.Login], "mobile")
    @Bindable
    var phone = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }

    @Bindable
    var code = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.code)
        }

    @Params([Url.Sms.Send], "sendType")
    private val sendType = "1"

    override fun checkSuccess(url: String): Boolean {
        return when (url) {
            Url.User.Login -> {
                when {
                    !fastLogin && user.isEmpty() -> {
                        toast("请输入手机号")
                        false
                    }
                    fastLogin && phone.isEmpty() -> {
                        toast("请输入正确的手机号")
                        false
                    }
                    pwd.isEmpty() -> {
                        toast("请输入密码")
                        false
                    }
                    !fastLogin && !RxRegTool.isMobileSimple(user) -> {
                        toast("请输入正确的手机号")
                        false
                    }
                    fastLogin && !RxRegTool.isMobileSimple(phone) -> {
                        toast("请输入正确的手机号")
                        false
                    }
                    else -> true
                }
            }
            Url.Sms.Send -> {
                when {
                    phone.isEmpty() -> {
                        toast("请输入手机号")
                        false
                    }
                    !RxRegTool.isMobileSimple(phone) -> {
                        toast("请输入正确的手机号")
                        false
                    }
                    else -> true
                }
            }
            else -> true
        }
    }
}