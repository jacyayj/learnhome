package pro.haichuang.learn.home.bean

import pro.haichuang.learn.home.bean.BaseModel
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

class ImageBean : BaseModel() {

    @Bindable
    var canDelete = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.canDelete)
        }

    var url = ""


}