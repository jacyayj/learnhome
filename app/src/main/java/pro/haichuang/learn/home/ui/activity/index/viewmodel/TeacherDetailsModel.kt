package pro.haichuang.learn.home.ui.activity.index.viewmodel

import android.databinding.Bindable
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.bean.BaseModel

class TeacherDetailsModel : BaseModel() {

    @Bindable
    var online = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.online)
        }

    var intro = ""
    var skill = ""
    var beginTime = ""
    var endTime = ""
    var teachername = ""
    var teacherImg = ""
    var commentCount = 0
    var attentionCount = 0
    var id = 0
    var questionCount = 0
    var subject = 0
    var type = 0
}