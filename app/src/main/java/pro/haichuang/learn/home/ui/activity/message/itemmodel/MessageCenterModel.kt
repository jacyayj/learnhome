package pro.haichuang.learn.home.ui.activity.message.itemmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

class MessageCenterModel : BaseObservable() {

    var id = 0L

    var account = ""

    @Bindable
    var header = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.header)
        }
    @Bindable
    var name = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }
    @Bindable
    var status = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.status)
        }
}