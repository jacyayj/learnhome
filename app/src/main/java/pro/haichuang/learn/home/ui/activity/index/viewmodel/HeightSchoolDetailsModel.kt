package pro.haichuang.learn.home.ui.activity.index.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.utils.DataUtils

class HeightSchoolDetailsModel : BaseModel() {

    var id = 0
    var collegeNature = 0
    var collegeType = 0
    var enrollBatch = 0
    var enrollNumber = 0
    var priority = 0
    var country = ""
    var collegeName = ""
    var collegeLevel = ""
    var englishName = ""
    var province = 0L
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
    @Bindable
    var hasCollect = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.hasCollect)
        }

    val provinceStr
        get() = DataUtils.findProvinceById(province)

    val countryStr
        get() = DataUtils.findCountryByCode(country)

    val enrollBatchStr
        get() = DataUtils.findPiCiById(enrollBatch)

    val collegeTypeStr
        get() = DataUtils.findTypeById(collegeType)

    val collegeTypeStrs
        get() = DataUtils.findLevelByIds(collegeLevel)

    val collegeNatureStr
        get() = DataUtils.findChuangBanById(collegeNature)
}