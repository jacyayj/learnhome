package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_xin_li.*
import kotlinx.android.synthetic.main.item_xinli.view.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity


@ContentView(R.layout.activity_xin_li)
class XinLiActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "心理舒压"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_xinli, arrayListOf(1, 2, 3, 4, 5, 6, 7, 8)) { v, _, i ->
            v.to_teacher.setOnClickListener {
                mStartActivity(TeacherDetailsActivity::class.java)
            }
        }
    }
}
