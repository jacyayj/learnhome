package pro.haichuang.learn.home.ui.activity.index.itemmodel

class MajorModel {

    var code = ""
    var majorCategory = ""
    var educationYear = 0
    var enrollCount = 0
    var tuition = 0
    var subject = 0
    var id = ""
    var majorName = ""

    val recommendStr
        get() = "学历层次：${if (subject == 1) "本" else "专"}科 专业类别：$majorCategory\n授予学位：历史学学士 修学年限：${educationYear}年"
}