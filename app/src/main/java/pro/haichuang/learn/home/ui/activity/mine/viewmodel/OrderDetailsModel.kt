package pro.haichuang.learn.home.ui.activity.mine.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import pro.haichuang.learn.home.bean.BaseModel

class OrderDetailsModel : BaseModel() {

    @Bindable
    var type = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }
}