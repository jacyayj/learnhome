package pro.haichuang.learn.home.ui.activity.message.itemmodel

import com.google.gson.annotations.SerializedName
import pro.haichuang.learn.home.bean.ImageBean

class MyCommentModel {

    var contentType = 0

    var content: Comment? = null

    var upUser: User? = null

    inner class Comment {
        var id = 0
        @SerializedName("releaseDate", alternate = ["createTime"])
        var createTime = ""
        @SerializedName("title", alternate = ["text"])
        var text = ""
        var picArr: ArrayList<ImageBean>? = null
        val picPath
            get() = picArr?.let { if (it.isEmpty()) "" else it.first().picPaths } ?: ""
    }

    inner class User {
        var uid = 0
        var userImg = ""
        var realname = ""
    }
}