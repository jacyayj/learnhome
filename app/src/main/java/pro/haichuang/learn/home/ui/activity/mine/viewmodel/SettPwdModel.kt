package pro.haichuang.learn.home.ui.activity.mine.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import com.jacy.kit.net.Params
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.Url

class SettPwdModel : BaseModel() {
    @Params([Url.Account.PayPassword], "oldPassword")
    var originPwd = ""

    var newPwd = ""

    @Params([Url.Account.PayPassword], "newPassword")
    var confirmPwd = ""

    @Bindable
    var step = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.step)
        }

    override fun checkSuccess(url: String): Boolean {
        return if (newPwd != confirmPwd) {
            toast("两次输入不相同，请重新输入")
            false
        } else true
    }
}