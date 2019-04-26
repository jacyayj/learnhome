package pro.haichuang.learn.home.bean

import android.databinding.BaseObservable

class Teacher : BaseObservable() {

    var teacherImg = ""
    var teachername = ""
    var skill = ""
    var subject = 1
    var id = 1
    var type = ""

    val subjectStr
        get() = when (subject) {
            10->"普通一对一"
            11->"自主招生一对一"
            12->"艺术类一对一"
            20->"亲子关系"
            21->"考前舒压"
            22->"提高自信"
            23->"消除焦虑"
            24->"心理分析"
            else -> ""
        }
}