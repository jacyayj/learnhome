package pro.haichuang.learn.home.config

import pro.haichuang.learn.home.bean.BaseModel
import android.databinding.Bindable
import android.view.View
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R

class TitleModel : BaseModel() {

    @Bindable
    var showBottomeLine = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.showBottomeLine)
        }

    @Bindable
    var showLeft = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.showLeft)
        }

    @Bindable
    var showRight = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showRight)
        }

    @Bindable
    var title = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @Bindable
    var titleLeftIcon = R.drawable.icon_back
        set(value) {
            field = value
            notifyPropertyChanged(BR.titleLeftIcon)
        }

    @Bindable
    var titleLeftText = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.titleLeftText)
        }

    @Bindable
    var titleRightIcon = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.titleRightIcon)
        }

    @Bindable
    var titleRightText = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.titleRightText)
        }

    var onLeftClick: () -> Unit = {}

    var onRightClick: () -> Unit = {}

    fun leftClick(view: View) {
        onLeftClick()
    }

    fun rightClick(view: View) {
        onRightClick()
    }
}