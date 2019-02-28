package pro.haichuang.learn.home.ui.activity.mine.itemmodel

import pro.haichuang.learn.home.bean.BaseModel
import android.databinding.Bindable
import android.view.View
import pro.haichuang.learn.home.BR

class FansModel :BaseModel(){

    @Bindable
    var fans = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.fans)
        }
    fun toggleFans(view: View){
        fans = fans.not()
    }
}