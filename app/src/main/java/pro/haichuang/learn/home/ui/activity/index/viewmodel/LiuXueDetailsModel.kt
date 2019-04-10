package pro.haichuang.learn.home.ui.activity.index.viewmodel

import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.bean.ImageBean
import pro.haichuang.learn.home.utils.DataUtils

class LiuXueDetailsModel : BaseModel() {

    var id = 0
    var collegeNature = 0
    var istudentNumber = 0
    var language = 0
    var priority = 0
    var studentNumber = 0
    var teacherNumber = 0
    var ts_ratio = ""
        get() = if (field.isEmpty()) "暂无数据" else field
    var toefl = ""
        get() = if (field.isEmpty()) "暂无数据" else field
    var logo = ""
    var picArr: ArrayList<ImageBean>? = null
    var applyCondition = ""
        get() = if (field.isEmpty()) "暂无数据" else field
    var address = ""
    var accommodationFee = ""
        get() = if (field.isEmpty()) "暂无数据" else field
    var tuitionFee = ""
        get() = if (field.isEmpty()) "暂无数据" else field
    var website = ""
        get() = if (field.isEmpty()) "暂无数据" else field
    var collegeName = ""
    var collegeTypeId = ""
    var collegeTypeName = ""
    var contact = ""
        get() = if (field.isEmpty()) "暂无数据" else field
    var country = ""
    var district = ""
        get() = if (field.isEmpty()) "暂无数据" else field
    var email = ""
        get() = if (field.isEmpty()) "暂无数据" else field
    var englishName = ""
    var foundTime = ""
    var hotMajor = ""
        get() = if (field.isEmpty()) "暂无数据" else field
    var ielts = ""
        get() = if (field.isEmpty()) "暂无数据" else field
    var intro = ""

    val natureStr
        get() = DataUtils.findChuangBanById(collegeNature)

    val countryStr
        get() = DataUtils.findCountryByCode(country)

    val languageStr
        get() = DataUtils.findLanguageById(language)

    val picStr
        get() = if (picArr.isNullOrEmpty()) "" else picArr?.get(0)?.picPaths ?: ""
}