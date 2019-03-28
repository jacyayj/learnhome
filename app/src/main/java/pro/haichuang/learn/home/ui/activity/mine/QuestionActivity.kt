package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.item_question.view.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.config.Constants
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.mine.itemmodel.FqaModel
import pro.haichuang.learn.home.utils.GsonUtil


@ContentView(R.layout.activity_question)
class QuestionActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "常见问题"

        post(Url.Fqa.List)
    }

    override fun onSuccess(url: String, result: Any?) {
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_question, GsonUtil.parseArray(result, FqaModel::class.java)) { v, t, i ->
            t.fqas?.let {
                it.forEach {
                    it.question = "${i + 1}、${it.question}"
                }
                v.question_child.adapter = CommonAdapter(layoutInflater, R.layout.item_question_child, it)
                v.question_child.setOnItemClickListener { _, _, p, _ ->
                    mStartActivity(HelpActivity::class.java, Pair(Constants.QUESTION_ID, it[p].id))
                }
            }
        }
    }
}
