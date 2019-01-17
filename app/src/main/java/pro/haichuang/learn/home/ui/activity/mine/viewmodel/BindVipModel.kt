package pro.haichuang.learn.home.ui.activity.mine.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.android.databinding.library.baseAdapters.BR

class BindVipModel : BaseObservable() {

    @Bindable
    var online = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.online)
        }

    fun toggleOnline(v: View) {
        online = online.not()
    }
}