package pro.haichuang.learn.home.ui.fragment.index

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.TabLayout
import com.jacy.kit.config.ContentView
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.fragment_zhi_yuan_college.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.Constants.JUDGE_BATCH
import pro.haichuang.learn.home.config.Constants.JUDGE_SCORE
import pro.haichuang.learn.home.config.Constants.JUDGE_SUBJECT
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.ui.dialog.GridMultiplePopup
import pro.haichuang.learn.home.utils.GsonUtil

@ContentView(R.layout.fragment_zhi_yuan_college)
class ZhiYuanCollegeFragment : ZhiYuanFragment() {

    private val tabBeans by lazy { arrayListOf(TabBean("全部"), TabBean("院校类型")) }

    private var collegeType = ""
    private var province = ""

    private val typePopup by lazy {
        GridMultiplePopup(tab) { it, _ ->
            collegeType = it
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[1].checked = false
            }
        }
    }
    private val provincePopup by lazy {
        GridMultiplePopup(tab) { id, name ->
            province = id
            tabBeans[0].text = name
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[0].checked = false
            }
        }
    }

    override fun initData() {
        initTab()
        pageUrl = Url.Judge.College
        listView.adapter = adapter
        fetchPageData()
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("batch", batch.toString())
        pageParams.put("score", mScore.toString())
        pageParams.put("subject", subject.toString())
        if (collegeType.isEmpty())
            pageParams.remove("collegeType")
        else
            pageParams.put("collegeType", collegeType)
        if (province.isEmpty())
            pageParams.remove("province")
        else
            pageParams.put("province", province)
    }

    override fun initListener() {
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                if (p0?.position == 0) provincePopup.show(2) else typePopup.show(1)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
            }
        })
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Judge.College -> GsonUtil.parseRows(result, CollegeModel::class.java).list?.let {
                it.forEach {
                    it.mScore = mScore
                }
                dealRows(adapter, it)
            }
        }
    }

    private fun initTab() {
        for (bean in tabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_17, tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            tab.addTab(tab.newTab().setCustomView(binding.root))
        }
    }

    companion object {

        fun getInstance(subject: Int, mScore: Int, batch: Int): ZhiYuanCollegeFragment {
            val instance = ZhiYuanCollegeFragment()
            val bundle = Bundle()
            bundle.putInt(JUDGE_SUBJECT, subject)
            bundle.putInt(JUDGE_SCORE, mScore)
            bundle.putInt(JUDGE_BATCH, batch)
            instance.arguments = bundle
            return instance
        }
    }
}
