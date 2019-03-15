package pro.haichuang.learn.home.ui.activity.mine.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import pro.haichuang.learn.home.bean.BaseModel

class FileModel : BaseModel() {

    @Bindable
    var studentName = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.studentName)
        }
    @Bindable
    var studentCode = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.studentCode)
        }
    @Bindable
    var studentClass = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.studentClass)
        }
    @Bindable
    var school = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.school)
        }
    @Bindable
    var qq = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.qq)
        }
    @Bindable
    var phone = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }
    @Bindable
    var graduate = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.graduate)
        }
    @Bindable
    var email = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }

    @Bindable
    var city = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.city)
        }
    @Bindable
    var district = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.district)
        }
    @Bindable
    var type = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }
    @Bindable
    var subject = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.subject)
        }
    @Bindable
    var fileTime = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.fileTime)
        }

    val done
        get() = innerChecked(false)

    override fun checkSuccess(url: String): Boolean {
        return innerChecked(true)
    }

    private fun innerChecked(showToast: Boolean): Boolean {
        return when {
            fileTime == 0 -> {
                if (showToast) toast("请选择毕业年份")
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
    }
}