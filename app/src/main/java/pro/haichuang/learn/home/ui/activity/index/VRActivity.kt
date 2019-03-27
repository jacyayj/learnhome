package pro.haichuang.learn.home.ui.activity.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_vr.*
import kotlinx.android.synthetic.main.item_vr.view.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemVrModel
import pro.haichuang.learn.home.ui.dialog.GridMultiplePopup
import pro.haichuang.learn.home.ui.dialog.MultipleChoosePopup
import pro.haichuang.learn.home.utils.DataUtils
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_vr)
class VRActivity : BaseActivity() {
    private val tabBeans by lazy { arrayListOf(TabBean("选择批次"), TabBean("所在地")) }
    private val provincePopup by lazy {
        GridMultiplePopup(tab) {
            queryProvince = it
            refresh_layout.autoRefresh()
        }
    }
    private val typePopup by lazy {
        MultipleChoosePopup(tab, DataUtils.formatPiciData()) {
            queryBatchs = it
            refresh_layout.autoRefresh()
        }
    }
    private val adapter by lazy {
        CommonAdapter<ItemVrModel>(layoutInflater, R.layout.item_vr) { v, t, _ ->
            v.to_details.setOnClickListener {
                mStartActivity(VRDetailsActivity::class.java, Pair("name", t.collegeName), Pair("url", t.viewUrl))
            }
        }
    }
    private var queryBatchs = ""
    private var queryCollegeName = ""
    private var queryProvince = ""


    override fun initData() {
        listView.adapter = adapter
        initTab()
        pageUrl = Url.VR.List
       fetchPageData()
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("queryBatchs", queryBatchs)
        pageParams.put("queryCollegeName", queryCollegeName)
        pageParams.put("queryProvince", queryProvince)
    }

    override fun initListener() {
        search_btn.setOnClickListener {
            queryCollegeName = search_input.text.toString()
            refresh_layout.autoRefresh()
        }
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
        typePopup.setOnDismissListener {
            tabBeans[0].checked = false
        }
        provincePopup.setOnDismissListener {
            tabBeans[1].checked = false
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        GsonUtil.parseRows(result, ItemVrModel::class.java).list?.let { dealRows(adapter, it) }
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
