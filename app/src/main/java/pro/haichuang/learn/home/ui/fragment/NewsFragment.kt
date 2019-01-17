package pro.haichuang.learn.home.ui.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.fragment_news.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.ui.activity.news.NewsDetailsActivity

@ContentView(R.layout.fragment_news)
class NewsFragment : BaseFragment() {
    private val tabBeans by lazy { arrayListOf(TabBean("高考政策"), TabBean(true, "信息资讯"), TabBean("高考习题"), TabBean(true, "热点问答")) }

    override fun initData() {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_find_other, arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
        initTab()
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(NewsDetailsActivity::class.java)
        }
    }

    private fun initTab() {
        for (bean in tabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_vip_14, tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            tab.addTab(tab.newTab().setCustomView(binding.root))
        }
    }

}