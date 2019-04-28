package pro.haichuang.learn.home.ui.activity.login.viewmodel

import pro.haichuang.learn.home.bean.BaseModel
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import com.jacy.kit.net.Params
import com.vondear.rxtool.RxRegTool
import pro.haichuang.learn.home.net.Url

class ForgetPwdModel : BaseModel() {

    @Params([Url.User.ForgetPassword, Url.Sms.Send], "mobile")
    @Bindable
    var phone = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }
    @Params([Url.User.ForgetPassword], "smsCode")
    @Bindable
    var code = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }
    @Params([Url.User.ForgetPassword], "newPassword")
    @Bindable
    var pwd = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }
    @Bindable
    var confirmPwd = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }

    @Params([Url.Sms.Send], "sendType")
    private val sendType = "2"

    override fun checkSuccess(url: String): Boolean {
        return when {
            phone.isEmpty() -> {
                toast("请输入手机号")
                false
            }
            RxRegTool.isMobileSimple(phone).not() -> {
                toast("请输入正确的手机号")
                false
            }
            url == Url.User.ForgetPassword && code.isEmpty()-> {
                toast("请输入验证码")
                false
            }
            else -> true
        }
    }
}