package pro.haichuang.learn.home.ui.activity.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.*
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_zhao_sheng_plan.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.ui.dialog.DoubleChoosePopup
import pro.haichuang.learn.home.ui.dialog.GridMultiplePopup
import pro.haichuang.learn.home.ui.dialog.MultipleChoosePopup
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_zhao_sheng_plan)
class ZhaoShengPlanActivity : BaseActivity() {
    private val tabBeans by lazy { arrayListOf(TabBean("选择层次"), TabBean("所在地"), TabBean("高级查询")) }
    private val provincePopup by lazy {
        GridMultiplePopup(tab) {
            province = it
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[1].checked = false
            }
        }
    }
    private val vipPopup by lazy {
        DoubleChoosePopup(tab) {
            isNew = it
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[2].checked = false
            }
        }
    }
    private val piCiPopup by lazy {
        MultipleChoosePopup(tab) {
            batches = it
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[0].checked = false
            }
        }
    }

    private var isNew = false
    private var province = ""
    private var batches = ""
    private var queryCollegeName = ""

    private var chooseCount = 0

    private val adapter by lazy { CommonAdapter<CollegeModel>(layoutInflater, R.layout.item_zhao_sheng_plan) }

    override fun initData() {
        initTab()
        listView.adapter = adapter
        pageUrl = Url.College.EnrollList
        fetchPageData()
    }

    private fun countChecked(checked: Boolean, position: Int) {
        if (checked) {
            if (chooseCount > 2) {
                toast("最多选择${chooseCount}项进行对比")
                adapter.getItem(position).checked = false
            } else
                chooseCount++
        } else
            chooseCount--
    }

    override fun initListener() {
        cancel.setOnClickListener {
            adapter.data.forEach {
                it.compare = false
            }
            compare_view.gone()
            search_btn.show()
            search_view.show()
        }
        confirm.setOnClickListener {
            if (chooseCount < 2) {
                toast("至少选择2项进行对比")
                return@setOnClickListener
            }
            adapter.data.forEach {
                it.compare = false
            }
            var ids = ""
            adapter.data.filter { it.checked }.forEach {
                ids += "${it.id},"
            }
            compare_view.gone()
            search_btn.show()
            search_view.show()
            mStartActivity(SchoolCompareActivity::class.java, Pair(Constants.COMPARE_IDS, ids))
        }
        compare.setOnClickListener {
            adapter.data.forEach {
                it.compare = true
            }
            compare_view.show()
            search_btn.gone()
            search_view.gone()
        }
        search_btn.setOnClickListener {
            queryCollegeName = search_input.text.toString()
            fetchPageData()
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            val item = adapter.getItem(position)
            if (item.compare) {
                item.checked = item.checked.not()
                countChecked(item.checked, position)
            } else
                mStartActivity(SchoolDetailsActivity::class.java, Pair(Constants.SCHOOL_ID, item.id))
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                p0?.apply {
                    tabBeans[position].checked = tabBeans[position].checked.not()
                    if (tabBeans[position].checked)
                        when (position) {
                            0 -> piCiPopup.showAsDropDown(tab)
                            1 -> provincePopup.show(2)
                            2 -> vipPopup.show(0)
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

    override fun onRefresh(refreshLayout: RefreshLayout) {
        queryCollegeName = ""
        search_input.setText("")
        super.onRefresh(refreshLayout)
    }

    override fun onSuccess(url: String, result: Any?) {
        GsonUtil.parseRows(result, CollegeModel::class.java).list?.let {
            dealRows(adapter, it)
        }
    }

    override fun setPageParams(pageParams: HttpParams) {
        if (batches.isNotEmpty())
            pageParams.put("batches", batches)
        else
            pageParams.remove("batches")
        if (province.isNotEmpty())
            pageParams.put("province", province)
        else
            pageParams.remove("province")
        if (queryCollegeName.isNotEmpty())
            pageParams.put("queryCollegeName", queryCollegeName)
        else
            pageParams.remove("queryCollegeName")
        if (isNew)
            pageParams.put("isNew", "true")
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
