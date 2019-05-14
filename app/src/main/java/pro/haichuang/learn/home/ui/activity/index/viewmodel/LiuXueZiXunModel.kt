package pro.haichuang.learn.home.ui.activity.index.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import com.jacy.kit.net.Params
import com.vondear.rxtool.RxRegTool
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.Url

class LiuXueZiXunModel : BaseModel() {
    @Params([Url.Consult.Save], "applyRegion")
    @Bindable
    var applyRegion = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.applyRegion)
        }
    @Params([Url.Consult.Save], "applySchool")
    @Bindable
    var applySchool = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.applySchool)
        }
    @Params([Url.Consult.Save], "contactName")
    @Bindable
    var contactName = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.contactName)
        }
    @Params([Url.Consult.Save], "contactPhone")
    @Bindable
    var contactPhone = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.contactPhone)
        }
    @Params([Url.Consult.Save], "latestSchool")
    @Bindable
    var latestSchool = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.latestSchool)
        }

    override fun checkSuccess(url: String): Boolean {
        return when {
            contactName.isEmpty() -> {
                toast("请输入姓名")
                false
            }
            contactPhone.isEmpty() -> {
                toast("请输入电话号码")
                false
            }
            !RxRegTool.isMobileSimple(contactPhone) -> {
                toast("请输入正确的电话号码")
                false
            }
            latestSchool.isEmpty() -> {
                toast("请输入就读学校")
                false
            }
            applyRegion.isEmpty() -> {
                toast("请输入申请地区")
                false
            }
            applySchool.isEmpty() -> {
                toast("请输入申请学校")
                false
            }
            else -> true
        }
    }
}