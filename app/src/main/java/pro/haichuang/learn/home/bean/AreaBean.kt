package pro.haichuang.learn.home.bean

import android.view.View

class AreaBean {

    var name = ""

    var code = ""

    var pinyin = ""

    var showIcon = false

    var result: (city: String) -> Unit = {}

    fun click(v: View) {
        result(name)
    }
}