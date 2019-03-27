package pro.haichuang.learn.home.ui.activity.index.itemmodel

class ItemTeacherModel {

    var id = 0
    var commentCount = 0
    var questionCount = 0
    var replyDuration = 0
    var subject = 0
    var intro = ""
    var teacherImg = ""
    var teachername = ""
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