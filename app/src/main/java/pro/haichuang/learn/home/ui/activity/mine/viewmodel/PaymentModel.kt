package pro.haichuang.learn.home.ui.activity.mine.viewmodel

import android.databinding.Bindable
import android.view.View
import com.android.databinding.library.baseAdapters.BR
import pro.haichuang.learn.home.bean.BaseModel

class PaymentModel : BaseModel() {

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