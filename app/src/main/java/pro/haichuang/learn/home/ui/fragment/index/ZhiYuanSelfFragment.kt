package pro.haichuang.learn.home.ui.fragment.index

import android.os.Bundle
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.toast
import com.zhouyou.http.model.HttpParams
import kotlinx.android.synthetic.main.fragment_zhi_yuan_self.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.index.itemmodel.CollegeModel
import pro.haichuang.learn.home.utils.GsonUtil

@ContentView(R.layout.fragment_zhi_yuan_self)
class ZhiYuanSelfFragment : ZhiYuanFragment() {

    private var collegeName = ""

    override fun setPageParams(pageParams: HttpParams) {
        pageParams.put("batch", batch.toString())
        pageParams.put("score", mScore.toString())
        pageParams.put("subject", subject.toString())
        if (collegeName.isEmpty())
            pageParams.remove("collegeName")
        else
            pageParams.put("collegeName", collegeName)
    }

    override fun initData() {
        pageUrl = Url.Judge.College
        fetchPageData()
        listView.adapter = adapter
    }

    override fun initListener() {
        do_search.setOnClickListener {
            if (search_input.text.isEmpty()) {
                toast("请输入${search_input.hint}")
                return@setOnClickListener
            }
            collegeName = search_input.text.toString()
            fetchPageData()
        }
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

    companion object {

        fun getInstance(subject: Int, mScore: Int, batch: Int): ZhiYuanSelfFragment {
            val instance = ZhiYuanSelfFragment()
            val bundle = Bundle()
            bundle.putInt(Constants.JUDGE_SUBJECT, subject)
            bundle.putInt(Constants.JUDGE_SCORE, mScore)
            bundle.putInt(Constants.JUDGE_BATCH, batch)
            instance.arguments = bundle
            return instance
        }
    }
}
