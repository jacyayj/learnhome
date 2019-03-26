package pro.haichuang.learn.home.ui.activity.index.itemmodel

class CollegeModel {

    var id = 0
    var collegeName = ""
    var country = ""
    var englishName = ""
    var logo = ""
    var tuitionFee = ""
    var picArr: Array<String>? = null
    val pic
        get() = if (picArr.isNullOrEmpty()) "" else picArr?.get(0)

}