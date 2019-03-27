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
    var confirmStr = ""
        get() = if (recharge) "确认充值" else "确认购买"
    @Bindable
    var noticeStr = ""
        get() = if (recharge) "余额充值" else "会员升级"

    @Bindable
    var recharge = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.recharge)
            notifyPropertyChanged(BR.confirmStr)
            notifyPropertyChanged(BR.noticeStr)
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