package pro.haichuang.learn.home.ui.activity.mine.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import com.jacy.kit.net.Params
import pro.haichuang.learn.home.bean.AreaBean
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.Url

class FileModel : BaseModel() {
    @Params([Url.User.FileSave], "studentName")
    @Bindable
    var studentName = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.studentName)
        }
    @Params([Url.User.FileSave], "studentCode")
    @Bindable
    var studentCode = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.studentCode)
        }
    @Params([Url.User.FileSave], "studentClass")
    @Bindable
    var studentClass = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.studentClass)
        }
    @Params([Url.User.FileSave], "school")
    @Bindable
    var school = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.school)
        }
    @Params([Url.User.FileSave], "qq")
    @Bindable
    var qq = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.qq)
        }
    @Params([Url.User.FileSave], "phone")
    @Bindable
    var phone = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }
    @Params([Url.User.FileSave], "email")
    @Bindable
    var email = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }
    @Params([Url.User.FileSave], "city")
    @Bindable
    var city = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.city)
        }

    @Params([Url.User.FileSave], "district")
    @Bindable
    var district = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.district)
        }

    var districtData: ArrayList<AreaBean>? = null

    @Params([Url.User.FileSave], "type")
    @Bindable
    var type = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }
    @Params([Url.User.FileSave], "subject")
    @Bindable
    var subject = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.subject)
        }
    @Params([Url.User.FileSave], "graduate")
    @Bindable
    var graduate = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.graduate)
        }

    @Bindable
    var done = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.done)
        }

    override fun checkSuccess(url: String): Boolean {
        return innerChecked(true)
    }

    fun innerChecked(showToast: Boolean): Boolean {
        val success = when {
            graduate == 0 -> {
                if (showToast) toast("请选择毕业年份")
                else done = false
                false
            }
            type == 0 -> {
                if (showToast) toast("请选择会员类型")
                false
            }
            subject == 0 -> {
                if (showToast) toast("请选择科目")
                false
            }
            studentName.isEmpty() -> {
                if (showToast) toast("请选输入学生姓名")
                false
            }
            city.isEmpty() -> {
                if (showToast) toast("请选择毕业城市")
                false
            }
            district.isEmpty() -> {
                if (showToast) toast("请选择毕业区县")
                false
            }
            school.isEmpty() -> {
                if (showToast) toast("请选择毕业年份")
                false
            }
            studentCode.isEmpty() -> {
                if (showToast) toast("请选择毕业年份")
                false
            }
            studentClass.isEmpty() -> {
                if (showToast) toast("请选择毕业年份")
                false
            }
            phone.isEmpty() -> {
                if (showToast) toast("请选择毕业年份")
                false
            }
            qq.isEmpty() -> {
                if (showToast) toast("请选择毕业年份")
                false
            }
            email.isEmpty() -> {
                if (showToast) toast("请选择毕业年份")
                false
            }
            else -> true
        }
        done = success
        return success
    }
}