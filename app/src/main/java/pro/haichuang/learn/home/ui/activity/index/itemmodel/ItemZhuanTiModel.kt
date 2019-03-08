package pro.haichuang.learn.home.ui.activity.index.itemmodel

import android.databinding.BaseObservable

class ItemZhuanTiModel : BaseObservable() {

    var attr: Address? = null
    var title = ""
    var titleImg = ""
    var id = 0

    class Address {
        var duration = ""
        var address = ""
        var teacherName = ""
        var lectureTime = ""
        var limitNumber = ""
        var teacherImg = ""
    }
}