package pro.haichuang.learn.home.ui.activity.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_height_school_search.*
import pro.haichuang.learn.home.BR
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemCompareModel
import pro.haichuang.learn.home.ui.dialog.GridMultiplePopup


@ContentView(R.layout.activity_height_school_search)
class HeightSchoolSearchActivity : BaseActivity() {
    private val tabBeans by lazy { arrayListOf(TabBean("院校级别"), TabBean("院校类型"), TabBean("所在地"), TabBean("选择层次")) }
    private val gridPopup by lazy { GridMultiplePopup(tab) }
    private val data by lazy {
        arrayListOf(ItemCompareModel(),
                ItemCompareModel(), ItemCompareModel(), ItemCompareModel(), ItemCompareModel())
    }

    override fun initData() {
        initTab()
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_height_school_search, data)
    }

    override fun initListener() {
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(HeightSchoolDetailsActivity::class.java)
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                p0?.apply {
                    tabBeans[position].checked = tabBeans[position].checked.not()
                    if (tabBeans[position].checked)
                        gridPopup.show(position)
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
