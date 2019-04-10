package pro.haichuang.learn.home.ui.activity.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_liu_xue.*
import kotlinx.android.synthetic.main.item_liu_xue.view.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.NameId
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants.COLLEGE_ID
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.ui.dialog.SingleChoosePopup
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_liu_xue)
class LiuXueActivity : BaseActivity() {
    private val tabBeans by lazy { arrayListOf(TabBean("地区"), TabBean("类别"), TabBean("学校类型")) }
    private val areaPopup by lazy {
        SingleChoosePopup(tab, arrayListOf(NameId("全世界", ""), NameId("加拿大", "CA"),
                NameId("美国", "US"), NameId("英国", "GB"), NameId("马来西亚", "MY"),
                NameId("澳大利亚", "AU"), NameId("日本", "JP"))) {
            country = it
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[0].checked = false
            }
        }
    }
    private lateinit var typePopup: SingleChoosePopup
    private val schoolPopup by lazy {
        SingleChoosePopup(tab, arrayListOf(NameId("不限", -1), NameId("公立", 1), NameId("私立", 2))) {
            collegeNature = it
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[1].checked = false
            }
        }
    }
    private val adapter by lazy {
        CommonAdapter<CollegeModel>(layoutInflater, R.layout.item_liu_xue) { v, t, _ ->
            v.to_zixun.setOnClickListener {
                mStartActivity(LiuXueZiXunActivity::class.java, Pair(COLLEGE_ID, t.id))
            }
        }
    }
    private var collegeNature = ""
    private var country = ""
    private var type = ""
    private var queryCollegeName = ""

    override fun initData() {
        listView.adapter = adapter
        initTab()
        pageUrl = Url.ForeignCollege.List
        post(Url.ForeignCollege.Type)
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
        search_input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                fetchPageData()
            }
            true
        }
        search_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                queryCollegeName = s.toString()
            }
        })
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(LiuXueDetailsActivity::class.java, Pair(COLLEGE_ID, adapter.getItem(position).id))
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.ForeignCollege.Type -> {
                typePopup = SingleChoosePopup(tab, GsonUtil.parseArray(result, NameId::class.java)) {
                    type = it
                    fetchPageData()
                }.apply {
                    setOnDismissListener {
                        tabBeans[2].checked = false
                    }
                }
                fetchPageData()
            }
            Url.ForeignCollege.List -> {
                GsonUtil.parseRows(result, CollegeModel::class.java).list?.let {
                    dealRows(adapter, it)
                }
            }
        }
    }

    override fun setPageParams(pageParams: HttpParams) {
        if (collegeNature.isNotEmpty())
            pageParams.put("collegeNature", collegeNature)
        else
            pageParams.remove("collegeNature")
        if (country.isNotEmpty())
            pageParams.put("country", country)
        else
            pageParams.remove("country")
        if (queryCollegeName.isNotEmpty())
            pageParams.put("queryCollegeName", queryCollegeName)
        else
            pageParams.remove("queryCollegeName")
        if (type.isNotEmpty())
            pageParams.put("type", type)
        else
            pageParams.remove("type")
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
