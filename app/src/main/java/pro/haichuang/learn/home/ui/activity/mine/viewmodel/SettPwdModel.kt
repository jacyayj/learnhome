package pro.haichuang.learn.home.ui.activity.mine.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

class SettPwdModel : BaseObservable() {

    var originPwd = ""

    var newPwd = ""

    var confirmPwd = ""

    @Bindable
    var step = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.step)
        }
}