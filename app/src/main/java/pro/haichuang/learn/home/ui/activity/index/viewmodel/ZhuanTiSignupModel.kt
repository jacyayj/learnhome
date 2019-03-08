package pro.haichuang.learn.home.ui.activity.index.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import com.jacy.kit.net.Params
import com.vondear.rxtool.RxRegTool
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.Url

class ZhuanTiSignupModel : BaseModel() {

    @Params([Url.Lecture.Apply], "id")
    var id = ""
    @Params([Url.Lecture.Apply], "contactName")
    @Bindable
    var name = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }
    @Params([Url.Lecture.Apply], "contactPhone")
    @Bindable
    var phone = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }
    @Params([Url.Lecture.Apply], "applyNumber")
    @Bindable
    var num = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.num)
        }

    override fun checkSuccess(url: String): Boolean {
        return when {
            name.isEmpty() -> {
                toast("请输入姓名")
                false
            }
            phone.isEmpty() -> {
                toast("请输入电话")
                false
            }
            !RxRegTool.isMobileSimple(phone) -> {
                toast("请输入正确的电话")
                false
            }
            num.isEmpty() -> {
                toast("请输入报名人数")
                false
            }
            num.toInt() <= 0 -> {
                toast("请输入正确的报名人数")
                false
            }
            else -> true
        }
    }
}