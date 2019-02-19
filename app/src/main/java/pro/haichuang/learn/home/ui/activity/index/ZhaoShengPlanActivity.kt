package pro.haichuang.learn.home.ui.activity.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.gone
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.show
import kotlinx.android.synthetic.main.activity_zhao_sheng_plan.*
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemCompareModel
import pro.haichuang.learn.home.ui.dialog.MultipleChoosePopup
import pro.haichuang.learn.home.ui.dialog.GridMultiplePopup
import pro.haichuang.learn.home.ui.dialog.DoubleChoosePopup


@ContentView(R.layout.activity_zhao_sheng_plan)
class ZhaoShengPlanActivity : BaseActivity() {
    private val tabBeans by lazy { arrayListOf(TabBean("选择层次"), TabBean("所在地"), TabBean("高级查询")) }
    private val provincePopup by lazy { GridMultiplePopup(tab) }
    private val vipPopup by lazy { DoubleChoosePopup(tab) }
    private val typePopup by lazy {
        MultipleChoosePopup(tab, arrayListOf("提前批录取院校",
                "本科第一批录取院校", "本科第二批录取院校", "专科批", "国家专项计划"))
    }
    private val data by lazy {
        arrayListOf(ItemCompareModel(),
                ItemCompareModel(), ItemCompareModel(), ItemCompareModel(), ItemCompareModel())
    }

    override fun initData() {
        initTab()
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_zhao_sheng_plan, data)
    }

    override fun initListener() {
        cancel.setOnClickListener {
            data.forEach {
                it.compare = false
            }
            compare_view.gone()
            search_btn.show()
            search_view.show()
        }
        confirm.setOnClickListener {
            data.forEach {
                it.compare = false
            }
            compare_view.gone()
            search_btn.show()
            search_view.show()
            mStartActivity(PlanCompareActivity::class.java)
        }
        compare.setOnClickListener {
            data.forEach {
                it.compare = true
            }
            compare_view.show()
            search_btn.gone()
            search_view.gone()
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                p0?.apply {
                    tabBeans[position].checked = tabBeans[position].checked.not()
                    if (tabBeans[position].checked)
                        when (position) {
                            0 -> typePopup.showAsDropDown(tab)
                            1 -> provincePopup.show(2)
                            2 -> vipPopup.showAsDropDown(tab)
                        }
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                p0?.apply {
                    tabBeans[position].checked = false
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
            }
        })
    }

    private fun initTab() {
        for (bean in tabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_14, tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            tab.addTab(tab.newTab().setCustomView(binding.root))
        }
    }
}
