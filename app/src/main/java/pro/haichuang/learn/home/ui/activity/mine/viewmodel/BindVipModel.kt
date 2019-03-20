package pro.haichuang.learn.home.ui.activity.mine.viewmodel

import android.databinding.Bindable
import android.view.View
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import com.jacy.kit.net.Params
import com.vondear.rxtool.RxEncryptTool
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.Url

class BindVipModel : BaseModel() {
    @Bindable
    var price = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.price)
        }
    @Bindable
    var online = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.online)
        }

    fun toggleOnline(v: View) {
        online = online.not()
    }

    @Params([Url.Account.Order], "payType")
    var payType = 2

    @Params([Url.Account.Order], "cardNo")
    @Bindable
    var cardNo = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardNo)
        }
    @Bindable
    var cardPassword = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardPassword)
            enPassword = RxEncryptTool.encryptMD5ToString(value)
        }

    @Params([Url.Account.Order], "cardPassword")
    var enPassword = ""

    override fun checkSuccess(url: String): Boolean {
        return when {
            cardNo.isEmpty() -> {
                toast("请输入序列号")
                false
            }
            cardPassword.isEmpty() -> {
                toast("请输入验证码")
                false
            }
            else -> return true
        }
    }
}