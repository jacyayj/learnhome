package pro.haichuang.learn.home.ui.activity.index.itemmodel

class VideoModel {

    var id = 1
    var views = 1
    var author = ""
    var contentImg = ""
    var releaseDate = ""
    var title = ""
    var attr: Detail? = null

    class Detail {
        var duration = ""
        var videoUrl = ""
        var videoType = ""
        var authorImg = ""
    }
}