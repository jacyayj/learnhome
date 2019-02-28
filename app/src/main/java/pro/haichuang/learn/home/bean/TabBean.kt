package pro.haichuang.learn.home.bean

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

class TabBean : BaseObservable {

    constructor( text: String) : super() {
        this.text = text
    }

    constructor(vip: Boolean, text: String) : super() {
        this.vip = vip
        this.text = text
    }
    constructor(text: String,notice: Boolean) : super() {
        this.notice = notice
        this.text = text
    }


    @Bindable
    var checked = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.checked)
        }

    @Bindable
    var vip = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.vip)
        }

    @Bindable
    var notice = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.notice)
        }
    var text = ""
}