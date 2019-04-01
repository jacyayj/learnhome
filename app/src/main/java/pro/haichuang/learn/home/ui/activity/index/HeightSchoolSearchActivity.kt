package pro.haichuang.learn.home.ui.activity.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.*
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_height_school_search.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.ui.dialog.GridMultiplePopup
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_height_school_search)
class HeightSchoolSearchActivity : BaseActivity() {
    private val tabBeans by lazy { arrayListOf(TabBean("院校级别"), TabBean("院校类型"), TabBean("所在地"), TabBean("选择层次")) }
    private val levelPopup by lazy {
        GridMultiplePopup(tab) {
            level = it
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[0].checked = false
            }
        }
    }
    private val typePopup by lazy {
        GridMultiplePopup(tab) {
            type = it
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[1].checked = false
            }
        }
    }
    private val provincePopup by lazy {
        GridMultiplePopup(tab) {
            province = it
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[2].checked = false
            }
        }
    }
    private val piCiPopup by lazy {
        GridMultiplePopup(tab) {
            batch = it
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[3].checked = false
            }
        }
    }

    private val adapter by lazy { CommonAdapter<CollegeModel>(layoutInflater, R.layout.item_height_school_search) }

    private var level = ""
    private var type = ""
    private var province = ""
    private var batch = ""
    private var queryMajorName = ""

    private var chooseCount = 0

    override fun initData() {
        initTab()
        listView.adapter = adapter
        pageUrl = Url.College.List
        fetchPageData()
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.College.List -> GsonUtil.parseRows(result, CollegeModel::class.java).list?.let { dealRows(adapter, it) }
        }
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
        listView.setOnItemClickListener { _, _, position, _ ->
            val item = adapter.getItem(position)
            if (item.compare) {
                item.checked = item.checked.not()
                countChecked(item.checked, position)
            } else
                mStartActivity(HeightSchoolDetailsActivity::class.java, Pair(Constants.SCHOOL_ID, item.id))
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                p0?.apply {
                    tabBeans[position].checked = tabBeans[position].checked.not()
                    if (tabBeans[position].checked)
                        when (position) {
                            0 -> levelPopup.show(position)
                            1 -> typePopup.show(position)
                            2 -> provincePopup.show(position)
                            3 -> piCiPopup.show(position)
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
        search_btn.setOnClickListener {
            queryMajorName = search_input.text.toString()
            fetchPageData()
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        queryMajorName = ""
        search_input.setText("")
        super.onRefresh(refreshLayout)
    }

    override fun setPageParams(pageParams: HttpParams) {
        if (level.isNotEmpty())
            pageParams.put("level", level)
        else
            pageParams.remove("level")
        if (type.isNotEmpty())
            pageParams.put("type", type)
        else
            pageParams.remove("type")
        if (province.isNotEmpty())
            pageParams.put("province", province)
        else
            pageParams.remove("province")
        if (batch.isNotEmpty())
            pageParams.put("batch", batch)
        else
            pageParams.remove("batch")
        if (queryMajorName.isNotEmpty())
            pageParams.put("queryMajorName", queryMajorName)
        else
            pageParams.remove("queryMajorName")
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

    private fun initTab() {
        for (bean in tabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_14, tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            tab.addTab(tab.newTab().setCustomView(binding.root))
        }
    }
}
