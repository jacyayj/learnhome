package pro.haichuang.learn.home.bean

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

class IconLabel : BaseObservable {

    constructor(icon: Int, label: String) : super() {
        this.icon = icon
        this.label = label
    }

    @Bindable
    var icon = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.icon)
        }

    @Bindable
    var label = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.label)
        }
}