package pro.haichuang.learn.home.ui.activity.index.viewmodel

import android.databinding.Bindable
import com.jacy.kit.net.Params
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.Url

class TeacherDetailsModel : BaseModel() {

    @Bindable
    var online = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.online)
        }
    @Params([Url.Teacher.Order], "payType")
    var payType = 0
    @Params([Url.Teacher.Order], "orderType")
    var orderType = 0
    @Params([Url.Teacher.Order], "teacherId")
    var id = 0
    var intro = ""
    var skill = ""
    var beginTime = ""
    var endTime = ""
    var teachername = ""
    var teacherImg = ""
    var commentCount = 0
    var attentionCount = 0
    var questionCount = 0
    var subject = 0
    var type = 0
}