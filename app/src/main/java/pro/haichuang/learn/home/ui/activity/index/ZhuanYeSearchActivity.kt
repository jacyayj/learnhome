package pro.haichuang.learn.home.ui.activity.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.gone
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.show
import kotlinx.android.synthetic.main.activity_zhuanye_search.*
import kotlinx.android.synthetic.main.item_zuanye_pinggu.view.*
import kotlinx.android.synthetic.main.layout_zuanye_tab_1.*
import kotlinx.android.synthetic.main.layout_zuanye_tab_2.*
import kotlinx.android.synthetic.main.layout_zuanye_tab_3.*
import pro.haichuang.learn.home.BR
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemPingGuModel
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemZuanYeModel
import pro.haichuang.learn.home.ui.dialog.GridMultiplePopup
import pro.haichuang.learn.home.ui.dialog.InputPopup
import pro.haichuang.learn.home.ui.dialog.LegendDialog
import pro.haichuang.learn.home.ui.dialog.DoubleChoosePopup


@ContentView(R.layout.activity_zhuanye_search)
class ZhuanYeSearchActivity : BaseActivity() {
    private val tabBeans by lazy { arrayListOf(TabBean("本科专业"), TabBean("专科专业"), TabBean(true, "学科评估"), TabBean("学科排位")) }
    private val zuanyeSubTabBeans by lazy { arrayListOf(TabBean("选择专业门类"), TabBean("输入关键字")) }
    private val rankSubTabBeans by lazy { arrayListOf(TabBean("选择查询项"), TabBean("选择学科类别")) }
    private val gridPopup by lazy { GridMultiplePopup(zuanye_sub_tab) }
    private val zuanyePopup by lazy { DoubleChoosePopup(rank_sub_tab) }
    private val inputPopup by lazy { InputPopup(zuanye_sub_tab) }
    private val data by lazy {
        arrayListOf(ItemZuanYeModel("哲学", "哲学/哲学类"),
                ItemZuanYeModel("哲学", "哲学/哲学类"),
                ItemZuanYeModel("哲学", "哲学/哲学类"),
                ItemZuanYeModel("哲学", "哲学/哲学类"),
                ItemZuanYeModel("哲学", "哲学/哲学类"),
                ItemZuanYeModel("哲学", "哲学/哲学类"),
                ItemZuanYeModel("哲学", "哲学/哲学类"),
                ItemZuanYeModel("哲学", "哲学/哲学类"),
                ItemZuanYeModel("哲学", "哲学/哲学类"),
                ItemZuanYeModel("哲学", "哲学/哲学类"),
                ItemZuanYeModel("哲学", "哲学/哲学类"),
                ItemZuanYeModel("哲学", "哲学/哲学类"))
    }

    override fun initData() {
        titleModel.title = "专业查询"
        initTab()
        initSubTab()
        zuanye_listview.adapter = CommonAdapter(layoutInflater, R.layout.item_zuanye_search, data)
        pinggu_listview.adapter = CommonAdapter(layoutInflater, R.layout.item_zuanye_pinggu, arrayListOf(ItemPingGuModel(),
                ItemPingGuModel(), ItemPingGuModel(), ItemPingGuModel(), ItemPingGuModel(), ItemPingGuModel())) { v, t, _ ->
            v.grid.adapter = CommonAdapter(layoutInflater, R.layout.item_zuanye_pinggu_child, t.child)
            v.grid.setOnItemClickListener { _, _, _, _ ->
                mStartActivity(ZhuanYePingGuDetailsActivity::class.java)
            }
        }
    }

    override fun initListener() {
        zuanye_sub_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                p0?.apply {
                    zuanyeSubTabBeans[position].checked = zuanyeSubTabBeans[position].checked.not()
                    if (zuanyeSubTabBeans[position].checked)
                        when (position) {
                            0 -> gridPopup.show(4)
                            1 -> inputPopup.showAsDropDown(tab)
                        }
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                p0?.apply {
                    zuanyeSubTabBeans[position].checked = false
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
            }
        })
        rank_sub_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                p0?.apply {
                    rankSubTabBeans[position].checked = rankSubTabBeans[position].checked.not()
                    if (rankSubTabBeans[position].checked)
                        when (position) {
                            0 -> zuanyePopup.show(1)
                            1 -> gridPopup.show(4)
                        }
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                p0?.apply {
                    rankSubTabBeans[position].checked = false
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
            }
        })
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position) {
                    0, 1 -> {
                        zuanye_tab_view1.show()
                        zuanye_tab_view2.gone()
                        zuanye_tab_view3.gone()
                    }
                    2 -> {
                        zuanye_tab_view1.gone()
                        zuanye_tab_view2.show()
                        zuanye_tab_view3.gone()
                    }
                    3 -> {
                        zuanye_tab_view1.gone()
                        zuanye_tab_view2.gone()
                        zuanye_tab_view3.show()
                    }
                }
            }
        })
        zuanye_listview.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(ZhuanYeDetailsActivity::class.java)
        }
        rank_listview.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(ZhuanYeDetailsActivity::class.java)
        }
        rank_listview.adapter = CommonAdapter(layoutInflater, R.layout.item_zuanye_rank, arrayListOf(1, 2, 3, 4, 5, 6, 7, 8))
        fetch_result.setOnClickListener {
            it.gone()
            result_view.show()
        }
        legend.setOnClickListener {
            LegendDialog(this).show(1)
        }
    }

    private fun initTab() {
        for (bean in tabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_vip, tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            tab.addTab(tab.newTab().setCustomView(binding.root))
        }
    }

    private fun initSubTab() {
        for (bean in zuanyeSubTabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_17, zuanye_sub_tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            zuanye_sub_tab.addTab(zuanye_sub_tab.newTab().setCustomView(binding.root))
        }
        for (bean in rankSubTabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_17, rank_sub_tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            rank_sub_tab.addTab(rank_sub_tab.newTab().setCustomView(binding.root))
        }
    }
}
