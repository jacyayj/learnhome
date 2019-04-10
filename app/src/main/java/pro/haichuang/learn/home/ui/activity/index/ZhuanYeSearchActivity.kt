package pro.haichuang.learn.home.ui.activity.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.app.FragmentPagerAdapter
import com.jacy.kit.config.ContentView
import kotlinx.android.synthetic.main.activity_zhuanye_search.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.fragment.RankFragment
import pro.haichuang.learn.home.ui.fragment.ZhuanYeFragment


@ContentView(R.layout.activity_zhuanye_search)
class ZhuanYeSearchActivity : BaseActivity() {
    private val tabBeans by lazy { arrayListOf(TabBean("本科专业"), TabBean("专科专业"),/* TabBean(true, "学科评估"),*/ TabBean("学科排位")) }
    private val fs by lazy {
        arrayListOf(ZhuanYeFragment.newInstance(1),
                ZhuanYeFragment.newInstance(2),
//                PinGuFragment.newInstance(),
                RankFragment.newInstance())
    }

    override fun initData() {
        titleModel.title = "专业查询"
        pager.offscreenPageLimit = 3
        pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(p0: Int) = fs[p0]
            override fun getCount() = 3
            override fun getPageTitle(position: Int) = tabBeans[position].text
        }
        tab.setupWithViewPager(pager)
        initTab()
    }

    private fun initTab() {
        tabBeans.forEachIndexed { index, tabBean ->
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_vip, tab, false)
            binding.setVariable(BR.item, tabBean)
            binding.executePendingBindings()
            tab.getTabAt(index)?.customView = binding.root
        }
    }

}
