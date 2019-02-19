package pro.haichuang.learn.home.ui.activity.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_liu_xue.*
import kotlinx.android.synthetic.main.item_liu_xue.view.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.dialog.SingleChoosePopup


@ContentView(R.layout.activity_liu_xue)
class LiuXueActivity : BaseActivity() {
    private var operationPosition = 0
    private val tabBeans by lazy { arrayListOf(TabBean("地区"), TabBean("类别"), TabBean("学校类型")) }
    private val areaPopup by lazy {
        SingleChoosePopup(tab, arrayListOf("全世界", "荷兰", "加拿大", "德国", "瑞典", "法国", "意大利", "日本")).apply {
            setOnDismissListener {
                tabBeans[operationPosition].checked = false
            }
        }
    }
    private val typePopup by lazy {
        SingleChoosePopup(tab, arrayListOf("全部", "综合类大学", "学院", "混校", "国际预科学院", "职业技术学院", "语言学院", "女校")).apply {
            setOnDismissListener {
                tabBeans[operationPosition].checked = false
            }
        }
    }
    private val schoolPopup by lazy {
        SingleChoosePopup(tab, arrayListOf("不限", "私立", "公立")).apply {
            setOnDismissListener {
                tabBeans[operationPosition].checked = false
            }
        }
    }

    override fun initData() {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_liu_xue, arrayListOf(1, 2, 3, 4, 5, 6)) { v, _, _ ->
            v.to_zixun.setOnClickListener {
                mStartActivity(LiuXueZiXunActivity::class.java)
            }
        }
        initTab()
    }

    override fun initListener() {
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                p0?.apply {
                    tabBeans[position].checked = tabBeans[position].checked.not()
                    if (tabBeans[position].checked)
                        when (position) {
                            0 -> areaPopup.showAsDropDown(tab)
                            1 -> typePopup.showAsDropDown(tab)
                            2 -> schoolPopup.showAsDropDown(tab)
                        }
                    operationPosition = position
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
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_17, tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            tab.addTab(tab.newTab().setCustomView(binding.root))
        }
    }
}
