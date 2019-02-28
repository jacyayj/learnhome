package pro.haichuang.learn.home.ui.activity.login.viewmodel

import pro.haichuang.learn.home.bean.BaseModel
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

class ForgetPwdModel : BaseModel() {

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

}