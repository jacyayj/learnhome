package pro.haichuang.learn.home.ui.activity.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.gone
import com.jacy.kit.config.show
import kotlinx.android.synthetic.main.activity_height_school_details.*
import kotlinx.android.synthetic.main.layout_jianzhang.*
import kotlinx.android.synthetic.main.layout_school_details_tabchildview_1.*
import kotlinx.android.synthetic.main.layout_school_details_tabchildview_2.*
import kotlinx.android.synthetic.main.layout_school_details_tabchildview_3.*
import kotlinx.android.synthetic.main.layout_school_details_tabchildview_4.*
import kotlinx.android.synthetic.main.layout_school_details_tabchildview_5.*
import kotlinx.android.synthetic.main.layout_school_details_tabview_1.*
import kotlinx.android.synthetic.main.layout_school_details_tabview_2.*
import kotlinx.android.synthetic.main.layout_school_details_tabview_3.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemZuanYeModel
import pro.haichuang.learn.home.ui.dialog.LegendDialog


@ContentView(R.layout.activity_height_school_details)
class HeightSchoolDetailsActivity : BaseActivity() {
    private val childTabs by lazy { arrayListOf(child_tab_view1, child_tab_view2, child_tab_view3, child_tab_view4, child_tab_view5) }
    private val tabBeans by lazy { arrayListOf(TabBean("高校简介"), TabBean(true, "求学价值"), TabBean(true, "主要专业"), TabBean("招生简章")) }
    override fun initData() {
        titleModel.title = "高校详情"
        initTab()
        zuanye_listview.adapter = CommonAdapter(layoutInflater,R.layout.item_height_school_details_3, arrayListOf(ItemZuanYeModel("社会工作"),
                ItemZuanYeModel("社会工作"),
                ItemZuanYeModel("应用语言学"),
                ItemZuanYeModel("阿拉伯语"),
                ItemZuanYeModel("波斯语"),
                ItemZuanYeModel("印度尼西亚语"),
                ItemZuanYeModel("印地语"),
                ItemZuanYeModel("缅甸语"),
                ItemZuanYeModel("蒙古语"),
                ItemZuanYeModel("乌尔都语"),
                ItemZuanYeModel("考古学")))
    }

    override fun initListener() {
        legend.setOnClickListener {
            LegendDialog(this).show(0)
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position) {
                    0 -> {
                        tab_view1.show()
                        tab_view2.gone()
                        tab_view3.gone()
                        jianzhang_view.gone()
                    }
                    1 -> {
                        tab_view1.gone()
                        tab_view2.show()
                        tab_view3.gone()
                        jianzhang_view.gone()
                    }
                    2 -> {
                        tab_view1.gone()
                        tab_view2.gone()
                        tab_view3.show()
                        jianzhang_view.gone()
                    }
                    3 -> {
                        tab_view1.gone()
                        tab_view2.gone()
                        tab_view3.gone()
                        jianzhang_view.show()
                    }
                }
            }
        })
        child_tab.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.tab1 ->toggleChild(0)
                R.id.tab2 ->toggleChild(1)
                R.id.tab3 ->toggleChild(2)
                R.id.tab4 ->toggleChild(3)
                R.id.tab5 ->toggleChild(4)
            }
        }
    }

    private fun toggleChild(position: Int) {
        childTabs.forEachIndexed { index, it ->
            if (index == position)
                it.show()
            else
                it.gone()
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

}
