package pro.haichuang.learn.home.ui.activity.mine.itemmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import pro.haichuang.learn.home.BR

class FollowModel :BaseObservable(){

    @Bindable
    var follow = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.follow)
        }
    fun toggleFollow(view: View){
        follow = follow.not()
    }
}