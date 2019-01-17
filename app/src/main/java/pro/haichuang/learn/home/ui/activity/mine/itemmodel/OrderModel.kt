package pro.haichuang.learn.home.ui.activity.mine.itemmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

class OrderModel : BaseObservable {
    constructor(type: Int) : super() {
        this.type = type
    }

    @Bindable
    var type = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
            notifyPropertyChanged(BR.text)
        }

    @Bindable
    var text = ""
        get() = when (type) {
            1 -> "去付款"
            2 -> "已退款"
            3 -> "评价"
            else -> ""
        }
}