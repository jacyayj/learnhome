package pro.haichuang.learn.home.ui.activity.message

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import kotlinx.android.synthetic.main.activity_my_up.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.message.itemmodel.MyCommentModel
import pro.haichuang.learn.home.utils.GsonUtil

@ContentView(R.layout.activity_my_up)
class MyUpActivity : BaseActivity() {

    private val adapter by lazy { CommonAdapter<MyCommentModel>(layoutInflater,R.layout.item_my_up) }

    override fun initData() {
        titleModel.title = "点赞"
        listView.adapter = adapter
        pageUrl = Url.Up.My
        fetchPageData()
    }

    override fun onSuccess(url: String, result: Any?) {
        when(url){
            Url.Up.My->GsonUtil.parseRows(result,MyCommentModel::class.java).list?.let { dealRows(adapter,it) }
        }
    }

}
