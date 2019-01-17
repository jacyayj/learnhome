package pro.haichuang.learn.home.ui.activity.mine.itemmodel

import android.databinding.BaseObservable

class ItemFile : BaseObservable {

    constructor(title: String, content: String, canChoose: Boolean = false) : super() {
        this.title = title
        this.content = content
        this.canChoose = canChoose
    }

    var title = ""
    var content = ""
    var canChoose = false
}