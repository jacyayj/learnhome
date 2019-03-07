package pro.haichuang.learn.home.ui.activity.news

import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_news_details.*
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

    override fun initListener() {
        collect.setOnClickListener {
            val params = HttpParams()
            params.put("id", model.id.toString())
            params.put("operate", if (model.hasCollect) "0" else "1")
            post(Url.Content.Collect, params, needSession = true)
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.News.Get -> {
                notifyModel(GsonUtil.parseObject(result, NewsDetailsModel::class.java))
                content.loadData(model.txt)
            }
            Url.Content.Collect -> {
                if (model.hasCollect)
                    toast("取消收藏成功")
                else
                    toast("收藏成功")
                model.hasCollect = model.hasCollect.not()
            }
        }
    }
}
