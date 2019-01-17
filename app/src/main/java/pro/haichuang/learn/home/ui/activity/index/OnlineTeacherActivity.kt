package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_online_teacher.*
import kotlinx.android.synthetic.main.item_xinli.view.*
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_online_teacher)
class OnlineTeacherActivity : BaseActivity() {
    override fun initData() {
        titleModel.title = "名师在线"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_xinli, arrayListOf(1, 2, 3, 4, 5, 6, 7, 8)) { v, _, i ->
            v.to_teacher.setOnClickListener {
                mStartActivity(TeacherDetailsActivity::class.java, Pair("online", true))
            }
        }
    }
}
