package pro.haichuang.learn.home.ui.activity.news

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import pro.haichuang.learn.home.bean.BaseModel

class NewsDetailsModel : BaseModel() {

    var id = 0
    var views = 0
    var origin = ""
    var txt = ""
    var typeName = ""
    var title = ""
    var releaseDate = ""
    @Bindable
    var hasCollect = false
    set(value) {
        field = value
        notifyPropertyChanged(BR.hasCollect)
    }
}