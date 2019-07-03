package pro.haichuang.learn.home.ui.activity.mine.itemmodel

import com.google.gson.annotations.SerializedName

class FqaModel {

    var id = 0

    var typeName = ""

    var subName = ""

    var question = ""

    var title = ""

    @SerializedName("fqas", alternate = ["guides"])
    var list: ArrayList<FqaModel>? = null
}