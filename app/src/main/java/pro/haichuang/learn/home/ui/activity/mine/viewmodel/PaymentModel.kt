package pro.haichuang.learn.home.ui.activity.mine.viewmodel

import android.databinding.Bindable
import android.view.View
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import com.jacy.kit.net.Params
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.Url

class PaymentModel : BaseModel() {

    @Params([Url.Account.Activate, Url.Account.Recharge], "payType")
    @Bindable
    var type = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }
    @Params([Url.Account.Recharge], "money")
    @Bindable
    var price = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }

    @Bindable
    var recharge = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.recharge)
        }

    fun togglePay(view: View) {
        type = view.tag.toString().toInt()
    }

    override fun checkSuccess(url: String): Boolean {
        return when (type) {
            0 -> {
                toast("选择支付方式")
                false
            }
            else -> true
        }
    }
}