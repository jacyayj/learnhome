package pro.haichuang.learn.home.ui.activity.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_vr.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.ui.dialog.MultipleChoosePopup
import pro.haichuang.learn.home.ui.dialog.GridMultiplePopup


@ContentView(R.layout.activity_vr)
class VRActivity : BaseActivity() {
    private val tabBeans by lazy { arrayListOf(TabBean("选择批次"), TabBean("所在地")) }
    private val provincePopup by lazy { GridMultiplePopup(tab) }
    private val typePopup by lazy { MultipleChoosePopup(tab, arrayListOf("本科第一批录取院校", "本科第二批录取院校", "专科录取院校")) }

    override fun initData() {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_vr, arrayListOf(1, 2, 3, 4, 5, 6))
        initTab()
    }

    override fun initListener() {
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                p0?.apply {
                    tabBeans[position].checked = tabBeans[position].checked.not()
                    if (tabBeans[position].checked)
                        when (position) {
                            0 -> typePopup.showAsDropDown(tab)
                            1 -> provincePopup.show(2)
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
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_17, tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            tab.addTab(tab.newTab().setCustomView(binding.root))
        }
    }
}
