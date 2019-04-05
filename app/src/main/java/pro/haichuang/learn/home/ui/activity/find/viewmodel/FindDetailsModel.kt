package pro.haichuang.learn.home.ui.activity.find.viewmodel

import com.jacy.kit.config.toast
import com.jacy.kit.net.Params
import pro.haichuang.learn.home.bean.Author
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.bean.ImageBean
import pro.haichuang.learn.home.net.Url

class FindDetailsModel : BaseModel() {

    @Params([Url.Comment.Save], "contentId")
    var id = 0
    var views = 0
    var commentsCount = 0
    var title = ""
    var txt = ""
    var shareUrl = ""
    var releaseDate = ""
    var picArr: ArrayList<ImageBean>? = null
    var author: Author? = null
    var hasCollect = false
    @Params([Url.Comment.Save], "text")
    var comment = ""

    @Params([Url.Comment.Save], "parentId")
    var parentId = ""

    override fun checkSuccess(url: String): Boolean {
        return if (comment.isEmpty()) {
            toast("请输入评论内容")
            false
        } else true
    }
}