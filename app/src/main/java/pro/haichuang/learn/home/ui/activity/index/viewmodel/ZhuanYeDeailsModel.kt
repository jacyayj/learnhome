package pro.haichuang.learn.home.ui.activity.index.viewmodel

import pro.haichuang.learn.home.bean.BaseModel

class ZhuanYeDeailsModel : BaseModel() {

    var id = 0
    var collegeId = 0
    var educationYear = 0
    var enrollCount = 0
    var graduateCount = 0
    var score = 0
    var jobGrade = 0
    var level = 0f
    var tuition = 0f
    var code = ""
    var mainMajorCode = ""
    var majorCategory = ""
    var majorName = ""
    val jobStr
        get() = when(jobGrade){
            1->"B"
            2->"B+"
            3->"A"
            else->"A+"
        }
    var subMajorCode = ""
    var collegeName = ""
    var intro = ""

}