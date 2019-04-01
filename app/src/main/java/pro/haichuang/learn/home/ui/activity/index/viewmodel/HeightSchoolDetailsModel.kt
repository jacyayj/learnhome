package pro.haichuang.learn.home.ui.activity.index.viewmodel

import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.utils.DataUtils

class HeightSchoolDetailsModel : BaseModel() {

    var id = 0
    var collegeNature = 0
    var collegeType = 0
    var enrollBatch = 0
    var enrollNumber = 0
    var priority = 0
    var collegeName = ""
    var collegeLevel = ""
    var country = ""
    var englishName = ""
    var province = ""
    var contact = ""
    var enrollCode = ""
    var enrollGuide = ""
    var enrollYear = ""
    var mainConstruct = ""
    var logo = ""
    var viewUrl = ""
    var website = ""
    var intro = ""
    var address = ""
    var isNew = false

    val provinceStr
        get() = DataUtils.findProvinceById(province)

    val countryStr
        get() = DataUtils.findProvinceById(country)

    val enrollBatchStr
        get() = DataUtils.findPiCiById(enrollBatch.toString())

    val collegeTypeStr
        get() = DataUtils.findTypeById(collegeType.toString())

    val collegeTypeStrs
        get() = DataUtils.findLevelByIds(collegeLevel)

    val collegeNatureStr
        get() = DataUtils.findChuangBanById(collegeNature.toString())
}