package pro.haichuang.learn.home.ui.activity.login.viewmodel

import pro.haichuang.learn.home.bean.BaseModel
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import com.vondear.rxtool.RxRegTool
import pro.haichuang.learn.home.net.Params
import pro.haichuang.learn.home.net.Url

class RegisterModel : BaseModel() {

    @Bindable
    @Params([Url.User.Register,Url.Sms.Send], "mobile")
    var phone = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }

    @Bindable
    @Params([Url.User.Register], "smsCode")
    var code = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.code)
        }

    @Bindable
    @Params([Url.User.Register], "password")
    var pwd = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.pwd)
        }

    @Bindable
    var confirmPwd = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.confirmPwd)
        }


    @Params([Url.Sms.Send], "sendType")
    private val sendType = "1"

    override fun checkSuccess(url: String): Boolean {
        return when {
            phone.isEmpty() -> {
                toast("请输入手机号")
                false
            }
            !RxRegTool.isMobileSimple(phone) -> {
                toast("请输入正确的手机号")
                false
            }
            url == Url.User.Register -> {
                when {
                    code.isEmpty() -> {
                        toast("请输入验证码")
                        false
                    }
                    pwd.isEmpty() -> {
                        toast("请输入密码")
                        false
                    }
                    confirmPwd.isEmpty() -> {
                        toast("请输入确认密码")
                        false
                    }
                    pwd != confirmPwd -> {
                        toast("两次密码不相同，请重新输入")
                        false
                    }
                    else -> true
                }
            }
            else -> true
        }
    }
}