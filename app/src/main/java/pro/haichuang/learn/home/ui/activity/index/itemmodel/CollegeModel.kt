package pro.haichuang.learn.home.ui.activity.index.itemmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import pro.haichuang.learn.home.utils.DataUtils

class CollegeModel : BaseObservable() {

    var id = 0
    var collegeNature = 0
    var collegeType = 0
    var enrollBatch = 0
    var enrollNumber = 0
    var enrollCode = ""
    var enrollYear = ""
    var collegeName = ""
    var collegeLevel = ""
    var country = ""
    var englishName = ""
    var province = ""
    var logo = ""
    var tuitionFee = ""
    var isNew = false
    var picArr: Array<String>? = null
    val pic
        get() = if (picArr.isNullOrEmpty()) "" else picArr?.get(0)

    val provinceStr
        get() = DataUtils.findProvinceById(province)

    val countryStr
        get() = DataUtils.findProvinceById(country)

    val collegeNatureStr
        get() = when (collegeNature) {
            1 -> "公办"
            2 -> "民办"
            else -> "中外合资"
        }

    val enrollBatchStr
        get() = when (collegeNature) {
            1 -> "本科提前批次"
            2 -> "本科第一批次"
            3 -> "本科第二批次"
            4 -> "本科预科"
            5 -> "专科提前批次"
            else -> "专科批次"
        }

    val collegeTypeStr
        get() = when (collegeNature) {
            1 -> "综合"
            2 -> "理工"
            3 -> "师范"
            4 -> "农林"
            5 -> "政法"
            6 -> "医药"
            7 -> "财经"
            8 -> "民族"
            9 -> "语言"
            10 -> "艺术"
            11 -> "体育"
            12 -> "军事"
            else -> "公安"
        }

    @Bindable
    var compare = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.compare)
        }

    @Bindable
    var checked = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.checked)
        }
}