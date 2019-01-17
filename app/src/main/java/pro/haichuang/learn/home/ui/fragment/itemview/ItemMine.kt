package pro.haichuang.learn.home.ui.fragment.itemview

import android.databinding.BaseObservable
import android.databinding.Bindable
import pro.haichuang.learn.home.BR

class ItemMine : BaseObservable {

    constructor(icon: Int, text: String, canJump: Boolean = true) : super() {
        this.icon = icon
        this.text = text
        this.canJump = canJump
    }

    constructor(text: String, subText: String = "", canJump: Boolean = true, line: Boolean = false) : super() {
        this.text = text
        this.subText = subText
        this.canJump = canJump
        this.line = line
    }


    var icon = -1

    var text = ""

    @Bindable
    var subText = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.subText)
        }

    var canJump = false

    var line = false
}