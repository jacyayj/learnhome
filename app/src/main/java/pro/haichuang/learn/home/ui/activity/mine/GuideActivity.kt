package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_guide.*
import kotlinx.android.synthetic.main.item_question.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.FqaModel
import pro.haichuang.learn.home.utils.GsonUtil

@ContentView(R.layout.activity_guide)
class GuideActivity : BaseActivity() {
    override fun initData() {
        titleModel.title ="使用指南"
        post(Url.Guide.List)
    }

    override fun onSuccess(url: String, result: Any?) {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_question, GsonUtil.parseArray(result, FqaModel::class.java)) { v, t, i ->
            t.list?.let {
                it.forEach {
                    it.subName = "${i + 1}、${it.title}"
                }
                v.question_child.adapter = CommonAdapter(layoutInflater, R.layout.item_question_child, it)
                v.question_child.setOnItemClickListener { _, _, p, _ ->
                    mStartActivity(GuideDetailsActivity::class.java, Pair(Constants.GUIDE_ID, it[p].id))
                }
            }
        }
    }

}
