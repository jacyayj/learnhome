package pro.haichuang.learn.home.ui.activity.mine.itemmodel

import pro.haichuang.learn.home.bean.ImageBean

class ArticleModel {

    var id = 0
    var commentsCount = 0
    var views = 0
    var releaseDate = ""
    var title = ""
    var picArr: ArrayList<ImageBean>? = null

    val picPath
        get() = if (picArr.isNullOrEmpty()) "" else picArr?.get(0)?.picPaths
}