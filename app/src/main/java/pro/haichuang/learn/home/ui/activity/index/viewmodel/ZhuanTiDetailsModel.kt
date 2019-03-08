package pro.haichuang.learn.home.ui.activity.index.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import pro.haichuang.learn.home.bean.BaseModel

class ZhuanTiDetailsModel : BaseModel() {

    var attr_duration = ""
    var attr_limitNumber = ""
    var titleImg = ""
    var attr_teacherImg = ""
    var attr_teacherName = ""
    var txt = ""
    var attr_address = ""
    var attr_lectureTime = ""
    var title = ""
    var applyCount = 0
    var id = 0
    @Bindable
    var hasCollect = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.hasCollect)
        }

    val applyStr
        get() = "已报名：${applyCount}人"
}