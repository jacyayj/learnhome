package pro.haichuang.learn.home.ui.activity.index

import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_zhao_sheng.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.NEWS_ID
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.news.NewsDetailsActivity
import pro.haichuang.learn.home.ui.dialog.GridMultiplePopup
import pro.haichuang.learn.home.ui.fragment.itemview.ItemNews
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_zhao_sheng)
class ZhaoShengActivity : BaseActivity() {

    private val adapter by lazy { CommonAdapter<ItemNews>(layoutInflater, R.layout.item_find_other) }
    private val province by lazy {
        GridMultiplePopup(choose_city, false) { it, name ->
            area = it
            choose_city.text = name
            fetchPageData()
        }
    }
    private var area = ""
    private var path = "zsjz"

    override fun initData() {
        titleModel.title = "自主招生"
        listView.adapter = adapter
        pageUrl = Url.ZhaoSheng.List
        fetchPageData()
    }

    override fun onSuccess(url: String, result: Any?) {
        GsonUtil.parseRows(result, ItemNews::class.java).list?.let {
            dealRows(adapter, it)
        }
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("path", path)
        if (area.isNotEmpty())
            pageParams.put("area", area)
        else
            pageParams.remove("area")
    }

    override fun initListener() {
        choose_city.setOnClickListener {
            province.show(2)
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(NewsDetailsActivity::class.java, Pair("isZhaoSheng", true), Pair(NEWS_ID, adapter.getItem(position).id))
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position) {
                    0 -> {
                        path = "zsjz"
                        fetchPageData()
                    }
                    1 -> {
                        path = "zhpj"
                        fetchPageData()
                    }
                    2 -> {
                        path = "zszc"
                        fetchPageData()
                    }
                }

            }
        })
    }
}
