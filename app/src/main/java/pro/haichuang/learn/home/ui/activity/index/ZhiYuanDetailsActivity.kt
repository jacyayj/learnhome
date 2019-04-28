package pro.haichuang.learn.home.ui.activity.index

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.design.widget.TabLayout
import android.view.View
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.*
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.activity_zhiyuan_details.*
import kotlinx.android.synthetic.main.item_zhiyuan_details.view.*
import pro.haichuang.learn.home.BR
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.bean.TabBean
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.config.Constants.COLLEGE_ID
import pro.haichuang.learn.home.config.Constants.JUDGE_BATCH_STR
import pro.haichuang.learn.home.config.Constants.JUDGE_SUBJECT
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.ui.activity.index.itemmodel.MajorModel
import pro.haichuang.learn.home.ui.dialog.*
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_zhiyuan_details)
class ZhiYuanDetailsActivity : BaseActivity() {

    private val mScore by lazy { intent.getIntExtra(Constants.JUDGE_SCORE, -1) }
    private val batch by lazy { intent.getIntExtra(Constants.JUDGE_BATCH, -1) }
    private val batchStr by lazy { intent.getStringExtra(Constants.JUDGE_BATCH_STR) }
    private val subject by lazy { intent.getIntExtra(Constants.JUDGE_SUBJECT, -1) }
    private val isDifference by lazy { intent.getBooleanExtra(Constants.JUDGE_IS_DIFFERENCE, false) }

    private val tabBeans by lazy { arrayListOf(TabBean("四川省"), TabBean("院校类型")) }
    private val zhiyuanDialog by lazy {
        ZhiYuanResultDialog(this,{ mStartActivity(ZhiYuanResultActivity::class.java) }) {id->
            adapter.data.find { it.id == id}?.let {
                it.checked = false
                it.zhiyuan = "填报为"
            }
        }
    }
    private var temp: CollegeModel? = null
    private val popup by lazy {
        ZhiYuanPopup(layoutInflater) { zhiyuan ->
            adapter.data.find { it.zhiyuan == zhiyuan }?.let {
                NoticeDialog(this) {
                    temp?.zhiyuan = zhiyuan
                    temp?.checked = true
                    it.zhiyuan = "填报为"
                    it.checked = false
                }.show("提示", "${zhiyuan}当前选择的是${it.collegeName},是否替换？")
            } ?: let {
                temp?.zhiyuan = zhiyuan
                temp?.checked = true
            }
            temp?.choosed = false
        }
    }

    private val typePopup by lazy {
        GridMultiplePopup(sub_tab) {
            collegeType = it
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[1].checked = false
            }
        }
    }
    private val provincePopup by lazy {
        GridMultiplePopup(sub_tab) {
            province = it
            fetchPageData()
        }.apply {
            setOnDismissListener {
                tabBeans[0].checked = false
            }
        }
    }
    private val adapter by lazy {
        CommonAdapter<CollegeModel>(layoutInflater, R.layout.item_zhiyuan_details) { v, t, _ ->
            v.to_choose_zhuanye.setOnClickListener {
                temp = t
                mStartActivityForResult(ZhiYuanZhuanYeActivity::class.java, 0x01, Pair(COLLEGE_ID, t.id), Pair(JUDGE_SUBJECT, subject))
            }
            v.to_details.setOnClickListener {
                mStartActivity(ZhiYuanSchoolActivity::class.java)
            }
        }
    }

    private var collegeType = ""
    private var province = ""
    private var majorName = ""

    override fun initData() {
        if (isDifference)
            titleModel.title = "${if (subject == 1) "文科" else "理科"}   线差:$mScore    $batchStr"
        else
            titleModel.title = "成绩:${mScore}分 ${if (subject == 1) "文科" else "理科"}   $batchStr"
        initTab()
        pageUrl = Url.Judge.College
        post(Url.Major.List, HttpParams().apply {
            put("subject", batch.toString())
            put("level", if (batch == 4) "2" else "1")
        })
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
        if (majorName.isEmpty())
            pageParams.remove("majorName")
        else
            pageParams.put("majorName", majorName)
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Judge.Save -> {
                toast("志愿生成成功！")
                mStartActivity(ZhiYuanResultActivity::class.java, Pair(JUDGE_SUBJECT, if (subject == 1) "本科" else "专科"), Pair(JUDGE_BATCH_STR, batchStr))
            }
            Url.Major.List -> GsonUtil.parseArray(result, MajorModel::class.java).let {
                it.forEach { it.subject = subject }
                listView2.adapter = CommonAdapter(layoutInflater, R.layout.item_zhiyuan_details2, it) { v, t, _ ->
                    v.setOnClickListener {
                        majorName = t.majorName
                        listView2.gone()
                        listView.show()
                        search_view.gone()
                        sub_tab.show()
                        fetchPageData()
                    }
                }
            }
            Url.Judge.College -> GsonUtil.parseRows(result, CollegeModel::class.java).list?.let {
                it.forEach {
                    it.mScore = mScore
                    it.showMajor = majorName.isNotEmpty()
                    it.majorName = majorName
                }
                dealRows(adapter, it)
            }
        }
    }

    override fun initListener() {
        create_zhiyuan.setOnClickListener {
            val chooseData = adapter.data.filter { it.zhiyuan != "填报为" }
            if (chooseData.size < 3) {
                TitleNoticeDialog(this).show()
            } else {
                val array = JsonArray()
                val prioritys = ArrayList<Int>()
                chooseData.forEach {
                    val json = JsonObject()
                    if (it.majorIds.isEmpty()) {
                        toast("请选择院校专业")
                        return@setOnClickListener
                    }
                    json.addProperty("collegeId", it.id)
                    json.addProperty("priority", it.priority)
                    json.addProperty("isObey", it.obey)
                    json.addProperty("majorIds", it.majorIds)
                    array.add(json)
                }
                prioritys.sort()
                if (!isOrderNumebr(prioritys)) {
                    toast("选择无效，请选择连续的志愿")
                    return@setOnClickListener
                }
                post(Url.Judge.Save, HttpParams("batch", batch.toString()).apply {
                    put("score", mScore.toString())
                    put("subject", subject.toString())
                    put("volunteerText", array.toString())
                }, needSession = true)
            }
        }
        show_result.setOnClickListener {
            val chooseData = adapter.data.filter { it.zhiyuan != "填报为" }
            if (chooseData.isEmpty())
                toast("请先选择志愿")
            else
                zhiyuanDialog.show(chooseData as ArrayList<CollegeModel>)
        }
        sub_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                if (p0?.position == 0) provincePopup.show(2) else typePopup.show(1)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
            }
        })
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position) {
                    0 -> {
                        listView2.gone()
                        listView.show()
                        search_view.gone()
                        sub_tab.show()
                        adapter.data.forEach { it.showMajor = false }
                    }
                    1 -> {
                        listView2.show()
                        listView.gone()
                        search_view.show()
                        sub_tab.gone()
                        adapter.data.forEach { it.showMajor = true }
                    }
                    2 -> {
                        listView2.gone()
                        listView.show()
                        search_view.gone()
                        sub_tab.show()
                        adapter.data.forEach { it.showMajor = false }
                    }
                }
            }
        })
    }

    /**
     * 是否是连续数字
     *
     * @param numOrStr
     * @return
     */
    private fun isOrderNumebr(arr: ArrayList<Int>): Boolean {
        for (i in 0..arr.lastIndex) {
            if (i != arr.lastIndex && arr[i] + 1 != arr[i + 1])
                return false
        }
        return true
    }

    fun choose(view: View) {
        val temp = view.tag
        if (temp is CollegeModel) {
            temp.choosed = true
            this.temp = temp
            popup.show(view, temp.zhiyuan)
        }
    }

    private fun initTab() {
        for (bean in tabBeans) {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_tab_17, sub_tab, false)
            binding.setVariable(BR.item, bean)
            binding.executePendingBindings()
            sub_tab.addTab(sub_tab.newTab().setCustomView(binding.root))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                temp?.majorIds = it.getStringExtra("majorIds")
            }
        }
    }
}
