package pro.haichuang.learn.home.ui.activity.mine.itemmodel

import android.databinding.Bindable
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.bean.BaseModel

class FansModel : BaseModel() {

    var id = 0
    var totalFans = 0
    var totalPublish = 0
    var userImg = ""
    var realname = ""

    @Bindable
    var hasAttention = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.hasAttention)
        }
}