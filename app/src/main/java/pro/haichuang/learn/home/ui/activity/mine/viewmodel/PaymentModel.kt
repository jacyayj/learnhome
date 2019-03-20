package pro.haichuang.learn.home.ui.activity.mine.viewmodel

import android.databinding.Bindable
import android.view.View
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.net.Params
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.Url

class PaymentModel : BaseModel() {

    @Params([Url.Account.Order], "payType")
    @Bindable
    var type = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }
    @Params([Url.Account.Order], "payType")
    @Bindable
    var price = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }

    fun togglePay(view: View) {
        type = view.tag.toString().toInt()
    }
}