package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_my_comment.*
import kotlinx.android.synthetic.main.item_mine_comment.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.NEWS_ID
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.find.FindDetailsActivity
import pro.haichuang.learn.home.ui.activity.find.itemmodel.CommentModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_my_comment)
class MyCommentActivity : BaseActivity() {

    private val adapter by lazy {
        CommonAdapter<CommentModel>(layoutInflater, R.layout.item_mine_comment) { v, t, _ ->
            v.to_details.setOnClickListener {
                mStartActivity(FindDetailsActivity::class.java, Pair(NEWS_ID, t.contentId))
            }
        }
    }

    override fun initData() {
        titleModel.title = "评论"
        listView.adapter = adapter
        pageUrl = Url.Comment.My
        fetchPageData()
    }

    override fun onSuccess(url: String, result: Any?) {
        GsonUtil.parseRows(result, CommentModel::class.java).list?.let {
            if (it.isEmpty()) {
                if (isLoadMore)
                    dealRows(adapter, it)
                else
                    status_layout.showEmpty()
            } else {
                status_layout.showSuccess()
                dealRows(adapter, it)
            }
        }
    }
}
