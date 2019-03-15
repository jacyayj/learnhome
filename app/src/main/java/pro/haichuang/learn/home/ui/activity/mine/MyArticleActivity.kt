package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_my_article.*
import kotlinx.android.synthetic.main.item_mine_article.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.find.FindReleaseActivity
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.ArticleModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_my_article)
class MyArticleActivity : BaseActivity() {
    private val adapter by lazy {
        CommonAdapter<ArticleModel>(layoutInflater, R.layout.item_mine_article) { v, t, i ->
            v.delete.setOnClickListener {
                val params = HttpParams()
                params.put("contentId", t.id.toString())
                post(Url.Content.Delete, params, needSession = true) {
                    toast("删除成功")
                    remove(i)
                }
            }
        }
    }

    override fun initData() {
        listView.adapter = adapter
        pageUrl = Url.Content.My
        fetchPageData()
    }

    override fun initListener() {
        to_release.setOnClickListener { mStartActivity(FindReleaseActivity::class.java) }
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Content.My -> {
                GsonUtil.parseRows(result, ArticleModel::class.java).list?.let { dealRows(adapter, it) }
            }
        }
    }

    private fun remove(position: Int) {
        adapter.remove(position)
    }
}
