package pro.haichuang.learn.home.ui.activity.index.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import pro.haichuang.learn.home.BR

class TeacherDetailsModel : BaseObservable() {

    @Bindable
    var online = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.online)
        }
}