package pro.haichuang.learn.home.ui.activity.message.itemmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

class MessageCenterModel : BaseObservable {
    constructor(status: Int) : super() {
        this.status = status
    }

    @Bindable
    var status = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.status)
        }
}