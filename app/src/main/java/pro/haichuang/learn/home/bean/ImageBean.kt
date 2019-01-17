package pro.haichuang.learn.home.bean

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

class ImageBean : BaseObservable() {

    @Bindable
    var canDelete = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.canDelete)
        }

    var url = ""


}