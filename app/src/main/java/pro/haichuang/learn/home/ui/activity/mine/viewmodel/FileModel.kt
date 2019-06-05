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
    var studentClass = 1
        set(value) {
            field = value
            notifyPropertyChanged(BR.studentClassStr)
        }

    @Params([Url.User.FileSave], "gender")
    var gender = 1
        set(value) {
            field = value
            notifyPropertyChanged(BR.genderStr)
        }

    @Bindable
    var studentClassStr = ""
        get() = "${studentClass}班"

    @Bindable
    var genderStr = ""
        get() = if (gender == 1) "男" else "女"

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
    var type = 1
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }
    @Params([Url.User.FileSave], "subject")
    @Bindable
    var subject = 1
        set(value) {
            field = value
            notifyPropertyChanged(BR.subject)
        }
    @Params([Url.User.FileSave], "graduate")
    @Bindable
    var graduate = 1
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
        return when {
            graduate == 0 -> {
                if (showToast) toast("请选择毕业年份")
                else done = false
                false
            }
            type == 0 -> {
                if (showToast) toast("请选择会员类型")
                else done = false
                false
            }
            subject == 0 -> {
                if (showToast) toast("请选择科目")
                else done = false
                false
            }
            studentName.isEmpty() -> {
                if (showToast) toast("请选输入学生姓名")
                else done = false
                false
            }
            city.isEmpty() -> {
                if (showToast) toast("请选择毕业城市")
                else done = false
                false
            }
            district.isEmpty() -> {
                if (showToast) toast("请选择毕业区县")
                else done = false
                false
            }
            school.isEmpty() -> {
                if (showToast) toast("请输入在读学校")
                else done = false
                false
            }
            studentCode.isEmpty() -> {
                if (showToast) toast("请输入学籍号")
                else done = false
                false
            }
            studentClass == 0 -> {
                if (showToast) toast("请选择班级")
                else done = false
                false
            }
            phone.isEmpty() -> {
                if (showToast) toast("请输入联系电话")
                else done = false
                false
            }
            qq.isEmpty() -> {
                if (showToast) toast("请输入QQ")
                else done = false
                false
            }
            email.isEmpty() -> {
                if (showToast) toast("请输入邮箱")
                else done = false
                false
            }
            else -> true
        }
    }
}