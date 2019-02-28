package pro.haichuang.learn.home.ui.activity.login.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import com.vondear.rxtool.RxRegTool
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.Params
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

    override fun checkSuccess(url: String): Boolean {
        return when (url) {
            Url.User.Login -> {
                when {
                    user.isEmpty() -> {
                        toast("请输入手机号")
                        false
                    }
                    pwd.isEmpty() -> {
                        toast("请输入密码")
                        false
                    }
                    !RxRegTool.isMobileSimple(user) -> {
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