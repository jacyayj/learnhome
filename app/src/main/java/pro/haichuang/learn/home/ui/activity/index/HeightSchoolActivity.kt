package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_height_school.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.NEWS_ID
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.news.NewsDetailsActivity
import pro.haichuang.learn.home.ui.dialog.GridMultiplePopup
import pro.haichuang.learn.home.ui.fragment.itemview.ItemNews
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_height_school)
class HeightSchoolActivity : BaseActivity() {

    private val adapter by lazy { CommonAdapter<ItemNews>(layoutInflater, R.layout.item_find_other) }
    private val province by lazy {
        GridMultiplePopup(choose_city, false) {
            area = it
            fetchPageData()
        }
    }
    private var area = ""

    override fun initData() {
        titleModel.title = "高校单招"
        listView.adapter = adapter
        pageUrl = Url.HeightSchool.List
        fetchPageData()
    }

    override fun setPageParams(pageParams: HttpParams) {
        if (area.isNotEmpty())
            pageParams.put("area", area)
        else
            pageParams.remove("area")
    }

    override fun onSuccess(url: String, result: Any?) {
        GsonUtil.parseRows(result, ItemNews::class.java).list?.let {
            dealRows(adapter, it)
        }
    }

    override fun initListener() {
        choose_city.setOnClickListener {
            province.show(2)
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(NewsDetailsActivity::class.java, Pair("isHeightSchool",true), Pair(NEWS_ID, adapter.getItem(position).id))
        }
    }
}
