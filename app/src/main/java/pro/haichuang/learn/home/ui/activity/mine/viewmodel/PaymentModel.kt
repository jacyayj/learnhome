package pro.haichuang.learn.home.ui.activity.mine.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.android.databinding.library.baseAdapters.BR

class PaymentModel : BaseObservable() {

    @Bindable
    var type = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }

    fun togglePay(view: View) {
        type = view.tag.toString().toInt()
    }
}