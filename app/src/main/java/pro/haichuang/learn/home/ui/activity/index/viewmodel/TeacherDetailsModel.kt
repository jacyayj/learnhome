package pro.haichuang.learn.home.ui.activity.index.viewmodel

import pro.haichuang.learn.home.bean.BaseModel
import android.databinding.Bindable
import pro.haichuang.learn.home.BR

class TeacherDetailsModel : BaseModel() {

    @Bindable
    var online = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.online)
        }
}