package pro.haichuang.learn.home.ui.fragment.index

import android.os.Bundle
import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.gone
import com.jacy.kit.config.show
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.fragment_zhi_yuan_major.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.ui.activity.index.itemmodel.MajorModel
import pro.haichuang.learn.home.utils.GsonUtil

@ContentView(R.layout.fragment_zhi_yuan_major)
class ZhiYuanMajorFragment : ZhiYuanFragment() {

    private var queryName = ""
    private var collegeName = ""
    private var majorName = ""
    private var isChooseMajor = false

    override fun initData() {
        pageUrl = Url.Major.List
        listView.adapter = adapter
        fetchPageData()
    }

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.clear()
        if (isChooseMajor) {
            pageParams.put("batch", batch.toString())
            pageParams.put("score", mScore.toString())
            pageParams.put("subject", subject.toString())
            if (collegeName.isEmpty())
                pageParams.remove("collegeName")
            else
                pageParams.put("collegeName", collegeName)
            if (majorName.isEmpty())
                pageParams.remove("majorName")
            else
                pageParams.put("majorName", majorName)
        } else {
            pageParams.put("subject", batch.toString())
            pageParams.put("level", if (batch == 4) "2" else "1")
            if (queryName.isEmpty())
                pageParams.remove("queryName")
            else
                pageParams.put("queryName", queryName)
        }
    }

    override fun onSuccess(url: String, result: Any?) {
        when (url) {
            Url.Major.List -> {
                val majors = GsonUtil.parseArray(result, MajorModel::class.java)
                majors.forEach { it.subject = subject }
                listView2.adapter = CommonAdapter(layoutInflater, R.layout.item_zhiyuan_details2, majors) { v, t, _ ->
                    v.setOnClickListener {
                        isChooseMajor = true
                        majorName = t.majorName
                        queryName = ""
                        pageUrl = Url.Judge.College
                        listView2.gone()
                        listView.show()
                        search_input.hint = "搜索学校名称/代码"
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
        do_search.setOnClickListener {
            if (search_input.text.isEmpty()) {
                toast("请输入${search_input.hint}")
                return@setOnClickListener
            }
            if (isChooseMajor)
                collegeName = search_input.text.toString()
            else
                queryName = search_input.text.toString()
            fetchPageData()
        }
    }

    companion object {

        fun getInstance(subject: Int, mScore: Int, batch: Int): ZhiYuanMajorFragment {
            val instance = ZhiYuanMajorFragment()
            val bundle = Bundle()
            bundle.putInt(Constants.JUDGE_SUBJECT, subject)
            bundle.putInt(Constants.JUDGE_SCORE, mScore)
            bundle.putInt(Constants.JUDGE_BATCH, batch)
            instance.arguments = bundle
            return instance
        }
    }
}
