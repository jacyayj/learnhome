package pro.haichuang.learn.home.bean

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

class Author : BaseObservable() {
    var userImg = ""
    var realname = ""
    var userId = 0
    @Bindable
    var hasAttention = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.hasAttention)
        }
}