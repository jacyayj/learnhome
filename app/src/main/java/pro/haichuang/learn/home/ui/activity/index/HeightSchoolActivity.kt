package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import com.jacy.kit.config.mStartActivity
import kotlinx.android.synthetic.main.activity_height_school.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.ui.activity.news.NewsDetailsActivity
import pro.haichuang.learn.home.ui.dialog.GridMultiplePopup
import pro.haichuang.learn.home.ui.fragment.itemview.ItemNews


@ContentView(R.layout.activity_height_school)
class HeightSchoolActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "高校单招"
        listView.adapter = CommonAdapter(layoutInflater, R.layout.item_find_other, arrayListOf(ItemNews(), ItemNews(), ItemNews(), ItemNews(), ItemNews(), ItemNews()))
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
