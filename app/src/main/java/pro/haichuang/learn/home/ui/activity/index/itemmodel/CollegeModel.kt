package pro.haichuang.learn.home.ui.activity.index.itemmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.graphics.Color
import com.android.databinding.library.baseAdapters.BR
import pro.haichuang.learn.home.utils.DataUtils

class CollegeModel : BaseObservable() {

    var id = 0
    var collegeNature = 0
    var collegeType = 0
    var enrollBatch = 0
    var enrollNumber = 0
    var score = 0
    var mScore = 0
    var middleScore = 0
    var subject = 0
    var enrollCode = ""
    var enrollYear = ""
    var collegeName = ""
    var collegeLevel = ""
    var country = ""
    var englishName = ""
    var majorName = ""
    var province = 0L
    var logo = ""
    var tuitionFee = ""
    var majorIds = ""
    var isNew = false
    @Bindable
    var showMajor = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showMajor)
        }
    var obey = false
    @Bindable
    var onShot = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.onShot)
        }
    var picArr: Array<String>? = null
    var majors: ArrayList<MajorModel>? = null
    val pic
        get() = if (picArr.isNullOrEmpty()) "" else picArr?.get(0)

    val provinceStr
        get() = DataUtils.findProvinceById(province)

    val levelStr
        get() = DataUtils.findLevelByIds(collegeLevel)

    val countryStr
        get() = DataUtils.findCountryByCode(country)

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

    var priority = 0
        get() = when (zhiyuan.subSequence(0, 1)) {
            "A" -> 1
            "B" -> 2
            "C" -> 3
            "D" -> 4
            "E" -> 5
            "F" -> 6
            "G" -> 7
            "H" -> 8
            "I" -> 9
            else -> field
        }

    val priorityLetter
        get() = zhiyuan.subSequence(0, 1)

    val majorSize
        get() = majorIds.split(",").size

    val color
        get() = when (mScore) {
            in 0..score -> Color.parseColor("#FF6F6F")
            in score..middleScore -> Color.parseColor("#FFE450")
            else -> Color.parseColor("#8BDE84")
        }
    val zhiyuanColor
        get() = when (zhiyuan.subSequence(0, 1)) {
            "A" -> Color.parseColor("#FFAA3B")
            "B" -> Color.parseColor("#FFCA3B")
            "C" -> Color.parseColor("#8FCF61")
            "D" -> Color.parseColor("#83DCE4")
            "E" -> Color.parseColor("#7CBCF8")
            "F" -> Color.parseColor("#C68CEA")
            "G" -> Color.parseColor("#EA8CCE")
            "H" -> Color.parseColor("#8C91EA")
            "I" -> Color.parseColor("#95D13F")
            else -> -1
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


    @Bindable
    var zhiyuan = "填报为"
        set(value) {
            field = value
            notifyPropertyChanged(BR.zhiyuan)
        }

    val zhiyuanStr
        get() = when (priority) {
            1 -> "志愿A"
            2 -> "志愿B"
            3 -> "志愿C"
            4 -> "志愿D"
            5 -> "志愿E"
            else -> "志愿F"
        }

    @Bindable
    var choosed = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.choosed)
        }
}