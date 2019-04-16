package pro.haichuang.learn.home.ui.activity.index.itemmodel

class ItemZuanYeModel {


    var id = 0
    var collegeId = 0
    var jobGrade = 0
    var graduateCount = 0
    var majorCategory = ""
    var majorName = ""
    val jobStr
        get() = when (jobGrade) {
            1 -> "B"
            2 -> "B+"
            3 -> "A"
            else -> "A+"
        }

    var collegeName = ""

}