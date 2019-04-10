package pro.haichuang.learn.home.ui.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.toast
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.fragment_zhuan_ye.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.NameId
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.config.Constants.ZHUAN_YE_ID
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.ZhuanYeDetailsActivity
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemZuanYeModel
import pro.haichuang.learn.home.ui.dialog.InputPopup
import pro.haichuang.learn.home.ui.dialog.LegendDialog
import pro.haichuang.learn.home.ui.dialog.ZhuanYePopup
import pro.haichuang.learn.home.utils.GsonUtil

@ContentView(R.layout.fragment_zhuan_ye)
class ZhuanYeFragment : BaseFragment() {

    private val typePopup by lazy {
        ZhuanYePopup(tab) { code, i, name ->
            mainMajor = code
            tabBeans[1].text = "选择学科"
            if (i == -1) {
                subMajor = ""
                tabBeans[0].text = "选择分类"
                tabBeans[0].checked = true
                fetchPageData()
            } else {
                tabBeans[0].text = name
                tab.setScrollPosition(1, 0f, true)
                tabBeans[1].checked = true
                types[i].child?.let { subjectPopup.show(it, true) }
            }
        }.apply {
            setOnDismissListener {
                tabBeans[0].checked = false
            }
        }
    }

    private val subjectPopup by lazy {
        ZhuanYePopup(tab) { code, i, name ->
            subMajor = code
            tabBeans[1].text = name
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[1].checked = false
            }
        }
    }

    private var mainMajor = ""

    private var subMajor = ""

    private var queryName = ""

    private val inputPopup by lazy {
        InputPopup(tab) {
            queryName = it
            fetchPageData()
        }
    }

    private val tabBeans by lazy { arrayListOf(TabBean("选择门类"), TabBean("选择学科"), TabBean("输入关键字")) }

    private lateinit var types: ArrayList<NameId>

    private val level by lazy { arguments?.getInt("level") ?: -1 }

    private val adapter by lazy { CommonAdapter<ItemZuanYeModel>(layoutInflater, R.layout.item_zuanye_search) }

    override fun initData() {
        initTab()
        listView.adapter = adapter
        pageUrl = Url.Major.List
        post(Url.Major.Category, HttpParams("level", level.toString()))
        fetchPageData()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        queryName = ""
        inputPopup.clear()
        super.onRefresh(refreshLayout)
    }

    override fun initListener() {
        legend.setOnClickListener {
            LegendDialog(context!!).show(1)
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(ZhuanYeDetailsActivity::class.java, Pair(ZHUAN_YE_ID, adapter.getItem(position).id))
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                val position = p0?.position ?: 0
                tabBeans[position].checked = true
                when (position) {
                    0 -> {
                        if (::types.isInitialized)
                            typePopup.show(types)
                        else
                            post(Url.Major.Category, HttpParams("level", level.toString()))
                    }
                    1 ->
                        if (mainMajor.isEmpty()) {
                            toast("请先选择分类")
                            tabBeans[position].checked = tabBeans[position].checked.not()
                        } else
                            subjectPopup.show()
                    2 -> inputPopup.show()
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                tabBeans[p0?.position ?: 0].checked = false
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val position = p0?.position
                when (position) {
                    0 -> {
                        if (::types.isInitialized)
                            typePopup.show(types)
                        else
                            post(Url.Major.Category, HttpParams("level", level.toString()))
                    }
                    1 ->
                        if (mainMajor.isEmpty()) {
                            toast("请先选择分类")
                            tabBeans[position].checked = tabBeans[position].checked.not()
                        } else
                            subjectPopup.show()
                    2 -> inputPopup.show()
                }
            }
        })
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("level", level.toString())
        if (mainMajor.isNotEmpty())
            pageParams.put("mainMajor", mainMajor)
        else
            pageParams.remove("mainMajor")
        if (subMajor.isNotEmpty())
            pageParams.put("subMajor", subMajor)
        else
            pageParams.remove("subMajor")
        if (queryName.isNotEmpty())
            pageParams.put("queryName", queryName)
        else
            pageParams.remove("queryName")
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Major.Category -> types = GsonUtil.parseArray(result, NameId::class.java)
            Url.Major.List -> dealRows(adapter, GsonUtil.parseArray(result, ItemZuanYeModel::class.java))
        }

    }

    private fun initTab() {
        for (bean in tabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_16, tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            tab.addTab(tab.newTab().setCustomView(binding.root))
        }
    }

    companion object {
        fun newInstance(level: Int): ZhuanYeFragment {
            val arg = Bundle()
            arg.putInt("level", level)
            return ZhuanYeFragment().apply {
                arguments = arg
            }
        }
    }

}
