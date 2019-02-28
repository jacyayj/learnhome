package pro.haichuang.learn.home.ui.activity.mine.itemmodel

import pro.haichuang.learn.home.bean.BaseModel
import android.databinding.Bindable
import pro.haichuang.learn.home.BR

class PwdModel : BaseModel() {

    @Bindable
    var pwd = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.pwd)
            onInput()
        }

    var onInput: () -> Unit = {}
}