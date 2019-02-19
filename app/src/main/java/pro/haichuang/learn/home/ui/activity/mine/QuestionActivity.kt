package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.item_question.view.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity


@ContentView(R.layout.activity_question)
class QuestionActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "常见问题"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_question, arrayListOf(1, 2, 3)) { v, _, _ ->
            v.question_child.adapter = CommonAdapter(layoutInflater, R.layout.item_question_child, arrayListOf(1, 2, 3, 4, 5))
            v.question_child.setOnItemClickListener { _, _, position, _ ->
                mStartActivity(HelpActivity::class.java)
            }
        }
    }

}
