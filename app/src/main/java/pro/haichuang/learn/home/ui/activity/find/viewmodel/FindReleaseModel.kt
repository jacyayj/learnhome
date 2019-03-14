package pro.haichuang.learn.home.ui.activity.find.viewmodel

import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.jacy.kit.config.toast
import com.jacy.kit.net.Params
import pro.haichuang.learn.home.bean.BaseModel
import pro.haichuang.learn.home.net.Url

class FindReleaseModel : BaseModel() {
    @Params([Url.Publish.Save], "channelId")
    var id = 0

    @Params([Url.Publish.Save], "picPaths")
    var picPaths = ""

    @Params([Url.Publish.Save], "title")
    @Bindable
    var title = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }
    @Params([Url.Publish.Save], "txt")
    @Bindable
    var txt = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.txt)
        }

    override fun checkSuccess(url: String): Boolean {
        return when {
            title.isEmpty() -> {
                toast("请输入标题")
                false
            }
            txt.isEmpty() -> {
                toast("请输入内容")
                false
            }
            else -> true
        }
    }
}