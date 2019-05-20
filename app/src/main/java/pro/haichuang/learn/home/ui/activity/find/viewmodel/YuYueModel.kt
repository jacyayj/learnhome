package pro.haichuang.learn.home.ui.activity.find.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import com.jacy.kit.net.Params
import com.vondear.rxtool.RxRegTool
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.Url

class YuYueModel : BaseModel() {

    @Params([Url.Teacher.Order], "teacherId")
    var id = 0

    @Params([Url.Teacher.Order], "orderType")
    var orderType = 2

    @Params([Url.Teacher.Order], "payType")
    var payType = 0

    @Params([Url.Teacher.Order], "payPassword")
    var payPassword = ""

    var hasPayPassword = false

    @Params([Url.Teacher.Order], "question")
    @Bindable
    var question = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.question)
        }
    @Params([Url.Teacher.Order], "picPaths")
    var picPaths = ""

    @Bindable
    @Params([Url.Teacher.Order], "contactPhone")
    var contactPhone = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.contactPhone)
        }
    @Bindable
    @Params([Url.Teacher.Order], "contactName")
    var contactName = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.contactName)
        }

    @Params([Url.Teacher.Order], "appointTime")
    var appointTime = ""

    var account = ""

    @Bindable
    var name = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }
    @Bindable
    var subject = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.subject)
        }
    @Bindable
    var type = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }
    @Bindable
    var skill = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.skill)
        }
    @Bindable
    var header = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.header)
        }

    val subjectStr
        get() = when (subject) {
            10->"普通一对一"
            11->"自主招生一对一"
            12->"艺术类一对一"
            20->"亲子关系"
            21->"考前舒压"
            22->"提高自信"
            23->"消除焦虑"
            24->"心理分析"
            else -> ""
        }

    override fun checkSuccess(url: String): Boolean {
        return when {
            question.isEmpty() -> {
                toast("请输入问题")
                false
            }
            contactName.isEmpty() -> {
                toast("请输入姓名")
                false
            }
            contactPhone.isEmpty() -> {
                toast("请输入联系电话")
                false
            }
            !RxRegTool.isMobileSimple(contactPhone) -> {
                toast("请输入正确的联系电话")
                false
            }
            else -> true
        }
    }
}