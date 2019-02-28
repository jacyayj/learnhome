package pro.haichuang.learn.home.ui.activity.mine.viewmodel

import pro.haichuang.learn.home.bean.BaseModel
import android.databinding.Bindable
import android.view.View
import com.android.databinding.library.baseAdapters.BR

class BindVipModel : BaseModel() {

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