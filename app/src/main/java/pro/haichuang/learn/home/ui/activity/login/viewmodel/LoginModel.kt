package pro.haichuang.learn.home.ui.activity.login.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

class LoginModel : BaseObservable() {

    @Bindable
    var fastLogin = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.fastLogin)
        }

    @Bindable
    var user = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.user)
        }

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
}