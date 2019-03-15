package pro.haichuang.learn.home.ui.activity.mine.itemmodel

import android.databinding.Bindable
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.bean.BaseModel

class FollowModel : BaseModel() {

    var id = 0
    var totalFans = 0
    var totalPublish = 0
    var realname = ""
    var userImg = ""
    @Bindable
    var follow = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.follow)
        }
}