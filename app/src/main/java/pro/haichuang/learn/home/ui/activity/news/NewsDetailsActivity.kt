package pro.haichuang.learn.home.ui.activity.news

import com.jacy.kit.config.ContentView
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_news_details.*
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_news_details)
class NewsDetailsActivity : DataBindingActivity<NewsDetailsModel>() {


    override fun initData() {
        val params = HttpParams()
        params.put("id", intent.getIntExtra(Constants.NEWS_ID, -1).toString())
        post(Url.News.Get, params, needSession = true)
    }

    override fun onSuccess(url: String, result: Any?) {
        notifyModel(GsonUtil.parseObject(result, NewsDetailsModel::class.java))
        content.loadData(model.txt)
    }
}
