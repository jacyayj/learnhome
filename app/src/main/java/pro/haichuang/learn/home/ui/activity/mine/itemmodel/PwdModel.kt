package pro.haichuang.learn.home.ui.activity.mine.itemmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import pro.haichuang.learn.home.BR

class PwdModel : BaseObservable() {

    @Bindable
    var pwd = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.pwd)
            onInput()
        }

    var onInput: () -> Unit = {}
}