package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_height_school.*
import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.activity.news.NewsDetailsActivity
import pro.haichuang.learn.home.ui.dialog.GridMultiplePopup


@ContentView(R.layout.activity_height_school)
class HeightSchoolActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "高校单招"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_find_other, arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    }

    override fun initListener() {
        choose_city.setOnClickListener {
            GridMultiplePopup(it).show(2)
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            mStartActivity(NewsDetailsActivity::class.java)
        }
    }
}
