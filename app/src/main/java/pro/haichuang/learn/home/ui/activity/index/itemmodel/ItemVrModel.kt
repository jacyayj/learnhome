package pro.haichuang.learn.home.ui.activity.index.itemmodel

import android.databinding.BaseObservable
import pro.haichuang.learn.home.utils.DataUtils

class ItemVrModel : BaseObservable() {

    var collegeName = ""
    var logo = ""
    var province = 0L
    var viewUrl = ""
    var enrollBatch = 0
    var id = 0

    val batchStr
        get() = DataUtils.findPiCiById(enrollBatch)

    val provinceStr
        get() = DataUtils.findProvinceById(province)
}