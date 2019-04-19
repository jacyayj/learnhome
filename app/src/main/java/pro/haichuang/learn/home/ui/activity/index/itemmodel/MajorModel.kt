package pro.haichuang.learn.home.ui.activity.index.itemmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

class MajorModel : BaseObservable() {

    var code = ""
    var majorCategory = ""
    var educationYear = 0
    var enrollCount = 0
    var tuition = 0
    var subject = 0
    var id = 0
    var majorName = ""

    val recommendStr
        get() = "学历层次：${if (subject == 1) "本" else "专"}科 专业类别：$majorCategory\n授予学位：历史学学士 修学年限：${educationYear}年"

    @Bindable
    var checked = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.checked)
            notifyPropertyChanged(BR.checkedText)
        }

    @Bindable
    var checkedText: String = ""
        get() = if (checked) "已选择" else "选择专业"

    @Bindable
    var resultText: String = ""
        get() = "计划数:" + enrollCount + "人  学制:" + educationYear + "年  学费:" + tuition + "元"
}