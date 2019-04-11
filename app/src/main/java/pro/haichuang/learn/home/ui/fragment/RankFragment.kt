package pro.haichuang.learn.home.ui.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.gone
import com.jacy.kit.config.mStartActivity
import com.jacy.kit.config.show
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.fragment_rank.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseFragment
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.ZhuanYeDetailsActivity
import pro.haichuang.learn.home.ui.activity.index.itemmodel.ItemZuanYeModel
import pro.haichuang.learn.home.ui.dialog.DoubleChoosePopup
import pro.haichuang.learn.home.ui.dialog.InputPopup
import pro.haichuang.learn.home.utils.GsonUtil

@ContentView(R.layout.fragment_rank)
class RankFragment : BaseFragment() {

    private val zuanyePopup by lazy { DoubleChoosePopup(tab) }

    private val tabBeans by lazy { arrayListOf(TabBean("选择查询项"), TabBean("输入关键字")) }
    private val inputPopup by lazy {
        InputPopup(tab) {
            queryName = it
            fetchPageData()
        }
    }

    private var mainMajor = ""
    private var queryName = ""

    private val adapter by lazy { CommonAdapter<ItemZuanYeModel>(layoutInflater, R.layout.item_zuanye_rank) }

    override fun initData() {
        initTab()
        listView.adapter = adapter
        pageUrl = Url.Major.Sort
    }
    override fun onRefresh(refreshLayout: RefreshLayout) {
        queryName = ""
        inputPopup.clear()
        super.onRefresh(refreshLayout)
    }
    override fun setPageParams(pageParams: HttpParams) {
        if (mainMajor.isNotEmpty())
            pageParams.put("mainMajor", mainMajor)
        else
            pageParams.remove("mainMajor")
        if (queryName.isNotEmpty())
            pageParams.put("queryName", queryName)
        else
            pageParams.remove("queryName")
    }

    override fun initListener() {
        fetch_result.setOnClickListener {
            fetchPageData()
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(ZhuanYeDetailsActivity::class.java)
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                p0?.apply {
                    tabBeans[position].checked = tabBeans[position].checked.not()
                    if (tabBeans[position].checked)
                        when (position) {
                            0 -> zuanyePopup.show(1)
                            1 -> inputPopup.show()
                        }
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                tabBeans[p0?.position ?: 0].checked = false
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                p0?.apply {
                    tabBeans[position].checked = tabBeans[position].checked.not()
                    if (tabBeans[position].checked)
                        when (position) {
                            0 -> zuanyePopup.show(1)
                            1 -> inputPopup.show()
                        }
                }
            }
        })
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Major.Sort -> {
                GsonUtil.parseRows(result, ItemZuanYeModel::class.java).list?.let {
                    dealRows(adapter, it)
                }
                fetch_result.gone()
                result_view.show()
            }
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
        fun newInstance(): RankFragment {
            return RankFragment()
        }
    }

}
