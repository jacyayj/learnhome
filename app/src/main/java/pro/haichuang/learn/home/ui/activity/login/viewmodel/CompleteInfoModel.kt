package pro.haichuang.learn.home.ui.activity.login.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import com.jacy.kit.net.Params
import com.vondear.rxtool.RxRegTool
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.Url

class CompleteInfoModel : BaseModel() {

    @Bindable
    var verify = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.verify)
        }

    @Params([Url.User.ThirdBind], "source")
    @Bindable
    var source = ""

    @Params([Url.User.ThirdBind], "thirdKey")
    @Bindable
    var thirdKey = ""

    @Params([Url.User.ThirdBind, Url.Sms.Send], "mobile")
    @Bindable
    var phone = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }
    @Params([Url.User.ThirdBind], "smsCode")
    @Bindable
    var code = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }
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
    private val sendType = "3"

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
            url == Url.User.ThirdBind && code.isEmpty() -> {
                toast("请输入验证码")
                false
            }
            else -> true
        }
    }
}