package pro.haichuang.learn.home.ui.fragment.itemview

import android.databinding.BaseObservable
import pro.haichuang.learn.home.bean.ImageBean

class ItemNews : BaseObservable() {

    var id = 0
    var views = 0
    var commentsCount = 0
    var ups = 0
    var releaseDate = ""
    var title = ""
    var description = ""
    var typeName = ""
    var recommend = false
    var picArr: ArrayList<ImageBean>? = null
    val picPath
        get() = if (picArr.isNullOrEmpty()) "" else picArr?.get(0)?.picPaths ?: ""

}