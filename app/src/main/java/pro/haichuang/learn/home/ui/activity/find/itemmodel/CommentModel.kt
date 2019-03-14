package pro.haichuang.learn.home.ui.activity.find.itemmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import pro.haichuang.learn.home.bean.Author
import pro.haichuang.learn.home.bean.BaseModel

class CommentModel : BaseModel() {
    var parentName = "就在科华中路科华之星"

    var commenter: Author? = null

    var createTime = ""
    var id = 0
    @Bindable
    var ups = 0
    set(value) {
        field = value
        notifyPropertyChanged(BR.ups)
    }
    var text = ""
    var child: ArrayList<CommentModel>? = null
}