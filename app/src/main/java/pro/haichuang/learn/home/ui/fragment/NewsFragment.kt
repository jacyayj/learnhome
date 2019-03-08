package pro.haichuang.learn.home.ui.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.fragment_news.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.news.NewsDetailsActivity
import pro.haichuang.learn.home.ui.fragment.itemview.ItemNews
import pro.haichuang.learn.home.utils.GsonUtil

@ContentView(R.layout.fragment_news)
class NewsFragment : BaseFragment() {

    private lateinit var tabBeans: ArrayList<TabBean>
    private val adapter by lazy { CommonAdapter<ItemNews>(layoutInflater, R.layout.item_find_other) }
    override fun initData() {
        listView.adapter = adapter
        post(Url.News.Channel)
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(NewsDetailsActivity::class.java, Pair(Constants.NEWS_ID, adapter.getItem(position).id))
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                pageParams.put("path", tabBeans[p0?.position ?: 0].path)
                refresh_layout.autoRefresh()
            }
        })
    }

    private fun initTab() {
        for (bean in tabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_vip_14, tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            tab.addTab(tab.newTab().setCustomView(binding.root))
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.News.List -> {
                val rows = GsonUtil.parseRows(result, ItemNews::class.java)
                rows.list?.let { dealRows(adapter, it) }
            }
            Url.News.Channel -> {
                tabBeans = GsonUtil.parseArray(result, TabBean::class.java)
                initTab()
                pageUrl = Url.News.List
                pageParams.put("path", "gkzc")
                refresh_layout.autoRefresh()
            }
        }
    }
}