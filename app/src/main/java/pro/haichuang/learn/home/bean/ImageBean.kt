package pro.haichuang.learn.home.bean

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

class ImageBean : BaseModel {
    constructor(picPaths: String) : super() {
        this.picPaths = picPaths
    }

    constructor() : super()

    @Bindable
    var canDelete = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.canDelete)
        }


    @Bindable
    var take = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.take)
        }

    @Bindable
    var picPaths = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.picPaths)
        }
    var uploadPath = ""
    var picDescs = ""
}